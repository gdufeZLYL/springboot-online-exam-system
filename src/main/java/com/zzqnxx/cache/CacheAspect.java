package com.zzqnxx.cache;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;

import java.lang.reflect.Method;

@Aspect
public class CacheAspect {

    @Pointcut("@annotation(com.zzqnxx.cache.QexzCacheResult)")
    public void point() {
    }

    private final Cache<String, Object> cache = Caffeine.newBuilder()
            .maximumSize(10000)
            .build();

    private final Cache<String, Long> expireCache = Caffeine.newBuilder()
            .maximumSize(10000)
            .build();

    protected Cache<String, Object> cache() {
        return cache;
    }

    protected String localKey() throws Exception {
        return "";
    }

    private static final String SPLIT = "$&$";

    private String getKey(ProceedingJoinPoint pjp) throws Exception {
        StringBuilder stringBuilder = new StringBuilder(pjp.getSignature().toString());
        for (Object arg : pjp.getArgs()) {
            stringBuilder.append(SPLIT).append(arg);
        }
//        return stringBuilder.append(SPLIT).append(localKey()).toString();
        return stringBuilder.append(SPLIT).append(localKey(pjp)).toString();
    }

    private String localKey(ProceedingJoinPoint pjp) throws Exception {
        MethodSignature signature = (MethodSignature) pjp.getSignature();
        Method method = pjp.getTarget()
                .getClass()
                .getMethod(signature.getMethod().getName(),
                        signature.getMethod().getParameterTypes());
        QexzCacheResult annotation = method.getAnnotation(QexzCacheResult.class);
        if (annotation.strategy() == QexzCacheResult.STRATEGY.ACCOUNT) {
            return localKey();
        }
        return "";
    }

    private boolean expired(ProceedingJoinPoint pjp, String key) throws NoSuchMethodException {
        MethodSignature signature = (MethodSignature) pjp.getSignature();
        Method method = pjp.getTarget()
                .getClass()
                .getMethod(signature.getMethod().getName(),
                        signature.getMethod().getParameterTypes());
        QexzCacheResult annotation = method.getAnnotation(QexzCacheResult.class);
        int expireSeconds = annotation.expireSeconds();
        Long lastTime = expireCache.getIfPresent(key);
        return lastTime == null || System.currentTimeMillis() - lastTime > expireSeconds * 1000;
    }


    /**
     * 一旦超过了过期时间，则更新缓存
     */
    @Around("point()")
    public Object around(ProceedingJoinPoint pjp) throws Throwable {
        String key = getKey(pjp);
        if (expired(pjp, key)) {
            Object res = pjp.proceed();
            cache.put(key, res);
            expireCache.put(key, System.currentTimeMillis());
            return res;
        } else {
            Object res = cache.get(key, keyString -> {
                try {
                    return pjp.proceed();
                } catch (Throwable throwable) {
                    return throwable;
                }
            });
            if (res instanceof Throwable) {
                throw (Throwable) res;
            }
            return res;
        }
    }

}

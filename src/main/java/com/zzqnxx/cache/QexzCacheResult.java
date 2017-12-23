package com.zzqnxx.cache;

import java.lang.annotation.*;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface QexzCacheResult {
    public enum STRATEGY{ACCOUNT, DEFAULT}

    String name() default "";

    int expireSeconds() default 10;

    STRATEGY strategy() default STRATEGY.DEFAULT;
}

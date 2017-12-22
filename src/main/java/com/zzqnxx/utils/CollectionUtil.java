package com.zzqnxx.utils;

import java.util.*;

/**
 * Collections工具类
 *
 * @author Qexz
 */
public class CollectionUtil {


    public static boolean isNullOrEmpty(Collection collection) {
        return collection == null || collection.isEmpty();
    }

    public static boolean notNullAndEmpty(Collection collection) {
        return collection != null && collection.isEmpty();
    }

    public static <K, V> Map<K, V> newHashMap(K key, V value) {
        Map<K, V> res = new HashMap<>();
        res.put(key, value);
        return res;
    }

    public static boolean notNullAndNotEmpty(Collection collection) {
        return !isNullOrEmpty(collection);
    }

    public static boolean leastOneNotNullAndNotEmpty(Collection... collections) {
        for (Collection collection : collections) {
            if (notNullAndNotEmpty(collection)) {
                return true;
            }
        }
        return false;
    }

    public static boolean leastOneNotEmpty(Collection... collections) {
        for (Collection collection : collections) {
            if (notNullAndNotEmpty(collection)) {
                return true;
            }
        }
        return false;
    }

    @Deprecated
    public static <T> String List2String(List<T> list, String splitChar) {
        return list2String(list, splitChar);
    }

    public static <T> String list2String(List<T> list, String splitChar) {
        if (list == null || list.isEmpty()) {
            return "";
        }

        StringBuilder sb = new StringBuilder();
        for (T t : list) {
            sb.append(t + splitChar);
        }
        return sb.substring(0, sb.length() - splitChar.length());
    }

    public static <T> String set2String(Set<T> set, String splitChar) {
        if (set == null || set.isEmpty()){
            return "";
        }

        StringBuilder sb = new StringBuilder();
        for (T t : set) {
            sb.append(t + splitChar);
        }
        return sb.substring(0, sb.length() - splitChar.length());
    }

    public static List<String> string2list(String str, String splitChar) {
        String[] arr = str.split(splitChar);
        List<String> result = new ArrayList<>();
        for (String a : arr) {
            result.add(a);
        }
        return result;
    }

    public static List<Integer> string2intList(String str, String splitChar) {
        String[] arr = str.split(splitChar);
        List<Integer> result = new ArrayList<>();
        for (String a : arr) {
            result.add(Integer.parseInt(a));
        }
        return result;
    }

    public static boolean sortedDesc(double[] doubles) {
        int len = doubles.length;
        if (len <= 1) {
            return true;
        }
        for (int i = 1; i < doubles.length; i++) {
            if (doubles[i] < doubles[i - 1]) {
                return false;
            }
        }
        return true;
    }

    public static boolean sortedAsc(double[] doubles) {
        int len = doubles.length;
        if (len <= 1) {
            return true;
        }
        for (int i = 1; i < doubles.length; i++) {
            if (doubles[i] > doubles[i - 1]) {
                return false;
            }
        }
        return true;
    }


    public static int binarySearch(double[] ranges, double key) {
        int index = Arrays.binarySearch(ranges, key);
        if (index < 0) {
            return -index - 1;
        } else {
            return index;
        }
    }
}

package com.zws.utils;

import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import java.util.Collection;
import java.util.Map;

public final class ParamCheck {
    public static boolean isNull(Object object) {
        if (object != null) {
            return false;
        }
        return  true;
    }

    public static boolean isTrue(boolean expression) {
        if (!expression) {
            return false;
        }
        return  true;
    }

    public static boolean notNull(Object object) {
        if (object == null) {
            return false;
        }
        return  true;
    }

    public static boolean notEmpty(Object[] array) {
        if (ObjectUtils.isEmpty(array)) {
            return false;
        }
        return  true;
    }

    public static boolean notEmpty(Integer[] array) {
        if (ObjectUtils.isEmpty(array)) {
            return false;
        }
        return  true;
    }

    public static boolean notEmpty(Collection<?> collection) {
        if (CollectionUtils.isEmpty(collection)) {
            return false;
        }
        return  true;
    }

    public static boolean notEmpty(Map<?, ?> map) {
        if (CollectionUtils.isEmpty(map)) {
            return false;
        }
        return  true;
    }

    public static boolean notEmpty(String text) {
        if (text == null || "".equals(text.trim())) {
            return false;
        }
        return  true;
    }
}

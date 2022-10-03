package com.ieltshub.enumeration;

import com.fasterxml.jackson.annotation.JsonValue;

import java.util.HashMap;
import java.util.Map;

/**
 * Status type
 * @author tuanlt
 * @since 1.0
 */
public enum AppType {

    APP_USER(1),
    APP_ADMIN(2);

    private int value;
    private static Map<Integer, AppType> valueMapping = new HashMap<>();

    static {
        for (AppType itemType : AppType.values()) {
            valueMapping.put(itemType.getValue(), itemType);
        }
    }

    AppType(int value) {
        this.value = value;
    }

    @JsonValue
    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public static AppType parse(int value) {
        return valueMapping.get(value);
    }
}

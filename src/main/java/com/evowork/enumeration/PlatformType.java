package com.evowork.enumeration;

import com.fasterxml.jackson.annotation.JsonValue;

import java.util.HashMap;
import java.util.Map;

/**
 * Status type
 * @author tuanlt
 * @since 1.0
 */
public enum PlatformType {

    WEB(1),
    ANDROID(2),
    IOS(3);

    private int value;
    private static Map<Integer, PlatformType> valueMapping = new HashMap<>();

    static {
        for (PlatformType itemType : PlatformType.values()) {
            valueMapping.put(itemType.getValue(), itemType);
        }
    }

    PlatformType(int value) {
        this.value = value;
    }

    @JsonValue
    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public static PlatformType parse(int value) {
        return valueMapping.get(value);
    }
}

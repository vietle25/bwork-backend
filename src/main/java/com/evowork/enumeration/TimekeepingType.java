package com.evowork.enumeration;

import com.fasterxml.jackson.annotation.JsonValue;

import java.util.HashMap;
import java.util.Map;

/**
 * Status type
 * @author tuanlt
 * @since 1.0
 */
public enum TimekeepingType {

    NORMAL(1),
    OUT_OFF_WORKING_TIME(2),
    CONNECTED_TO_INCORRECT_WI_FI(3);

    private int value;
    private static Map<Integer, TimekeepingType> valueMapping = new HashMap<>();

    static {
        for (TimekeepingType itemType : TimekeepingType.values()) {
            valueMapping.put(itemType.getValue(), itemType);
        }
    }

    TimekeepingType(int value) {
        this.value = value;
    }

    @JsonValue
    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public static TimekeepingType parse(int value) {
        return valueMapping.get(value);
    }
}

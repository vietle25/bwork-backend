package com.evowork.enumeration;

import com.fasterxml.jackson.annotation.JsonValue;

import java.util.HashMap;
import java.util.Map;

/**
 * Status type
 * @author tuanlt
 * @since 1.0
 */
public enum WorkingTimeType {

    GENERAL_WORKING_TIME(1),
    SPECIFIC_WORKING_TIME_FOR_USER(2);

    private int value;
    private static Map<Integer, WorkingTimeType> valueMapping = new HashMap<>();

    static {
        for (WorkingTimeType itemType : WorkingTimeType.values()) {
            valueMapping.put(itemType.getValue(), itemType);
        }
    }

    WorkingTimeType(int value) {
        this.value = value;
    }

    @JsonValue
    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public static WorkingTimeType parse(int value) {
        return valueMapping.get(value);
    }
}

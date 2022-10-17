package com.evowork.enumeration;

import com.fasterxml.jackson.annotation.JsonValue;

import java.util.HashMap;
import java.util.Map;

/**
 * Schedule type
 * @author tuanlt
 * @since 1.0
 */
public enum ScheduleType {
	
	REPEAT(1),
    RANGE(2);
	
    private int value;
    private static Map<Integer, ScheduleType> valueMapping = new HashMap<>();

    static {
        for (ScheduleType itemType : ScheduleType.values()) {
            valueMapping.put(itemType.getValue(), itemType);
        }
    }

    ScheduleType(int value) {
        this.value = value;
    }

    @JsonValue
    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public static ScheduleType parse(int value) {
        return valueMapping.get(value);
    }
}

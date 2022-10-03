package com.ieltshub.enumeration;

import com.fasterxml.jackson.annotation.JsonValue;

import java.util.HashMap;
import java.util.Map;

/**
 * User level type
 * @author tuanlt
 * @since 1.0
 */
public enum TimeTypeType {
	
	TIME_DATE(1),
    TIME_WEEK(2),
    TIME_WEEK_MONTH(3);
	
    private int value;
    private static Map<Integer, TimeTypeType> valueMapping = new HashMap<>();

    static {
        for (TimeTypeType itemType : TimeTypeType.values()) {
            valueMapping.put(itemType.getValue(), itemType);
        }
    }

    TimeTypeType(int value) {
        this.value = value;
    }

    @JsonValue
    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public static TimeTypeType parse(int value) {
        return valueMapping.get(value);
    }
}

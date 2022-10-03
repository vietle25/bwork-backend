package com.ieltshub.enumeration;

import com.fasterxml.jackson.annotation.JsonValue;

import java.util.HashMap;
import java.util.Map;

/**
 * Status type
 * @author tuanlt
 * @since 1.0
 */
public enum StatusType {
	
	DELETE(-1),
    DRAFT(0),
    ACTIVE(1),
    SUSPENDED(2);
	
    private int value;
    private static Map<Integer, StatusType> valueMapping = new HashMap<>();

    static {
        for (StatusType itemType : StatusType.values()) {
            valueMapping.put(itemType.getValue(), itemType);
        }
    }

    StatusType(int value) {
        this.value = value;
    }

    @JsonValue
    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public static StatusType parse(int value) {
        return valueMapping.get(value);
    }
}

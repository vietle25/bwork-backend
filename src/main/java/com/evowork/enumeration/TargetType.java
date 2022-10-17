package com.evowork.enumeration;

import com.fasterxml.jackson.annotation.JsonValue;

import java.util.HashMap;
import java.util.Map;

public enum TargetType {

    ALL_CUSTOMER(1),
    HOME_CLEANING_CUSTOMER(2),
    LAUNDRY_CUSTOMER(3),
    ESSENTIAL_CUSTOMER(4),
    SPECIFIC_CUSTOMER(5);

    private int value;
    private static Map<Integer, TargetType> valueMapping = new HashMap<>();

    static {
        for (TargetType itemType : TargetType.values()) {
            valueMapping.put(itemType.getValue(), itemType);
        }
    }

    TargetType(int value) {
        this.value = value;
    }

    @JsonValue
    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public static TargetType parse(int value) {
        return valueMapping.get(value);
    }

}

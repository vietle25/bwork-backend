package com.evowork.enumeration;

import com.fasterxml.jackson.annotation.JsonValue;

import java.util.HashMap;
import java.util.Map;

/**
 * Status type
 * @author tuanlt
 * @since 1.0
 */
public enum StaffType {

    DOCTOR(1),
    NURSE(2),
    RECEPTION(3);

    private int value;
    private static Map<Integer, StaffType> valueMapping = new HashMap<>();

    static {
        for (StaffType itemType : StaffType.values()) {
            valueMapping.put(itemType.getValue(), itemType);
        }
    }

    StaffType(int value) {
        this.value = value;
    }

    @JsonValue
    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public static StaffType parse(int value) {
        return valueMapping.get(value);
    }
}

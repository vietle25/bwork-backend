package com.ieltshub.enumeration;

import com.fasterxml.jackson.annotation.JsonValue;

import java.util.HashMap;
import java.util.Map;

public enum CategoryType {

    STAFF_TYPE(1);

    private int value;

    CategoryType(int value) {
        this.value = value;
    }

    private static Map<Integer, CategoryType> valueMapping = new HashMap<>();


    static {
        for (CategoryType itemType : CategoryType.values()) {
            valueMapping.put(itemType.getValue(), itemType);
        }
    }

    @JsonValue
    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public static CategoryType parse(int value) {
        return valueMapping.get(value);
    }
}

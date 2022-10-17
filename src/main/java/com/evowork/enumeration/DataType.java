package com.evowork.enumeration;

import com.fasterxml.jackson.annotation.JsonValue;

import java.util.HashMap;
import java.util.Map;

public enum DataType {

    NUMBER(1),
    TEXT(2),
    DATE(3),
    DATE_TIME(4),
    TIME(5);

    private int value;

    DataType(int value) {
        this.value = value;
    }

    private static Map<Integer, DataType> valueMapping = new HashMap<>();


    static {
        for (DataType itemType : DataType.values()) {
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

    public static DataType parse(int value) {
        return valueMapping.get(value);
    }
}

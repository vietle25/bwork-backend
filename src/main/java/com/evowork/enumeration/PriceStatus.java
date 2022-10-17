package com.evowork.enumeration;

import com.fasterxml.jackson.annotation.JsonValue;

import java.util.HashMap;
import java.util.Map;

public enum PriceStatus {

    DRAFT(0),
    ACTIVE(1),
    STOPPED(2),
    DELETED(-1);

    private int value;

    PriceStatus(int value) {
        this.value = value;
    }

    private static Map<Integer, PriceStatus> valueMapping = new HashMap<>();

    static {
        for (PriceStatus itemType : PriceStatus.values()) {
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

    public static PriceStatus parse(int value) {
        return valueMapping.get(value);
    }
}

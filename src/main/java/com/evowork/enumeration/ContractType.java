package com.evowork.enumeration;

import com.fasterxml.jackson.annotation.JsonValue;

import java.util.HashMap;
import java.util.Map;

public enum ContractType {

    EVERY_MONTH(1),
    EVERY_3_MONTH(2),
    EVERY_6_MONTH(3);

    private int value;

    ContractType(int value) {
        this.value = value;
    }

    private static Map<Integer, ContractType> valueMapping = new HashMap<>();


    static {
        for (ContractType itemType : ContractType.values()) {
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

    public static ContractType parse(int value) {
        return valueMapping.get(value);
    }
}

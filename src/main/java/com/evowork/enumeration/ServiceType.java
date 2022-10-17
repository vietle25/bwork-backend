package com.evowork.enumeration;


import com.fasterxml.jackson.annotation.JsonValue;

import java.util.HashMap;
import java.util.Map;

/**
 * service Type
 */
public enum ServiceType {

    HOME_CLEANING(1),
    ESSENTIAL(3),
    LAUNDRY(2);

    private int value;

    ServiceType(int value) {
        this.value = value;
    }

    private static Map<Integer, ServiceType> valueMapping = new HashMap<>();


    static {
        for (ServiceType itemType : ServiceType.values()) {
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

    public static ServiceType parse(int value) {
        return valueMapping.get(value);
    }
}

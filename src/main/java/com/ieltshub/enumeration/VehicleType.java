package com.ieltshub.enumeration;

import com.fasterxml.jackson.annotation.JsonValue;

import java.util.HashMap;
import java.util.Map;

public enum VehicleType {

    MOTORBIKE(1),
    CAR(2);

    private int value;

    VehicleType(int value) {
        this.value = value;
    }

    private static Map<Integer, VehicleType> valueMapping = new HashMap<>();


    static {
        for (VehicleType itemType : VehicleType.values()) {
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

    public static VehicleType parse(int value) {
        return valueMapping.get(value);
    }

}

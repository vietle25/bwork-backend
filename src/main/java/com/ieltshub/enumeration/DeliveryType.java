package com.ieltshub.enumeration;

import com.fasterxml.jackson.annotation.JsonValue;

import java.util.HashMap;
import java.util.Map;

public enum DeliveryType {

    IN_3_5_DAY(1),
    IN_DAY(2);

    private int value;

    DeliveryType(int value) {
        this.value = value;
    }

    private static Map<Integer, DeliveryType> valueMapping = new HashMap<>();


    static {
        for (DeliveryType itemType : DeliveryType.values()) {
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

    public static DeliveryType parse(int value) {
        return valueMapping.get(value);
    }

}

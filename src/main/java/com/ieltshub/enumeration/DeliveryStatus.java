package com.ieltshub.enumeration;

import com.fasterxml.jackson.annotation.JsonValue;

import java.util.HashMap;
import java.util.Map;

public enum DeliveryStatus {

    WAITING_FOR_APPROVAL(0),
    ON_THE_WAY(1),
    DELIVERY_SUCCESS(2),
    CUSTOMER_REFUSED_TO_RECEIVE(3);

    private int value;
    private static Map<Integer, DeliveryStatus> valueMapping = new HashMap<>();

    static {
        for (DeliveryStatus itemType : DeliveryStatus.values()) {
            valueMapping.put(itemType.getValue(), itemType);
        }
    }

    DeliveryStatus(int value) {
        this.value = value;
    }

    @JsonValue
    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public static DeliveryStatus parse(int value) {
        return valueMapping.get(value);
    }

}

package com.evowork.enumeration;

import com.fasterxml.jackson.annotation.JsonValue;

import java.util.HashMap;
import java.util.Map;

public enum PaymentReceivedType {

    PAID(1),
    UNPAID(2),
    ACTIVE(3),
    INACTIVE(4),
    COMPLETE(5),
    CANCELLED(6);

    private int value;
    private static Map<Integer, PaymentReceivedType> valueMapping = new HashMap<>();

    static {
        for (PaymentReceivedType itemType : PaymentReceivedType.values()) {
            valueMapping.put(itemType.getValue(), itemType);
        }
    }

    PaymentReceivedType(int value) {
        this.value = value;
    }

    @JsonValue
    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public static PaymentReceivedType parse(int value) {
        return valueMapping.get(value);
    }

}

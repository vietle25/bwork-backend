package com.evowork.enumeration;

import com.fasterxml.jackson.annotation.JsonValue;

import java.util.HashMap;
import java.util.Map;

public enum PaymentMethodType {

    COD(1),
    VISA_MASTERCARD(3),
    BANK_TRANSFER(4);

    private int value;
    private static Map<Integer, PaymentMethodType> valueMapping = new HashMap<>();

    static {
        for (PaymentMethodType itemType : PaymentMethodType.values()) {
            valueMapping.put(itemType.getValue(), itemType);
        }
    }

    PaymentMethodType(int value) {
        this.value = value;
    }

    @JsonValue
    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public static PaymentMethodType parse(int value) {
        return valueMapping.get(value);
    }
}

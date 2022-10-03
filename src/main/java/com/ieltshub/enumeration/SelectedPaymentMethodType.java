package com.ieltshub.enumeration;

import com.fasterxml.jackson.annotation.JsonValue;

import java.util.HashMap;
import java.util.Map;

/***
 *
 * Selected payment method
 */
public enum SelectedPaymentMethodType {

    COD(1),
    LIN_CARE_CARD(2),
    VISA_MASTER_CARD(3),
    BANK_TRANSFER(4);

    private int value;

    SelectedPaymentMethodType(int value) {
        this.value = value;
    }

    private static Map<Integer, SelectedPaymentMethodType> valueMapping = new HashMap<>();


    static {
        for (SelectedPaymentMethodType itemType : SelectedPaymentMethodType.values()) {
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

    public static SelectedPaymentMethodType parse(int value) {
        return valueMapping.get(value);
    }
}

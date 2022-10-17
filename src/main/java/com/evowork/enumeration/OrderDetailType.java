package com.evowork.enumeration;

import com.fasterxml.jackson.annotation.JsonValue;

import java.util.HashMap;
import java.util.Map;

/**
 * Status Order Detail
 *
 * @author tuanlt
 * @since 1.0
 */
public enum OrderDetailType {

    EXAM_COST(1),
    MEDICINE_COST(2);

    private int value;
    private static Map<Integer, OrderDetailType> valueMapping = new HashMap<>();

    static {
        for (OrderDetailType itemType : OrderDetailType.values()) {
            valueMapping.put(itemType.getValue(), itemType);
        }
    }

    OrderDetailType(int value) {
        this.value = value;
    }

    @JsonValue
    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public static OrderDetailType parse(int value) {
        return valueMapping.get(value);
    }
}
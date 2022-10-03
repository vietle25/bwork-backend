package com.ieltshub.enumeration;

import com.fasterxml.jackson.annotation.JsonValue;

import java.util.HashMap;
import java.util.Map;

/**
 * OrderStatus
 *
 * @author tuanlt
 * @since 1.0
 */
public enum OrderStatus {

    WAITING_TO_APPROVED(0),
    APPROVED(1),
    CANCELED_BY_ADMIN(2),
    CANCELED_BY_USER(3);

    private int value;
    private static Map<Integer, OrderStatus> valueMapping = new HashMap<>();

    static {
        for (OrderStatus itemType : OrderStatus.values()) {
            valueMapping.put(itemType.getValue(), itemType);
        }
    }

    OrderStatus(int value) {
        this.value = value;
    }

    @JsonValue
    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public static OrderStatus parse(int value) {
        return valueMapping.get(value);
    }
}
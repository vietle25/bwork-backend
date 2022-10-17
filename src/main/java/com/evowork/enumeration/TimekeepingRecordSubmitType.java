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
public enum TimekeepingRecordSubmitType {

    AUTO_BY_DEVICE(1),
    WEB_ADMIN(2);

    private int value;
    private static Map<Integer, TimekeepingRecordSubmitType> valueMapping = new HashMap<>();

    static {
        for (TimekeepingRecordSubmitType itemType : TimekeepingRecordSubmitType.values()) {
            valueMapping.put(itemType.getValue(), itemType);
        }
    }

    TimekeepingRecordSubmitType(int value) {
        this.value = value;
    }

    @JsonValue
    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public static TimekeepingRecordSubmitType parse(int value) {
        return valueMapping.get(value);
    }
}
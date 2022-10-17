package com.evowork.enumeration;

import com.fasterxml.jackson.annotation.JsonValue;

import java.util.HashMap;
import java.util.Map;

/**
 * Status type
 * @author tuanlt
 * @since 1.0
 */
public enum TransferStatusType {

    NOT_PROCESSED_YET(0), // Not processed yet
    ASSIGNED(1), // Assigned shop and staff to rescue
    FINISH(2); // Transfer completed

    private int value;
    private static Map<Integer, TransferStatusType> valueMapping = new HashMap<>();

    static {
        for (TransferStatusType itemType : TransferStatusType.values()) {
            valueMapping.put(itemType.getValue(), itemType);
        }
    }

    TransferStatusType(int value) {
        this.value = value;
    }

    @JsonValue
    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public static TransferStatusType parse(int value) {
        return valueMapping.get(value);
    }
}

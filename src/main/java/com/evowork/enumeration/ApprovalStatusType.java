package com.evowork.enumeration;

import com.fasterxml.jackson.annotation.JsonValue;

import java.util.HashMap;
import java.util.Map;

/**
 * Status type
 * @author tuanlt
 * @since 1.0
 */
public enum ApprovalStatusType {

    WAITING_FOR_APPROVAL(0),
    APPROVED(1),
    DENIED(2);

    private int value;
    private static Map<Integer, ApprovalStatusType> valueMapping = new HashMap<>();

    static {
        for (ApprovalStatusType itemType : ApprovalStatusType.values()) {
            valueMapping.put(itemType.getValue(), itemType);
        }
    }

    ApprovalStatusType(int value) {
        this.value = value;
    }

    @JsonValue
    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public static ApprovalStatusType parse(int value) {
        return valueMapping.get(value);
    }
}

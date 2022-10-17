package com.evowork.enumeration;

import com.fasterxml.jackson.annotation.JsonValue;

import java.util.HashMap;
import java.util.Map;

/**
 * Status type
 * @author tuanlt
 * @since 1.0
 */
public enum MembershipCardStatus {

    DELETED(-1),
    ACTIVE(1),
    WAITING_TO_PROCESS(2);

    private int value;
    private static Map<Integer, MembershipCardStatus> valueMapping = new HashMap<>();

    static {
        for (MembershipCardStatus itemType : MembershipCardStatus.values()) {
            valueMapping.put(itemType.getValue(), itemType);
        }
    }

    MembershipCardStatus(int value) {
        this.value = value;
    }

    @JsonValue
    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public static MembershipCardStatus parse(int value) {
        return valueMapping.get(value);
    }
}

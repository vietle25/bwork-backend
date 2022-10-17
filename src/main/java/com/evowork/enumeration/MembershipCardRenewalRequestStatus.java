package com.evowork.enumeration;

import com.fasterxml.jackson.annotation.JsonValue;

import java.util.HashMap;
import java.util.Map;

/**
 * Notification Type
 *
 * @author dulx
 * @since 1.0
 */
public enum MembershipCardRenewalRequestStatus {

    DELETE(-1),
    PENDING(0),
    APPROVED(1);

    private int value;
    private static Map<Integer, MembershipCardRenewalRequestStatus> valueMapping = new HashMap<>();

    static {
        for (MembershipCardRenewalRequestStatus itemType : MembershipCardRenewalRequestStatus.values()) {
            valueMapping.put(itemType.getValue(), itemType);
        }
    }

    MembershipCardRenewalRequestStatus(int value) {
        this.value = value;
    }

    @JsonValue
    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public static MembershipCardRenewalRequestStatus parse(int value) {
        return valueMapping.get(value);
    }
}

package com.ieltshub.enumeration;

import com.fasterxml.jackson.annotation.JsonValue;

import java.util.HashMap;
import java.util.Map;

/**
 * Status type
 * @author tuanlt
 * @since 1.0
 */
public enum MembershipCardLevel {

    NO_LEVEL(0),
    STANDARD(1),
    SILVER(2),
    GOLD(3),
    DIAMOND(4);

    private int value;
    private static Map<Integer, MembershipCardLevel> valueMapping = new HashMap<>();

    static {
        for (MembershipCardLevel itemType : MembershipCardLevel.values()) {
            valueMapping.put(itemType.getValue(), itemType);
        }
    }

    MembershipCardLevel(int value) {
        this.value = value;
    }

    @JsonValue
    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public static MembershipCardLevel parse(int value) {
        return valueMapping.get(value);
    }
}

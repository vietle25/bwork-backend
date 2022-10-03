package com.ieltshub.enumeration;

import com.fasterxml.jackson.annotation.JsonValue;

import java.util.HashMap;
import java.util.Map;

/**
 * Status type
 * @author tuanlt
 * @since 1.0
 */
public enum CheckInType {

    CHECK_IN(1),
    CHECK_OUT(2);

    private int value;
    private static Map<Integer, CheckInType> valueMapping = new HashMap<>();

    static {
        for (CheckInType itemType : CheckInType.values()) {
            valueMapping.put(itemType.getValue(), itemType);
        }
    }

    CheckInType(int value) {
        this.value = value;
    }

    @JsonValue
    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public static CheckInType parse(int value) {
        return valueMapping.get(value);
    }
}

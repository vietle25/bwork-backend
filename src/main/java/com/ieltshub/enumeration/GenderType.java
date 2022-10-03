package com.ieltshub.enumeration;

import com.fasterxml.jackson.annotation.JsonValue;

import java.util.HashMap;
import java.util.Map;

/**
 * Schedule type
 * @author tuanlt
 * @since 1.0
 */
public enum GenderType {

	MALE(1),
    FEMALE(2),
    OTHER(3);

    private int value;
    private static Map<Integer, GenderType> valueMapping = new HashMap<>();

    static {
        for (GenderType itemType : GenderType.values()) {
            valueMapping.put(itemType.getValue(), itemType);
        }
    }

    GenderType(int value) {
        this.value = value;
    }

    @JsonValue
    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public static GenderType parse(int value) {
        return valueMapping.get(value);
    }
}

package com.ieltshub.enumeration;

import com.fasterxml.jackson.annotation.JsonValue;

import java.util.HashMap;
import java.util.Map;

/**
 * User type
 * @author tuanlt
 * @since 1.0
 */
public enum ReviewType {

	EXAM_DATE(1);

    private int value;
    private static Map<Integer, ReviewType> valueMapping = new HashMap<>();

    static {
        for (ReviewType itemType : ReviewType.values()) {
            valueMapping.put(itemType.getValue(), itemType);
        }
    }

    ReviewType(int value) {
        this.value = value;
    }

    @JsonValue
    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public static ReviewType parse(int value) {
        return valueMapping.get(value);
    }
}

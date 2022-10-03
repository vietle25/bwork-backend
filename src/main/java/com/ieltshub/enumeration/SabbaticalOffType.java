package com.ieltshub.enumeration;

import com.fasterxml.jackson.annotation.JsonValue;

import java.util.HashMap;
import java.util.Map;

/**
 * Status type
 * @author tuanlt
 * @since 1.0
 */
public enum SabbaticalOffType {

    FULL_WORKING_DAY_1(2),
    FULL_WORKING_DAY_2(3),
    PARTLY_WORKING_DAY(1);

    private int value;
    private static Map<Integer, SabbaticalOffType> valueMapping = new HashMap<>();

    static {
        for (SabbaticalOffType itemType : SabbaticalOffType.values()) {
            valueMapping.put(itemType.getValue(), itemType);
        }
    }

    SabbaticalOffType(int value) {
        this.value = value;
    }

    @JsonValue
    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public static SabbaticalOffType parse(int value) {
        return valueMapping.get(value);
    }
}

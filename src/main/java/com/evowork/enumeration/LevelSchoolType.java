package com.evowork.enumeration;

import com.fasterxml.jackson.annotation.JsonValue;

import java.util.HashMap;
import java.util.Map;

/**
 * Status type
 * @author tuanlt
 * @since 1.0
 */
public enum LevelSchoolType {

    NURSERY_SCHOOL(0),
    PRIMARY_SCHOOL(1),
    SECONDARY_SCHOOL(2),
    HIGH_SCHOOL(3);

    private int value;
    private static Map<Integer, LevelSchoolType> valueMapping = new HashMap<>();

    static {
        for (LevelSchoolType itemType : LevelSchoolType.values()) {
            valueMapping.put(itemType.getValue(), itemType);
        }
    }

    LevelSchoolType(int value) {
        this.value = value;
    }

    @JsonValue
    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public static LevelSchoolType parse(int value) {
        return valueMapping.get(value);
    }
}

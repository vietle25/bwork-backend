package com.ieltshub.enumeration;

import com.fasterxml.jackson.annotation.JsonValue;

import java.util.HashMap;
import java.util.Map;

public enum SchoolLevelType {

    PRIMARY_SCHOOL(1),
    SECONDARY_SCHOOL(2),
    PRIMARY_SECONDARY_SCHOOL(3),
    HIGH_SCHOOL(4),
    PRIMARY_HIGH_SCHOOL(5),
    SECONDARY_HIGH_SCHOOL(6);

    private int value;
    private static Map<Integer, SchoolLevelType> valueMapping = new HashMap<>();

    static {
        for (SchoolLevelType itemType : SchoolLevelType.values()) {
            valueMapping.put(itemType.getValue(), itemType);
        }
    }

    SchoolLevelType(int value) {
        this.value = value;
    }

    @JsonValue
    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public static SchoolLevelType parse(int value) {
        return valueMapping.get(value);
    }
}

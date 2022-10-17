package com.evowork.enumeration;

import com.fasterxml.jackson.annotation.JsonValue;

import java.util.HashMap;
import java.util.Map;

public enum VersionForceType {

    OPTIONAL(0),
    FORCE(1);

    private int value;
    private static Map<Integer, VersionForceType> valueMapping = new HashMap<>();

    static {
        for (VersionForceType itemType : VersionForceType.values()) {
            valueMapping.put(itemType.getValue(), itemType);
        }
    }

    VersionForceType(int value) {
        this.value = value;
    }

    @JsonValue
    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public static VersionForceType parse(int value) {
        return valueMapping.get(value);
    }
}

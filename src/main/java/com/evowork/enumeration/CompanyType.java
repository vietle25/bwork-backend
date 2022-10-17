package com.evowork.enumeration;

import com.fasterxml.jackson.annotation.JsonValue;

import java.util.HashMap;
import java.util.Map;

/**
 * Status type
 * @author tuanlt
 * @since 1.0
 */
public enum CompanyType {

    BASIC(1),
    ADVANCED(2);

    private int value;
    private static Map<Integer, CompanyType> valueMapping = new HashMap<>();

    static {
        for (CompanyType itemType : CompanyType.values()) {
            valueMapping.put(itemType.getValue(), itemType);
        }
    }

    CompanyType(int value) {
        this.value = value;
    }

    @JsonValue
    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public static CompanyType parse(int value) {
        return valueMapping.get(value);
    }
}

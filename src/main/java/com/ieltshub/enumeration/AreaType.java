package com.ieltshub.enumeration;

import com.fasterxml.jackson.annotation.JsonValue;

import java.util.HashMap;
import java.util.Map;

/**
 * Area Type
 *
 * @author tuanlt
 * @since 1.0
 */
public enum AreaType {

    PROVINCE(1),
    DISTRICT(2),
    WARD(3);

    private int value;
    private static Map<Integer, AreaType> valueMapping = new HashMap<>();

    static {
        for (AreaType itemType : AreaType.values()) {
            valueMapping.put(itemType.getValue(), itemType);
        }
    }

    AreaType(int value) {
        this.value = value;
    }

    @JsonValue
    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public static AreaType parse(int value) {
        return valueMapping.get(value);
    }
}

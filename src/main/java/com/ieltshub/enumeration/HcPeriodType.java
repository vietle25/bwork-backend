package com.ieltshub.enumeration;

import com.fasterxml.jackson.annotation.JsonValue;

import java.util.HashMap;
import java.util.Map;

/**
 * Hc Period Type
 */
public enum HcPeriodType {

    DAY(1),
    WEEK(2),
    MONTH(3);

    private int value;

    HcPeriodType(int value) {
        this.value = value;
    }

    private static Map<Integer, HcPeriodType> valueMapping = new HashMap<>();

    static {
        for (HcPeriodType itemType : HcPeriodType.values()) {
            valueMapping.put(itemType.getValue(), itemType);
        }
    }

    @JsonValue
    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public static HcPeriodType parse(int value) {
        return valueMapping.get(value);
    }
}

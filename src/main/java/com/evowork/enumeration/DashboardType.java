package com.evowork.enumeration;

import com.fasterxml.jackson.annotation.JsonValue;

import java.util.HashMap;
import java.util.Map;

/**
 * Status type
 * @author tuanlt
 * @since 1.0
 */
public enum DashboardType {

    CHECK_IN(1),
    LATE_FOR_WORKING(2),
    NOT_CHECK_IN(3);

    private int value;
    private static Map<Integer, DashboardType> valueMapping = new HashMap<>();

    static {
        for (DashboardType itemType : DashboardType.values()) {
            valueMapping.put(itemType.getValue(), itemType);
        }
    }

    DashboardType(int value) {
        this.value = value;
    }

    @JsonValue
    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public static DashboardType parse(int value) {
        return valueMapping.get(value);
    }
}

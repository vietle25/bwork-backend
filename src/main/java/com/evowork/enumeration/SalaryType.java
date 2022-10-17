package com.evowork.enumeration;

import com.fasterxml.jackson.annotation.JsonValue;

import java.util.HashMap;
import java.util.Map;

public enum SalaryType {

    TEMPORARY_SALARY(1),
    BONUS(2),
    FINE(3),
    WORKING_HOURS(4),
    LACK_TIME(5),
    WORKDAYS(6),
    TOTAL_PLAN_WORKING_HOURS(7);

    private int value;

    SalaryType(int value) {
        this.value = value;
    }

    private static Map<Integer, com.evowork.enumeration.SalaryType> valueMapping = new HashMap<>();


    static {
        for (com.evowork.enumeration.SalaryType itemType : com.evowork.enumeration.SalaryType.values()) {
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

    public static com.evowork.enumeration.SalaryType parse(int value) {
        return valueMapping.get(value);
    }
}
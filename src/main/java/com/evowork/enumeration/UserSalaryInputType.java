package com.evowork.enumeration;

import com.fasterxml.jackson.annotation.JsonValue;

import java.util.HashMap;
import java.util.Map;

public enum UserSalaryInputType {

    HOURS(1),
    DAY(2),
    MONTH(3);

    private int value;

    UserSalaryInputType(int value) {
        this.value = value;
    }

    private static Map<Integer, UserSalaryInputType> valueMapping = new HashMap<>();


    static {
        for (UserSalaryInputType itemType : UserSalaryInputType.values()) {
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

    public static UserSalaryInputType parse(int value) {
        return valueMapping.get(value);
    }
}
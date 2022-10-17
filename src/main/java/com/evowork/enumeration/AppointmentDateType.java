package com.evowork.enumeration;

import com.fasterxml.jackson.annotation.JsonValue;

import java.util.HashMap;
import java.util.Map;

public enum AppointmentDateType {

    UPCOMING(1),
    HISTORY(2);

    private int value;

    AppointmentDateType(int value) {
        this.value = value;
    }

    private static Map<Integer, AppointmentDateType> valueMapping = new HashMap<>();


    static {
        for (AppointmentDateType itemType : AppointmentDateType.values()) {
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

    public static AppointmentDateType parse(int value) {
        return valueMapping.get(value);
    }
}

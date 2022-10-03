package com.ieltshub.enumeration;

import com.fasterxml.jackson.annotation.JsonValue;

import java.util.HashMap;
import java.util.Map;

public enum  AppointmentType {

    EXAMINATION(1),
    RE_EXAMINATION(2);

    private int value;

    AppointmentType(int value) {
        this.value = value;
    }

    private static Map<Integer, AppointmentType> valueMapping = new HashMap<>();


    static {
        for (AppointmentType itemType : AppointmentType.values()) {
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

    public static AppointmentType parse(int value) {
        return valueMapping.get(value);
    }
}

package com.ieltshub.enumeration;

import com.fasterxml.jackson.annotation.JsonValue;

import java.util.HashMap;
import java.util.Map;

public enum DoingStatusType {

    NEW_ASSIGN(0),
    IN_PROGRESS(1),
    COMPLETED(2);

    private int value;

    DoingStatusType(int value) {
        this.value = value;
    }

    private static Map<Integer, DoingStatusType> valueMapping = new HashMap<>();


    static {
        for (DoingStatusType itemType : DoingStatusType.values()) {
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

    public static DoingStatusType parse(int value) {
        return valueMapping.get(value);
    }
}

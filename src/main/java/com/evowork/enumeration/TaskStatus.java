package com.evowork.enumeration;

import com.fasterxml.jackson.annotation.JsonValue;

import java.util.HashMap;
import java.util.Map;

public enum TaskStatus {

    NEW(1),
    COMPLETED(2),
    CANCEL(3);

    private int value;
    private static Map<Integer, TaskStatus> valueMapping = new HashMap<>();

    static {
        for (TaskStatus itemType : TaskStatus.values()) {
            valueMapping.put(itemType.getValue(), itemType);
        }
    }

    TaskStatus(int value) {
        this.value = value;
    }

    @JsonValue
    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public static TaskStatus parse(int value) {
        return valueMapping.get(value);
    }
}

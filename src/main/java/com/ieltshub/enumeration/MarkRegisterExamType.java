package com.ieltshub.enumeration;

import com.fasterxml.jackson.annotation.JsonValue;

import java.util.HashMap;
import java.util.Map;

public enum MarkRegisterExamType {

    WRITING(1),
    SPEAKING(2),
    WRITING_SPEAKING(3);

    private int value;
    private static Map<Integer, MarkRegisterExamType> valueMapping = new HashMap<>();

    static {
        for (MarkRegisterExamType itemType : MarkRegisterExamType.values()) {
            valueMapping.put(itemType.getValue(), itemType);
        }
    }

    MarkRegisterExamType(int value) {
        this.value = value;
    }

    @JsonValue
    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public static MarkRegisterExamType parse(int value) {
        return valueMapping.get(value);
    }
}

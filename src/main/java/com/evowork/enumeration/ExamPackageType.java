package com.evowork.enumeration;

import com.fasterxml.jackson.annotation.JsonValue;

import java.util.HashMap;
import java.util.Map;

public enum ExamPackageType {

    TEST_LEARN(1),
    TEST(2),
    LEARN(3);

    private int value;
    private static Map<Integer, ExamPackageType> valueMapping = new HashMap<>();

    static {
        for (ExamPackageType itemType : ExamPackageType.values()) {
            valueMapping.put(itemType.getValue(), itemType);
        }
    }

    ExamPackageType(int value) {
        this.value = value;
    }

    @JsonValue
    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public static ExamPackageType parse(int value) {
        return valueMapping.get(value);
    }
}

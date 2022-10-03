package com.ieltshub.enumeration;

import com.fasterxml.jackson.annotation.JsonValue;

import java.util.HashMap;
import java.util.Map;

/**
 * User type
 * @author tuanlt
 * @since 1.0
 */
public enum ExamRegistrationStatusType {

	NOT_YET(1),
    WAITING_RESULT(2),
    DONE(3);

    private int value;
    private static Map<Integer, ExamRegistrationStatusType> valueMapping = new HashMap<>();

    static {
        for (ExamRegistrationStatusType itemType : ExamRegistrationStatusType.values()) {
            valueMapping.put(itemType.getValue(), itemType);
        }
    }

    ExamRegistrationStatusType(int value) {
        this.value = value;
    }

    @JsonValue
    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public static ExamRegistrationStatusType parse(int value) {
        return valueMapping.get(value);
    }
}

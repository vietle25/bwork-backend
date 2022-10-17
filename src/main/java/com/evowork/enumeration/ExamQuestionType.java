package com.evowork.enumeration;

import com.fasterxml.jackson.annotation.JsonValue;

import java.util.HashMap;
import java.util.Map;

/**
 * Exam question type
 * @author tuanlt
 * @since 1.0
 */
public enum ExamQuestionType {
	
	GENERAL_TRAINING(1),
    ACADEMIC(2);
	
    private int value;
    private static Map<Integer, ExamQuestionType> valueMapping = new HashMap<>();

    static {
        for (ExamQuestionType itemType : ExamQuestionType.values()) {
            valueMapping.put(itemType.getValue(), itemType);
        }
    }

    ExamQuestionType(int value) {
        this.value = value;
    }

    @JsonValue
    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public static ExamQuestionType parse(int value) {
        return valueMapping.get(value);
    }
}

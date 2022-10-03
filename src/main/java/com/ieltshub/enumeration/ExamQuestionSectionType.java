package com.ieltshub.enumeration;

import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonValue;

/**
 * Exam question type
 * @author tuanlt
 * @since 1.0
 */
public enum ExamQuestionSectionType {
	
	LISTENING(1),
    READING(2),
    WRITING(3),
    SPEAKING(4),
	ENGLISH_STAR(5);

    private int value;
    private static Map<Integer, ExamQuestionSectionType> valueMapping = new HashMap<>();

    static {
        for (ExamQuestionSectionType itemType : ExamQuestionSectionType.values()) {
            valueMapping.put(itemType.getValue(), itemType);
        }
    }

    ExamQuestionSectionType(int value) {
        this.value = value;
    }

    @JsonValue
    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public static ExamQuestionSectionType parse(int value) {
        return valueMapping.get(value);
    }
}

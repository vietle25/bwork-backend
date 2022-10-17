package com.evowork.enumeration;

import com.fasterxml.jackson.annotation.JsonValue;

import java.util.HashMap;
import java.util.Map;

/**
 * User type
 * @author tuanlt
 * @since 1.0
 */
public enum QuestionAnswerType {

	FORM_COMPLETION_LISTENING(101),
    PLAN_MAP_DIAGRAM_LABELING_LISTENING(102),
	TABLE_COMPLETION_LISTENING(103),
    FLOW_CHART_COMPLETION_LISTENING(104),
    SENTENCE_COMPLETION_LISTENING(105),
    SUMMARY_COMPLETION_LISTENING(106),
    SHORT_ANSWER_QUESTION_LISTENING(107),
    MULTIPLE_CHOICE_LISTENING(108),
    MATCHING_LISTENING(109),
    NOTE_COMPLETION_LISTENING(110),

    SENTENCE_COMPLETION_READING(201),
    SHORT_ANSWER_QUESTION_READING(202),
    TABLE_COMPLETION_READING(203),
    NOTE_COMPLETION_READING(204),
    FLOW_CHART_COMPLETION_READING(205),
    DIAGRAM_LABELING_READING(206),
    SUMMARY_COMPLETION_READING(207),
    MATCHING_INFORMATION_READING(208),
    IDENTIFYING_INFORMATION_READING(209),
    IDENTIFYING_A_WRITERS_VIEW_CLAIMS_READING(210),
    MULTIPLE_CHOICE_READING(211),
    MATCHING_SENTENCE_ENDINGS_READING(212),
    MATCHING_HEADINGS_READING(213),
    MATCHING_FEATURES_READING(214);

    private int value;
    private static Map<Integer, QuestionAnswerType> valueMapping = new HashMap<>();

    static {
        for (QuestionAnswerType itemType : QuestionAnswerType.values()) {
            valueMapping.put(itemType.getValue(), itemType);
        }
    }

    QuestionAnswerType(int value) {
        this.value = value;
    }

    @JsonValue
    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public static QuestionAnswerType parse(int value) {
        return valueMapping.get(value);
    }
}

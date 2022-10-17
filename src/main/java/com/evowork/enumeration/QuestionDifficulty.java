package com.evowork.enumeration;

import com.fasterxml.jackson.annotation.JsonValue;

import java.util.HashMap;
import java.util.Map;

/**
 * question difficulty
 * 
 * @author tuannd
 * @date Aug 28, 2018
 * @since 1.0
 */
public enum QuestionDifficulty {
	EASY(1),
    MEDIUM(2),
    UPPER_MEDIUM(3),
    HARD(4);

	private int value;
	private static Map<Integer, QuestionDifficulty> valueMapping = new HashMap<>();

	static {
		for (QuestionDifficulty itemType : QuestionDifficulty.values()) {
			valueMapping.put(itemType.getValue(), itemType);
		}
	}

	QuestionDifficulty(int value) {
		this.value = value;
	}

	@JsonValue
	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}

	public static QuestionDifficulty parse(int value) {
		return valueMapping.get(value);
	}
}

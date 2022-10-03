package com.ieltshub.enumeration.converter;

import com.ieltshub.enumeration.QuestionDifficulty;

import javax.persistence.AttributeConverter;

/**
 * @author tuannd
 * @date 29/08/2018
 * @since 1.0
 */
public class QuestionDifficultyConverter implements AttributeConverter<QuestionDifficulty, Integer> {
	
    @Override
    public Integer convertToDatabaseColumn(QuestionDifficulty itemType) {
        return itemType != null ? itemType.getValue() : null;
    }

    @Override
    public QuestionDifficulty convertToEntityAttribute(Integer value) {
        return value == null ? null : QuestionDifficulty.parse(value);
    }
}

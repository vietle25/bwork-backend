package com.evowork.enumeration.converter;

import javax.persistence.AttributeConverter;

import com.evowork.enumeration.ExamQuestionType;

/**
 * @author tuannd
 * @date 14/05/2016
 * @since 1.0
 */
public class ExamQuestionTypeConverter implements AttributeConverter<ExamQuestionType, Integer> {
	
    @Override
    public Integer convertToDatabaseColumn(ExamQuestionType itemType) {
        return itemType != null ? itemType.getValue() : null;
    }

    @Override
    public ExamQuestionType convertToEntityAttribute(Integer value) {
        return value == null ? null : ExamQuestionType.parse(value);
    }
}

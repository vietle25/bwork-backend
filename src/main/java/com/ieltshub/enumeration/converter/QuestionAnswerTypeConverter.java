package com.ieltshub.enumeration.converter;

import javax.persistence.AttributeConverter;

import com.ieltshub.enumeration.QuestionAnswerType;

/**
 * @author tuannd
 * @date 14/05/2016
 * @since 1.0
 */
public class QuestionAnswerTypeConverter implements AttributeConverter<QuestionAnswerType, Integer> {
	
    @Override
    public Integer convertToDatabaseColumn(QuestionAnswerType itemType) {
        return itemType != null ? itemType.getValue() : null;
    }

    @Override
    public QuestionAnswerType convertToEntityAttribute(Integer value) {
        return value == null ? null : QuestionAnswerType.parse(value);
    }
}

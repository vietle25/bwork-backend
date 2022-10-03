package com.ieltshub.enumeration.converter;

import javax.persistence.AttributeConverter;

import com.ieltshub.enumeration.ExamQuestionSectionType;

/**
 * @author tuannd
 * @date 14/05/2016
 * @since 1.0
 */
public class ExamQuestionSectionTypeConverter implements AttributeConverter<ExamQuestionSectionType, Integer> {

	@Override
	public Integer convertToDatabaseColumn(ExamQuestionSectionType itemType) {
		return itemType != null ? itemType.getValue() : null;
	}

	@Override
	public ExamQuestionSectionType convertToEntityAttribute(Integer value) {
		return value == null ? null : ExamQuestionSectionType.parse(value);
	}
}

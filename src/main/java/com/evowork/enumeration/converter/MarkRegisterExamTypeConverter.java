package com.evowork.enumeration.converter;

import com.evowork.enumeration.MarkRegisterExamType;

import javax.persistence.AttributeConverter;

public class MarkRegisterExamTypeConverter implements AttributeConverter<MarkRegisterExamType, Integer> {

    @Override
    public Integer convertToDatabaseColumn(MarkRegisterExamType markRegisterExamType) {
        return markRegisterExamType != null ? markRegisterExamType.getValue() : null;
    }

    @Override
    public MarkRegisterExamType convertToEntityAttribute(Integer integer) {
        return integer == null ? null : MarkRegisterExamType.parse(integer);
    }
}

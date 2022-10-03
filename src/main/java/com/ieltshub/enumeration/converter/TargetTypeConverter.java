package com.ieltshub.enumeration.converter;

import com.ieltshub.enumeration.TargetType;

import javax.persistence.AttributeConverter;

public class TargetTypeConverter implements AttributeConverter<TargetType, Integer> {

    @Override
    public Integer convertToDatabaseColumn(TargetType targetType) {
        return targetType != null ? targetType.getValue() : null;
    }

    @Override
    public TargetType convertToEntityAttribute(Integer value) {
        return value == null ? null : TargetType.parse(value);
    }
}

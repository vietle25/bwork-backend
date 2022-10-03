package com.ieltshub.enumeration.converter;

import com.ieltshub.enumeration.CheckInType;

import javax.persistence.AttributeConverter;

public class CheckInTypeConverter implements AttributeConverter<CheckInType, Integer> {

    @Override
    public Integer convertToDatabaseColumn(CheckInType itemType) {
        return itemType != null ? itemType.getValue() : null;
    }

    @Override
    public CheckInType convertToEntityAttribute(Integer value) {
        return value == null ? null : CheckInType.parse(value);
    }
}

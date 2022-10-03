package com.ieltshub.enumeration.converter;

import com.ieltshub.enumeration.SabbaticalOffType;

import javax.persistence.AttributeConverter;

public class SabbaticalOffTypeConverter implements AttributeConverter<SabbaticalOffType, Integer> {

    @Override
    public Integer convertToDatabaseColumn(SabbaticalOffType itemType) {
        return itemType != null ? itemType.getValue() : null;
    }

    @Override
    public SabbaticalOffType convertToEntityAttribute(Integer value) {
        return value == null ? null : SabbaticalOffType.parse(value);
    }
}

package com.evowork.enumeration.converter;

import com.evowork.enumeration.TimekeepingType;

import javax.persistence.AttributeConverter;

public class TimekeepingTypeConverter implements AttributeConverter<TimekeepingType, Integer> {

    @Override
    public Integer convertToDatabaseColumn(TimekeepingType itemType) {
        return itemType != null ? itemType.getValue() : null;
    }

    @Override
    public TimekeepingType convertToEntityAttribute(Integer value) {
        return value == null ? null : TimekeepingType.parse(value);
    }
}

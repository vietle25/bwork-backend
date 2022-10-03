package com.ieltshub.enumeration.converter;

import com.ieltshub.enumeration.RescueStatusType;

import javax.persistence.AttributeConverter;

public class RescueStatusTypeConverter implements AttributeConverter<RescueStatusType, Integer> {

    @Override
    public Integer convertToDatabaseColumn(RescueStatusType itemType) {
        return itemType != null ? itemType.getValue() : null;
    }

    @Override
    public RescueStatusType convertToEntityAttribute(Integer value) {
        return value == null ? null : RescueStatusType.parse(value);
    }
}

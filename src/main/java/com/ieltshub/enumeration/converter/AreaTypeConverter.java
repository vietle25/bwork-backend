package com.ieltshub.enumeration.converter;

import com.ieltshub.enumeration.AreaType;

import javax.persistence.AttributeConverter;

public class AreaTypeConverter implements AttributeConverter<AreaType, Integer> {

    @Override
    public Integer convertToDatabaseColumn(AreaType itemType) {
        return itemType != null ? itemType.getValue() : null;
    }

    @Override
    public AreaType convertToEntityAttribute(Integer value) {
        return value == null ? null : AreaType.parse(value);
    }
}

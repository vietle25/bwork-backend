package com.evowork.enumeration.converter;

import com.evowork.enumeration.PlatformType;

import javax.persistence.AttributeConverter;

public class PlatformTypeConverter implements AttributeConverter<PlatformType, Integer> {

    @Override
    public Integer convertToDatabaseColumn(PlatformType itemType) {
        return itemType != null ? itemType.getValue() : null;
    }

    @Override
    public PlatformType convertToEntityAttribute(Integer value) {
        return value == null ? null : PlatformType.parse(value);
    }
}

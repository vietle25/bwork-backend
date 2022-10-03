package com.ieltshub.enumeration.converter;

import com.ieltshub.enumeration.AppType;

import javax.persistence.AttributeConverter;

public class AppTypeConverter implements AttributeConverter<AppType, Integer> {

    @Override
    public Integer convertToDatabaseColumn(AppType itemType) {
        return itemType != null ? itemType.getValue() : null;
    }

    @Override
    public AppType convertToEntityAttribute(Integer value) {
        return value == null ? null : AppType.parse(value);
    }
}

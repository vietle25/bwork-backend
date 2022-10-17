package com.evowork.enumeration.converter;

import com.evowork.enumeration.BannerType;

import javax.persistence.AttributeConverter;

public class BannerTypeConverter implements AttributeConverter<BannerType, Integer> {

    @Override
    public Integer convertToDatabaseColumn(BannerType itemType) {
        return itemType != null ? itemType.getValue() : null;
    }

    @Override
    public BannerType convertToEntityAttribute(Integer value) {
        return value == null ? null : BannerType.parse(value);
    }
}

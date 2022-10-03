package com.ieltshub.enumeration.converter;

import com.ieltshub.enumeration.ActionBannerType;

import javax.persistence.AttributeConverter;

public class ActionBannerTypeConverter implements AttributeConverter<ActionBannerType, Integer> {

    @Override
    public Integer convertToDatabaseColumn(ActionBannerType itemType) {
        return itemType != null ? itemType.getValue() : null;
    }

    @Override
    public ActionBannerType convertToEntityAttribute(Integer value) {
        return value == null ? null : ActionBannerType.parse(value);
    }
}

package com.ieltshub.enumeration.converter;

import com.ieltshub.enumeration.GenderType;
import com.ieltshub.enumeration.UserType;

import javax.persistence.AttributeConverter;

/**
 * @author tuannd
 * @date 14/05/2016
 * @since 1.0
 */
public class GenderTypeConverter implements AttributeConverter<GenderType, Integer> {
	
    @Override
    public Integer convertToDatabaseColumn(GenderType itemType) {
        return itemType != null ? itemType.getValue() : null;
    }

    @Override
    public GenderType convertToEntityAttribute(Integer value) {
        return value == null ? null : GenderType.parse(value);
    }
}

package com.ieltshub.enumeration.converter;

import com.ieltshub.enumeration.FormalType;

import javax.persistence.AttributeConverter;

/**
 * @author tuannd
 * @date 14/05/2016
 * @since 1.0
 */
public class FormalTypeConverter implements AttributeConverter<FormalType, Integer> {
	
    @Override
    public Integer convertToDatabaseColumn(FormalType itemType) {
        return itemType != null ? itemType.getValue() : null;
    }

    @Override
    public FormalType convertToEntityAttribute(Integer value) {
        return value == null ? null : FormalType.parse(value);
    }
}

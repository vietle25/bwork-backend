package com.ieltshub.enumeration.converter;

import javax.persistence.AttributeConverter;

import com.ieltshub.enumeration.TimeTypeType;

/**
 * @author tuannd
 * @date 14/05/2016
 * @since 1.0
 */
public class TimeTypeTypeConverter implements AttributeConverter<TimeTypeType, Integer> {
	
    @Override
    public Integer convertToDatabaseColumn(TimeTypeType itemType) {
        return itemType != null ? itemType.getValue() : null;
    }

    @Override
    public TimeTypeType convertToEntityAttribute(Integer value) {
        return value == null ? null : TimeTypeType.parse(value);
    }
}

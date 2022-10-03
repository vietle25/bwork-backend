package com.ieltshub.enumeration.converter;

import javax.persistence.AttributeConverter;

import com.ieltshub.enumeration.ScheduleType;

/**
 * @author tuannd
 * @date 14/05/2016
 * @since 1.0
 */
public class ScheduleTypeConverter implements AttributeConverter<ScheduleType, Integer> {
	
    @Override
    public Integer convertToDatabaseColumn(ScheduleType itemType) {
        return itemType != null ? itemType.getValue() : null;
    }

    @Override
    public ScheduleType convertToEntityAttribute(Integer value) {
        return value == null ? null : ScheduleType.parse(value);
    }
}

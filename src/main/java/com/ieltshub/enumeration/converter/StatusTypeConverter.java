package com.ieltshub.enumeration.converter;

import javax.persistence.AttributeConverter;

import com.ieltshub.enumeration.StatusType;

/**
 * @author tuannd
 * @date 14/05/2016
 * @since 1.0
 */
public class StatusTypeConverter implements AttributeConverter<StatusType, Integer> {
	
    @Override
    public Integer convertToDatabaseColumn(StatusType itemType) {
        return itemType != null ? itemType.getValue() : null;
    }

    @Override
    public StatusType convertToEntityAttribute(Integer value) {
        return value == null ? null : StatusType.parse(value);
    }
}

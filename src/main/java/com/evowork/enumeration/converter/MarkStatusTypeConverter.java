package com.evowork.enumeration.converter;

import com.evowork.enumeration.MarkStatusType;

import javax.persistence.AttributeConverter;

/**
 * @author tuannd
 * @date 14/05/2016
 * @since 1.0
 */
public class MarkStatusTypeConverter implements AttributeConverter<MarkStatusType, Integer> {
	
    @Override
    public Integer convertToDatabaseColumn(MarkStatusType itemType) {
        return itemType != null ? itemType.getValue() : null;
    }

    @Override
    public MarkStatusType convertToEntityAttribute(Integer value) {
        return value == null ? null : MarkStatusType.parse(value);
    }
}

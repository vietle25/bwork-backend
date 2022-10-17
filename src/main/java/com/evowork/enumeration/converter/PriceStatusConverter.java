package com.evowork.enumeration.converter;

import com.evowork.enumeration.PriceStatus;

import javax.persistence.AttributeConverter;

/**
 * @author tuannd
 * @date 14/05/2016
 * @since 1.0
 */
public class PriceStatusConverter implements AttributeConverter<PriceStatus, Integer> {
	
    @Override
    public Integer convertToDatabaseColumn(PriceStatus itemType) {
        return itemType != null ? itemType.getValue() : null;
    }

    @Override
    public PriceStatus convertToEntityAttribute(Integer value) {
        return value == null ? null : PriceStatus.parse(value);
    }
}

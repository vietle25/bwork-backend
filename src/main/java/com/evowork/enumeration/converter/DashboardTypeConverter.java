package com.evowork.enumeration.converter;

import com.evowork.enumeration.DashboardType;

import javax.persistence.AttributeConverter;

/**
 * @author tuannd
 * @date 14/05/2016
 * @since 1.0
 */
public class DashboardTypeConverter implements AttributeConverter<DashboardType, Integer> {
	
    @Override
    public Integer convertToDatabaseColumn(DashboardType itemType) {
        return itemType != null ? itemType.getValue() : null;
    }

    @Override
    public DashboardType convertToEntityAttribute(Integer value) {
        return value == null ? null : DashboardType.parse(value);
    }
}

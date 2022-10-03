package com.ieltshub.enumeration.converter;

import com.ieltshub.enumeration.SortSellingVehicleType;

import javax.persistence.AttributeConverter;

/**
 * @author tuannd
 * @date 14/05/2016
 * @since 1.0
 */
public class SortSellingVehicleConverter implements AttributeConverter<SortSellingVehicleType, Integer> {
	
    @Override
    public Integer convertToDatabaseColumn(SortSellingVehicleType itemType) {
        return itemType != null ? itemType.getValue() : 0;
    }

    @Override
    public SortSellingVehicleType convertToEntityAttribute(Integer value) {
        return value == null ? SortSellingVehicleType.parse(0) : SortSellingVehicleType.parse(value);
    }
}

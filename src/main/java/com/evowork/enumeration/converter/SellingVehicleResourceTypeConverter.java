package com.evowork.enumeration.converter;

import com.evowork.enumeration.SellingVehicleResourceType;

import javax.persistence.AttributeConverter;

/**
 * @author tuannd
 * @date 14/05/2016
 * @since 1.0
 */
public class SellingVehicleResourceTypeConverter implements AttributeConverter<SellingVehicleResourceType, Integer> {
	
    @Override
    public Integer convertToDatabaseColumn(SellingVehicleResourceType itemType) {
        return itemType != null ? itemType.getValue() : 0;
    }

    @Override
    public SellingVehicleResourceType convertToEntityAttribute(Integer value) {
        return value == null ? SellingVehicleResourceType.parse(0) : SellingVehicleResourceType.parse(value);
    }
}

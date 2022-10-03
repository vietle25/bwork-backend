package com.ieltshub.enumeration.converter;

import com.ieltshub.enumeration.SellingVehicleDisplayOrderType;

import javax.persistence.AttributeConverter;

/**
 * @author tuannd
 * @date 14/05/2016
 * @since 1.0
 */
public class SellingVehicleDisplayOrderTypeConverter implements AttributeConverter<SellingVehicleDisplayOrderType, Integer> {
	
    @Override
    public Integer convertToDatabaseColumn(SellingVehicleDisplayOrderType itemType) {
        return itemType != null ? itemType.getValue() : 0;
    }

    @Override
    public SellingVehicleDisplayOrderType convertToEntityAttribute(Integer value) {
        return value == null ? SellingVehicleDisplayOrderType.parse(0) : SellingVehicleDisplayOrderType.parse(value);
    }
}

package com.ieltshub.enumeration.converter;

import com.ieltshub.enumeration.SellingVehicleResourceKind;

import javax.persistence.AttributeConverter;

/**
 * @author tuannd
 * @date 14/05/2016
 * @since 1.0
 */
public class SellingVehicleResourceKindConverter implements AttributeConverter<SellingVehicleResourceKind, Integer> {
	
    @Override
    public Integer convertToDatabaseColumn(SellingVehicleResourceKind itemType) {
        return itemType != null ? itemType.getValue() : 0;
    }

    @Override
    public SellingVehicleResourceKind convertToEntityAttribute(Integer value) {
        return value == null ? SellingVehicleResourceKind.parse(0) : SellingVehicleResourceKind.parse(value);
    }
}

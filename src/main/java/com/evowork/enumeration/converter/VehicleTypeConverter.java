package com.evowork.enumeration.converter;

import com.evowork.enumeration.VehicleType;

import javax.persistence.AttributeConverter;

public class VehicleTypeConverter implements AttributeConverter<VehicleType, Integer> {

    @Override
    public Integer convertToDatabaseColumn(VehicleType vehicleType) {
        return vehicleType != null ? vehicleType.getValue() : null;
    }

    @Override
    public VehicleType convertToEntityAttribute(Integer integer) {
        return integer == null ? null : VehicleType.parse(integer);
    }
}

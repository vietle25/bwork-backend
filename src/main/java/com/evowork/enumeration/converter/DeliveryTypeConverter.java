package com.evowork.enumeration.converter;

import com.evowork.enumeration.DeliveryType;

import javax.persistence.AttributeConverter;

public class DeliveryTypeConverter implements AttributeConverter<DeliveryType, Integer> {

    @Override
    public Integer convertToDatabaseColumn(DeliveryType deliveryType) {
        return deliveryType != null ? deliveryType.getValue() : null;
    }

    @Override
    public DeliveryType convertToEntityAttribute(Integer integer) {
        return integer == null ? null : DeliveryType.parse(integer);
    }
}

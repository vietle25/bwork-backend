package com.evowork.enumeration.converter;

import com.evowork.enumeration.DeliveryStatus;

import javax.persistence.AttributeConverter;

public class DeliveryStatusConverter implements AttributeConverter<DeliveryStatus, Integer> {


    @Override
    public Integer convertToDatabaseColumn(DeliveryStatus deliveryStatus) {
        return deliveryStatus != null ? deliveryStatus.getValue() : null;
    }

    @Override
    public DeliveryStatus convertToEntityAttribute(Integer integer) {
        return integer == null ? null : DeliveryStatus.parse(integer);
    }
}

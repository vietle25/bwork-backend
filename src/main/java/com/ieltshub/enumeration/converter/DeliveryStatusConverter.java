package com.ieltshub.enumeration.converter;

import com.ieltshub.enumeration.DeliveryStatus;

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

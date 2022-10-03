package com.ieltshub.enumeration.converter;

import com.ieltshub.enumeration.OrderStatus;

import javax.persistence.AttributeConverter;

public class OrderStatusConverter implements AttributeConverter<OrderStatus, Integer> {

    @Override
    public Integer convertToDatabaseColumn(OrderStatus itemType) {
        return itemType != null ? itemType.getValue() : null;
    }

    @Override
    public OrderStatus convertToEntityAttribute(Integer value) {
        return value == null ? null : OrderStatus.parse(value);
    }
}

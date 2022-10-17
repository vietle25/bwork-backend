package com.evowork.enumeration.converter;

import com.evowork.enumeration.OrderStatus;

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

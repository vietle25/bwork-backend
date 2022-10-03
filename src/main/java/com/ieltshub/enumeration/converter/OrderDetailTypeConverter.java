package com.ieltshub.enumeration.converter;

import com.ieltshub.enumeration.OrderDetailType;

import javax.persistence.AttributeConverter;

/**
 * @author dulx
 * @date 2019
 * @since 1.0
 */
public class OrderDetailTypeConverter implements AttributeConverter<OrderDetailType, Integer> {

    @Override
    public Integer convertToDatabaseColumn(OrderDetailType itemType) {
        return itemType != null ? itemType.getValue() : null;
    }

    @Override
    public OrderDetailType convertToEntityAttribute(Integer value) {
        return value == null ? null : OrderDetailType.parse(value);
    }
}

package com.evowork.enumeration.converter;

import com.evowork.enumeration.PaymentMethodType;

import javax.persistence.AttributeConverter;

public class PaymentMethodTypeConverter implements AttributeConverter<PaymentMethodType, Integer> {

    @Override
    public Integer convertToDatabaseColumn(PaymentMethodType itemType) {
        return itemType != null ? itemType.getValue() : null;
    }

    @Override
    public PaymentMethodType convertToEntityAttribute(Integer value) {
        return value == null ? null : PaymentMethodType.parse(value);
    }
}
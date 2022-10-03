package com.ieltshub.enumeration.converter;

import com.ieltshub.enumeration.PaymentReceivedType;

import javax.persistence.AttributeConverter;

public class PaymentReceivedTypeConverter implements AttributeConverter<PaymentReceivedType, Integer> {


    @Override
    public Integer convertToDatabaseColumn(PaymentReceivedType paymentReceivedType) {
        return paymentReceivedType != null ? paymentReceivedType.getValue() : null;
    }

    @Override
    public PaymentReceivedType convertToEntityAttribute(Integer integer) {
        return integer == null ? null : PaymentReceivedType.parse(integer);
    }
}

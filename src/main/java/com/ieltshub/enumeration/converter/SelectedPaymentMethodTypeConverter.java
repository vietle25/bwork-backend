package com.ieltshub.enumeration.converter;

import com.ieltshub.enumeration.SelectedPaymentMethodType;

import javax.persistence.AttributeConverter;

public class SelectedPaymentMethodTypeConverter implements AttributeConverter<SelectedPaymentMethodType, Integer> {

    @Override
    public Integer convertToDatabaseColumn(SelectedPaymentMethodType selectedPaymentMethodType) {
        return selectedPaymentMethodType != null ? selectedPaymentMethodType.getValue() : null;
    }

    @Override
    public SelectedPaymentMethodType convertToEntityAttribute(Integer integer) {
        return integer == null ? null : SelectedPaymentMethodType.parse(integer);
    }
}



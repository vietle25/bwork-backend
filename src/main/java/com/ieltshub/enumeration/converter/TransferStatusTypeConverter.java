package com.ieltshub.enumeration.converter;

import com.ieltshub.enumeration.TransferStatusType;

import javax.persistence.AttributeConverter;

public class TransferStatusTypeConverter implements AttributeConverter<TransferStatusType, Integer> {

    @Override
    public Integer convertToDatabaseColumn(TransferStatusType itemType) {
        return itemType != null ? itemType.getValue() : null;
    }

    @Override
    public TransferStatusType convertToEntityAttribute(Integer value) {
        return value == null ? null : TransferStatusType.parse(value);
    }
}

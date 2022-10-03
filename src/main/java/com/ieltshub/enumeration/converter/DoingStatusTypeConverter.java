package com.ieltshub.enumeration.converter;

import com.ieltshub.enumeration.DoingStatusType;

import javax.persistence.AttributeConverter;

public class DoingStatusTypeConverter implements AttributeConverter<DoingStatusType, Integer> {

    @Override
    public Integer convertToDatabaseColumn(DoingStatusType doingStatusType) {
        return doingStatusType != null ? doingStatusType.getValue() : null;
    }

    @Override
    public DoingStatusType convertToEntityAttribute(Integer integer) {
        return integer == null ? null : DoingStatusType.parse(integer);
    }
}

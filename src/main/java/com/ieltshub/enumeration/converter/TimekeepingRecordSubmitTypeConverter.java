package com.ieltshub.enumeration.converter;

import com.ieltshub.enumeration.TimekeepingRecordSubmitType;

import javax.persistence.AttributeConverter;

public class TimekeepingRecordSubmitTypeConverter implements AttributeConverter<TimekeepingRecordSubmitType, Integer> {

    @Override
    public Integer convertToDatabaseColumn(TimekeepingRecordSubmitType markRegisterExamType) {
        return markRegisterExamType != null ? markRegisterExamType.getValue() : null;
    }

    @Override
    public TimekeepingRecordSubmitType convertToEntityAttribute(Integer integer) {
        return integer == null ? null : TimekeepingRecordSubmitType.parse(integer);
    }
}

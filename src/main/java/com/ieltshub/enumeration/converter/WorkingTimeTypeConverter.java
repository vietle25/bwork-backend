package com.ieltshub.enumeration.converter;

import com.ieltshub.enumeration.WorkingTimeType;

import javax.persistence.AttributeConverter;

public class WorkingTimeTypeConverter implements AttributeConverter<WorkingTimeType, Integer> {

    @Override
    public Integer convertToDatabaseColumn(WorkingTimeType itemType) {
        return itemType != null ? itemType.getValue() : null;
    }

    @Override
    public WorkingTimeType convertToEntityAttribute(Integer value) {
        return value == null ? null : WorkingTimeType.parse(value);
    }
}

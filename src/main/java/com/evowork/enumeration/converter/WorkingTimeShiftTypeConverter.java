package com.evowork.enumeration.converter;

import com.evowork.enumeration.WorkingTimeShiftType;

import javax.persistence.AttributeConverter;

public class WorkingTimeShiftTypeConverter implements AttributeConverter<WorkingTimeShiftType, Integer> {

    @Override
    public Integer convertToDatabaseColumn(WorkingTimeShiftType itemType) {
        return itemType != null ? itemType.getValue() : null;
    }

    @Override
    public WorkingTimeShiftType convertToEntityAttribute(Integer value) {
        return value == null ? null : WorkingTimeShiftType.parse(value);
    }
}

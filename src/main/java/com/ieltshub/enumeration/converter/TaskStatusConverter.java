package com.ieltshub.enumeration.converter;

import com.ieltshub.enumeration.TaskStatus;

import javax.persistence.AttributeConverter;

public class TaskStatusConverter implements AttributeConverter<TaskStatus, Integer> {

    @Override
    public Integer convertToDatabaseColumn(TaskStatus itemType) {
        return itemType != null ? itemType.getValue() : null;
    }

    @Override
    public TaskStatus convertToEntityAttribute(Integer value) {
        return value == null ? null : TaskStatus.parse(value);
    }
}

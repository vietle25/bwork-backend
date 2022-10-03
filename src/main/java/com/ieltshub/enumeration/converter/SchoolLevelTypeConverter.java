package com.ieltshub.enumeration.converter;

import com.ieltshub.enumeration.SchoolLevelType;

import javax.persistence.AttributeConverter;

public class SchoolLevelTypeConverter implements AttributeConverter<SchoolLevelType, Integer> {

    @Override
    public Integer convertToDatabaseColumn(SchoolLevelType schoolLevelType) {
        return schoolLevelType != null ? schoolLevelType.getValue() : null;
    }

    @Override
    public SchoolLevelType convertToEntityAttribute(Integer integer) {
        return integer == null ? null : SchoolLevelType.parse(integer);
    }
}

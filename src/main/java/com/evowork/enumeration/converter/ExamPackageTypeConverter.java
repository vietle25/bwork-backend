package com.evowork.enumeration.converter;

import com.evowork.enumeration.ExamPackageType;

import javax.persistence.AttributeConverter;

public class ExamPackageTypeConverter implements AttributeConverter<ExamPackageType, Integer> {

    @Override
    public Integer convertToDatabaseColumn(ExamPackageType examPackageType) {
        return examPackageType != null ? examPackageType.getValue() : null;
    }

    @Override
    public ExamPackageType convertToEntityAttribute(Integer integer) {
        return integer == null ? null : ExamPackageType.parse(integer);
    }
}

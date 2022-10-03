package com.ieltshub.enumeration.converter;

import com.ieltshub.enumeration.DataType;

import javax.persistence.AttributeConverter;

public class DataTypeConverter implements AttributeConverter<DataType, Integer> {
    @Override
    public Integer convertToDatabaseColumn(DataType dataType) {
        return dataType != null ? dataType.getValue() : null;
    }

    @Override
    public DataType convertToEntityAttribute(Integer integer) {
        return integer == null ? null : DataType.parse(integer);
    }
}

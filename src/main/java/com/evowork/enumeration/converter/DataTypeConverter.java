package com.evowork.enumeration.converter;

import com.evowork.enumeration.DataType;

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

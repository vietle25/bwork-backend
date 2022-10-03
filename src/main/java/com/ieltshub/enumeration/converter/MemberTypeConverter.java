package com.ieltshub.enumeration.converter;

import com.ieltshub.enumeration.MemberType;

import javax.persistence.AttributeConverter;

public class MemberTypeConverter implements AttributeConverter<MemberType, Integer> {

    @Override
    public Integer convertToDatabaseColumn(MemberType itemType) {
        return itemType != null ? itemType.getValue() : null;
    }

    @Override
    public MemberType convertToEntityAttribute(Integer value) {
        return value == null ? null : MemberType.parse(value);
    }
}

package com.ieltshub.enumeration.converter;

import com.ieltshub.enumeration.MembershipCardLevel;

import javax.persistence.AttributeConverter;

public class MembershipCardLevelConverter implements AttributeConverter<MembershipCardLevel, Integer> {

    @Override
    public Integer convertToDatabaseColumn(MembershipCardLevel itemType) {
        return itemType != null ? itemType.getValue() : null;
    }

    @Override
    public MembershipCardLevel convertToEntityAttribute(Integer value) {
        return value == null ? null : MembershipCardLevel.parse(value);
    }
}

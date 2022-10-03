package com.ieltshub.enumeration.converter;

import com.ieltshub.enumeration.MembershipCardStatus;

import javax.persistence.AttributeConverter;

public class MembershipCardStatusConverter implements AttributeConverter<MembershipCardStatus, Integer> {

    @Override
    public Integer convertToDatabaseColumn(MembershipCardStatus itemType) {
        return itemType != null ? itemType.getValue() : null;
    }

    @Override
    public MembershipCardStatus convertToEntityAttribute(Integer value) {
        return value == null ? null : MembershipCardStatus.parse(value);
    }
}

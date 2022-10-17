package com.evowork.enumeration.converter;

import com.evowork.enumeration.MembershipCardStatus;

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

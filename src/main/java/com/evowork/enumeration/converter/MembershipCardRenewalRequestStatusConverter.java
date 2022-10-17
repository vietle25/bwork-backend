package com.evowork.enumeration.converter;

import com.evowork.enumeration.MembershipCardRenewalRequestStatus;

import javax.persistence.AttributeConverter;

public class MembershipCardRenewalRequestStatusConverter implements AttributeConverter<MembershipCardRenewalRequestStatus, Integer> {

    @Override
    public Integer convertToDatabaseColumn(MembershipCardRenewalRequestStatus itemType) {
        return itemType != null ? itemType.getValue() : null;
    }

    @Override
    public MembershipCardRenewalRequestStatus convertToEntityAttribute(Integer value) {
        return value == null ? null : MembershipCardRenewalRequestStatus.parse(value);
    }
}

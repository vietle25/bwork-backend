package com.ieltshub.enumeration.converter;

import com.ieltshub.enumeration.MembershipCardRenewalRequestStatus;

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

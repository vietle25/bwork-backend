package com.ieltshub.enumeration.converter;

import com.ieltshub.enumeration.VersionForceType;

import javax.persistence.AttributeConverter;

public class VersionForceTypeConverter implements AttributeConverter<VersionForceType, Integer> {

    @Override
    public Integer convertToDatabaseColumn(VersionForceType versionForceType) {
        return versionForceType != null ? versionForceType.getValue() : null;
    }

    @Override
    public VersionForceType convertToEntityAttribute(Integer integer) {
        return integer == null ? null : VersionForceType.parse(integer);
    }
}

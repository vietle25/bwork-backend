package com.evowork.enumeration.converter;

import com.evowork.enumeration.CompanyType;

import javax.persistence.AttributeConverter;

public class CompanyTypeConverter implements AttributeConverter<CompanyType, Integer> {

    @Override
    public Integer convertToDatabaseColumn(CompanyType companyType) {
        return companyType != null ? companyType.getValue() : null;
    }

    @Override
    public CompanyType convertToEntityAttribute(Integer integer) {
        return integer == null ? null : CompanyType.parse(integer);
    }
}

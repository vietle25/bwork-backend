package com.ieltshub.enumeration.converter;

import com.ieltshub.enumeration.ContractType;

import javax.persistence.AttributeConverter;

public class ContractTypeConverter implements AttributeConverter<ContractType, Integer> {

    @Override
    public Integer convertToDatabaseColumn(ContractType contractType) {
        return contractType != null ? contractType.getValue() : null;
    }

    @Override
    public ContractType convertToEntityAttribute(Integer integer) {
        return integer == null ? null : ContractType.parse(integer);
    }
}
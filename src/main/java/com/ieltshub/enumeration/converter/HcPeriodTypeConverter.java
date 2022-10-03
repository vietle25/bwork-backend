package com.ieltshub.enumeration.converter;

import com.ieltshub.enumeration.HcPeriodType;

import javax.persistence.AttributeConverter;

public class HcPeriodTypeConverter implements AttributeConverter<HcPeriodType, Integer> {


    @Override
    public Integer convertToDatabaseColumn(HcPeriodType hcPeriodType) {
        return hcPeriodType != null ? hcPeriodType.getValue() : null;
    }

    @Override
    public HcPeriodType convertToEntityAttribute(Integer integer) {
        return integer == null ? null : HcPeriodType.parse(integer);
    }
}
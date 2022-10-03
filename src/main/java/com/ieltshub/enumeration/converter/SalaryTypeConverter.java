package com.ieltshub.enumeration.converter;

import com.ieltshub.enumeration.SalaryType;

import javax.persistence.AttributeConverter;

public class SalaryTypeConverter implements AttributeConverter<SalaryType, Integer> {

        @Override
        public Integer convertToDatabaseColumn(SalaryType salaryType) {
            return salaryType != null ? salaryType.getValue() : null;
        }

        @Override
        public SalaryType convertToEntityAttribute(Integer integer) {
            return integer == null ? null : SalaryType.parse(integer);
        }
}

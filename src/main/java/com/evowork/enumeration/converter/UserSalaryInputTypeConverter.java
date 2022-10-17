package com.evowork.enumeration.converter;

import com.evowork.enumeration.UserSalaryInputType;

import javax.persistence.AttributeConverter;

/**
 * @author tuannd
 * @date 14/05/2016
 * @since 1.0
 */
public class UserSalaryInputTypeConverter implements AttributeConverter<UserSalaryInputType, Integer> {
	
    @Override
    public Integer convertToDatabaseColumn(UserSalaryInputType itemType) {
        return itemType != null ? itemType.getValue() : null;
    }

    @Override
    public UserSalaryInputType convertToEntityAttribute(Integer value) {
        return value == null ? null : UserSalaryInputType.parse(value);
    }
}

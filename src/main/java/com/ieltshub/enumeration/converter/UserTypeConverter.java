package com.ieltshub.enumeration.converter;

import javax.persistence.AttributeConverter;

import com.ieltshub.enumeration.UserType;

/**
 * @author tuannd
 * @date 14/05/2016
 * @since 1.0
 */
public class UserTypeConverter implements AttributeConverter<UserType, Integer> {
	
    @Override
    public Integer convertToDatabaseColumn(UserType itemType) {
        return itemType != null ? itemType.getValue() : null;
    }

    @Override
    public UserType convertToEntityAttribute(Integer value) {
        return value == null ? null : UserType.parse(value);
    }
}

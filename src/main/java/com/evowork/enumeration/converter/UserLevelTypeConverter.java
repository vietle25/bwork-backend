package com.evowork.enumeration.converter;

import javax.persistence.AttributeConverter;

import com.evowork.enumeration.UserLevelType;

/**
 * @author tuannd
 * @date 14/05/2016
 * @since 1.0
 */
public class UserLevelTypeConverter implements AttributeConverter<UserLevelType, Integer> {
	
    @Override
    public Integer convertToDatabaseColumn(UserLevelType itemType) {
        return itemType != null ? itemType.getValue() : null;
    }

    @Override
    public UserLevelType convertToEntityAttribute(Integer value) {
        return value == null ? null : UserLevelType.parse(value);
    }
}

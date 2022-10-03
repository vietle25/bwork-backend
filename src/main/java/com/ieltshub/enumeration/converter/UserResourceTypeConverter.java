package com.ieltshub.enumeration.converter;

import com.ieltshub.enumeration.ResourceType;
import com.ieltshub.enumeration.UserResourceType;

import javax.persistence.AttributeConverter;


/**
 * @author tuannd
 * @date 14/05/2016
 * @since 1.0
 */
public class UserResourceTypeConverter implements AttributeConverter<UserResourceType, Integer> {

    @Override
    public Integer convertToDatabaseColumn(UserResourceType itemType) {
        return itemType != null ? itemType.getValue() : 0;
    }

    @Override
    public UserResourceType convertToEntityAttribute(Integer value) {
        return value == null ? UserResourceType.parse(0) : UserResourceType.parse(value);
    }
}

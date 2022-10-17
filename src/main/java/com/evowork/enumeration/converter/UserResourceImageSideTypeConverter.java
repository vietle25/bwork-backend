package com.evowork.enumeration.converter;

import com.evowork.enumeration.UserResourceImageSideType;

import javax.persistence.AttributeConverter;


/**
 * @author tuannd
 * @date 14/05/2016
 * @since 1.0
 */
public class UserResourceImageSideTypeConverter implements AttributeConverter<UserResourceImageSideType, Integer> {

    @Override
    public Integer convertToDatabaseColumn(UserResourceImageSideType itemType) {
        return itemType != null ? itemType.getValue() : 0;
    }

    @Override
    public UserResourceImageSideType convertToEntityAttribute(Integer value) {
        return value == null ? UserResourceImageSideType.parse(0) : UserResourceImageSideType.parse(value);
    }
}

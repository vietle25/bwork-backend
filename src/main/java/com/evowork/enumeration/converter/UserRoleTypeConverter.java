package com.evowork.enumeration.converter;

import com.evowork.enumeration.UserRoleType;

import javax.persistence.AttributeConverter;

public class UserRoleTypeConverter implements AttributeConverter<UserRoleType, Integer> {

    @Override
    public Integer convertToDatabaseColumn(UserRoleType userRoleType) {
        return userRoleType != null ? userRoleType.getValue() : 0;
    }

    @Override
    public UserRoleType convertToEntityAttribute(Integer integer) {
        return integer == null ? UserRoleType.parse(0) : UserRoleType.parse(integer);
    }
}

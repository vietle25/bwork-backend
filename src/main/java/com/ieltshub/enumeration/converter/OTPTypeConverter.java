package com.ieltshub.enumeration.converter;

import com.ieltshub.enumeration.OTPType;

import javax.persistence.AttributeConverter;

/**
 * @author tuannd
 * @date 14/05/2016
 * @since 1.0
 */
public class OTPTypeConverter implements AttributeConverter<OTPType, Integer> {
	
    @Override
    public Integer convertToDatabaseColumn(OTPType itemType) {
        return itemType != null ? itemType.getValue() : null;
    }

    @Override
    public OTPType convertToEntityAttribute(Integer value) {
        return value == null ? null : OTPType.parse(value);
    }
}

package com.ieltshub.enumeration.converter;

import com.ieltshub.enumeration.ReviewType;
import com.ieltshub.enumeration.UserType;

import javax.persistence.AttributeConverter;

/**
 * @author tuanlt
 * @since 1.0
 */
public class ReviewTypeConverter implements AttributeConverter<ReviewType, Integer> {
	
    @Override
    public Integer convertToDatabaseColumn(ReviewType itemType) {
        return itemType != null ? itemType.getValue() : null;
    }

    @Override
    public ReviewType convertToEntityAttribute(Integer value) {
        return value == null ? null : ReviewType.parse(value);
    }
}

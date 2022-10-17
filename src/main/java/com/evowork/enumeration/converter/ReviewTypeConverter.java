package com.evowork.enumeration.converter;

import com.evowork.enumeration.ReviewType;

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

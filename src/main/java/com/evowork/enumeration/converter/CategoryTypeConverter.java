package com.evowork.enumeration.converter;

import com.evowork.enumeration.CategoryType;

import javax.persistence.AttributeConverter;

public class CategoryTypeConverter implements AttributeConverter<CategoryType, Integer> {

    @Override
    public Integer convertToDatabaseColumn(CategoryType categoryType) {
        return categoryType != null ? categoryType.getValue() : null;
    }

    @Override
    public CategoryType convertToEntityAttribute(Integer integer) {
        return integer == null ? null : CategoryType.parse(integer);
    }
}
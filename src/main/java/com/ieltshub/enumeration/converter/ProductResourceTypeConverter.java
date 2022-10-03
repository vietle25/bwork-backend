package com.ieltshub.enumeration.converter;

import com.ieltshub.enumeration.ProductResourceType;

import javax.persistence.AttributeConverter;

public class ProductResourceTypeConverter implements AttributeConverter<ProductResourceType, Integer> {

    @Override
    public Integer convertToDatabaseColumn(ProductResourceType productResourceType) {
        return productResourceType != null ? productResourceType.getValue() : null;
    }

    @Override
    public ProductResourceType convertToEntityAttribute(Integer integer) {
        return integer == null ? null : ProductResourceType.parse(integer);
    }
}
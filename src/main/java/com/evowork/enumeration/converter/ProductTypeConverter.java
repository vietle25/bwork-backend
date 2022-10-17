package com.evowork.enumeration.converter;

import com.evowork.enumeration.ProductType;

import javax.persistence.AttributeConverter;

public class ProductTypeConverter implements AttributeConverter<ProductType, Integer> {

    @Override
    public Integer convertToDatabaseColumn(ProductType productType) {
        return productType != null ? productType.getValue() : null;
    }

    @Override
    public ProductType convertToEntityAttribute(Integer integer) {
        return integer == null ? null : ProductType.parse(integer);
    }
}
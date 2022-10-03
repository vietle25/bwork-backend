package com.ieltshub.enumeration.converter;

import javax.persistence.AttributeConverter;

import com.ieltshub.enumeration.ResourceType;

/**
 * @author tuannd
 * @date 14/05/2016
 * @since 1.0
 */
public class ResourceTypeConverter implements AttributeConverter<ResourceType, Integer> {
	
    @Override
    public Integer convertToDatabaseColumn(ResourceType itemType) {
        return itemType != null ? itemType.getValue() : 0;
    }

    @Override
    public ResourceType convertToEntityAttribute(Integer value) {
        return value == null ? ResourceType.parse(0) : ResourceType.parse(value);
    }
}

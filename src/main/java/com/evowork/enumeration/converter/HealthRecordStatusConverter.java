package com.evowork.enumeration.converter;

import com.evowork.enumeration.HealthRecordStatus;

import javax.persistence.AttributeConverter;

/**
 * @author tuannd
 * @date 14/05/2016
 * @since 1.0
 */
public class HealthRecordStatusConverter implements AttributeConverter<HealthRecordStatus, Integer> {

    @Override
    public Integer convertToDatabaseColumn(HealthRecordStatus itemType) {
        return itemType != null ? itemType.getValue() : null;
    }

    @Override
    public HealthRecordStatus convertToEntityAttribute(Integer value) {
        return value == null ? null : HealthRecordStatus.parse(value);
    }
}

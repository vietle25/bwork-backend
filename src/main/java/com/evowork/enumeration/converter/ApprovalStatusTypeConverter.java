package com.evowork.enumeration.converter;

import com.evowork.enumeration.ApprovalStatusType;

import javax.persistence.AttributeConverter;

/**
 * @author tuannd
 * @date 14/05/2016
 * @since 1.0
 */
public class ApprovalStatusTypeConverter implements AttributeConverter<ApprovalStatusType, Integer> {
	
    @Override
    public Integer convertToDatabaseColumn(ApprovalStatusType itemType) {
        return itemType != null ? itemType.getValue() : null;
    }

    @Override
    public ApprovalStatusType convertToEntityAttribute(Integer value) {
        return value == null ? null : ApprovalStatusType.parse(value);
    }
}

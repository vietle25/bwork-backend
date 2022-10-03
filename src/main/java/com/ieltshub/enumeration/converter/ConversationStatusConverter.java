package com.ieltshub.enumeration.converter;

import com.ieltshub.enumeration.ConversationStatus;

import javax.persistence.AttributeConverter;

/**
 * @author tuannd
 * @date 14/05/2016
 * @since 1.0
 */
public class ConversationStatusConverter implements AttributeConverter<ConversationStatus, Integer> {

    @Override
    public Integer convertToDatabaseColumn(ConversationStatus itemType) {
        return itemType != null ? itemType.getValue() : null;
    }

    @Override
    public ConversationStatus convertToEntityAttribute(Integer value) {
        return value == null ? null : ConversationStatus.parse(value);
    }
}

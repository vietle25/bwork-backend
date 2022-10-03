package com.ieltshub.enumeration.converter;

import com.ieltshub.enumeration.NotificationType;

import javax.persistence.AttributeConverter;

/**
 * @author dulx
 * @date 2019
 * @since 1.0
 */
public class NotificationTypeConverter implements AttributeConverter<NotificationType, Integer> {

    @Override
    public Integer convertToDatabaseColumn(NotificationType itemType) {
        return itemType != null ? itemType.getValue() : null;
    }

    @Override
    public NotificationType convertToEntityAttribute(Integer value) {
        return value == null ? null : NotificationType.parse(value);
    }
}

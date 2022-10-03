package com.ieltshub.enumeration.converter;

import com.ieltshub.enumeration.AppointmentDateType;

import javax.persistence.AttributeConverter;

public class AppointmentTypeDateConverter implements AttributeConverter<AppointmentDateType, Integer> {

    @Override
    public Integer convertToDatabaseColumn(AppointmentDateType appointmentType) {
        return appointmentType != null ? appointmentType.getValue() : null;
    }

    @Override
    public AppointmentDateType convertToEntityAttribute(Integer integer) {
        return integer == null ? null : AppointmentDateType.parse(integer);
    }
}

package com.ieltshub.enumeration.converter;

import com.ieltshub.enumeration.AppointmentType;

import javax.persistence.AttributeConverter;

public class AppointmentTypeConverter implements AttributeConverter<AppointmentType, Integer> {

    @Override
    public Integer convertToDatabaseColumn(AppointmentType appointmentType) {
        return appointmentType != null ? appointmentType.getValue() : null;
    }

    @Override
    public AppointmentType convertToEntityAttribute(Integer integer) {
        return integer == null ? null : AppointmentType.parse(integer);
    }
}

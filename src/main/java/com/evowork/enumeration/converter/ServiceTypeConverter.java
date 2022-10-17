package com.evowork.enumeration.converter;

import com.evowork.enumeration.ServiceType;

import javax.persistence.AttributeConverter;

public class ServiceTypeConverter implements AttributeConverter<ServiceType, Integer> {
    @Override
    public Integer convertToDatabaseColumn(ServiceType serviceType) {
        return serviceType != null ? serviceType.getValue() : null;
    }

    @Override
    public ServiceType convertToEntityAttribute(Integer integer) {
        return integer == null ? null : ServiceType.parse(integer);
    }
}

package com.ieltshub.enumeration;

import com.fasterxml.jackson.annotation.JsonValue;

import java.util.HashMap;
import java.util.Map;

/**
 * Status type
 * @author tuanlt
 * @since 1.0
 */
public enum ProductResourceType {

    IMAGE(1),
    VIDEO(2);

    private int value;
    private static Map<Integer, ProductResourceType> valueMapping = new HashMap<>();

    static {
        for (ProductResourceType itemType : ProductResourceType.values()) {
            valueMapping.put(itemType.getValue(), itemType);
        }
    }

    ProductResourceType(int value) {
        this.value = value;
    }

    @JsonValue
    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public static ProductResourceType parse(int value) {
        return valueMapping.get(value);
    }
}

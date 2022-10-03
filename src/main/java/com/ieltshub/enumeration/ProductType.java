package com.ieltshub.enumeration;

import com.fasterxml.jackson.annotation.JsonValue;

import java.util.HashMap;
import java.util.Map;

/**
 * Status type
 * @author tuanlt
 * @since 1.0
 */
public enum ProductType {

    MEDICINE(1),
    FUNCTIONAL_FOODS(2),
    MEDICAL_MATERIAL(3),
    COSMETICS(4),
    AEROSOLIZATION(5);

    private int value;
    private static Map<Integer, ProductType> valueMapping = new HashMap<>();

    static {
        for (ProductType itemType : ProductType.values()) {
            valueMapping.put(itemType.getValue(), itemType);
        }
    }

    ProductType(int value) {
        this.value = value;
    }

    @JsonValue
    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public static ProductType parse(int value) {
        return valueMapping.get(value);
    }
}

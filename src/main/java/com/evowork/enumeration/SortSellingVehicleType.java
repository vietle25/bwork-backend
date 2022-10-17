package com.evowork.enumeration;

import com.fasterxml.jackson.annotation.JsonValue;

import java.util.HashMap;
import java.util.Map;

/**
 * Status type
 * @author tuanlt
 * @since 1.0
 */
public enum SortSellingVehicleType {

    DATE_MOST_RECENT(0),
    DATE_MOST_OLD(1),
    PRICE_LOW_TO_HIGH(2),
    PRICE_HIGH_TO_LOW(3);

    private int value;
    private static Map<Integer, SortSellingVehicleType> valueMapping = new HashMap<>();

    static {
        for (SortSellingVehicleType itemType : SortSellingVehicleType.values()) {
            valueMapping.put(itemType.getValue(), itemType);
        }
    }

    SortSellingVehicleType(int value) {
        this.value = value;
    }

    @JsonValue
    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public static SortSellingVehicleType parse(int value) {
        return valueMapping.get(value);
    }
}

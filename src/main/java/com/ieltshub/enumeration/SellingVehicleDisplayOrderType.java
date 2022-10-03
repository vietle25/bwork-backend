package com.ieltshub.enumeration;

import com.fasterxml.jackson.annotation.JsonValue;

import java.util.HashMap;
import java.util.Map;

/**
 * Status type
 * @author tuanlt
 * @since 1.0
 */
public enum SellingVehicleDisplayOrderType {

    NOT_DISPLAY(0),
    DISPLAY(1);

    private int value;
    private static Map<Integer, SellingVehicleDisplayOrderType> valueMapping = new HashMap<>();

    static {
        for (SellingVehicleDisplayOrderType itemType : SellingVehicleDisplayOrderType.values()) {
            valueMapping.put(itemType.getValue(), itemType);
        }
    }

    SellingVehicleDisplayOrderType(int value) {
        this.value = value;
    }

    @JsonValue
    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public static SellingVehicleDisplayOrderType parse(int value) {
        return valueMapping.get(value);
    }
}

package com.ieltshub.enumeration;

import com.fasterxml.jackson.annotation.JsonValue;

import java.util.HashMap;
import java.util.Map;

/**
 * Status type
 * @author tuanlt
 * @since 1.0
 */
public enum SellingVehicleResourceKind {

    VEHICLE_RESOURCE(1),
    CA_VET(2);

    private int value;
    private static Map<Integer, SellingVehicleResourceKind> valueMapping = new HashMap<>();

    static {
        for (SellingVehicleResourceKind itemType : SellingVehicleResourceKind.values()) {
            valueMapping.put(itemType.getValue(), itemType);
        }
    }

    SellingVehicleResourceKind(int value) {
        this.value = value;
    }

    @JsonValue
    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public static SellingVehicleResourceKind parse(int value) {
        return valueMapping.get(value);
    }
}

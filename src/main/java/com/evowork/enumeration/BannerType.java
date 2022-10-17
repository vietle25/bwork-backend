package com.evowork.enumeration;

import com.fasterxml.jackson.annotation.JsonValue;

import java.util.HashMap;
import java.util.Map;

/**
 * Status type
 * @author tuanlt
 * @since 1.0
 */
public enum BannerType {

    AFTER_LOGIN(1),
    MAIN_SCREEN(2),
    ADS_BANNER(3);

    private int value;
    private static Map<Integer, BannerType> valueMapping = new HashMap<>();

    static {
        for (BannerType itemType : BannerType.values()) {
            valueMapping.put(itemType.getValue(), itemType);
        }
    }

    BannerType(int value) {
        this.value = value;
    }

    @JsonValue
    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public static BannerType parse(int value) {
        return valueMapping.get(value);
    }
}

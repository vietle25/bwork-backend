package com.evowork.enumeration;

import com.fasterxml.jackson.annotation.JsonValue;

import java.util.HashMap;
import java.util.Map;

/**
 * Status type
 * @author tuanlt
 * @since 1.0
 */
public enum FormalType {

	OFFICIAL_TEST(1),
    LEARN(2),
    TRIAL(3),
    ENGLISH_STAR_TESTING_2018(10);

    private int value;
    private static Map<Integer, FormalType> valueMapping = new HashMap<>();

    static {
        for (FormalType itemType : FormalType.values()) {
            valueMapping.put(itemType.getValue(), itemType);
        }
    }

    FormalType(int value) {
        this.value = value;
    }

    @JsonValue
    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public static FormalType parse(int value) {
        return valueMapping.get(value);
    }

}

package com.evowork.enumeration;

import com.fasterxml.jackson.annotation.JsonValue;

import java.util.HashMap;
import java.util.Map;

/**
 * User level type
 * @author tuanlt
 * @since 1.0
 */
public enum UserLevelType {
	
	BASE_USER(1),
    VIP_USER(2),
    PREMIUM_USER(3);
	
    private int value;
    private static Map<Integer, UserLevelType> valueMapping = new HashMap<>();

    static {
        for (UserLevelType itemType : UserLevelType.values()) {
            valueMapping.put(itemType.getValue(), itemType);
        }
    }

    UserLevelType(int value) {
        this.value = value;
    }

    @JsonValue
    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public static UserLevelType parse(int value) {
        return valueMapping.get(value);
    }
}

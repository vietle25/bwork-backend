package com.ieltshub.enumeration;

import com.fasterxml.jackson.annotation.JsonValue;

import java.util.HashMap;
import java.util.Map;

/**
 * User type
 * @author tuanlt
 * @since 1.0
 */
public enum UserType {

	WEB_ADMIN(0),
    NORMAL_USERS(2);
	
    private int value;
    private static Map<Integer, UserType> valueMapping = new HashMap<>();

    static {
        for (UserType itemType : UserType.values()) {
            valueMapping.put(itemType.getValue(), itemType);
        }
    }

    UserType(int value) {
        this.value = value;
    }

    @JsonValue
    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public static UserType parse(int value) {
        return valueMapping.get(value);
    }
}

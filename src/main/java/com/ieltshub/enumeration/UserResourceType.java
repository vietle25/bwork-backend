package com.ieltshub.enumeration;

import com.fasterxml.jackson.annotation.JsonValue;

import java.util.HashMap;
import java.util.Map;

/**
 * Resource type
 * @author tuanlt
 * @since 1.0
 */
public enum UserResourceType {

    PERSONAL_FILE(1),
    DRIVER_LICENSE(2);

    private int value;
    private static Map<Integer, UserResourceType> valueMapping = new HashMap<>();

    static {
        for (UserResourceType itemType : UserResourceType.values()) {
            valueMapping.put(itemType.getValue(), itemType);
        }
    }

    UserResourceType(int value) {
        this.value = value;
    }

    @JsonValue
    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public static UserResourceType parse(int value) {
        return valueMapping.get(value);
    }
}

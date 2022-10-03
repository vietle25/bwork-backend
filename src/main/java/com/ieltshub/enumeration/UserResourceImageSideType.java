package com.ieltshub.enumeration;

import com.fasterxml.jackson.annotation.JsonValue;

import java.util.HashMap;
import java.util.Map;

/**
 * Resource type
 * @author tuanlt
 * @since 1.0
 */
public enum UserResourceImageSideType {

    FRONT_SIDE(1),
    BACK_SIDE(2);

    private int value;
    private static Map<Integer, UserResourceImageSideType> valueMapping = new HashMap<>();

    static {
        for (UserResourceImageSideType itemType : UserResourceImageSideType.values()) {
            valueMapping.put(itemType.getValue(), itemType);
        }
    }

    UserResourceImageSideType(int value) {
        this.value = value;
    }

    @JsonValue
    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public static UserResourceImageSideType parse(int value) {
        return valueMapping.get(value);
    }
}

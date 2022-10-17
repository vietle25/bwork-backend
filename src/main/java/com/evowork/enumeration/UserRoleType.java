package com.evowork.enumeration;

import com.fasterxml.jackson.annotation.JsonValue;

import java.util.HashMap;
import java.util.Map;

public enum UserRoleType {

    ADMINISTRATOR(1),
    TEACHER(2),
    USER(3),
    EDITOR(4),
    MARK_WRITING(5),
    TESTER(6);

    private int value;
    private static Map<Integer, UserRoleType> valueMapping = new HashMap<>();

    static {
        for (UserRoleType itemType : UserRoleType.values()) {
            valueMapping.put(itemType.getValue(), itemType);
        }
    }

    UserRoleType(int value) {
        this.value = value;
    }

    @JsonValue
    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public static UserRoleType parse(int value) {
        return valueMapping.get(value);
    }
}

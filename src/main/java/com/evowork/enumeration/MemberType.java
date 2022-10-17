package com.evowork.enumeration;

import com.fasterxml.jackson.annotation.JsonValue;

import java.util.HashMap;
import java.util.Map;

public enum MemberType {

    MEMBER_INVITE(0),
    MEMBER_GROUP(1);

    private int value;
    private static Map<Integer, MemberType> valueMapping = new HashMap<>();

    static {
        for (MemberType itemType : MemberType.values()) {
            valueMapping.put(itemType.getValue(), itemType);
        }
    }

    MemberType(int value) {
        this.value = value;
    }

    @JsonValue
    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public static MemberType parse(int value) {
        return valueMapping.get(value);
    }
}

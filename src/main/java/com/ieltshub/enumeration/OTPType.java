package com.ieltshub.enumeration;

import com.fasterxml.jackson.annotation.JsonValue;

import java.util.HashMap;
import java.util.Map;

/**
 * Status type
 * @author tuanlt
 * @since 1.0
 */
public enum OTPType {

	REGISTER(0),
    FORGOT_PASS(1);

    private int value;
    private static Map<Integer, OTPType> valueMapping = new HashMap<>();

    static {
        for (OTPType itemType : OTPType.values()) {
            valueMapping.put(itemType.getValue(), itemType);
        }
    }

    OTPType(int value) {
        this.value = value;
    }

    @JsonValue
    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public static OTPType parse(int value) {
        return valueMapping.get(value);
    }
}

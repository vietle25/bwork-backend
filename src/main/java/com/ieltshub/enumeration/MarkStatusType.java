package com.ieltshub.enumeration;

import com.fasterxml.jackson.annotation.JsonValue;

import java.util.HashMap;
import java.util.Map;

/**
 * Schedule type
 * @author tuanlt
 * @since 1.0
 */
public enum MarkStatusType {

	PENDING(0),
    IN_PROGRESS(1),
    DONE(2);

    private int value;
    private static Map<Integer, MarkStatusType> valueMapping = new HashMap<>();

    static {
        for (MarkStatusType itemType : MarkStatusType.values()) {
            valueMapping.put(itemType.getValue(), itemType);
        }
    }

    MarkStatusType(int value) {
        this.value = value;
    }

    @JsonValue
    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public static MarkStatusType parse(int value) {
        return valueMapping.get(value);
    }
}

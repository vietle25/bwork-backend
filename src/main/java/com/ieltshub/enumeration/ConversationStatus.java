package com.ieltshub.enumeration;

import com.fasterxml.jackson.annotation.JsonValue;

import java.util.HashMap;
import java.util.Map;

/**
 * Conversation Status
 * @author tuanlt
 * @since 1.0
 */
public enum ConversationStatus {

	DELETE(-1),
    SUSPEND(2),
    ACTIVE(1);

    private int value;
    private static Map<Integer, ConversationStatus> valueMapping = new HashMap<>();

    static {
        for (ConversationStatus itemType : ConversationStatus.values()) {
            valueMapping.put(itemType.getValue(), itemType);
        }
    }

    ConversationStatus(int value) {
        this.value = value;
    }

    @JsonValue
    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public static ConversationStatus parse(int value) {
        return valueMapping.get(value);
    }
}

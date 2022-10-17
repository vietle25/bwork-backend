package com.evowork.enumeration;

import com.fasterxml.jackson.annotation.JsonValue;

import java.util.HashMap;
import java.util.Map;

/**
 * Notification Type
 *
 * @author dulx
 * @since 1.0
 */
public enum NotificationType {

    COMMON_NOTIFICATION(1), //  (new branch, branch maintenance, ...);
    COMMERCIAL_NOTIFICATION(2), //  (discount, promotion, new product, ...);
    ORDER_NOTIFICATION(3),
    TASK_NOTIFICATION(5);

    private int value;
    private static Map<Integer, NotificationType> valueMapping = new HashMap<>();

    static {
        for (NotificationType itemType : NotificationType.values()) {
            valueMapping.put(itemType.getValue(), itemType);
        }
    }

    NotificationType(int value) {
        this.value = value;
    }

    @JsonValue
    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public static NotificationType parse(int value) {
        return valueMapping.get(value);
    }
}

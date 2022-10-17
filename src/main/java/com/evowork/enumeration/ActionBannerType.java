package com.evowork.enumeration;

import com.fasterxml.jackson.annotation.JsonValue;

import java.util.HashMap;
import java.util.Map;

/**
 * Status type
 * @author tuanlt
 * @since 1.0
 */
public enum ActionBannerType {

    DO_NOTHING(1),
    GOTO_SCREEN(2),
    OPEN_APP_OTHER(3),
    OPEN_URL(4);

    private int value;
    private static Map<Integer, ActionBannerType> valueMapping = new HashMap<>();

    static {
        for (ActionBannerType itemType : ActionBannerType.values()) {
            valueMapping.put(itemType.getValue(), itemType);
        }
    }

    ActionBannerType(int value) {
        this.value = value;
    }

    @JsonValue
    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public static ActionBannerType parse(int value) {
        return valueMapping.get(value);
    }
}

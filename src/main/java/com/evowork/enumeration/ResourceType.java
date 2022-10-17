package com.evowork.enumeration;

import com.fasterxml.jackson.annotation.JsonValue;

import java.util.HashMap;
import java.util.Map;

/**
 * Resource type
 * @author tuanlt
 * @since 1.0
 */
public enum ResourceType {
	
	SCAN_FILE(1),
    AUDIO_FILE(2),
    IMAGE_FILE(3);
	
    private int value;
    private static Map<Integer, ResourceType> valueMapping = new HashMap<>();

    static {
        for (ResourceType itemType : ResourceType.values()) {
            valueMapping.put(itemType.getValue(), itemType);
        }
    }

    ResourceType(int value) {
        this.value = value;
    }

    @JsonValue
    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public static ResourceType parse(int value) {
        return valueMapping.get(value);
    }
}

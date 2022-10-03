package com.ieltshub.enumeration;

import com.fasterxml.jackson.annotation.JsonValue;

import java.util.HashMap;
import java.util.Map;

/**
 * Status type
 * @author tuanlt
 * @since 1.0
 */
public enum HealthRecordStatus {

	DELETE(-1),
    WAITING_TO_CONFIRM(0),
    ACTIVE(1);

    private int value;
    private static Map<Integer, HealthRecordStatus> valueMapping = new HashMap<>();

    static {
        for (HealthRecordStatus itemType : HealthRecordStatus.values()) {
            valueMapping.put(itemType.getValue(), itemType);
        }
    }

    HealthRecordStatus(int value) {
        this.value = value;
    }

    @JsonValue
    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public static HealthRecordStatus parse(int value) {
        return valueMapping.get(value);
    }
}

package com.evowork.enumeration;

import com.fasterxml.jackson.annotation.JsonValue;

import java.util.HashMap;
import java.util.Map;

/**
 * Login type
 * 
 * @author tuanlt
 * @date Jul 11, 2016
 * @since 1.0
 */
public enum LoginType {
	NORMAL(1), FACEBOOK(2), GOOGLE(3), TWITTER(4);
	private int value;
	private static Map<Integer, LoginType> valueMapping = new HashMap<>();

	static {
		for (LoginType itemType : LoginType.values()) {
			valueMapping.put(itemType.getValue(), itemType);
		}
	}

	LoginType(int value) {
		this.value = value;
	}

	@JsonValue
	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}

	public static LoginType parse(int value) {
		return valueMapping.get(value);
	}
}

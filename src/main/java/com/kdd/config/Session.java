package com.kdd.config;

import java.util.HashMap;
import java.util.Map;

public class Session {
	
	private static Map<String, Object> variable = new HashMap<String, Object>();

	public static Map<String, Object> getVariable() {
		return variable;
	}

	public static void setVariable(String key, Object value) {
		Session.variable.put(key, value);
	}
	
}

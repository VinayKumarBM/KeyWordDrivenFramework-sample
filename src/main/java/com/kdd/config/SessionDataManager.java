package com.kdd.config;

import java.util.HashMap;
import java.util.Map;

public class SessionDataManager {

	private static SessionDataManager SESSION_DATA_MANAGER;
	private static ThreadLocal<Map<String,Object>> tSessionData = new ThreadLocal<>();
	private static Map<String,Object> testData = new HashMap<String, Object>();
	
	private SessionDataManager() {
		
	}
	
	public static SessionDataManager getInstance(){
		if(SESSION_DATA_MANAGER == null) {
			synchronized (SessionDataManager.class) {
				if(SESSION_DATA_MANAGER == null) {
					SESSION_DATA_MANAGER  = new SessionDataManager();
				}
			}			
		}
		return SESSION_DATA_MANAGER;
	}
	
	public synchronized void setSessionData (String key, Object value) {
		testData.put(key, value);
		tSessionData.set(testData);
	}

	public synchronized Object getSessionData (String key) {
		Map<String,Object> sessionData = tSessionData.get();
		if (sessionData == null) {
			throw new IllegalStateException("Session data is NULL !!");
		}
		System.out.println("Data in Session for key: "+key+" is: "+sessionData.get(key));
		return sessionData.get(key);
	}

}

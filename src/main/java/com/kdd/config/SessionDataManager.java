package com.kdd.config;

import java.util.HashMap;

public class SessionDataManager {

	private static SessionDataManager SESSION_DATA_MANAGER;
	private static ThreadLocal<HashMap<String,Object>> tSessionData = new ThreadLocal<HashMap<String, Object>>() {
		 @Override
		    protected HashMap<String, Object> initialValue() {
		        return new HashMap<>();
		    }
	};
	
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
		tSessionData.get().put(key, value);
	}

	public synchronized Object getSessionData (String key) {
		return tSessionData.get().get(key);
	}

}

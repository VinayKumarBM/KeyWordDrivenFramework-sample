package com.kdd.config;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.log4j.Logger;

public class ObjectReader implements GlobalVariables{	
	private Properties properties = new Properties();
	private final Logger Log = Logger.getLogger(ObjectReader.class.getName());
	
	public String getObjectProperty(String key) {
		try {
			InputStream stream = new FileInputStream(new File(objectRepository));
			properties.load(stream);
			stream.close();
		} catch (FileNotFoundException e) {
			Log.error("File was Not Found: "+e.getMessage());
		} catch (IOException e) {
			Log.error("There was a IO Exception: ", e);
		} 
		return properties.getProperty(key);
	}
}
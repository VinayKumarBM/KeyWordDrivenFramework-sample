package com.kdd.config;

import java.util.HashMap;
import java.util.Map;

import com.kdd.utility.ExcelReader;

public class ExcelManager {

	private static ExcelManager excelReader;
	static Map<Integer, ExcelReader> readerMap = new HashMap<Integer, ExcelReader>();
	
	private ExcelManager() {
		
	}
	
	public static ExcelManager getInstance() {
		if(excelReader == null) {
			excelReader = new ExcelManager();
		}
		return excelReader;
	}
	
	public synchronized void setExcelReader (ExcelReader reader) {
		readerMap.put((int) (long) (Thread.currentThread().getId()), reader);
	}

	public synchronized ExcelReader getExcelReader () {		
		return readerMap.get((int) (long) (Thread.currentThread().getId()));
	}
	

}

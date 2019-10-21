package com.kdd.utility;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelReader {
	private static XSSFSheet excelSheet;
	private static XSSFWorkbook excelWorkbook;
	private static XSSFCell cell;
	private static XSSFRow row;
	private static Logger log = Logger.getLogger(ExcelReader.class.getName());
	private static final String RUN_MODE_YES = "YES";

	public static void setExcelFile(String sheetPath) {
		try{
			FileInputStream excelFile = new FileInputStream(sheetPath);
			excelWorkbook = new XSSFWorkbook(excelFile);			
		} catch(Exception exp){
			log.error("Exception occured in setExcelFile: ", exp);
		}		
	}

	public static int getNumberOfRows(String sheetName) {
		excelSheet = excelWorkbook.getSheet(sheetName);
		int numberOfRows = excelSheet.getPhysicalNumberOfRows();
		log.debug("Number Of Rows: "+numberOfRows);
		return numberOfRows;
	}

	public static String getCellData(int rowNumb, int colNumb) throws Exception{
		try{
			cell = excelSheet.getRow(rowNumb).getCell(colNumb);
			//log.debug("Getting cell data.");
			if(cell.getCellType() == XSSFCell.CELL_TYPE_NUMERIC) {
				cell.setCellType(XSSFCell.CELL_TYPE_STRING);
			}
			String cellData = cell.getStringCellValue();
			return cellData;
		}
		catch(Exception exp){
			return "";
		}
	}

	public static void clearColumnData(String sheetName, int colNumb, String excelFilePath) {
		int rowCount = getNumberOfRows(sheetName);
		for(int i=1; i< rowCount; i++) {
			cell = excelSheet.getRow(i).getCell(colNumb);
			if(cell==null){
				cell = row.createCell(colNumb);
			}
			cell.setCellValue("");			
		}
		log.debug("Clearing column "+colNumb+" of Sheet: "+sheetName);
		writingDataIntoFile(excelFilePath);
	}

	public static void setCellData(String result, int rowNumb, int colNumb, String excelFilePath) {		
		row = excelSheet.getRow(rowNumb);
		cell = row.getCell(colNumb);
		log.debug("Setting results into the excel sheet.");
		if(cell==null){
			cell = row.createCell(colNumb);
		}
		cell.setCellValue(result);
		log.debug("Setting value into cell["+rowNumb+"]["+colNumb+"]: "+result);
		writingDataIntoFile(excelFilePath);		
	}

	private static void writingDataIntoFile(String excelFilePath) {
		try{
			FileOutputStream fileOut = new FileOutputStream(excelFilePath);
			excelWorkbook.write(fileOut);
			fileOut.flush();
			fileOut.close();
		}catch(Exception exp){
			log.error("Exception occured in setCellData: ",exp);
		}
	}

	public static Map<Integer, String> getTestCasesToRun(String sheetName, int runModeColumn, int testCaseColumn) {
		Map<Integer, String> testListMap = new HashMap<Integer, String>();
		try {
			int rowCount = getNumberOfRows(sheetName);
			String testCase;
			for(int i=1; i< rowCount; i++) {
				testCase = getTestCaseToRun(i, runModeColumn, testCaseColumn);
				if(testCase != null) {
					testListMap.put(i,testCase);
				}
			}
		}catch (Exception e) {
			log.error("Exeception Occured while adding data to List:\n", e);
		}
		return testListMap;
	}

	private static String getTestCaseToRun(int row, int runModeColumn, int testCaseColumn) {
		String testCaseName = null;
		try{
			if(getCellData(row, runModeColumn).equalsIgnoreCase(RUN_MODE_YES)){
				testCaseName = getCellData(row, testCaseColumn).trim();
				log.debug("Test Case to Run: "+testCaseName);
			} 
		} catch(Exception exp){
			log.error("Exception occured in getTestCaseRow: ", exp);
		}
		return testCaseName;
	}
}

package com.yash.selenium.webdriver.TestData;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import com.yash.selenium.webdriver.constant.ProjectConstant;

public class LateActivityExcelFileReadWriteDelete {
	
	public static Map<String,String> readProjectTestCasefromExcelAsTrueOrFalse(String sheetName) throws InvalidFormatException, IOException {
		
		InputStream inputFile = new FileInputStream(new File(ProjectConstant.ALL_PROJECT_ACTIVITY_DATA_FILE));
		
		Workbook workbook =WorkbookFactory.create(inputFile);
		Sheet sheet = workbook.getSheet(sheetName);
		
		Row row =sheet.getRow(1);
		HashMap<String, String> hasMap= new HashMap<String, String>();
		
		int rowCount = row.getLastCellNum();
		int columCount = sheet.getRow(0).getLastCellNum();
		
		 for (int i = 1; i <=rowCount+1; i++) {
				for (int j = 0; j < columCount; j++) {
					if(j==0) {
						hasMap.put(sheet.getRow(i).getCell(j).getStringCellValue(), String.valueOf(sheet.getRow(i).getCell(j+1).getStringCellValue()));
						break;
					}
					
				}
			}
			/*
			 * 
			 * for(java.util.Map.Entry<String, String> m:hasMap.entrySet()) {
			 * 
			 * 
			 * System.out.println(m.getKey()+ "   " +m.getValue());
			 * 
			 * 
			 * }
			 */
		return hasMap;
		
	}
	
	

}

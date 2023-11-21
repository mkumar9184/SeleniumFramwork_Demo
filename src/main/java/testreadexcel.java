import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import com.google.common.collect.Multiset.Entry;



public class testreadexcel {

	public static void main(String[] args) throws InvalidFormatException, IOException {
		// TODO Auto-generated method stub
		
		InputStream inputFile = new FileInputStream(new File("D:\\Selenium Pratical\\com.mk.selenium\\Project_ACTIVITY_DATA_FILE.xlsx"));
			
			Workbook workbook =WorkbookFactory.create(inputFile);
			Sheet sheet = workbook.getSheet("DEVL");
			
			Row row =sheet.getRow(1);
			HashMap<String, String> hasMap= new HashMap<String, String>();
			
			int rowCount = row.getLastCellNum();
			int columnCount = sheet.getRow(0).getLastCellNum();
		
			
			 for (int i = 1; i <=rowCount; i++) {
					for (int j = 0; j < columnCount; j++) {
						System.out.println(sheet.getRow(i).getCell(j).getStringCellValue());
						if(j==0) {
							hasMap.put(sheet.getRow(i).getCell(j).getStringCellValue(), String.valueOf(sheet.getRow(i).getCell(j+1).getStringCellValue()));
							break;
						}
						
					}
				}
			
			
			for(java.util.Map.Entry<String, String> m:hasMap.entrySet()) {
				
				
				System.out.println(m.getKey());
					System.out.println(m.getValue());
					
			}
		}
	

}

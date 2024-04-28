package utilities;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class Excelfileutilities {
	XSSFWorkbook wb;
	//constructor for reading excel path
	public Excelfileutilities(String excelpath) throws Throwable
	{
		FileInputStream fi = new FileInputStream(excelpath);
		wb = new XSSFWorkbook(fi);
	}
	//method for counting no.rows in a sheet

	public int rowcount (String sheetname)
	{
      return wb.getSheet(sheetname).getLastRowNum();
	}
	//method for reading cell data
	public String getcelldata(String sheetname,int rownum,int columnnum)
	{
		String data;  
		if(wb.getSheet(sheetname).getRow(rownum).getCell(columnnum).getCellType()==CellType.NUMERIC)
		{
			int celldata =(int) wb.getSheet(sheetname).getRow(rownum).getCell(columnnum).getNumericCellValue();
			data = String.valueOf(celldata);
		}
		else
		{
			data = wb.getSheet(sheetname).getRow(rownum).getCell(columnnum).getStringCellValue();	
		}
		return data;
	}
	//method for set celldata
	public void setcelldata(String sheetname,int rownum,int columnnum,String status,String writeexcel) throws Throwable
	{
		// get sheet from wb
		XSSFSheet ws = wb.getSheet(sheetname);
		//get row from sheet
		XSSFRow rownumber = ws.getRow(rownum);
		//crete cell in a row
		XSSFCell cellnumber = rownumber.createCell(columnnum);
		//write status into cell
		cellnumber.setCellValue(status);
		if(status.equalsIgnoreCase("pass"))
		{
			XSSFCellStyle style = wb.createCellStyle();
			XSSFFont font = wb.createFont();
			font.setColor(IndexedColors.GREEN.getIndex());
			font.setBold(true);
			style.setFont(font);
			ws.getRow(rownum).getCell(columnnum).setCellStyle(style);

		}
		else if (status.equalsIgnoreCase("fail"))
		{
			XSSFCellStyle style = wb.createCellStyle();
			XSSFFont font = wb.createFont();
			font.setColor(IndexedColors.RED.getIndex());
			font.setBold(true);
			style.setFont(font);
			ws.getRow(rownum).getCell(columnnum).setCellStyle(style);
		}
		else if (status.equalsIgnoreCase("blocked"))
		{
			XSSFCellStyle style = wb.createCellStyle();
			XSSFFont font = wb.createFont();
			font.setColor(IndexedColors.BLUE.getIndex());
			font.setBold(true);
			style.setFont(font);
			ws.getRow(rownum).getCell(columnnum).setCellStyle(style);
		}
		FileOutputStream fo = new FileOutputStream(writeexcel);
		wb.write(fo);

	}
}

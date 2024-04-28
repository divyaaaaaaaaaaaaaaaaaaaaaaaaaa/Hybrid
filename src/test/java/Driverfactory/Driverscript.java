package Driverfactory;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.Test;
import Commonfunction.Functionlibrary;
import utilities.Excelfileutilities;
public class Driverscript {
	String inputpath ="./Fileinput/data.xlsx";
	String outputpath = "./Fileoutput/HybridResults.xlsx";
	String testcases = "MasterTestCase";
	WebDriver driver;
	
	@Test
	public void starttest() throws Throwable
	{
		String moduleStatus ="";
		String moduleNew ="";
		//create object to call excel methods
		Excelfileutilities xl = new Excelfileutilities(inputpath);
		//iterate all rows in sheet in master test case sheet
		for(int i=1;i<=xl.rowcount(testcases);i++)
		{
			if(xl.getcelldata(testcases, i, 2).equalsIgnoreCase("Y"))
			{
				//store corresponding sheet into one variable
				String TCmodule = xl.getcelldata(testcases, i, 1);
				//iterate all rows in tc module
				for(int j=1;j<=xl.rowcount(TCmodule);j++)
				{
					//read all cells from tc module sheet
					String description = xl.getcelldata(TCmodule, j, 0);
					String objecttype = xl.getcelldata(TCmodule, j, 1);
					String Ltype = xl.getcelldata(TCmodule, j, 2);
					String Lvalue = xl.getcelldata(TCmodule, j, 3);
					String Testdata = xl.getcelldata(TCmodule, j, 4);
					try {
						if (objecttype.equalsIgnoreCase("startbrowser"))
						{
							driver= Functionlibrary.startbrowser();

						}
						
						if(objecttype.equalsIgnoreCase("openurl"))
						{
							Functionlibrary.openurl();
						}
						if(objecttype.equalsIgnoreCase("waitForElement"))
						{
							Functionlibrary.waitForElement(Ltype, Lvalue, Testdata);
						}
						if(objecttype.equalsIgnoreCase("typeAction"))
						{
							Functionlibrary.typeAction(Ltype, Lvalue, Testdata);
						}
						if(objecttype.equalsIgnoreCase("clickAction"))
						{
							Functionlibrary.clickAction(Ltype, Lvalue);
						}
						if(objecttype.equalsIgnoreCase("validateTitle"))
						{
							Functionlibrary.validateTitle(Testdata);
						}
						if(objecttype.equalsIgnoreCase("dropDownAction"))
						{
							Functionlibrary.dropDownAction(Ltype, Lvalue, Testdata);		
						}
						
						if(objecttype.equalsIgnoreCase("closeBrowser"))
						{
							Functionlibrary.closeBrowser();
						}

						if(objecttype.equalsIgnoreCase("captureStock"))
						{
							Functionlibrary.captureStock(Ltype, Lvalue);
						}

						if(objecttype.equalsIgnoreCase("stockTable"))
						{
							Functionlibrary.stockTable();
						}

						if(objecttype.equalsIgnoreCase("capturesupplier"))
						{
							Functionlibrary.capturesupplier(Ltype, Lvalue);
						}

						if(objecttype.equalsIgnoreCase("suppliertable"))
						{
							Functionlibrary.suppliertable();
						}

						if(objecttype.equalsIgnoreCase("capturecustomer"))
						{
							Functionlibrary.capturecustomer(Ltype, Lvalue);
						}

						if(objecttype.equalsIgnoreCase("customertable"))
						{
							Functionlibrary.customertable();
						}
						
						//write as pass into status cell in tcmodule
						xl.setcelldata(TCmodule, j, 5, "pass", outputpath);
						moduleStatus = "true";
					}catch(Exception e)
					{
						System.out.println(e.getMessage());
						//write as fail into status cell in tcmodule
						xl.setcelldata(TCmodule, j, 5, "fail", outputpath);
						moduleNew = "false";
					}
				}
				if(moduleNew.equalsIgnoreCase("false"))
				{
					xl.setcelldata(testcases, i, 3, "fail", outputpath);
				}
				else if(moduleStatus.equalsIgnoreCase("true"))
				{
					xl.setcelldata(testcases, i, 3, "pass", outputpath);
				}
			}
					
				
			else 
			{
				//write as blocked in status cell for test cases flag to N
			   	xl.setcelldata(testcases, i, 3, "blocked", outputpath);
			}
		}
	}

}

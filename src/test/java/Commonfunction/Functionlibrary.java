package Commonfunction;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.time.Duration;
import java.util.Properties;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.Reporter;

public class Functionlibrary {
	public static WebDriver driver;
	public static Properties prop;
	//method for launching browser 
	public static WebDriver startbrowser() throws Throwable, Throwable
	{
		prop = new Properties();
		prop.load(new FileInputStream("./Propertyfiles/Environment.properties"));
		if(prop.getProperty("browser").equalsIgnoreCase("chrome"))
		{
			System.setProperty("webdriver.chrome.driver", "chromedriver.exe");
			driver= new ChromeDriver();
			driver.manage().window().maximize();
		}
		else if(prop.getProperty("browser").equalsIgnoreCase("firefox"))
		{
			driver= new FirefoxDriver();
		}
		else
		{
			Reporter.log("browser value is not matchng",true);
		}
		return driver;
	}
	//method for launching url
	public static void openurl()
	{
		driver.get(prop.getProperty("url"));
	}

	//method for to wait for any web element
	public static void waitForElement(String locatortype,String locatorvalue,String testdata)
	{
		WebDriverWait mywait = new WebDriverWait(driver, Duration.ofSeconds(Integer.parseInt(testdata)));
		if(locatortype.equalsIgnoreCase("xpath"))
		{
			mywait .until(ExpectedConditions.visibilityOfElementLocated(By.xpath(locatorvalue)));
		}
		if(locatortype.equalsIgnoreCase("name"))
		{
			mywait.until(ExpectedConditions.visibilityOfElementLocated(By.name(locatorvalue)));
		}
		if(locatortype.equals("id"))
		{
			mywait.until(ExpectedConditions.visibilityOfElementLocated(By.id(locatorvalue)));
		}
	}
	//method for any textbox
	public static void typeAction(String locatortype,String locatorvalue,String testdata)
	{
		if (locatortype.equalsIgnoreCase("xpath")) 
		{
			driver.findElement(By.xpath(locatorvalue)).clear();
			driver.findElement(By.xpath(locatorvalue)).sendKeys(testdata);

		}
		if (locatortype.equalsIgnoreCase("name"))
		{
			driver.findElement(By.name(locatorvalue)).clear();
			driver.findElement(By.name(locatorvalue)).sendKeys(testdata);
		}
		if (locatortype.equalsIgnoreCase("id"))
		{
			driver.findElement(By.id(locatorvalue)).clear();
			driver.findElement(By.id(locatorvalue)).sendKeys(testdata);
		}
	}
	//method for any click buttons,images,check boxes,links,radio buttons
	public static void clickAction(String locatortype,String locatorvalue)
	{
		if (locatortype.equalsIgnoreCase("xpath"))
		{
			driver.findElement(By.xpath(locatorvalue)).click();
		}	
		if (locatortype.equalsIgnoreCase("name"))
		{
			driver.findElement(By.name(locatorvalue)).click();
		}
		if (locatortype.equalsIgnoreCase("id"))
		{
			driver.findElement(By.id(locatorvalue)).sendKeys(Keys.ENTER);
		}
	}
	//method for page title validation 
	public static void validateTitle(String expected_title)
	{
		String actualtitle = driver.getTitle();
		try {
			Assert.assertEquals(actualtitle, expected_title,"title is not matching");
		}catch(AssertionError a)
		{
			Reporter.log(a.getMessage(),true);	
		}
	}
	//method for closing browser
	public static void closeBrowser() 
	{
		driver.close();	
	}
	//method for list box or dropdown
	public static void dropDownAction(String locatortype,String locatorvalue,String testdata)
	{
		if(locatortype.equalsIgnoreCase("xpath"))
		{
			//convert string to integer type
			int value = Integer.parseInt(testdata);
			Select element = new Select(driver.findElement(By.xpath(locatorvalue)));
			element.selectByIndex(value);
		}
		if(locatortype.equalsIgnoreCase("name"))
		{
			//convert string to integer type
			int value = Integer.parseInt(testdata);
			Select element = new Select(driver.findElement(By.name(locatorvalue)));
			element.selectByIndex(value);
		}
		if(locatortype.equalsIgnoreCase("id"))
		{
			//convert string to integer type
			int value = Integer.parseInt(testdata);
			Select element = new Select(driver.findElement(By.id(locatorvalue)));
			element.selectByIndex(value);
		}
	}
	//method to capture stock number into notepad
	public static void captureStock(String locatortype,String locatorvalue) throws Throwable
	{
		String stockNumber="";
		if(locatortype.equalsIgnoreCase("xpath"))
		{
			stockNumber = driver.findElement(By.xpath(locatorvalue)).getAttribute("value");

		}
		if (locatortype.equalsIgnoreCase("name"))
		{
			stockNumber = driver.findElement(By.name(locatorvalue)).getAttribute("value");
		}
		if(locatortype.equalsIgnoreCase("id"))
		{
			stockNumber = driver.findElement(By.id(locatorvalue)).getAttribute("value");
		}
		//write stock number into notepad
		FileWriter fw = new FileWriter("./Capturedata/stocknumber.txt");
		BufferedWriter bw = new BufferedWriter(fw);
		bw.write(stockNumber);
		bw.flush();
		bw.close();

	}
	// method for capture stock number from stock table
	public static void stockTable() throws Throwable
	{
		//read stock number from note pad
		FileReader fr = new FileReader("./Capturedata/stocknumber.txt");
		BufferedReader br = new BufferedReader(fr);
		String exp_data = br.readLine();
		//if search textbox is displayed dont click search panel
		if(!driver.findElement(By.xpath(prop.getProperty("search_textbox"))).isDisplayed())
			//click search panel button
			driver.findElement(By.xpath(prop.getProperty("search_panel"))).click();

		Thread.sleep(1000);
		driver.findElement(By.xpath(prop.getProperty("search_textbox"))).clear();
		Thread.sleep(1000);
		//enter stock number
		driver.findElement(By.xpath(prop.getProperty("search_textbox"))).sendKeys(exp_data);
		Thread.sleep(1000);
		//click search button
		driver.findElement(By.xpath(prop.getProperty("search_button"))).click();
		Thread.sleep(1000);
		String act_data = driver.findElement(By.xpath("//table[@class='table ewTable']/tbody/tr[1]/td[8]/div/span/span")).getText();
		Reporter.log(act_data+"     "+exp_data,true);
		try {
			Assert.assertEquals(act_data, exp_data, "stock number not matching");
		} catch (Exception e) {
			Reporter.log(e.getMessage());
		}
		
	}
	//method for supplier number to capture
	public static void capturesupplier(String locatortype,String locatorvalue) throws Throwable
	{
		String supplierNumber="";
		if(locatortype.equalsIgnoreCase("xpath"))
		{
			supplierNumber = driver.findElement(By.xpath(locatorvalue)).getAttribute("value");

		}
		if (locatortype.equalsIgnoreCase("name"))
		{
			supplierNumber = driver.findElement(By.name(locatorvalue)).getAttribute("value");
		}
		if(locatortype.equalsIgnoreCase("id"))
		{
			supplierNumber = driver.findElement(By.id(locatorvalue)).getAttribute("value");
		}
		//write stock number into notepad
		FileWriter fw = new FileWriter("./Capturedata/suppliernumber.txt");
		BufferedWriter bw = new BufferedWriter(fw);
		bw.write(supplierNumber);
		bw.flush();
		bw.close();
	}
	//method for supplier table
	public static void suppliertable() throws Throwable
	{
		//read stock number from note pad
		FileReader fr = new FileReader("./Capturedata/suppliernumber.txt");
		BufferedReader br = new BufferedReader(fr);
		String exp_data = br.readLine();
		//if search textbox is displayed dont click search panel
		if(!driver.findElement(By.xpath(prop.getProperty("search_textbox"))).isDisplayed())
			//click search panel button
			driver.findElement(By.xpath(prop.getProperty("search_panel"))).click();
		Thread.sleep(1000);
		driver.findElement(By.xpath(prop.getProperty("search_textbox"))).clear();
		Thread.sleep(1000);
		//enter stock number
		driver.findElement(By.xpath(prop.getProperty("search_textbox"))).sendKeys(exp_data);
		Thread.sleep(1000);
		//click search button
		driver.findElement(By.xpath(prop.getProperty("search_button"))).click();
		Thread.sleep(1000);
		String act_data = driver.findElement(By.xpath("//table[@class='table ewTable']/tbody/tr[1]/td[6]/div/span/span")).getText();
		Reporter.log(act_data+"     "+exp_data,true);
		try {
			Assert.assertEquals(act_data, exp_data, "supplier number not matching");
		} catch (Exception e) {
			Reporter.log(e.getMessage());
		}
	}
	//method for customer number to capture
	public static void capturecustomer(String locatortype,String locatorvalue) throws Throwable
	{
		String customerNumber="";
		if(locatortype.equalsIgnoreCase("xpath"))
		{
			customerNumber = driver.findElement(By.xpath(locatorvalue)).getAttribute("value");

		}
		if (locatortype.equalsIgnoreCase("name"))
		{
			customerNumber = driver.findElement(By.name(locatorvalue)).getAttribute("value");
		}
		if(locatortype.equalsIgnoreCase("id"))
		{
			customerNumber = driver.findElement(By.id(locatorvalue)).getAttribute("value");
		}
		//write stock number into notepad
		FileWriter fw = new FileWriter("./Capturedata/customernumber.txt");
		BufferedWriter bw = new BufferedWriter(fw);
		bw.write(customerNumber);
		bw.flush();
		bw.close();
	}
	//method for customer table
	public static void customertable() throws Throwable
	{
		//read stock number from note pad
		FileReader fr = new FileReader("./Capturedata/customernumber.txt");
		BufferedReader br = new BufferedReader(fr);
		String exp_data = br.readLine();
		//if search textbox is displayed dont click search panel
		if(!driver.findElement(By.xpath(prop.getProperty("search_textbox"))).isDisplayed())
			//click search panel button
			driver.findElement(By.xpath(prop.getProperty("search_panel"))).click();
		Thread.sleep(1000);
		driver.findElement(By.xpath(prop.getProperty("search_textbox"))).clear();
		Thread.sleep(1000);
		//enter stock number
		driver.findElement(By.xpath(prop.getProperty("search_textbox"))).sendKeys(exp_data);
		Thread.sleep(1000);
		//click search button
		driver.findElement(By.xpath(prop.getProperty("search_button"))).click();
		Thread.sleep(1000);
		String act_data = driver.findElement(By.xpath("//table[@class='table ewTable']/tbody/tr[1]/td[5]/div/span/span")).getText();
		Reporter.log(act_data+"     "+exp_data,true);
		try {
			Assert.assertEquals(act_data, exp_data, "customer number not matching");
		} catch (Exception e) {
			Reporter.log(e.getMessage());
		}
	}

}
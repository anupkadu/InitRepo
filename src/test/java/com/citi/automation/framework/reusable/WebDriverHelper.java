package com.citi.automation.framework.reusable;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.Reporter;

public class WebDriverHelper {
static WebDriver driver;
static WebDriverWait wait;
static Properties p = new Properties();
{
	try {
		p.load(new FileInputStream("src/test/resources/Project.properties"));
	} catch (FileNotFoundException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
}
public static void initialize()
{
	switch(p.getProperty("browser"))
	{
	case "gc":
		System.setProperty("webdriver.chrome.driver", "src/test/resources/drivers/chromedriver.exe");
		driver = new ChromeDriver();
		break;
	case "ff":
		System.setProperty("webdriver.gecko.driver", "src/test/resources/drivers/geckodriver.exe");
		driver = new FirefoxDriver();
		break;
	case "ie":
		System.setProperty("webdriver.ie.driver", "src/test/resources/drivers/IEDriverServer.exe");
		driver = new InternetExplorerDriver();
		break;
		default:
			System.setProperty("webdriver.chrome.driver", "src/test/resources/drivers/chromedriver.exe");
			driver = new ChromeDriver();
			break;
	}
	driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
	driver.manage().window().maximize();
	Reporter.log("Initialized browser");
	navigate(p.getProperty("url"));
}
public static void navigate(String url)
{
	driver.get(url);
	Reporter.log("Navigated to url: "+driver.getCurrentUrl());
	//Assert.assertEquals(driver.getCurrentUrl(), url);
	Assert.assertEquals(driver.getCurrentUrl(), url);
}
public static void click(By locator)
{
	wait.until(ExpectedConditions.elementToBeClickable(locator));
	driver.findElement(locator).click();
	Reporter.log("clicked on "+locator.toString());
}
public static void type(By locator, String text)
{
	driver.findElement(locator).sendKeys(text);
}
public static void select(By locator, String text)
{
	Select obj = new Select(driver.findElement(locator));
	obj.selectByVisibleText(text);
}
public static void switch_frame(By locator)
{
	driver.switchTo().frame(driver.findElement(locator));
}
public static void switch_back()
{
	driver.switchTo().defaultContent();
}
public static void select_checkbox(By locator)
{
	if(!driver.findElement(locator).isSelected())
	{
		driver.findElement(locator).click();
	}
}
public static void quit() {
	driver.quit();
}
}

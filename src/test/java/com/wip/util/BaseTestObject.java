package com.wip.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Driver;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.testng.ITestContext;
import org.testng.ITestResult;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Listeners;
import org.testng.annotations.Parameters;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

public class BaseTestObject {
	public static ExtentTest extest;
	public static ExtentReports report;

	public WebDriver driver;

	public static String propertyFilePath = System.getProperty("user.dir")
			+ "\\src\\test\\resources\\testdata\\data.properties";
	public static String chromeDriverPath = System.getProperty("user.dir")
			+ "\\src\\test\\resources\\drivers\\81\\chromedriver.exe";
	public static String IEDriverPath = System.getProperty("user.dir")
			+ "\\src\\test\\resources\\drivers\\IE\\IEDriverServer.exe";
	public static String geckoDriverPath = System.getProperty("user.dir")
			+ "\\src\\test\\resources\\drivers\\geckodriver\\geckodriver.exe";
	public static String lo4jpath = System.getProperty("user.dir")
			+ "\\src\\test\\resources\\testdata\\log4j.properties";

	public static String filePath = System.getProperty("user.dir") + "\\test-output\\screenshots\\screenshot";
	FileInputStream fileInput = null;

	VedioRecording test = new VedioRecording();

	public Properties ObjProperty = getPropertyContents();
	public String browser = ObjProperty.getProperty("browser");
	public String url = ObjProperty.getProperty("url");

	private static final Properties prop = new Properties();

	public WebDriver getDriver() {

		return driver;
	}

	private static void loadPropertiesFile() {
		InputStream input = null;

		try {
			input = new FileInputStream(propertyFilePath);
			// load a properties file
			prop.load(input);
		} catch (IOException ex) {
			ex.printStackTrace();
		} finally {
			if (input != null) {
				try {
					input.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public static Properties getPropertyContents() {
		loadPropertiesFile();
		return prop;
	}

	/**
	 * 
	 * This function will execute before each Test tag in testng.xml
	 * 
	 * @param browser
	 * 
	 * @throws Exception
	 * 
	 */

	@Parameters({ "browserType" })
	@BeforeTest(alwaysRun = true)
	public void setup(String browserType) throws Exception {
		if (browser.equalsIgnoreCase("FF")) {
			System.out.println(browser);

			System.setProperty("webdriver.gecko.driver", geckoDriverPath);
			driver = new FirefoxDriver();
		} else if (browser.trim().equalsIgnoreCase(browserType.trim())) {

			System.setProperty("webdriver.chrome.driver", chromeDriverPath);
			driver = new ChromeDriver();
		} else if (browser.equalsIgnoreCase("IE")) {
			System.setProperty("webdriver.ie.driver", IEDriverPath);
			driver = new InternetExplorerDriver();
		} else {
			throw new Exception("Browser is not correct");
		}

		driver.get(url);
		// test.startRecording();
		driver.manage().timeouts().implicitlyWait(50, TimeUnit.SECONDS);
		driver.manage().window().maximize();

	}

	@AfterTest(alwaysRun = true)
	public void tearDown() throws Exception {
		try {

			// test.stopRecording();
			driver.quit();
			report.endTest(extest);
			report.flush();
		} catch (Exception e) {
			System.out.println("Exception while taking screenshot " + e.getMessage());
		}
	}

	public static void screenShot(WebDriver driver) throws IOException, InterruptedException {

		TakesScreenshot screenshot = (TakesScreenshot) driver;
		File scr = screenshot.getScreenshotAs(OutputType.FILE);
		File dest = new File(filePath + timestamp() + ".png");

		try {
			FileUtils.copyFile(scr, dest);
			Thread.sleep(3000);
		} catch (IOException e) {
			System.out.println("Capture Failed " + e.getMessage());
		}
	}

	public static String timestamp() {
		return new SimpleDateFormat("yyyy-MM-dd HH-mm-ss").format(new Date());
	}



	public void closePopUp() throws InterruptedException {
		String parent = driver.getWindowHandle();
		Set<String> pops = driver.getWindowHandles();
		{
			Iterator<String> it = pops.iterator();
			while (it.hasNext()) {
				String popupHandle = it.next().toString();
				if (!popupHandle.contains(parent)) {
					driver.switchTo().window(popupHandle);
					System.out.println("Popu Up Title: " + driver.switchTo().window(popupHandle).getTitle());
					driver.close();
					Thread.sleep(5000);
				}
			}
		}
	}

}

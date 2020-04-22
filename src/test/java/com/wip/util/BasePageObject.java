
package com.wip.util;

import java.awt.Toolkit;
import java.sql.Driver;
import java.time.Duration;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;

//import org.apache.logging.log4j.*;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.UnhandledAlertException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.Reporter;

import com.wip.pages.Homepage;
import com.wip.pages.Searchpage;
import org.apache.log4j.BasicConfigurator;

public class BasePageObject {

	// protected static WebDriver uiDriver;
	public WebDriver driver;
	public WebElement element;

	public static String lo4jpath = System.getProperty("user.dir")
			+ "\\src\\test\\resources\\testdata\\log4j.properties";
	private final static Logger LOGGER = Logger.getLogger(Searchpage.class.getName());

	public BasePageObject(WebDriver driver) {
		BasicConfigurator.configure();
		this.driver = driver;

	}


	public void waitForAnElement(final By theElement, int timeoutInSeconds) throws InterruptedException {
		WebElement element = null;
		try {
			element = driver.findElement(theElement);
			LOGGER.info("webElement[" + theElement + "] and waited for [" + timeoutInSeconds + " ] seconds to load");
			WebDriverWait wait = new WebDriverWait(driver, timeoutInSeconds);
			LOGGER.info(" webdriver wait will waits for [" + timeoutInSeconds + "] seconds to load");
			wait.until(ExpectedConditions.visibilityOf(element));
			LOGGER.info("waits for expected Element to visible [" + element + "]");
		} catch (Exception e) {
			waitForElementLoad(theElement);
			Reporter.log("There was an issue in finding the webelement, " + theElement.getClass() + e.getMessage());
			e.printStackTrace();
		}
	}

	/**
	 * @param locatorname
	 * @param timeout
	 * @return
	 */
	public WebElement waitTillElementTobeVisible(By locatorname, int timeout) {
		LOGGER.info("Waiting for the element [" + locatorname.toString() + "], timeout = [" + timeout + "]");
		WebDriverWait wait = new WebDriverWait(driver, timeout);
		return wait.until(ExpectedConditions.visibilityOfElementLocated(locatorname));
	}

	public void waitForAnElementToClick(final By theElement, int timeoutInSeconds) {
		try {
			WebElement element = driver.findElement(theElement);
			LOGGER.info("webElement[" + theElement + "] and waited for [" + timeoutInSeconds + " ] seconds to load");
			WebDriverWait wait = new WebDriverWait(driver, timeoutInSeconds);
			LOGGER.info(" webdriver wait will waits for [" + timeoutInSeconds + "] seconds to load");
			wait.until(ExpectedConditions.elementToBeClickable(element));
			LOGGER.info("waits for expected Element to visible [" + element + "]");
		} catch (Exception e) {
			Reporter.log("There was an issue in finding the webelement, " + theElement.getClass() + e.getMessage());
			e.printStackTrace();
		}
	}

	public void javaScriptClick(By theElement) {
		WebElement element = driver.findElement(theElement);
		LOGGER.info("Getting the Webelemnt [" + element + "]");
		((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
	}

	public void scrollTillElementVisible(By theElement) {
		WebElement element = driver.findElement(theElement);
		LOGGER.info("Scrolling the element till visiblet [" + element + "]");
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
	}

	public boolean isElementPresent(By by) {

		try {
			driver.findElement(by);
			LOGGER.info("Finding the Element [" + by + "]");
			return true;
		} catch (NoSuchElementException e) {
			return false;
		}
	}

	public WebElement setElement(By by) throws Exception {
		try {
			element = driver.findElement(by);
			LOGGER.info("Set the Webelement [" + by + "]");
		} catch (NoSuchElementException e) {

			throw new Exception("Element is located while:" + element + e.getLocalizedMessage());
		}

		return element;
	}

	/**
	 * 
	 * @param locator
	 */
	public String getText(By theElement) {

		WebElement element = driver.findElement(theElement);
		LOGGER.info("Getting the Webelement text [" + theElement + "]");
		return element.getText();
	}

	public String getAttributeValue(By theElement, String attributeValue) {
		WebElement element = driver.findElement(theElement);
		LOGGER.info("Getting the AttributeValue of the text [" + theElement + "] and it's attribute value is [ "
				+ attributeValue + "]");
		return element.getAttribute(attributeValue);
	}

	public boolean isTextPresent(String textValue) {
		// Reporter.log("Looking for the element...: " + textValue);
		System.out.println("Looking for the element...: " + textValue);
		WebElement webElement = driver.findElement(By.tagName("html"));
		if (webElement.getText().contains(textValue)) {
			return true;
		} else {
			System.out.println("Element not found : " + textValue);
			return false;
		}
	}

	public void mouseover(By theElement) throws Exception {
		explicitWait(3);
		Actions act = new Actions(driver);

		WebElement webelement = driver.findElement(theElement);
		act.moveToElement(webelement).build().perform();
		// new Actions(driver).moveToElement((WebElement) theElement).build().perform();
	}

	/*
	 * public WebElement mouseover(WebElement theElement) { new
	 * Actions(driver).moveToElement(theElement).build().perform(); return
	 * theElement; }
	 */
	public void selectDropDown(By theSelectElement, String valToSelect) throws InterruptedException {
		WebElement element = driver.findElement(theSelectElement);
		Select select = new Select(element);
		// Get a list of the options
		List<WebElement> options = select.getOptions();
		// For each option in the list, verify if it's the one you want and then
		// click it
		for (WebElement we : options) {
			if (we.getText().equals(valToSelect)) {
				we.click();
				Thread.sleep(2000);
				break;
			}
		}

	}

	public String selectedItem(By theSelectElement) {
		WebElement element = driver.findElement(theSelectElement);
		Select select = new Select(element);
		WebElement anElement = select.getFirstSelectedOption();
		return anElement.getText();
	}

	/**
	 * This api can be used to verify if a UIElement like checkbox, radio button is
	 * checked or not
	 * 
	 * @param theElement
	 * @return
	 */
	public boolean isElementChecked(By theElement) {
		WebElement element = driver.findElement(theElement);
		boolean retValue = false;
		if (element.isSelected()) {
			retValue = true;
		}
		return retValue;
	}

	public void waitImplicit() {
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	}

	public void dragDrop(By element, By target) {
		WebElement elementSource = driver.findElement(element);
		WebElement elementDestination = driver.findElement(target);
		(new Actions(driver)).dragAndDrop(elementSource, elementDestination).perform();

	}

	public void homePageScreen() {
		try {

			if (!driver.getCurrentUrl().equals("https://www.jbhifi.com.au/")) {
				driver.get("https://www.jbhifi.com.au/");

			}
			explicitWait(2);
		} catch (Exception e) {
			driver.navigate().back();
			;
		}
	}

	public void deleteCookies() {
		driver.manage().deleteAllCookies();
	}

	public void closeBrowser() {

		driver.quit();
	}

	public void alert() {
		Alert alert = driver.switchTo().alert();
		alert.accept();

	}

	/**
	 * This method Verifies click on close button
	 * 
	 * @return
	 * @throws Exception
	 */
	public void closePresentWindow() throws Exception {
		try {
			// driver.close();
		} catch (Exception e) {
			throw new Exception(
					"ISSUE IN CLOSING THE 'window'" + "\nMETHOD:clickOnCloseWindow\n" + e.getLocalizedMessage());
		}
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

	public String getSelectedValue(By element) {
		WebElement element1 = driver.findElement(element);
		Select select = new Select(element1);
		String selectedValue = select.getFirstSelectedOption().getText();
		return selectedValue;
	}

	public void selectByIndex(By element, int index) {
		WebElement element1 = driver.findElement(element);
		Select select = new Select(element1);
		List<WebElement> options = select.getOptions();
		int size = options.size();
		if (size > 0) {
			select.selectByIndex(index);
		}
	}

	public String getSelectedValueFromCombo(By element) {
		WebElement element1 = driver.findElement(element);
		Select select = new Select(element1);
		String selectedValue = select.getFirstSelectedOption().getText();
		return selectedValue;
	}

	public void clearAndEnterValueInTextBox(By by, String value) {
		WebElement textBox = driver.findElement(by);
		Assert.assertTrue(textBox.isEnabled(), "Text Box is enabled");
		textBox.sendKeys(Keys.CONTROL + "a");
		textBox.sendKeys(Keys.DELETE);
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
		}
		textBox.sendKeys(value);

	}

	public void clearAndEnterValueInTextBoxAndPressEnter(By by, String value) {
		WebElement textBox = driver.findElement(by);
		Assert.assertTrue(textBox.isEnabled(), "Text Box is enabled");
		textBox.sendKeys(Keys.CONTROL + "a");
		textBox.sendKeys(Keys.DELETE);
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
		}
		textBox.sendKeys(value);

	}

	public void handleAlert() {
		try {
			WebDriverWait wait = new WebDriverWait(driver, 60);
			boolean alertPresent = false;
			if (wait.until(ExpectedConditions.alertIsPresent()) == null) {
				alertPresent = false;
			} else {
				alertPresent = true;
			}
			if (alertPresent) {
				Alert alert = driver.switchTo().alert();
				alert.accept();
			}
		} catch (UnhandledAlertException e) {

		}
	}

	public boolean retryingFindClick(By by) {
		boolean result = false;
		int attempts = 0;
		while (attempts < 3) {
			try {
				driver.findElement(by).click();
				result = true;
				break;
			} catch (StaleElementReferenceException e) {
			}
			attempts++;
		}
		return result;
	}

	public void waitImplicit(int millisecods) {
		driver.manage().timeouts().implicitlyWait(millisecods, TimeUnit.MILLISECONDS);
		try {
			Thread.sleep(millisecods);
		} catch (InterruptedException e) {
		}
	}

	public void switchToNewWindow() throws Exception {
		for (String winHandle : driver.getWindowHandles()) {
			driver.switchTo().window(winHandle);
		}
	}

	public String getWindowName() throws Exception {
		String windowName = driver.getWindowHandle();
		return windowName;
	}

	public boolean isChildWindowPresent() throws Exception {
		Set<String> windows = driver.getWindowHandles();
		int size = windows.size();
		return size > 1;
	}

	public void switchToParentWindow(String windowName) throws Exception {
		windowName = windowName.toString();
		driver.switchTo().window(windowName);
		waitImplicit(3000);
	}

	public String getPageTitle() {
		return driver.getTitle().trim();
	}

	public void compareTwoStrings(String Actual, String Expected, String Message) {
		Assert.assertEquals(Actual, Expected, Message);
	}

	public void implicitWait(int seconds) {

		driver.manage().timeouts().implicitlyWait(seconds, TimeUnit.SECONDS);
	}

	public static void explicitWait(int Seconds) throws InterruptedException {

		Thread.sleep(Seconds);
		LOGGER.info("Explicit wait for: [" + Seconds + "]Seconds ");
	}

	public void waitForElementLoad(By locator) throws InterruptedException {
		try {

			WebElement element = driver.findElement(locator);
			int sec = 0;
			for (sec = 0; sec <= 30; sec++) {
				try {
					Thread.sleep(1000);
					sec++;
					if (element.isEnabled()) {
						System.out.println(" : ELEMENT IS ENABLED : true");
						break;
					}
				} catch (Exception e) {
					System.out.println(" : ELEMENT NOT YET ENABLED : " + element.getAttribute("name"));
				}
				//
			}
		} catch (Exception e) {
			System.out.println(" : ELEMENT NOT YET ENABLED : " + element.getAttribute("name"));
		}
	}

}

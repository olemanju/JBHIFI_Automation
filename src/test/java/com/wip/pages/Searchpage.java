package com.wip.pages;

import org.apache.log4j.Logger;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;

import com.wip.common.HIFIConstants;
import com.wip.util.BasePageObject;

public class Searchpage extends BasePageObject {

	public Searchpage(WebDriver driver) {
		super(driver);
	}

	private final static Logger LOGGER = Logger.getLogger(Searchpage.class.getName());

	boolean flag = false;
	/* Web elements */
	By txtbox_searchBox_xpath1 = By.xpath("//input[@placeholder='Search products']");
	By txtbox_searchBox_xpath = By.xpath("//input[@class='ais-search-box--input']");
	By txt_myaccount_xpath = By.xpath("//h1");
	By txt_result_xpath = By.xpath("//span[@class='ais-stats--nb-results']/strong");
	By lst_select_item_xpath = By.xpath(
			"//div[@class='ais-infinite-hits ais-results-as-block']/div/div/a[@title='Canon PowerShot G7X II Compact Digital Camera']");
	By header_xpath = By.xpath("//h1");
	By btn_cart_id = By.id("AddToCartText");

	By lbl_myaccount_xpath = By.xpath("//span[text()='My Account']");

	By btn_logout_xpath = By.xpath("(//a[text()='Logout'])[1]");

	By btn_toggle_myacc_id = By.id("myaccount-toggle");

	public boolean isMyAccountDisplayed() throws Exception {
		try {
			isSearchTextDisplayed();
			waitTillElementTobeVisible(btn_toggle_myacc_id, 30);
			flag = isElementPresent(btn_toggle_myacc_id);

		} catch (Exception e) {
			throw new Exception("My account is displayed::" + isMyAccountDisplayed() + e.getLocalizedMessage());
		}
		return flag;
	}

	public void ClickOnMyAccountButtont() throws Exception {
		try {

			waitForAnElementToClick(lbl_myaccount_xpath, 30);
			javaScriptClick(lbl_myaccount_xpath);
		} catch (Exception e) {
			retryingFindClick(btn_toggle_myacc_id);
			throw new Exception("Click on the MY Account " + e.getLocalizedMessage());
		}
	}

	public void ClickOnLogout() throws Exception {
		try {
			mouseover(lbl_myaccount_xpath);

			Actions abt = new Actions(driver);
			WebElement mySub = driver.findElement(lbl_myaccount_xpath);
			abt.moveToElement(mySub);
			WebElement Logout = driver.findElement(btn_logout_xpath);
			abt.moveToElement(Logout).click().perform();
			System.out.println("Clicked on Logout");

		} catch (Exception e) {

			throw new Exception("Click on the MY Account" + e.getLocalizedMessage());
		}

	}

	public String getMyAccountText() throws Exception {
		String name = null;
		try {

			// waitForElementLoad(header_xpath);
			waitTillElementTobeVisible(header_xpath, 30);
			name = getText(header_xpath);
			// System.out.println("The text value is " + name);
			explicitWait(7);

		} catch (Exception e) {
			throw new Exception("Failed to verify the My Account text after login  :" + getMyAccountText()
					+ e.getLocalizedMessage());
		}

		return name;
	}

	public boolean isSearchTextDisplayed() throws Exception {
		try {

			waitTillElementTobeVisible(txtbox_searchBox_xpath, 30);
			flag = isElementPresent(txtbox_searchBox_xpath);
			explicitWait(5);
			Assert.assertTrue(flag, "Search Text box object is not displayed");

		} catch (Exception e) {
			retryingFindClick(txtbox_searchBox_xpath);
			throw new Exception("Search textbox  is displayed::" + isSearchTextDisplayed() + e.getLocalizedMessage());

		}
		return flag;
	}

	public void enterProductNameToSearch(String product) throws Exception {
		String header = null;
		try {

			if (isSearchTextDisplayed() == true) {
				header = getMyAccountText();

				explicitWait(8);
				waitTillElementTobeVisible(txtbox_searchBox_xpath, 30);
				waitForAnElement(txtbox_searchBox_xpath, 30);
				retryingFindClick(txtbox_searchBox_xpath);
				clearAndEnterValueInTextBoxAndPressEnter(txtbox_searchBox_xpath, product);
			} else {
				LOGGER.info("Search Text is missing or not able to identify the text");
			}

		} catch (Exception e) {
			explicitWait(6);
			System.out.println("Taking More time to page Load");
			clearAndEnterValueInTextBoxAndPressEnter(txtbox_searchBox_xpath, product);
			throw new Exception(
					"Failed to Enter the Product in the search Box ::" + e.getStackTrace() + e.getLocalizedMessage());
		}
	}

	public String getExtrcatedText() throws Exception {
		String name = null;
		String extractedvalue = null;
		try {
			// camera
			name = getText(txt_result_xpath);
			// extractedvalue= name.substring(name.length()-5);
			System.out.println(name);
			Assert.assertEquals(name, HIFIConstants.SEARCH_TEXT,
					" Search element is Missing or  not matching with the Expected value");

		} catch (Exception e) {
			throw new Exception("My Account is :" + getMyAccountText() + e.getLocalizedMessage());
		}

		return name;
	}

	public void ScrolledTillVisible() throws Exception {
		try {
			explicitWait(4);
			WebElement el = driver.findElement(lst_select_item_xpath);
			((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", el);

		} catch (Exception e) {
			throw new Exception("Search textbox  is displayed and Entered the value:" + e.getLocalizedMessage());
		}

	}

	public boolean isSearchItemdisplayedAdnScrolledTillVisible() throws Exception {
		try {

			// scrollTillElementVisible(searchBox_xpath);
			waitForAnElement(lst_select_item_xpath, 30);
			flag = isElementPresent(lst_select_item_xpath);

		} catch (Exception e) {
			throw new Exception("Search textbox  is displayed and Entered the value:"
					+ isSearchItemdisplayedAdnScrolledTillVisible() + e.getLocalizedMessage());
		}
		return flag;

	}

	public String gettheAttributevalueofSearch(String value) throws Exception {
		String name = null;
		try {

			name = getAttributeValue(lst_select_item_xpath, value);

		} catch (Exception e) {
			throw new Exception("Search textbox  is displayed and Entered the value:"
					+ isSearchItemdisplayedAdnScrolledTillVisible() + e.getLocalizedMessage());
		}
		return name;

	}

	public ProductDetailsPage ClickOnProduct() throws Exception {
		try {
			ScrolledTillVisible();
			// Get the attribute value of the element
			String titleoftheProduct = gettheAttributevalueofSearch("title");
			System.out.println(titleoftheProduct);
			flag = isSearchItemdisplayedAdnScrolledTillVisible();
			Assert.assertTrue(flag, "Search Text box object is not displayed");
			waitTillElementTobeVisible(lst_select_item_xpath, 20);
			javaScriptClick(lst_select_item_xpath);
		} catch (Exception e) {

			throw new Exception("Click on the Product" + ClickOnProduct() + e.getLocalizedMessage());
		}
		return new ProductDetailsPage(driver);

	}

}

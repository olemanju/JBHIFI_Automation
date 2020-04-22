package com.wip.pages;

import javax.swing.tree.ExpandVetoException;

import org.apache.log4j.Logger;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;

import com.wip.common.HIFIConstants;
import com.wip.util.BasePageObject;

public class Homepage extends BasePageObject {
	public Homepage(WebDriver driver) {
		super(driver);
	}

	private final static Logger LOGGER = Logger.getLogger(Homepage.class.getName());

	// Page Object Model- Stored all the page related objects
	boolean flag = false;
	/* Web elements */
	By lbl_myaccount_css = By.cssSelector("span.stack span");

	String pagetitle = null;

	/**
	 * @author Manjunath Ole
	 * @return
	 * @throws Exception Gets the Title of the page
	 */

	public String verifyHomePageTitle() throws Exception {
		try {
			pagetitle = driver.getTitle().trim();
			System.out.println("Page Title is " + pagetitle);
			Assert.assertEquals(pagetitle, HIFIConstants.HOME_TITLE, "Title of the Page is not Matching");
		} catch (Exception e) {

			throw new Exception(
					"FAILED GETING THE HOMEPAGE TITLE  " + "\n verifyHomePageTitle " + e.getLocalizedMessage());
		}
		return pagetitle;
	}

	/**
	 * @author Manjunath Ole
	 * @return
	 * @throws Exception Checks My account object is displayed
	 */
	public boolean isMyAccountDisplayed() throws Exception {
		try {
			waitForAnElement(lbl_myaccount_css, 30);
			flag = isElementPresent(lbl_myaccount_css);
			Assert.assertTrue(flag, "MyAccount object is not displayed");

		} catch (Exception e) {
			throw new Exception("My account :" + isMyAccountDisplayed() + e.getLocalizedMessage());
		}
		return flag;
	}

	/**
	 * @author Manjunath Ole
	 * @return
	 * @throws Exception Clicks on the My accounts which will takes to Login Page
	 */
	public LoginPage clickOnMyAccount() throws Exception {

		try {
			waitForAnElement(lbl_myaccount_css, 30);
			javaScriptClick(lbl_myaccount_css);

		} catch (Exception e) {

			throw new Exception("FAILED CLICKING ON MY ACCOUNT " + clickOnMyAccount() + e.getLocalizedMessage());
		}
		return new LoginPage(driver);

	}

}

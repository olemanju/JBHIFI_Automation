package com.wip.pages;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

import com.wip.util.BasePageObject;

public class MyAccountPage extends BasePageObject

{
	public MyAccountPage(WebDriver driver) {
		super(driver);
	}

	private final static Logger LOGGER = Logger.getLogger(MyAccountPage.class.getName());
	boolean flag = false;
	By lbl_myaccount_xpath = By.xpath("//span[text()='My Account']");
	// Both Xpath and Linktext locator
	// By btn_logout_xpath= By.xpath("//a[text()='Logout']");
	By btn_logout_link = By.linkText("Logout");

	public boolean isMyAccountDisplayed() throws Exception {
		try {
			waitForAnElement(lbl_myaccount_xpath, 30);
			flag = isElementPresent(lbl_myaccount_xpath);
		} catch (Exception e) {
			throw new Exception("My account is displayed::" + isMyAccountDisplayed() + e.getLocalizedMessage());
		}
		return flag;
	}

	public void ClickOnMyAccountButtont() throws Exception {
		try {
			javaScriptClick(lbl_myaccount_xpath);
		} catch (Exception e) {

			throw new Exception("Click on the MY Account" + e.getLocalizedMessage());
		}
	}

	public void MovetoLogout() throws Exception {
		try {
			mouseover(lbl_myaccount_xpath);

			Actions abt = new Actions(driver);
			WebElement mySub = driver.findElement(lbl_myaccount_xpath);
			abt.moveToElement(mySub);
			WebElement Logout = driver.findElement(btn_logout_link);
			abt.moveToElement(Logout).click().perform();
			System.out.println("Clicked on Logout");

			Thread.sleep(5000);

		} catch (Exception e) {

			throw new Exception("Click Logout button" + e.getLocalizedMessage());
		}

	}

}

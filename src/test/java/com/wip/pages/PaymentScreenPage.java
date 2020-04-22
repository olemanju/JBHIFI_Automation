package com.wip.pages;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.wip.util.BasePageObject;

public class PaymentScreenPage extends BasePageObject {
	public PaymentScreenPage(WebDriver driver) {
		super(driver);
	}

	private final static Logger LOGGER = Logger.getLogger(PaymentScreenPage.class.getName());
	Boolean flag = false;

	// By txt_returntoCart_xpath=By.xpath("//span[text()='Return to cart']");
	By txt_returntoCart_xpath = By.xpath("//span[@class='step__footer__previous-link-content']");

	public boolean isReturnToCartDisplayed() throws Exception {
		try {
			waitTillElementTobeVisible(txt_returntoCart_xpath, 30);
			flag = isElementPresent(txt_returntoCart_xpath);

		} catch (Exception e) {
			throw new Exception("Retrun to cart is displayed::" + isReturnToCartDisplayed() + e.getLocalizedMessage());
		}
		return flag;
	}

	public Searchpage clickOnReturntoCart() throws Exception {
		try {
			isReturnToCartDisplayed();
			javaScriptClick(txt_returntoCart_xpath);
		} catch (Exception e) {

			throw new Exception("FAILED CLICKING ON RETURN TO CART" + e.getLocalizedMessage());
		}
		return new Searchpage(driver);
	}
}

package com.wip.test;

import java.lang.reflect.Method;
import java.util.logging.LogManager;

//import org.apache.logging.log4j.*;
import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.LogStatus;
import com.wip.common.HIFIConstants;
import com.wip.pages.Homepage;
import com.wip.pages.LoginPage;
import com.wip.pages.MyAccountPage;
import com.wip.pages.PaymentScreenPage;
import com.wip.pages.ProductDetailsPage;
import com.wip.pages.Searchpage;
import com.wip.util.BaseTestObject;
import com.wip.ExtentReports.ExtentTestManager;

//@Listeners(com.wip.util.ListenerTest.class)
public class LoginPageTest extends BaseTestObject {

	// Fetching the values from the property file
	public String username = ObjProperty.getProperty("username");
	public String password = ObjProperty.getProperty("password");
	public String searchItem = ObjProperty.getProperty("searchItem");
	public String searchItem_sel = ObjProperty.getProperty("selectItem");
	public String invalid_username = ObjProperty.getProperty("invalid_username");
	public String Invalid_password = ObjProperty.getProperty("invalid_password");

	// Creating the objects for the Classes
	Homepage objhomepage;
	LoginPage objloginpage;
	Searchpage objsearchpage;
	ProductDetailsPage objproductdescriptionpage;
	PaymentScreenPage objpaymentscreenpage;
	MyAccountPage objmyaccountpage;

	// Extent manager Obj
	ExtentTestManager extentlog;

	boolean flag = false;

	String home_page_title = null;
	String Login = null;
	String Enter_search_value = null;
	String model = null;
	String SKU = null;
	String price_val = null;
	String instock_val = null;

	// @Parameters({"browserType"})
	/**
	 * @author Manjunath Below Test case coveres End to end flow Testcase
	 * 
	 * @throws Exception
	 */
	@Test(priority = 0, enabled = true, description = "Login with Valid User and Search for a product and place an order")
	public void verifyEndtoEndFlowOfPurchasingtheProduct(Method method) throws Exception {
		try {
			// Reporter Log Starts for the Test case
			ExtentTestManager.startTest(method.getName(),
					"Valid Login Scenario with valid Username and Valid Password.");

			objhomepage = new Homepage(driver);
			home_page_title = objhomepage.verifyHomePageTitle();
			// Print in the Report HomePage Title
			ExtentTestManager.getTest().log(LogStatus.PASS, "Home Page Title is " + home_page_title);
			flag = objhomepage.isMyAccountDisplayed();

			objloginpage = objhomepage.clickOnMyAccount();
			Login = objloginpage.getMyLoginText();

			// Login functionality
			objsearchpage = objloginpage.enterValidCredentials(username, password);
			ExtentTestManager.getTest().log(LogStatus.PASS, "Entered Valid Username and Password");

			// Enter the Product details
			objsearchpage.enterProductNameToSearch(searchItem);
			Enter_search_value = objsearchpage.getExtrcatedText();
			ExtentTestManager.getTest().log(LogStatus.PASS, "Entered the product Name as " + Enter_search_value);
			// Select the Product
			objproductdescriptionpage = objsearchpage.ClickOnProduct();

			// Verify the Product Name, Model, Price, SKU Details
			objproductdescriptionpage.verifyTheProductNameDescriptionPriceDetails();
			ExtentTestManager.getTest().log(LogStatus.PASS,
					"Verified the Name, Model Number, SKU, Price and available to buy ");

			// Add to Cart and check out
			objproductdescriptionpage.clickOnAddToCart();
			objproductdescriptionpage.clickOnMyCart();
			ExtentTestManager.getTest().log(LogStatus.PASS, "Product Added to the Cart ");

			objpaymentscreenpage = objproductdescriptionpage.clickOnCheckOut();
			ExtentTestManager.getTest().log(LogStatus.PASS, "user has check out the product");
			objsearchpage = objpaymentscreenpage.clickOnReturntoCart();

			objsearchpage.isMyAccountDisplayed();
			objsearchpage.ClickOnMyAccountButtont();
			objsearchpage.ClickOnLogout();
			ExtentTestManager.getTest().log(LogStatus.PASS, "user has logout successfully");

		} catch (Exception e) {
			throw new Exception("FAILED COMPELTE THE END TO FLOW " + e.getLocalizedMessage());
		}

	}

	/**
	 * 
	 * @author Manjunath Below Test case coveres the Login and Logout functionality
	 * 
	 * @throws Exception
	 */

	@Test(priority = 1, enabled = true, description = "Verify Succesful Login functionality with valid credentials")
	public void verifySuccessfullLoginFunctionality(Method method) throws Exception {
		try {
			// Reporter Log Starts for the Test case
			ExtentTestManager.startTest(method.getName(),
					"Valid Login Scenario with valid Username and Valid Password.");
			objhomepage = new Homepage(driver);
			home_page_title = objhomepage.verifyHomePageTitle();

			// Print in the Report HomePage Title
			ExtentTestManager.getTest().log(LogStatus.PASS, "Home Page Title is " + home_page_title);
			flag = objhomepage.isMyAccountDisplayed();
			objloginpage = objhomepage.clickOnMyAccount();
			Login = objloginpage.getMyLoginText();
			// Enter the Valid User name and Password
			objsearchpage = objloginpage.enterValidCredentials(username, password);
			ExtentTestManager.getTest().log(LogStatus.PASS, "Entered Valid Username and Password");

			objsearchpage.isMyAccountDisplayed();
			ExtentTestManager.getTest().log(LogStatus.PASS, "My account screen is displayed");
			objsearchpage.ClickOnMyAccountButtont();
			// Logout
			objsearchpage.ClickOnLogout();
			ExtentTestManager.getTest().log(LogStatus.PASS, "Clicked on Logout");

		} catch (Exception e) {

			throw new Exception("Failed to Complete the Valid Login Functionality" + e.getLocalizedMessage());
		}

	}

	/**
	 * @author Manjunath Below Test case covers Invalid Login functionality
	 * 
	 * @throws Exception
	 */
	@Test(priority = 2, enabled = true, description = "Invalid Login Scenario with wrong username and password")
	public void verifyInvalidLoginFunctionality(Method method) throws Exception {
		try {
			// Reporter Log Starts for the Test case
			ExtentTestManager.startTest(method.getName(),
					"Invalid Login Scenario with Invalid Username and Valid Password.");
			objhomepage = new Homepage(driver);
			home_page_title = objhomepage.verifyHomePageTitle();

			// Print in the Report HomePage Title
			ExtentTestManager.getTest().log(LogStatus.PASS, "Home Page Title is " + home_page_title);
			flag = objhomepage.isMyAccountDisplayed();
			if (flag == true) {
				ExtentTestManager.getTest().log(LogStatus.PASS, "MyAccount Element is displayed on the Page");
			} else {
				// It will print this in the Report if the My Account is missing
				ExtentTestManager.getTest().log(LogStatus.FAIL,
						"MyAccount Element is Not displayed on the Page or Not Able click on it at this moment");
			}
			objloginpage = objhomepage.clickOnMyAccount();

			// Enter Invalid Credentials to Verify Error Message
			objloginpage.enterInvalidCredentials(invalid_username, Invalid_password);
			// Entered Invalid Username and Password
			ExtentTestManager.getTest().log(LogStatus.PASS, "Entered Username and Password");
			flag = objloginpage.isErrorMessageDisplayed();
			String msg = objloginpage.getErrorMessage();
			// Log the Error Message
			ExtentTestManager.getTest().log(LogStatus.PASS,
					"Error Message is displayed for the Invalid Login Entry, Error Message is " + msg);
			objloginpage.homePageScreen();

		} catch (Exception e) {
			throw new Exception("Invalid Login Scenario with Invalid Username and Valid Password Is Failed"
					+ e.getLocalizedMessage());
		}

	}

	/**
	 * @author Manjunath Below Test case covers Invalid Login functionality
	 * 
	 * @throws Exception
	 */
	@Test(priority = 3, enabled = true, description = "To verify the screenshot is captured when there is a failure")
	public void verifyTheScreenCaptureFunctionalityForFailedCase(Method method) throws Exception {
		try {
			// Reporter Log Starts for the Test case
			ExtentTestManager.startTest(method.getName(),
					"Invalid Login Scenario with Invalid Username and Valid Password.");
			objhomepage = new Homepage(driver);
			home_page_title = objhomepage.verifyHomePageTitle();

			// Print in the Report HomePage Title
			ExtentTestManager.getTest().log(LogStatus.PASS, "Home Page Title is " + home_page_title);
			flag = objhomepage.isMyAccountDisplayed();
			if (flag == true) {
				ExtentTestManager.getTest().log(LogStatus.PASS, "MyAccount Element is displayed on the Page");
			} else {
				// It will print this in the Report if the My Account is missing
				ExtentTestManager.getTest().log(LogStatus.FAIL,
						"MyAccount Element is Not displayed on the Page or Not Able click on it at this moment");
			}
			objloginpage = objhomepage.clickOnMyAccount();

			// Entered Invalid Locator to capture Screenshot
			flag = objloginpage.isForgotPasswordDisplayed();
			if (flag == true) {
				ExtentTestManager.getTest().log(LogStatus.PASS,
						"Forget Password is displayed on the Page and Locator is correct");
			} else {
				// It will print this in the Report if the My Account is missing
				ExtentTestManager.getTest().log(LogStatus.FAIL,
						"Forgot password is not displayed or chnage the locator");
			}
			// Enter Invalid Credentials to Verify Error Message
			objloginpage.enterInvalidCredentials(invalid_username, Invalid_password);

		} catch (Exception e) {
			throw new Exception(
					"To verify the screenshot is captured when there is a failure Is Failed" + e.getLocalizedMessage());
		}

	}

}

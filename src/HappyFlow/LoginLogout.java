package HappyFlow;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import BaseClass.TestBase;

public class LoginLogout extends TestBase{

	public LoginLogout() {
		super();
	}

	@BeforeClass
	public void setupBrowser() throws Exception{	
		launchBrowser();
	}

	@Test (priority =1)
	public void loadURL(){	
		logger = report.createTest("TC-002:LoginLogout");
		logger.info("Starting of loading URL");
		Reporter.log("Starting of loading URL" , true);
		loadUrl(properties.getProperty("testurl"));
		logger.pass("Loading URL is successful");
		Reporter.log("Loading URL is successful" , true);
		//Assert.assertEquals(driver.getTitle(), "Login - My Store");
	}

	@Test (priority =2 , dependsOnMethods = {"loadURL"})
	public void testLogin(){
		logger.info("Starting of login");
		Reporter.log("Starting of login" , true);
		WebElement emailField = driver.findElement(By.id(pr.getProperty("email_id")));		
		emailField.sendKeys(properties.getProperty("emailid"));

		WebElement passwordField = driver.findElement(By.xpath(pr.getProperty("passwordField_xpath")));
		passwordField.sendKeys(properties.getProperty("password"));

		WebElement btnSignIn= driver.findElement(By.id(pr.getProperty("btnSignin_id")));
		if(btnSignIn.isEnabled()){
			btnSignIn.click();
		}

		Assert.assertTrue(driver.findElement(By.xpath(pr.getProperty("signout_xpath"))).isDisplayed(), "Login is not successful");
		logger.pass("Login is successful");
		Reporter.log("Login is successful" , true);

	}

	@Test (priority =2 , dependsOnMethods = {"loadURL" , "testLogin"})
	public void testLogOut(){
		logger.info("Starting of logout");
		Reporter.log("Starting of logout" , true);
		WebElement btnSignOut= driver.findElement(By.xpath(pr.getProperty("signout_xpath")));
		if(btnSignOut.isEnabled()){
			btnSignOut.click();
		}

		Assert.assertTrue(driver.findElement(By.id(pr.getProperty("btnSignin_id"))).isDisplayed(), "Logout is not successful");
		logger.pass("Logout is successful");
		Reporter.log("Logout is successful" , true);
	}

	@AfterTest
	public void endReporting() {
		endReport();
	}

	@AfterClass	
	public void closeBrowser() {		
		teardownBrowser();

	}

}

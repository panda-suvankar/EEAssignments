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

public class CreateAccount extends TestBase {

	public CreateAccount() {

		super();
	}

	@BeforeClass
	public void setupBrowser() throws Exception{
		launchBrowser();
	}
	@Test (priority =1)
	public void loadURL(){
		logger = report.createTest("TC-001:Create Account");
		logger.info("Starting of loading URL");
		Reporter.log("Starting of loading URL" , true);
		loadUrl(properties.getProperty("testurl"));
		Assert.assertEquals(driver.getTitle(), "Login - My Store");
		logger.pass("Loading URL is successful");
		Reporter.log("Loading URL is successful" , true);
	}

	@Test (priority =2 , dependsOnMethods = {"loadURL"} )
	public void createAccount() throws Exception {
		logger.info("Starting of Create Account");
		Reporter.log("Starting of Create Account" , true);
		WebElement emailField = driver.findElement(By.xpath(pr.getProperty("emailField_xpath")));		
		emailField.clear();	
		emailField.sendKeys(properties.getProperty("emailid"));

		WebElement btnSubmit = driver.findElement(By.id(pr.getProperty("btnsubmit_id")));
		if(btnSubmit.isEnabled()){
			btnSubmit.click();
		}

		Thread.sleep(5000);
		WebElement radioGender = driver.findElement(By.id(pr.getProperty("radiogender_id")));
		radioGender.click();

		WebElement firstnameField = driver.findElement(By.xpath(pr.getProperty("firstnameField_xpath")));		
		firstnameField.sendKeys(properties.getProperty("firstname"));

		WebElement lastnameField = driver.findElement(By.xpath(pr.getProperty("lastnameField_xpath")));		
		lastnameField.sendKeys(properties.getProperty("lastname"));

		WebElement passwordField = driver.findElement(By.xpath(pr.getProperty("passwordField_xpath")));
		passwordField.sendKeys(properties.getProperty("password"));

		selectDropdownList("days" , properties.getProperty("dayOfBirth"));
		selectDropdownList("months" , properties.getProperty("monthOfBirth"));
		selectDropdownList("years" , properties.getProperty("yearOfBirth"));

		WebElement addressField = driver.findElement(By.id(pr.getProperty("addressField_id")));
		addressField.sendKeys(properties.getProperty("address"));
		WebElement cityField = driver.findElement(By.id(pr.getProperty("cityFiled_id")));
		cityField.sendKeys(properties.getProperty("city"));

		selectDropdownList("id_state" , properties.getProperty("state"));

		WebElement postalField = driver.findElement(By.id(pr.getProperty("postalcodeField_id")));
		postalField.sendKeys(properties.getProperty("postcode"));
		WebElement mobileField = driver.findElement(By.id(pr.getProperty("mobilephoneField_id")));
		mobileField.sendKeys(properties.getProperty("mobileNo"));

		WebElement btnRegister = driver.findElement(By.id(pr.getProperty("btnregister_id")));
		if(btnRegister.isEnabled()){
			btnRegister.click();
		}

		Assert.assertEquals(driver.findElement(By.xpath(pr.getProperty("accountHolderName_xpath"))).getText(), properties.getProperty("expectedAccountHolderName"));
		logger.pass("Account is created successfully");
		Reporter.log("Account is created successfully" , true);
	}

	@Test (priority =3 , dependsOnMethods = {"loadURL" ,"createAccount"})	
	public void accountSignout() {
		logger.info("Starting of Account Signout");
		Reporter.log("Starting of Account Signout" , true);
		signout();
		logger.pass("Account got signout successfully");
		Reporter.log("Account got signout successfully" , true);
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

package HappyFlow;

import java.text.DecimalFormat;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import BaseClass.TestBase;

public class AddProductsToCart extends TestBase {
	DecimalFormat df2;
	double totalCost = 0.0;

	public AddProductsToCart() {

		super();
	}

	@BeforeClass
	public void setupBrowser() throws Exception{
		launchBrowser();
	}

	@Test (priority =1)
	public void loadURL(){
		logger = report.createTest("TC-003:AddProductsToCart");
		logger.info("Starting of loading URL");
		Reporter.log("Starting of loading URL" , true);
		loadUrl(properties.getProperty("testurl"));
		logger.pass("Loading URL is successful");
		Reporter.log("Loading URL is successful" , true);
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

	@Test (priority =3 , dependsOnMethods = {"testLogin" , "loadURL"})
	public void addProductsToCart() throws Exception{
		logger.info("Starting of add products to cart");
		Reporter.log("Starting of add products to cart" , true);
		WebElement menuDress = driver.findElement(By.xpath(pr.getProperty("dressMenu_xpath")));
		menuDress.click();

		List<WebElement> dressImages = driver.findElements(By.xpath(pr.getProperty("productImage_xpath")));

		for (int i=1 ; i<(dressImages.size()-2) ; i++) {

			String dressImage_FirstPart = "(//a[@class='product_img_link']/img)[";
			int dressImage_secondPart =i;
			String dressImage_thirdPart = "]";

			String dressImage_xpath = dressImage_FirstPart + dressImage_secondPart + dressImage_thirdPart ;

			WebElement dressImage = driver.findElement(By.xpath(dressImage_xpath));				
			mouseHoverAction(dressImage);
			Thread.sleep(3000);

			String addToCart_FirstPart = "(//a[@title='Add to cart'])[";
			int addToCart_SecondPart =i;
			String addToCart_ThirdPart = "]";
			String addToCart_xpath = addToCart_FirstPart + addToCart_SecondPart + addToCart_ThirdPart ;			

			WebElement addToCart = driver.findElement(By.xpath(addToCart_xpath));
			addToCart.click();
			Thread.sleep(3000);
			driver.findElement(By.xpath(pr.getProperty("btnCloseWindow_xpath"))).click();
		}
		
		logger.pass("Products are added to cart successfully");
		Reporter.log("Products are added to cart successfully" , true);

	}

	@Test (priority =4 , dependsOnMethods = {"testLogin","loadURL" , "addProductsToCart"})
	public void completePurchaseWithoutShippingCost() throws Exception{
		logger.info("Starting of complete purchase without shipping cost");
		Reporter.log("Starting of complete purchase without shipping cost" , true);
		WebElement cart = driver.findElement(By.xpath(pr.getProperty("btnCart")));				
		mouseHoverAction(cart);
		Thread.sleep(3000);		
		driver.findElement(By.xpath(pr.getProperty("btnCheckOut"))).click();

		List<WebElement>rows = driver.findElements(By.xpath("//table[@id='cart_summary']/tbody/tr"));

		for(int i=1 ;i<=rows.size() ; i++) {
			String firstPart = "//table[@id='cart_summary']/tbody/tr[";
			int secondPart = i ;
			String thirdPart = "]/td[6]";
			String totalCost_xpath = firstPart+secondPart+thirdPart;
			String Cost = driver.findElement(By.xpath(totalCost_xpath)).getText();

			String [] itemCost = Cost.split("\\$");
			for(int j=1 ; j<itemCost.length ;j++) {
				double cost = Double.parseDouble(itemCost[j]);
				totalCost = totalCost +cost ;

			}

		}
		df2 = new DecimalFormat("#.##");											
		String expectedTotalCost = df2.format(totalCost);
		String expectedFinalCost = "$"+ expectedTotalCost;	
		String actualTotalCost = driver.findElement(By.xpath(pr.getProperty("totalProduct_xpath"))).getText();				
		Assert.assertEquals(actualTotalCost, expectedFinalCost);
		logger.pass("Purchase without shipping cost completed successfully");
		Reporter.log("Purchase without shipping cost completed successfully" , true);

	}

	@Test(priority =5 , dependsOnMethods = {"testLogin","loadURL" , "addProductsToCart"})	
	public void verifyFinalTotalprice(){
		logger.info("Starting of verification of total price includes shipping cost");
		Reporter.log("Starting of verification of total price includes shipping cost" , true);
		String shippingCost = driver.findElement(By.xpath(pr.getProperty("totalShipping_xpath"))).getText();
		String [] shipCost = shippingCost.split("\\$");
		for(int j=1 ; j<shipCost.length ;j++) {
			double totalShipCost = Double.parseDouble(shipCost[j]);
			double expectedTotalPrice = totalCost +totalShipCost;
			df2 = new DecimalFormat("#.##");											
			String expectedTotalCost = df2.format(expectedTotalPrice);
			String expectedFinalCost = "$"+ expectedTotalCost;
			String actualFinalCost = driver.findElement(By.xpath(pr.getProperty("totalPrice_xpath"))).getText();				
			Assert.assertEquals(actualFinalCost, expectedFinalCost);
			logger.pass("Total price verified  successfully by including shipping cost");
			Reporter.log("Total price verified  successfully by including shipping cost" , true);

		}

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

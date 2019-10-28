package BaseClass;

import java.io.File;
import java.io.FileInputStream;
import java.util.Properties;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;

/*@Author = Suvankar Panda
 * Date= 27th Oct , 2019
 * This is the base class which is created to have all common methods required for this assignments for reusing code.
 * 
 * 
 */
public class TestBase {

	public Properties properties;
	public Properties pr;
	public WebDriver driver;
	public ExtentReports report;
	public ExtentTest logger;

	// Reading configuration file
	public void readConfigFile() throws Exception{
		properties = new Properties();
		FileInputStream fis = new FileInputStream(System.getProperty("user.dir")+"\\src\\Properties\\Config.Properties");
		properties.load(fis);
	}

	// Reading OR file
	public void readORFile() throws Exception{
		pr = new Properties();
		FileInputStream fis = new FileInputStream(System.getProperty("user.dir")+"\\src\\Properties\\OR.Properties");
		pr.load(fis);
	}

	public void setExtentReport() {
		
		ExtentHtmlReporter extent = new ExtentHtmlReporter(new File(System.getProperty("user.dir")+"/Reports/ExtentReport.html"));
		report = new ExtentReports();
		report.attachReporter(extent);
	}
	
	//Launch Chrome browser 
	public void launchBrowser() throws Exception{ 
		readConfigFile();
		readORFile();
		setExtentReport();

		String browserName = properties.getProperty("browser");

		if(browserName.equals("Chrome")){
			System.setProperty("webdriver.chrome.driver",System.getProperty("user.dir")+"\\chromedriver.exe");
			driver = new ChromeDriver();
			driver.manage().window().maximize();
			driver.manage().timeouts().implicitlyWait(3000, TimeUnit.SECONDS);

		}
	}

	//Loading URL
	public void loadUrl(String url){
		driver.get(url);

	}
	public void mouseHoverAction(WebElement element){

		Actions act = new Actions(driver);
		act.moveToElement(element).perform();
	}
	public void clickOnMouseHover(WebElement menu){

		Actions act = new Actions(driver);
		act.moveToElement(menu).click().build().perform();
	}

	public void waitForElement (WebElement element) {
		WebDriverWait wait = new WebDriverWait (driver , 10);
		wait.until(ExpectedConditions.visibilityOf(element));
	}

	public void selectDropdownList(String dropDownMenu , String value) {
		Select se = new Select (driver.findElement(By.id(dropDownMenu)));
		se.selectByValue(value);
	}
	
	public void signout() {
		
		driver.findElement(By.xpath(pr.getProperty("signout_xpath"))).click();
		
	}
	
	public void endReport(){
		report.flush();
	}
	//Closing browser
	public void teardownBrowser(){

		if (driver != null){
			driver.quit();
		}
	}

}

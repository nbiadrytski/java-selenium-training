package com.seleniumjava.base;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import com.seleniumjava.utilities.ExcelReader;
import com.seleniumjava.utilities.ExtentManager;
import com.seleniumjava.utilities.TestUtil;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

public class TestBase {

    protected static WebDriver driver;
    private static Properties config = new Properties();
    protected static Properties OR = new Properties();
    private static FileInputStream fileStream;
    protected static Logger log = Logger.getLogger("rootLogger");
    
    private static String workDir = System.getProperty("user.dir");
    private static String pathSeparator = File.separator;  // detects path separator by OS
    private static String configPropertiesPath =
            workDir + pathSeparator + "src" + pathSeparator + "test" + pathSeparator + "resources" + pathSeparator  + "properties" + pathSeparator  + "Config.properties";
    private static String orPropertiesPath =
            workDir + pathSeparator + "src" + pathSeparator + "test" + pathSeparator + "resources" + pathSeparator  + "properties" + pathSeparator  + "OR.properties";
    private static String testDataPath =
            workDir + pathSeparator + "src" + pathSeparator + "test" + pathSeparator + "resources" + pathSeparator  + "excel" + pathSeparator  + "testdata.xlsx";
    protected static ExcelReader excel = new ExcelReader(testDataPath);
    protected static WebDriverWait wait;
    protected ExtentReports rep = ExtentManager.getInstance();
    protected static ExtentTest test;
    
    @BeforeSuite
    public void setUp() {
        if (driver == null) {
            try {
                fileStream = new FileInputStream(configPropertiesPath);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            try {
                config.load(fileStream);
                log.info("Config file loaded!!!");
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                fileStream = new FileInputStream(orPropertiesPath);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            try {
                OR.load(fileStream);
                log.info("OR file loaded!!!");
            } catch (IOException e) {
                e.printStackTrace();
            }

            if (config.getProperty("browser").equals("firefox")) {
                WebDriverManager.firefoxdriver().setup();
                driver = new FirefoxDriver();
                log.info("Firefox launched!!!");
            } else if (config.getProperty("browser").equals("chrome")){
                WebDriverManager.chromedriver().setup();
                driver = new ChromeDriver();
                log.info("Chrome launched!!!");
            }

            driver.manage().window().maximize();
            driver.get(config.getProperty("testsiteurl"));
            log.info("Navigated to: " + config.getProperty("testsiteurl"));
            driver.manage().timeouts().implicitlyWait(Integer.parseInt(config.getProperty("implicit.wait")), TimeUnit.SECONDS);
            wait = new WebDriverWait(driver, 5);
        }
    }

    protected void click(String locator) {
        
        if(locator.endsWith("_CSS")) {
            driver.findElement(By.cssSelector(OR.getProperty(locator))).click();
        } else if(locator.endsWith("_XPATH")) {
            driver.findElement(By.xpath(OR.getProperty(locator))).click();
        } else if(locator.endsWith("_ID")) {
            driver.findElement(By.id(OR.getProperty(locator))).click();
        }
        // Extent Report logger
        test.log(LogStatus.INFO, "Clicking on: " + locator);
    }
    
    protected void type(String locator, String value) {

        if(locator.endsWith("_CSS")) {
            driver.findElement(By.cssSelector(OR.getProperty(locator))).sendKeys(value);
        } else if(locator.endsWith("_XPATH")) {
            driver.findElement(By.xpath(OR.getProperty(locator))).sendKeys(value);
        } else if(locator.endsWith("_ID")) {
            driver.findElement(By.id(OR.getProperty(locator))).sendKeys(value);
        }
        test.log(LogStatus.INFO, "Typing in: " + locator + " entered value as " + value);
    }
    
    private static WebElement dropdown;
    
    protected void selectFromDropdown(String locator, String value) {

        if(locator.endsWith("_CSS")) {
            dropdown = driver.findElement(By.cssSelector(OR.getProperty(locator)));
        } else if(locator.endsWith("_XPATH")) {
            dropdown = driver.findElement(By.xpath(OR.getProperty(locator)));
        } else if(locator.endsWith("_ID")) {
            dropdown = driver.findElement(By.id(OR.getProperty(locator)));
        }

        Select select = new Select(dropdown);
        select.selectByVisibleText(value);
        test.log(LogStatus.INFO, "Selecting from dropdown: " + locator + " value as " + value);
        
    }
    
    protected boolean isElementPresent(By by) {
        try {
            driver.findElement(by);
            return true;

        }catch (NoSuchElementException e) {
            return false;
        }
    }
    
    public static void verifyEquals(String expected, String actual) throws IOException {
        
        try {
            Assert.assertEquals(actual, expected);
        } catch(Throwable t) {
            TestUtil.captureScreenshot();
            //ReportNG
            Reporter.log("<br>" + "Verification failure: " + t.getMessage() + "<br>");
            Reporter.log("<a target=\"_blank\" href=" + TestUtil.screenshotName + "><img src=" + TestUtil.screenshotName + " height=200 width=200></img></a>");
            Reporter.log("<br>");
            // Extent Report
            test.log(LogStatus.FAIL, " Verification failure with exception: " + t.getMessage());
            test.log(LogStatus.FAIL, test.addScreenCapture(TestUtil.screenshotName));
        }
        
    }

    @AfterSuite
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
        log.info("Tests execution completed!!!");
    }
}

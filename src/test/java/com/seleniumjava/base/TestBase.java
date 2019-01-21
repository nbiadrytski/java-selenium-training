package com.seleniumjava.base;

import com.seleniumjava.utilities.ExcelReader;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
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
    
    private static String pathSeparator = File.separator;  // detects path separator by OS
    private static String configPropertiesPath = pathSeparator + "src" + pathSeparator + "test" + pathSeparator + "resources" + pathSeparator  + "properties" + pathSeparator  + "Config.properties";
    private static String orPropertiesPath = pathSeparator + "src" + pathSeparator + "test" + pathSeparator + "resources" + pathSeparator  + "properties" + pathSeparator  + "OR.properties";
    private static String testDataPath = pathSeparator + "src" + pathSeparator + "test" + pathSeparator + "resources" + pathSeparator  + "excel" + pathSeparator  + "testdata.xlsx";
    
    protected static ExcelReader excel = new ExcelReader(System.getProperty("user.dir") + testDataPath);
    protected static WebDriverWait wait;
    
    @BeforeSuite
    public void setUp() {
        if (driver == null) {
            try {
                fileStream = new FileInputStream(System.getProperty("user.dir") + configPropertiesPath);
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
                fileStream = new FileInputStream(System.getProperty("user.dir") + orPropertiesPath);
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

    protected boolean isElementPresent(By by) {
        try {
            driver.findElement(by);
            return true;

        }catch (NoSuchElementException e) {
            return false;
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

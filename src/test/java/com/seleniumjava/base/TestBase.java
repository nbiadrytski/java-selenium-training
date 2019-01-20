package com.seleniumjava.base;

import com.seleniumjava.utilities.ExcelReader;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

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
    public static ExcelReader excel = new ExcelReader(
            System.getProperty("user.dir") + "\\src\\test\\resources\\excel\\testdata.xlsx");
    public static WebDriverWait wait;

    @BeforeSuite
    public void setUp() {
        if (driver == null) {
            try {
                fileStream = new FileInputStream(
                        System.getProperty("user.dir") + "\\src\\test\\resources\\properties\\Config.properties");
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            try {
                config.load(fileStream);
                log.debug("Config file loaded!!!");
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                fileStream = new FileInputStream(
                        System.getProperty("user.dir") + "\\src\\test\\resources\\properties\\OR.properties");
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            try {
                OR.load(fileStream);
                log.debug("OR file loaded!!!");
            } catch (IOException e) {
                e.printStackTrace();
            }

            if (config.getProperty("browser").equals("firefox")) {
                driver = new FirefoxDriver();
                log.debug("Firefox launched!!!");
            } else if (config.getProperty("browser").equals("chrome")){
                driver = new ChromeDriver();
                log.debug("Chrome launched!!!");
            }

            driver.manage().window().maximize();
            driver.get(config.getProperty("testsiteurl"));
            log.debug("Navigated to: " + config.getProperty("testsiteurl"));
            driver.manage().timeouts().implicitlyWait(Integer.parseInt(config.getProperty("implicit.wait")), TimeUnit.SECONDS);
            wait = new WebDriverWait(driver, 5);
        }
    }

    public boolean isElementPresent(By by) {
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
        log.debug("Tests execution completed!!!");
    }
}

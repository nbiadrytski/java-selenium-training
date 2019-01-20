package com.seleniumjava.testcases;

import com.seleniumjava.base.TestBase;
import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.Test;

public class BankManagerLoginTest extends TestBase {
    @Test
    public void loginAsBankManager() {

        log.debug("Inside Login Test");
        Reporter.log("Inside Login Test");  // will be added to testng report

        driver.findElement(By.xpath(OR.getProperty("bmlBtn"))).click();

        // Javadoc CTRL + Q (Windows), CTRL + J (Mac)
        Assert.assertTrue(isElementPresent(By.cssSelector(OR.getProperty("addCustomerBtn"))), "Login not successful");

        Reporter.log("Login successfully executed");  // will be added to testng report
    }
}

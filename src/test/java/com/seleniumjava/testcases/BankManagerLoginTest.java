package com.seleniumjava.testcases;

import com.seleniumjava.base.TestBase;
import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.Test;

import java.io.IOException;

public class BankManagerLoginTest extends TestBase {
    
    @Test
    public void bankManagerLoginTest() throws IOException, InterruptedException {

        //verifyEquals("abc", "xyz");  // soft assert
        Thread.sleep(3000);
        
        log.info("Inside Login Test");
        Reporter.log("Inside Login Test");  // will be added to testng report

        click("bmlBtn_CSS");

        Assert.assertTrue(isElementPresent(By.cssSelector(OR.getProperty("addCustomerBtn_CSS"))), "Login not successful");

        Reporter.log("Login successfully executed");
    }
}

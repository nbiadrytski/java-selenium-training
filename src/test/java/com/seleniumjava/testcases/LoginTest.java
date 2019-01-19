package com.seleniumjava.testcases;

import com.seleniumjava.base.TestBase;
import org.openqa.selenium.By;
import org.testng.annotations.Test;

public class LoginTest extends TestBase {
    @Test
    public void loginAsBankManager() throws InterruptedException {

        log.debug("Inside Login Test");

        driver.findElement(By.xpath(OR.getProperty("bmlBtn"))).click();

        log.debug("Login successfully executed");

        Thread.sleep(3000);

    }
}

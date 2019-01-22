package com.seleniumjava.testcases;

import com.seleniumjava.base.TestBase;
import com.seleniumjava.utilities.TestUtil;
import org.openqa.selenium.Alert;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class OpenAccountTest extends TestBase{

    @Test(dataProviderClass = TestUtil.class, dataProvider = "dp")
    public void openAccountTest(String customer, String currency) throws InterruptedException {
        
        click("openaccount_CSS");

        selectFromDropdown("customer_CSS", customer);
        selectFromDropdown("currency_CSS", currency);
        
        click("process_CSS");
        Thread.sleep(3000);

        Alert alert = wait.until(ExpectedConditions.alertIsPresent());
        alert.accept();
        
    }

}

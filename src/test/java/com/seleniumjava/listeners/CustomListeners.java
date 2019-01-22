package com.seleniumjava.listeners;

import com.relevantcodes.extentreports.LogStatus;
import com.seleniumjava.base.TestBase;
import com.seleniumjava.utilities.TestUtil;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.SkipException;

import java.io.IOException;

public class CustomListeners extends TestBase implements ITestListener {
    public void onTestStart(ITestResult result) {
        // start ExtentReport
        test = rep.startTest(result.getName().toUpperCase());
    }

    public void onTestSuccess(ITestResult result) {
        // ExtentReport logging
        test.log(LogStatus.PASS, result.getName().toUpperCase() + " PASS");
        rep.endTest(test);
        rep.flush();
    }

    public void onTestFailure(ITestResult result) {
        System.setProperty("org.uncommons.reportng.escape-output", "false");

        try {
            TestUtil.captureScreenshot();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // ExtentReport logging
        test.log(LogStatus.FAIL, result.getName().toUpperCase() + " FAILED with exception: " + result.getThrowable());
        test.log(LogStatus.FAIL, test.addScreenCapture(TestUtil.screenshotName));

        Reporter.log("Click to see screenshot");
        Reporter.log("<a target=\"_blank\" href=" + TestUtil.screenshotName + ">Screenshot</a>");
        Reporter.log("<br>");
        Reporter.log("<br>");
        Reporter.log("<a target=\"_blank\" href=" + TestUtil.screenshotName + "><img src=" + TestUtil.screenshotName + " height=200 width=200></img></a>");  // screenshot at test report

        rep.endTest(test);
        rep.flush();
    }

    public void onTestSkipped(ITestResult result) {
        
        test.log(LogStatus.SKIP, result.getName().toUpperCase() + " Skipped the test as the Run mode is NO");
        rep.endTest(test);
        rep.flush();
    }

    public void onTestFailedButWithinSuccessPercentage(ITestResult result) {

    }

    public void onStart(ITestContext context) {

    }

    public void onFinish(ITestContext context) {

    }
}

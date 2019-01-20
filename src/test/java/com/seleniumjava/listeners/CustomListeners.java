package com.seleniumjava.listeners;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import org.testng.Reporter;

public class CustomListeners implements ITestListener {
    public void onTestStart(ITestResult result) {

    }

    public void onTestSuccess(ITestResult result) {

    }

    public void onTestFailure(ITestResult result) {
        System.setProperty("org.uncommons.reportng.escape-output", "false");
        Reporter.log("Capturing screenshot...");
        Reporter.log("<a target=\"_blank\" href=\"C:\\Users\\TOSHIBA\\Desktop\\main.jpg\">Screenshot</a>");  // screenshot at test report
        Reporter.log("<br>");
        Reporter.log("<a target=\"_blank\" href=\"C:\\Users\\TOSHIBA\\Desktop\\main.jpg\">" +
                "<img src=\"C:\\Users\\TOSHIBA\\Desktop\\main.jpg\" height=200 width=200></img></a>");  // screenshot at test report
    }

    public void onTestSkipped(ITestResult result) {

    }

    public void onTestFailedButWithinSuccessPercentage(ITestResult result) {

    }

    public void onStart(ITestContext context) {

    }

    public void onFinish(ITestContext context) {

    }
}

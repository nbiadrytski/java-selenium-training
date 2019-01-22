package com.seleniumjava.utilities;

import com.relevantcodes.extentreports.DisplayOrder;
import com.relevantcodes.extentreports.ExtentReports;

import java.io.File;

public class ExtentManager {
    
    private static ExtentReports extent;
    private static String pathToExtentReport = System.getProperty("user.dir") + File.separator + "target" + File.separator
            + "surefire-reports" + File.separator + "html" + File.separator + "extent.html";
    private static String pathToExtentConfig = System.getProperty("user.dir") + File.separator + "src" + File.separator + "test"
            + File.separator + "resources" + File.separator + "extentconfig" + File.separator + "extent-config.xml";
    
    public static ExtentReports getInstance() {
        
        if(extent == null) {
            extent = new ExtentReports(pathToExtentReport, true, DisplayOrder.OLDEST_FIRST);
            extent.loadConfig(new File(pathToExtentConfig));
        }
        
        return extent;
        
    }
}

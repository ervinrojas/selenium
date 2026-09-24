package com.ervinrojas.util;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

public class ExtentManager {
    private static ExtentReports extent;

    public static ExtentReports getInstance() {
        if (extent == null) {
            createInstance();
        }
        return extent;
    }

    private static void createInstance() {
        // Define dónde se guardará el reporte
        String reportPath = "test-output/ExtentReport.html";
        ExtentSparkReporter sparkReporter = new ExtentSparkReporter(reportPath);

        // Configuración visual
        sparkReporter.config().setDocumentTitle("Automation Report");
        sparkReporter.config().setReportName("Selenium Tests - OrangeHRM");
        sparkReporter.config().setTheme(Theme.DARK); // O Theme.STANDARD

        extent = new ExtentReports();
        extent.attachReporter(sparkReporter);

        // Info del entorno
        extent.setSystemInfo("OS", System.getProperty("os.name"));
        extent.setSystemInfo("Java", System.getProperty("java.version"));
    }
}
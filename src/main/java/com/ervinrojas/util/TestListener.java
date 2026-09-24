package com.ervinrojas.util;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TestListener implements ITestListener {

    private WebDriver driver;
    private static ExtentReports extent = ExtentManager.getInstance();
    private static ThreadLocal<ExtentTest> test = new ThreadLocal<>();

    @Override
    public void onStart(ITestContext context){}

    @Override
    public void onTestStart(ITestResult result){
        ExtentTest extentTest = extent.createTest(result.getName());
        test.set(extentTest);
    }

    @Override
    public void onTestSuccess(ITestResult result){
        test.get().log(Status.PASS, "Test passed");
    }

    @Override
    public void onTestFailure(ITestResult result) {
        System.out.println("TEST FAILED: " + result.getThrowable());

        // 1. Obtener el driver del BaseTest (lo pasaremos en el paso 2)
        String screenshotPath = BaseTest.lastScreenshotPath;
        if(screenshotPath != null){
            try{
                test.get().addScreenCaptureFromPath(screenshotPath);
            }catch (Exception e){
                test.get().log(Status.WARNING, "System cannot attach screenshot to the report");
            }
        }
    }

    @Override
    public void onTestSkipped(ITestResult result){
        test.get().log(Status.SKIP, "Test skipped: "+result.getThrowable());
    }

    @Override
    public void onFinish(ITestContext context){
        extent.flush();
    }

    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult result){}

    private void takeScreenshot(String testName) {
        try {
            // 2. Crear la carpeta si no existe
            String directory = "screenshots/";
            Path path = Paths.get(directory);
            if (!Files.exists(path)) {
                Files.createDirectories(path);
            }

            // 3. Generar nombre de archivo con timestamp
            String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
            String fileName = directory + testName + "_" + timestamp + ".png";

            // 4. Tomar la captura
            File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            Files.copy(screenshot.toPath(), Paths.get(fileName));

            System.out.println("📸 Screenshot guardada en: " + fileName);

        } catch (IOException e) {
            System.out.println("Error guardando screenshot: " + e.getMessage());
        }
    }

}
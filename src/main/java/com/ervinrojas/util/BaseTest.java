package com.ervinrojas.util;

import com.ervinrojas.util.TestListener;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.ITestResult;
import org.testng.annotations.Listeners;
import org.testng.annotations.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;

@Listeners(TestListener.class)
public class BaseTest {

    public WebDriver driver;
    protected ConfigManager config = ConfigManager.getInstance();

    public static String lastScreenshotPath = null;

    @BeforeMethod
    public void setupTest(){
        ChromeOptions options = new ChromeOptions();
        if ("true".equals(System.getenv("CI"))){
            options.addArguments("--headless");
            options.addArguments("--nno-sandbox");
            options.addArguments("--disable-dev-shm-usage");
        }

        driver = new ChromeDriver(options);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(config.getInt("timeout.implicit")));
        // Solo vamos a la URL base, sin inyectar nada
        driver.get(config.getString("base.url"));
    }

    @AfterMethod
    public void tearDown(ITestResult result) throws IOException {
        lastScreenshotPath = null;
        // 1. Si el test falló, tomar screenshot ANTES de cerrar el driver
        if (ITestResult.FAILURE == result.getStatus()) {
            System.out.println("TEST FAILED: " + result.getName());

            // Create folder
            String directory = "screenshots/";
            Path path = Paths.get(directory);
            if (!Files.exists(path)) {
                Files.createDirectories(path);
            }

            // Generate Name
            String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
            String fileName = directory + result.getName() + "_" + timestamp + ".png";

            // Take screenshot
            File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            Files.copy(screenshot.toPath(), Paths.get(fileName));
            System.out.println("📸 Screenshot saved in: " + fileName);
        }

        // 2. Close browser
        if (driver != null) {
            driver.quit();

        }
    }
}

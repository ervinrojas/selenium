package com.ervinrojas.util;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.*;

import java.time.Duration;

public class BaseTest {

    protected WebDriver driver;
    protected ConfigManager config = ConfigManager.getInstance();

    @BeforeMethod
    public void setupTest(){
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(config.getInt("timeout.implicit")));
        // Solo vamos a la URL base, sin inyectar nada
        driver.get(config.getString("base.url"));
    }

    @AfterMethod
    public void tearDown(){
        if(driver != null) {
            driver.quit();
        }
    }
}

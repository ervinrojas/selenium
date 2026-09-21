package com.ervinrojas.util;

import com.ervinrojas.factory.UserFactory;
import com.ervinrojas.pages.LoginPage;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.*;

import java.time.Duration;
import java.util.Set;

public class BaseTest {

    protected WebDriver driver;
    private static Set<Cookie> savedCookies;
    protected ConfigManager config = ConfigManager.getInstance();

    @BeforeSuite
    public void LoginOnce(){
        WebDriver driverLogin = new ChromeDriver();
        driverLogin.get(config.getString("base.url"));

        LoginPage loginPage = new LoginPage(driverLogin);
        UserFactory.LoginCredentials creds = UserFactory.getValidAdminCredentials();
        loginPage.login(creds.username, creds.password);

        WebDriverWait wait = new WebDriverWait(driverLogin, Duration.ofSeconds(config.getInt("timeout.explicit")));
        wait.until(org.openqa.selenium.support.ui.ExpectedConditions.urlContains("dashboard"));

        savedCookies = driverLogin.manage().getCookies();
        System.out.println("Cookies guardadas: "+ savedCookies.size());
        driverLogin.quit();
    }

    @BeforeMethod
    public void setupTest(){
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(config.getInt("timeout.implicit")));
        driver.get(config.getString("base.url"));

        if(savedCookies != null){
            driver.manage().deleteAllCookies();
            for(Cookie cookie : savedCookies){
                driver.manage().addCookie(cookie);
            }
        }
        driver.get(config.getString("base.url")+"web/index.php/dashboard/index");
    }

    @AfterMethod
    public void tearDown(){
        driver.quit();
    }
}

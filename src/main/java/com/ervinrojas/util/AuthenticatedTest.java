package com.ervinrojas.util;

import com.ervinrojas.factory.UserFactory;
import com.ervinrojas.pages.LoginPage;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.*;

import java.time.Duration;
import java.util.Set;

public class AuthenticatedTest extends BaseTest {

    private static Set<Cookie> savedCookies;

    // Se ejecuta UNA vez antes de toda la suite de pruebas autenticadas
    @BeforeSuite
    public void LoginOnce(){
        ChromeOptions options = new ChromeOptions();
        if("true".equals(System.getProperty("headless"))){
            options.addArguments("--headless=new","--no-sandbox","--disable-dev-shm-usage","window-size=1920,1080");
        }
        // Creamos un driver temporal solo para obtener las cookies
        WebDriver driverLogin = new ChromeDriver(options);
        driverLogin.get(config.getString("base.url"));

        LoginPage loginPage = new LoginPage(driverLogin);
        UserFactory.LoginCredentials creds = UserFactory.getValidAdminCredentials();
        loginPage.login(creds.username, creds.password);

        WebDriverWait wait = new WebDriverWait(driverLogin, Duration.ofSeconds(config.getInt("timeout.explicit")));
        wait.until(ExpectedConditions.urlContains("dashboard"));

        savedCookies = driverLogin.manage().getCookies();
        System.out.println("Cookies guardadas: " + savedCookies.size());
        driverLogin.quit();
    }

    // Sobreescribimos el setup para inyectar cookies después de abrir el navegador
    @Override
    @BeforeMethod
    public void setupTest(){
        super.setupTest(); // Llama al setup de BaseTest (abre navegador y va a URL base)

        // Inyectamos cookies si existen
        if(savedCookies != null){
            driver.manage().deleteAllCookies();
            for(Cookie cookie : savedCookies){
                driver.manage().addCookie(cookie);
            }
            // Navegamos directo al dashboard
            driver.get(config.getString("base.url")+"web/index.php/dashboard/index");
        }
    }
}
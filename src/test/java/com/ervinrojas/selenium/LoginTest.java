package com.ervinrojas.selenium;

import com.ervinrojas.pages.LoginPage;
import com.ervinrojas.util.BaseTest;
import com.ervinrojas.util.TestListener;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import java.time.Duration;

@Listeners(TestListener.class)
public class LoginTest extends BaseTest {

    @Test(groups = {"login"})
    public void testLoginExitoso() {
        // 1. Crear el objeto de la página (POM)
        LoginPage loginPage = new LoginPage(driver);

        // 2. Ejecutar la acción de autenticación
        loginPage.login("Admin", "admin123");

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        boolean esDashboard = wait.until(ExpectedConditions.urlContains("dashboard"));

        Assert.assertTrue(esDashboard, "El login falló: No se redirigió al Dashboard");
        System.out.println("Prueba exitosa: Autenticación validada.");
    }
}
package com.ervinrojas.selenium;

import com.ervinrojas.pages.LoginPage;
import com.ervinrojas.util.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;

public class LoginTest extends BaseTest {

    @Test
    public void testLoginExitoso() {
        // 1. Crear el objeto de la página (POM)
        LoginPage loginPage = new LoginPage(driver);

        // 2. Ejecutar la acción de autenticación
        loginPage.login("Admin", "admin123");

        // 3. Validar (Assertion)
        // Verificamos que la URL cambie a "dashboard"
        String urlActual = driver.getCurrentUrl();
        boolean esDashboard = urlActual.contains("dashboard");

        Assert.assertTrue(esDashboard, "El login falló: No se redirigió al Dashboard");

        System.out.println("Prueba exitosa: Autenticación validada.");
    }
}
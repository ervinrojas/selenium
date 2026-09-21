package com.ervinrojas.selenium;

import com.ervinrojas.factory.UserFactory;
import com.ervinrojas.pages.AddUserPage;
import com.ervinrojas.util.AuthenticatedTest;
import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

public class UserTest extends AuthenticatedTest {

    @Test
    public void testCrearNuevoUsuario() throws InterruptedException {

        SoftAssert softAssert = new SoftAssert();
        UserFactory.NewUserData userData = UserFactory.getNewEmployeeData();

        String expectedSuccessMessage = "Successfully Saved";
        // 1. Ir a la sección Add User
        driver.get(config.getString("base.url")+"web/index.php/admin/saveSystemUser");
        AddUserPage addUserPage = new AddUserPage(driver);

        addUserPage.crearUsuario(
                userData.role,
                userData.employeeName,
                userData.status,
                userData.username,
                userData.password
        );

        // 3. Crear nuevo usuario
        String actualMessage = driver.findElement(By.cssSelector("p.oxd-text--toast-message")).getText();
        // 4. Validación (Opcional)
        softAssert.assertEquals(actualMessage, expectedSuccessMessage);
        System.out.println("Usuario creado exitosamente."+userData.username);

        softAssert.assertAll();
    }
}
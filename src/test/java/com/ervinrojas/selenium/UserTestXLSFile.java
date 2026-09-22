package com.ervinrojas.selenium;

import com.ervinrojas.factory.UserFactory;
import com.ervinrojas.pages.AddUserPage;
import com.ervinrojas.util.AuthenticatedTest;
import com.ervinrojas.util.ReadXLSdata;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import java.time.Duration;

public class UserTestXLSFile extends AuthenticatedTest {

    @DataProvider(name = "excelDataProvider")
    public Object[][] getDataFromExcel() {
        String filePath ="src/test/resources/testdata/testdata.xlsx";
        String sheetName = "AddUser";
        return ReadXLSdata.getTestData(filePath, sheetName);
    }

    @Test(dataProvider = "excelDataProvider")
    public void testAddNewUserXLS(String role, String status, String employeeName) throws InterruptedException {

        SoftAssert softAssert = new SoftAssert();
        UserFactory.NewUserData dynamicData = UserFactory.getNewEmployeeData();
        String username = dynamicData.username;
        String password = dynamicData.password;

        //User navigates to Add user page
        driver.get(config.getString("base.url")+"web/index.php/admin/saveSystemUser");
        AddUserPage addUserPage = new AddUserPage(driver);

        addUserPage.crearUsuario(
                role,
                employeeName,
                status,
                username,
                password
        );

        // 3. Crear nuevo usuario
        By toastLocator = By.cssSelector("p.oxd-text--toast-message");
        WebDriverWait waitToast = new WebDriverWait(driver, Duration.ofSeconds(10));

        try{
            waitToast.until(ExpectedConditions.visibilityOfElementLocated(toastLocator));
            String actualMessage = driver.findElement(toastLocator).getText();

            String expectedSuccessMessage = "Successfully Saved";
            softAssert.assertEquals(actualMessage, expectedSuccessMessage, "Failure with: "+role+ " Status: "+status+" employee name: "+employeeName);
            System.out.println("Usuario creado exitosamente."+dynamicData.username);
        }catch (TimeoutException e){
            softAssert.fail("Error message was not displayed");
        }
        softAssert.assertAll();
    }
}
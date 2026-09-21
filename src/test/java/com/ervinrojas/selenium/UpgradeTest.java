package com.ervinrojas.selenium;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import com.ervinrojas.pages.HeaderPage;
import com.ervinrojas.pages.LoginPage;
import com.ervinrojas.util.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.Set;

public class UpgradeTest extends BaseTest {

    @Test
    public void openNewTab() throws InterruptedException {
        // 1. Login
        LoginPage loginPage = new LoginPage(driver);
        loginPage.login("Admin", "admin123");

        // 2. Clicking Upgrade button in Header layer
        HeaderPage headerPage = new HeaderPage(driver);
        headerPage.clickUpgradeBtn();

        //Examples how to open new tabs
        //((JavascriptExecutor)driver).executeScript("window.open()");
        //((JavascriptExecutor)driver).executeScript("window.open('https://www.saucedemo.com/')");

        String mainTab = driver.getWindowHandle();
        String newTab = "";
        String expectedTitle = "Upgrade to Advanced from Open Source | OrangeHRM";
        String homeTitle = "OrangeHRM";

        System.out.println("Main tab: "+ mainTab);

        //3. Handle tabs
        Set<String> handles = driver.getWindowHandles();
        for (String actual : handles){
            System.out.println("-- Handle ID: "+actual);
            if(!actual.equalsIgnoreCase(mainTab)){
                System.out.println(" -- Changing Tab -- ");
                driver.switchTo().window(actual);
                newTab = actual;
                String actualTitle = driver.getTitle();

                System.out.println("Actual Title: "+actualTitle);
                Assert.assertEquals(expectedTitle,actualTitle);

            }
        }
        //4. Going back to the first tab
        System.out.println(" -- Going back to first tab -- ");
        driver.switchTo().window(mainTab);
        String currentTitle = driver.getTitle();

        System.out.println("First tab title is: "+currentTitle);
        Assert.assertEquals(currentTitle, homeTitle);

    }
}
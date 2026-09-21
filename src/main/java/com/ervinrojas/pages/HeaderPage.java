package com.ervinrojas.pages;

import com.ervinrojas.util.LocatorManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class HeaderPage {

    private WebDriver driver;
    private LocatorManager locators = LocatorManager.getInstance();

    private By upgradeBtn = locators.getLocator("header.upgrade.button");

    public HeaderPage(WebDriver driver) {
        this.driver = driver;
    }

    public void clickUpgradeBtn() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement upgradeButton = wait.until(ExpectedConditions.visibilityOfElementLocated(upgradeBtn));
        upgradeButton.click();
    }
}

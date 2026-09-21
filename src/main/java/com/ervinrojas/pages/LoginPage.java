package com.ervinrojas.pages;

import com.ervinrojas.util.LocatorManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class LoginPage {

    private WebDriver driver;
    private WebDriverWait wait;
    private LocatorManager locators = LocatorManager.getInstance();

    // Localizadores (Selectores corregidos)
    private By usernameInput = locators.getLocator("login.username.input");
    private By passwordInput = locators.getLocator("login.password.input");
    private By loginButton = locators.getLocator("login.submit.button");

    // Constructor
    public LoginPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    // Acciones
    public void escribirUsuario(String usuario) {
        WebElement userInput = wait.until(ExpectedConditions.elementToBeClickable(usernameInput));
        userInput.sendKeys(usuario);
    }

    public void escribirPassword(String password) {
        WebElement passInput = wait.until(ExpectedConditions.elementToBeClickable(passwordInput));
        passInput.sendKeys(password);
    }

    public void clickLogin() {
        WebElement btnLogin = wait.until(ExpectedConditions.elementToBeClickable(loginButton));
        btnLogin.click();
    }

    public void login(String usuario, String password) {
        escribirUsuario(usuario);
        escribirPassword(password);
        clickLogin();
    }
}
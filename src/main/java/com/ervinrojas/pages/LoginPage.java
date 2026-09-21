package com.ervinrojas.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class LoginPage {

    private WebDriver driver;

    // Localizadores (Selectores corregidos)
    private By usernameInput = By.name("username");
    private By passwordInput = By.name("password");
    private By loginButton = By.cssSelector("button[type='submit']");

    // Constructor
    public LoginPage(WebDriver driver) {
        this.driver = driver;
    }

    // Acciones
    public void escribirUsuario(String usuario) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement userInput = wait.until(ExpectedConditions.visibilityOfElementLocated(usernameInput));
        userInput.sendKeys(usuario);
    }

    public void escribirPassword(String password) {
        driver.findElement(passwordInput).sendKeys(password);
    }

    public void clickLogin() {
        driver.findElement(loginButton).click();
    }

    // Método completo de login
    public void login(String usuario, String password) {
        escribirUsuario(usuario);
        escribirPassword(password);
        clickLogin();
    }
}
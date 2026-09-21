package com.ervinrojas.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import javax.lang.model.element.Element;
import java.time.Duration;
import java.util.List;

public class AddUserPage {

    private WebDriver driver;
    private WebDriverWait wait;

    // Locators
    private By lknAdmin = By.linkText("Admin");
    private By addBtn = By.cssSelector("i.bi-plus");
    private By userRoleDd = By.xpath("(//div[@class='oxd-select-wrapper'])[1]");
    private By employeeInput = By.cssSelector("input[placeholder='Type for hints...']");
    private By userStatusDd = By.xpath("(//div[@class='oxd-select-wrapper'])[2]");
    private By usernameInput = By.cssSelector("input.oxd-input:nth-child(1)");
    private By passwordInput = By.xpath("(//input[@class='oxd-input oxd-input--active'])[2]");
    private By confirmPasswordInput = By.xpath("(//input[@type='password'])[2]");
    private By saveBtn = By.xpath("//button[@type='submit']");

    // Constructor
    public AddUserPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    // Método para ir a la sección de Add User
    public void irAAddUser() {
        // Esperar a que el botón Add User esté visible
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement adminLink = wait.until(ExpectedConditions.visibilityOfElementLocated(lknAdmin));
        adminLink.click();
    }

    public void clickAddBtn() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement clickAddButton = wait.until(ExpectedConditions.visibilityOfElementLocated(addBtn));
        clickAddButton.click();
    }

    private void selectDropdown(By dropdownLocator, String optionText){
        WebElement dropdown = wait.until(ExpectedConditions.visibilityOfElementLocated(dropdownLocator));
        dropdown.click();
        By optionLocator = By.xpath("//div[contains(@class, 'oxd-select-option')]//span[normalize-space(text())='"+ optionText +"']");
        try {
            WebElement option = wait.until(ExpectedConditions.elementToBeClickable(optionLocator));
            option.click();
        }catch (org.openqa.selenium.TimeoutException e){
            System.out.println(">>>ERROR: System cannot find option '"+optionText+ "'");
            System.out.println(">>>Options available in dropdown");
            List<WebElement> allOption = driver.findElements(By.xpath("//div[@class='oxd-select-option']"));
            for(WebElement opt : allOption){
                System.out.println(" - Visible text: '"+opt.getText()+"'");
            }
            throw e;
        }
    }

    // Acción de llenar el formulario
    public void crearUsuario(String role, String employeeName, String status, String username,String password) {
        // Esperar a que los campos sean visibles
        selectDropdown(userRoleDd, role);
        selectDropdown(userStatusDd, status);

        WebElement empInput = wait.until(ExpectedConditions.visibilityOfElementLocated(employeeInput));
        empInput.click();
        empInput.sendKeys(employeeName);

        By suggestionLocator = By.xpath("//input[@placeholder='Type for hints...']");
        wait.until(ExpectedConditions.visibilityOfElementLocated(suggestionLocator));
        By optionLocator = By.xpath("//div[@role='option']//span[contains(text(), '"+employeeName+"')]");
        try{
            WebElement suggestion = wait.until(ExpectedConditions.elementToBeClickable(optionLocator));
            suggestion.click();
        }catch (TimeoutException e){
            System.out.println("Employee was not found: '"+employeeName+"' in dropdown list");
            throw e;
        }

        WebElement userField = driver.findElement(usernameInput);
        userField.sendKeys(username);

        WebElement passField = driver.findElement(passwordInput);
        passField.sendKeys(password);

        WebElement confirmPassField = driver.findElement(confirmPasswordInput);
        confirmPassField.sendKeys(password);

        driver.findElement(saveBtn).click();
    }
}
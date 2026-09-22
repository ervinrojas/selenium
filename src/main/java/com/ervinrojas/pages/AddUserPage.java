package com.ervinrojas.pages;

import com.ervinrojas.util.LocatorManager;
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
    private LocatorManager locators = LocatorManager.getInstance();

    // Locators
    private By lknAdmin = locators.getLocator("usermgmt.admin.link");
    private By addBtn = locators.getLocator("adduser.add.button");
    private By userRoleDd = locators.getLocator("adduser.role.dropdown");
    private By employeeInput = locators.getLocator("adduser.employee.input");
    private By userStatusDd = locators.getLocator("adduser.status.dropdown");
    private By usernameInput = locators.getLocator("adduser.username.input");
    private By passwordInput = locators.getLocator("adduser.password.input");
    private By confirmPasswordInput = locators.getLocator("adduser.confirm.password.input");
    private By saveBtn = locators.getLocator("adduser.save.button");

    // Constructor
    public AddUserPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    // Método para ir a la sección de Add User
    public void irAAddUser() {
        WebElement adminLink = wait.until(ExpectedConditions.visibilityOfElementLocated(lknAdmin));
        adminLink.click();
    }

    public void clickAddBtn() {
        WebElement clickAddButton = wait.until(ExpectedConditions.visibilityOfElementLocated(addBtn));
        clickAddButton.click();
    }

    private void selectDropdown(By dropdownLocator, String optionText){
        WebElement dropdown = wait.until(ExpectedConditions.visibilityOfElementLocated(dropdownLocator));
        dropdown.click();

        String xpathTemplate = locators.getProperty("adduser.dropdown.option.template");
        String dynamicXpath = String.format(xpathTemplate, optionText);

        try {
            WebElement option = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(dynamicXpath)));
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
        selectDropdown(userRoleDd, role);
        selectDropdown(userStatusDd, status);

        WebElement empInput = wait.until(ExpectedConditions.visibilityOfElementLocated(employeeInput));
        empInput.click();
        empInput.sendKeys(employeeName);

        String empXpathTemplate = locators.getProperty("adduser.employee.suggestion.template");
        String dynamicEmpXpath = String.format(empXpathTemplate, employeeName);

        try{
            WebElement suggestion = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(dynamicEmpXpath)));
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
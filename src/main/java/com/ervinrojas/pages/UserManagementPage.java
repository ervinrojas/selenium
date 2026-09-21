package com.ervinrojas.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class UserManagementPage {

    private WebDriver driver;
    private WebDriverWait wait;

    // Locators
    private By adminLink = By.linkText("Admin");
    // En OrangeHRM v5+, las filas son divs con role='row' dentro del body
    private By tableRows = By.xpath("//div[@class='oxd-table-body']//div[@role='row']");
    // Las celdas son divs con clase 'oxd-table-cell'
    private By tableCells = By.className("oxd-table-cell");

    // Constructor
    public UserManagementPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    // Método para ir a la página de Admin
    public void goToUserManagement() {
        WebElement adminBtn = wait.until(ExpectedConditions.elementToBeClickable(adminLink));
        adminBtn.click();
    }

    // Método para obtener una lista de Strings con la info de los usuarios
    public List<String> getAllUsernames() {
        List<String> userList = new ArrayList<>();

        // 1. Esperar a que la tabla cargue (buscamos que haya filas)
        wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(tableRows));

        // 2. Obtener todas las filas de la tabla
        List<WebElement> rows = driver.findElements(tableRows);

        System.out.println("Se encontraron " + rows.size() + " usuarios en la tabla.");

        // 3. Iterar sobre cada fila
        for (WebElement row : rows) {
            try {
                // 4. Dentro de la fila, buscar todas las celdas
                List<WebElement> cells = row.findElements(tableCells);

                // En OrangeHRM, la columna 0 es el Checkbox, la 1 es Username.
                if (cells.size() > 1) {
                    String username = cells.get(1).getText();
                    String userRole = cells.get(2).getText();
                    String status = cells.get(4).getText();

                    // Agregamos la info formateada a la lista
                    String userInfo = String.format("User: %-15s | Role: %-10s | Status: %s", username, userRole, status);
                    userList.add(userInfo);

                    // Opcional: Imprimir en consola directamente para ver el progreso
                    System.out.println(userInfo);
                }
            } catch (Exception e) {
                System.out.println("Error leyendo una fila: " + e.getMessage());
            }
        }

        return userList;
    }

    // Método auxiliar si quieres verificar un usuario específico existe
    public boolean isUserInList(String usernameToFind) {
        List<String> users = getAllUsernames();
        for (String userEntry : users) {
            if (userEntry.contains(usernameToFind)) {
                return true;
            }
        }
        return false;
    }
}
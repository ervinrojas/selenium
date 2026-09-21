package com.ervinrojas.selenium;

import com.ervinrojas.pages.UserManagementPage;
import com.ervinrojas.util.AuthenticatedTest;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;

public class UserListTest extends AuthenticatedTest {

    @Test
    public void testListarUsuarios() {

        // 1. Instanciar la página
        UserManagementPage userMgmtPage = new UserManagementPage(driver);

        // 2. Navegar a la sección de Admin
        // Usamos la URL del config para asegurar que estamos en el lugar correcto
        driver.get(config.getString("base.url") + "web/index.php/admin/viewSystemUsers");

        // 3. Ir a la sección (Click en Admin - aunque la URL directa ya carga la vista)
        // userMgmtPage.goToUserManagement();

        // 4. Obtener la lista de usuarios
        System.out.println("--------------------------------------------------");
        System.out.println("INICIANDO LISTADO DE USUARIOS");
        System.out.println("--------------------------------------------------");

        List<String> usuarios = userMgmtPage.getAllUsernames();

        // 5. Validación (Assert) para asegurarse que la tabla no está vacía
        Assert.assertFalse(usuarios.isEmpty(), "La tabla de usuarios está vacía o no se cargaron los datos.");

        System.out.println("--------------------------------------------------");
        System.out.println("TOTAL USUARIOS LISTADOS: " + usuarios.size());
        System.out.println("--------------------------------------------------");
    }
}
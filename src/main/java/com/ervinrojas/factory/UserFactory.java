package com.ervinrojas.factory;

public class UserFactory {

    // Método que devuelve un usuario válido para el login
    public static LoginCredentials getValidAdminCredentials() {
        // Usamos ConfigManager para obtener credenciales
        // Leemos de variable de entorno o un placeholder del properties
        return new LoginCredentials(
                System.getenv().getOrDefault("APP_USER", "Admin"),
                System.getenv().getOrDefault("APP_PASSWORD", "admin123")
        );
    }

    // Método para crear un usuario nuevo para la prueba "Add User"
    public static NewUserData getNewEmployeeData() {
        // Aquí puedes generar datos aleatorios usando bibliotecas como Faker si quisieras
        String usernameRandom = "User_"+System.currentTimeMillis();
        String passwordRandom = "Pass_"+System.currentTimeMillis();

        return new NewUserData(usernameRandom,passwordRandom);
    }

    // Clases internas simples para estructura de datos
    public static class LoginCredentials {
        public String username;
        public String password;
        public LoginCredentials(String u, String p) { this.username = u; this.password = p; }
    }

    public static class NewUserData {
        public String role;
        public String status;
        public String employeeName;
        public String username;
        public String password;

        public NewUserData(String username, String password) {
            this.role = "ESS";
            this.employeeName = "Qwerty Qwerty LName";
            this.status = "Enabled";
            this.username = username;
            this.password = password;
        }
    }
}
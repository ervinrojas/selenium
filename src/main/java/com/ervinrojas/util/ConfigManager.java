package com.ervinrojas.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ConfigManager {
    private static ConfigManager instance;
    private Properties properties;

    // Constructor privado para Singleton
    private ConfigManager() {
        properties = new Properties();
        try {
            // Cargar el archivo desde resources
            FileInputStream ip = new FileInputStream("src/test/resources/config.properties");
            properties.load(ip);
        } catch (IOException e) {
            System.out.println("No se pudo cargar el archivo de configuración: " + e.getMessage());
        }
    }

    public static ConfigManager getInstance() {
        if (instance == null) {
            instance = new ConfigManager();
        }
        return instance;
    }

    // Método para obtener un string.
    // PRIORIDAD: 1. Variable de Entorno (CI/CD), 2. Archivo .properties (Local)
    public String getString(String key) {
        // 1. Verificar si existe variable de entorno (Ideal para Jenkins/GitHub Actions)
        String envValue = System.getenv(key);
        if (envValue != null) {
            return envValue;
        }

        // 2. Si no, buscar en el archivo properties
        String propValue = properties.getProperty(key);

        // Manejo para URLs dinámicas según ambiente
        if (key.equals("base.url")) {
            String env = properties.getProperty("env", "qa"); // Default a QA
            return properties.getProperty("url." + env);
        }

        return propValue;
    }

    public int getInt(String key) {
        return Integer.parseInt(getString(key));
    }
}
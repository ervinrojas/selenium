package com.ervinrojas.util;

import org.openqa.selenium.By;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class LocatorManager {
    private static LocatorManager instance;
    private Properties properties;

    private LocatorManager() {
        properties = new Properties();
        try {
            // Asegúrate de que la ruta coincida con tu proyecto
            FileInputStream ip = new FileInputStream("src/test/resources/locators.properties");
            properties.load(ip);
        } catch (IOException e) {
            throw new RuntimeException("No se pudo cargar locators.properties: " + e.getMessage());
        }
    }

    public static LocatorManager getInstance() {
        if (instance == null) {
            instance = new LocatorManager();
        }
        return instance;
    }

    public String getProperty(String key) {
        return properties.getProperty(key);
    }

    /**
     * Convierte un string del archivo properties en un objeto By de Selenium
     * Ejemplo de entrada: "name=username" -> devuelve By.name("username")
     */
    public By getLocator(String key) {
        String value = properties.getProperty(key);
        if (value == null) {
            throw new IllegalArgumentException("La clave '" + key + "' no existe en locators.properties");
        }

        // Separar el tipo (name, id, css, xpath) del valor real
        int separatorIndex = value.indexOf("=");
        if (separatorIndex == -1) {
            throw new IllegalArgumentException("Formato inválido para la clave '" + key + "'. Use tipo=valor (ej: id=miId)");
        }

        String locatorType = value.substring(0, separatorIndex).trim().toLowerCase();
        String locatorValue = value.substring(separatorIndex + 1).trim();

        switch (locatorType) {
            case "id":      return By.id(locatorValue);
            case "name":    return By.name(locatorValue);
            case "css":     return By.cssSelector(locatorValue);
            case "xpath":   return By.xpath(locatorValue);
            case "class":   return By.className(locatorValue);
            case "tag":     return By.tagName(locatorValue);
            case "link":    return By.linkText(locatorValue);
            case "partiallink": return By.partialLinkText(locatorValue);
            default:
                throw new IllegalArgumentException("Tipo de localizador no soportado: " + locatorType);
        }
    }
}
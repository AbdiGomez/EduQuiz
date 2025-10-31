package com.example.eduquiz;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class DatabaseConfig {

    private static final Properties props = new Properties();

    static {
        try (InputStream input = DatabaseConfig.class.getResourceAsStream("/database.properties")) {
            if (input == null) {
                throw new RuntimeException("No se encontró el archivo database.properties en resources/");
            }
            props.load(input);
            System.out.println("Archivo database.properties cargado correctamente.");
        } catch (IOException e) {
            throw new RuntimeException("Error al leer database.properties", e);
        }
    }

    public static String get(String key) {
        return props.getProperty(key);
    }
}
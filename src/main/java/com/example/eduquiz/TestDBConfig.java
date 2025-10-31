package com.example.eduquiz;

public class TestDBConfig {
    public static void main(String[] args) {
        System.out.println("Host: " + DatabaseConfig.get("db.host"));
        System.out.println("Puerto: " + DatabaseConfig.get("db.port"));
        System.out.println("Base de datos: " + DatabaseConfig.get("db.name"));
        System.out.println("Usuario: " + DatabaseConfig.get("db.user"));
        System.out.println("Contraseña: " + DatabaseConfig.get("db.password"));
    }
}
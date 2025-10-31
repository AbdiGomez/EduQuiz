package com.example.eduquiz;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class TestDBConnection {
    public static void main(String[] args) {
        try (Connection conn = DatabaseConnection.getConnection()) {
            if (conn != null) {
                System.out.println("Conexión verificada correctamente.");

                // Prueba rápida (por ejemplo, listar tablas)
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery("SHOW TABLES;");

                System.out.println("Tablas encontradas:");
                while (rs.next()) {
                    System.out.println(" - " + rs.getString(1));
                }
            } else {
                System.out.println("No se estableció conexión.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
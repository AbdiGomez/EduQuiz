package com.example.eduquiz;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {

    private static Connection connection;

    public static Connection getConnection() {
        if (connection != null) {
            return connection;
        }

        try {
            // Leemos los valores desde el archivo properties
            String host = DatabaseConfig.get("db.host");
            String port = DatabaseConfig.get("db.port");
            String name = DatabaseConfig.get("db.name");
            String user = DatabaseConfig.get("db.user");
            String password = DatabaseConfig.get("db.password");

            // Creamos la URL JDBC para MariaDB
            String url = "jdbc:mariadb://" + host + ":" + port + "/" + name;

            // Conexión
            connection = DriverManager.getConnection(url, user, password);
            System.out.println("Conectado exitosamente a la base de datos: " + name);
        } catch (SQLException e) {
            System.err.println("Error al conectar con la base de datos:");
            e.printStackTrace();
        }

        return connection;
    }
}
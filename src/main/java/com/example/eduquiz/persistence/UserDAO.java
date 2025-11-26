package com.example.eduquiz.persistence;

import com.example.eduquiz.DatabaseConnection;
import com.example.eduquiz.model.User;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.sql.*;

public class UserDAO {

    private Connection connection;

    // 🔹 Constructor
    public UserDAO() {
        try {
            this.connection = DatabaseConnection.getConnection();
            if (this.connection == null) {
                throw new SQLException("❌ No se pudo obtener conexión a la base de datos (connection es null).");
            } else {
                System.out.println("✅ Conexión establecida correctamente en UserDAO.");
            }
        } catch (SQLException e) {
            System.err.println("⚠️ Error al inicializar UserDAO: " + e.getMessage());
            this.connection = null;
        }
    }

    // 🔹 Método para verificar si la conexión está activa
    private boolean isConnected() {
        try {
            return (connection != null && !connection.isClosed());
        } catch (SQLException e) {
            return false;
        }
    }

    // 🔹 Crear nuevo usuario
    public boolean createUser(@NotNull User user) {
        if (!isConnected()) {
            System.err.println("⚠️ No hay conexión a la base de datos. createUser cancelado.");
            return false;
        }

        String sql = "INSERT INTO users (username, email, password_hash, current_level) VALUES (?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, user.getUsername());
            stmt.setString(2, user.getEmail());
            stmt.setString(3, user.getPasswordHash());
            stmt.setInt(4, user.getCurrentLevel());
            stmt.executeUpdate();

            System.out.println("✅ Usuario creado correctamente: " + user.getUsername());
            return true;
        } catch (SQLException e) {
            System.err.println("❌ Error al crear usuario: " + e.getMessage());
            return false;
        }
    }

    // 🔹 Buscar usuario por correo
    public @Nullable User getUserByEmail(@NotNull String email) {
        if (!isConnected()) {
            System.err.println("⚠️ No hay conexión a la base de datos. getUserByEmail cancelado.");
            return null;
        }

        String sql = "SELECT * FROM users WHERE email = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                User user = new User(
                        rs.getInt("id"),
                        rs.getString("username"),
                        rs.getString("email"),
                        rs.getString("password_hash"),
                        rs.getInt("current_level")
                );
                System.out.println("🔎 Usuario encontrado: " + user.getUsername());
                return user;
            } else {
                System.out.println("ℹ️ No se encontró usuario con email: " + email);
            }
        } catch (SQLException e) {
            System.err.println("❌ Error al obtener usuario: " + e.getMessage());
        }
        return null;
    }

    // 🔹 Actualizar nivel del usuario
    public boolean updateCurrentLevel(@NotNull User user, int newLevel) {
        if (!isConnected()) {
            System.err.println("⚠️ No hay conexión a la base de datos. updateCurrentLevel cancelado.");
            return false;
        }

        String sql = "UPDATE users SET current_level = ? WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, newLevel);
            stmt.setInt(2, user.getId());
            int updated = stmt.executeUpdate();

            if (updated > 0) {
                System.out.println("📈 Nivel actualizado a " + newLevel + " para " + user.getUsername());
                return true;
            } else {
                System.out.println("ℹ️ No se encontró el usuario para actualizar nivel.");
                return false;
            }
        } catch (SQLException e) {
            System.err.println("❌ Error al actualizar nivel: " + e.getMessage());
            return false;
        }
    }
}

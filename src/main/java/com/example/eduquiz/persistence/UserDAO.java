package com.example.eduquiz.persistence;

import com.example.eduquiz.DatabaseConnection;
import com.example.eduquiz.model.User;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.sql.*;

public class UserDAO {

    private final Connection connection;

    public UserDAO() {
        this.connection = DatabaseConnection.getConnection(); // usa tu conexión central
    }

    // Crear un nuevo usuario
    public boolean createUser(@NotNull User user) {
        String sql = "INSERT INTO users (username, email, password_hash, current_level) VALUES (?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, user.getUsername());
            stmt.setString(2, user.getEmail());
            stmt.setString(3, user.getPasswordHash());
            stmt.setInt(4, user.getCurrentLevel());
            stmt.executeUpdate();
            System.out.println("Usuario creado correctamente: " + user.getUsername());
            return true;
        } catch (SQLException e) {
            System.err.println("Error al crear usuario: " + e.getMessage());
            return false;
        }
    }

    // Buscar usuario por correo
    public @Nullable User getUserByEmail(@NotNull String email) {
        String sql = "SELECT * FROM users WHERE email = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new User(
                        rs.getInt("id"),
                        rs.getString("username"),
                        rs.getString("email"),
                        rs.getString("password_hash"),
                        rs.getInt("current_level")
                );
            }
        } catch (SQLException e) {
            System.err.println("❌ Error al obtener usuario: " + e.getMessage());
        }
        return null;
    }

    // Actualizar el nivel del usuario
    public void updateCurrentLevel(@NotNull User user, int newLevel) {
        String sql = "UPDATE users SET current_level = ? WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, newLevel);
            stmt.setInt(2, user.getId());
            stmt.executeUpdate();
            System.out.println("📈 Nivel actualizado a " + newLevel + " para " + user.getUsername());
        } catch (SQLException e) {
            System.err.println("❌ Error al actualizar nivel: " + e.getMessage());
        }
    }
}

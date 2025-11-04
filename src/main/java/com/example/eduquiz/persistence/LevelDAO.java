package com.example.eduquiz.persistence;

import com.example.eduquiz.DatabaseConnection;
import com.example.eduquiz.model.Level;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class LevelDAO {
    private final Connection connection = DatabaseConnection.getConnection();

    public List<Level> getAllLevels() {
        List<Level> levels = new ArrayList<>();
        String sql = "SELECT * FROM levels ORDER BY difficulty ASC";
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                levels.add(new Level(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("description"),
                        rs.getInt("difficulty")
                ));
            }
        } catch (SQLException e) {
            System.err.println("❌ Error al obtener niveles: " + e.getMessage());
        }
        return levels;
    }
}

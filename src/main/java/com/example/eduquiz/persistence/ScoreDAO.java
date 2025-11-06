package com.example.eduquiz.persistence;

import com.example.eduquiz.DatabaseConnection;
import com.example.eduquiz.model.Score;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ScoreDAO {
    private final Connection connection = DatabaseConnection.getConnection();

    public void saveScore(Score score) {
        String sql = "INSERT INTO scores (user_id, level_id, score) VALUES (?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, score.getUserId());
            stmt.setInt(2, score.getLevelId());
            stmt.setInt(3, score.getScore());
            stmt.executeUpdate();
            System.out.println("Puntaje guardado correctamente.");
        } catch (SQLException e) {
            System.err.println("Error al guardar puntaje: " + e.getMessage());
        }
    }
}

package com.example.eduquiz.persistence;

import com.example.eduquiz.DatabaseConnection;
import com.example.eduquiz.model.Question;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class QuestionDAO {

    private final Connection connection;

    public QuestionDAO() {
        this.connection = DatabaseConnection.getConnection();
        if (this.connection == null) {
            System.err.println("❌ Error: No se pudo establecer conexión con la base de datos en QuestionDAO.");
        }
    }

    /**
     * Obtiene todas las preguntas desde la base de datos (sin filtrar por nivel)
     */
    public List<Question> getAllQuestions() {
        List<Question> list = new ArrayList<>();

        if (connection == null) {
            System.err.println("⚠ No hay conexión activa a la base de datos (getAllQuestions).");
            return list;
        }

        String sql = "SELECT * FROM questions ORDER BY level_id, id";
        try (PreparedStatement stmt = connection.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Question q = new Question(
                        rs.getInt("id"),
                        rs.getInt("level_id"),
                        rs.getString("text"),
                        rs.getString("correct_answer"),
                        rs.getString("option_a"),
                        rs.getString("option_b"),
                        rs.getString("option_c"),
                        rs.getString("option_d")
                );
                list.add(q);
            }

            System.out.println("✅ Se cargaron " + list.size() + " preguntas totales.");
        } catch (SQLException e) {
            System.err.println("❌ Error al obtener preguntas: " + e.getMessage());
        }

        return list;
    }

    /**
     * Obtiene todas las preguntas correspondientes a un nivel específico
     */
    public List<Question> getQuestionsByLevel(int levelId) {
        List<Question> list = new ArrayList<>();

        if (connection == null) {
            System.err.println("⚠ No hay conexión activa a la base de datos (getQuestionsByLevel).");
            return list;
        }

        String sql = "SELECT * FROM questions WHERE level_id = ? ORDER BY id";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, levelId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Question q = new Question(
                        rs.getInt("id"),
                        rs.getInt("level_id"),
                        rs.getString("text"),
                        rs.getString("correct_answer"),
                        rs.getString("option_a"),
                        rs.getString("option_b"),
                        rs.getString("option_c"),
                        rs.getString("option_d")
                );
                list.add(q);
            }

            System.out.println("✅ Se cargaron " + list.size() + " preguntas del nivel " + levelId);

        } catch (SQLException e) {
            System.err.println("❌ Error al obtener preguntas por nivel: " + e.getMessage());
        }

        return list;
    }
}



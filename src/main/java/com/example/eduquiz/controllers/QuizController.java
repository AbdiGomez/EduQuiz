package com.example.eduquiz.controllers;

import com.example.eduquiz.model.Question;
import com.example.eduquiz.persistence.QuestionDAO;
import com.example.eduquiz.persistence.ScoreDAO;
import com.example.eduquiz.model.Score;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.util.List;

public class QuizController {

    @FXML private Label questionLabel;
    @FXML private RadioButton optionA;
    @FXML private RadioButton optionB;
    @FXML private RadioButton optionC;
    @FXML private RadioButton optionD;
    @FXML private Button nextButton;
    @FXML private Label feedbackLabel;

    private List<Question> questions;
    private int currentIndex = 0;
    private int score = 0;
    private final QuestionDAO questionDAO = new QuestionDAO();

    @FXML
    public void initialize() {
        // Por defecto cargamos el nivel 1
        questions = questionDAO.getQuestionsByLevel(1);
        if (!questions.isEmpty()) showQuestion();
    }

    private void showQuestion() {
        Question q = questions.get(currentIndex);
        questionLabel.setText(q.getText());
        optionA.setText(q.getOptionA());
        optionB.setText(q.getOptionB());
        optionC.setText(q.getOptionC());
        optionD.setText(q.getOptionD());
        feedbackLabel.setText("");
    }

    @FXML
    void onNextQuestion() {
        Question q = questions.get(currentIndex);
        String selected = "";
        if (optionA.isSelected()) selected = optionA.getText();
        else if (optionB.isSelected()) selected = optionB.getText();
        else if (optionC.isSelected()) selected = optionC.getText();
        else if (optionD.isSelected()) selected = optionD.getText();

        if (selected.equals(q.getCorrectAnswer())) {
            score += 10;
            feedbackLabel.setText("✅ Correcto!");
        } else {
            feedbackLabel.setText("❌ Incorrecto. Respuesta: " + q.getCorrectAnswer());
        }

        currentIndex++;
        if (currentIndex < questions.size()) {
            showQuestion();
        } else {
            feedbackLabel.setText("Fin del nivel. Puntuación: " + score);
            new ScoreDAO().saveScore(new Score(1, q.getLevelId(), score)); // ← ejemplo con user_id=1
            nextButton.setDisable(true);
        }
    }
}

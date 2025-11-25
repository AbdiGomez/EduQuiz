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
    @FXML private ToggleGroup optionsGroup;

    private List<Question> questions;
    private int currentIndex = 0;
    private int score = 0;
    private final QuestionDAO questionDAO = new QuestionDAO();

    @FXML
    public void initialize() {
        // Cargar nivel 1 por defecto
        questions = questionDAO.getQuestionsByLevel(1);
        if (!questions.isEmpty()) {
            showQuestion();
        } else {
            feedbackLabel.setText("No hay preguntas disponibles.");
            nextButton.setDisable(true);
        }
    }

    private void showQuestion() {
        Question q = questions.get(currentIndex);
        questionLabel.setText(q.getText());
        optionA.setText(q.getOptionA());
        optionB.setText(q.getOptionB());
        optionC.setText(q.getOptionC());
        optionD.setText(q.getOptionD());
        feedbackLabel.setText("");

        // Limpiar selección previa
        optionsGroup.selectToggle(null);
    }

    @FXML
    void onNextQuestion() {
        Question q = questions.get(currentIndex);

        RadioButton selectedBtn = (RadioButton) optionsGroup.getSelectedToggle();
        if (selectedBtn == null) {
            feedbackLabel.setText("Debes seleccionar una opción.");
            return;
        }

        String selected = selectedBtn.getText();

        if (selected.equals(q.getCorrectAnswer())) {
            score += 10;
            feedbackLabel.setText("Correcto!");
        } else {
            feedbackLabel.setText("Incorrecto. Respuesta: " + q.getCorrectAnswer());
        }

        currentIndex++;
        if (currentIndex < questions.size()) {
            showQuestion();
        } else {
            feedbackLabel.setText("Fin del nivel. Puntuación: " + score);
            new ScoreDAO().saveScore(new Score(1, q.getLevelId(), score)); // ejemplo con user_id = 1
            nextButton.setDisable(true);
        }
    }
}
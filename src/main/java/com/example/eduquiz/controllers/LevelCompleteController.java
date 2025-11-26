package com.example.eduquiz.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.scene.media.AudioClip;
import javafx.stage.Stage;

import java.net.URL;

public class LevelCompleteController {

    @FXML private Label scoreLabel;
    @FXML private Button nextLevelButton;
    @FXML private Button exitButton;

    private int nextLevel;
    private int score;

    public void setData(int nextLevel, int score) {
        this.nextLevel = nextLevel;
        this.score = score;
        scoreLabel.setText("Tu puntuación final en este nivel fue: " + score + " puntos 🎯");

        playLevelCompleteSound();
    }

    private void playLevelCompleteSound() {
        try {
            URL soundURL = getClass().getResource("/sounds/level_complete.wav");
            if (soundURL != null) {
                AudioClip clip = new AudioClip(soundURL.toExternalForm());
                clip.play();
            }
        } catch (Exception e) {
            System.err.println("⚠ No se pudo reproducir el sonido de nivel completado: " + e.getMessage());
        }
    }

    @FXML
    private void onNextLevel() {
        Stage stage = (Stage) nextLevelButton.getScene().getWindow();
        stage.close();

        // Aquí podrías cargar el siguiente nivel desde QuizController
        System.out.println("✅ Avanzando al nivel " + nextLevel);
    }

    @FXML
    private void onExitClick() {
        Stage stage = (Stage) exitButton.getScene().getWindow();
        stage.close();
        System.exit(0);
    }
}

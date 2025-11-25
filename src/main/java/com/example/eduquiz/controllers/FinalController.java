package com.example.eduquiz.controllers;

import javafx.animation.*;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.util.Duration;

public class FinalController {

    @FXML private HBox starsBox;
    @FXML private Label titleLabel;
    @FXML private Label correctLabel;
    @FXML private Label wrongLabel;
    @FXML private Label percentageLabel;
    @FXML private Label attemptsLabel;

    @FXML private Button nextLevelButton;
    @FXML private Button retryButton;
    @FXML private Button exitButton;

    private int stars;
    private int correct;
    private int wrong;
    private int attempts;
    private int level;
    private Runnable onNext;
    private Runnable onRetry;

    public void setup(int level, int correct, int wrong, int attempts, Runnable next, Runnable retry) {
        this.level = level;
        this.correct = correct;
        this.wrong = wrong;
        this.attempts = attempts;
        this.onNext = next;
        this.onRetry = retry;

        int total = correct + wrong;
        double percentage = (correct * 100.0 / total);

        // ⭐ Cálculo de estrellas
        if (attempts == 1) stars = 3;
        else if (attempts == 2) stars = 2;
        else stars = 1;

        titleLabel.setText("¡Nivel " + level + " completado!");
        correctLabel.setText("Correctas: " + correct);
        wrongLabel.setText("Incorrectas: " + wrong);
        percentageLabel.setText("Puntaje: " + String.format("%.1f", percentage) + "%");
        attemptsLabel.setText("Intentos: " + attempts);

        drawStars();
    }

    private void drawStars() {
        starsBox.getChildren().clear();

        for (int i = 0; i < stars; i++) {
            ImageView star = new ImageView(new Image(getClass().getResource("/img/star.png").toExternalForm()));
            star.setFitWidth(80);
            star.setFitHeight(80);
            star.setOpacity(0);

            starsBox.getChildren().add(star);

            FadeTransition fade = new FadeTransition(Duration.seconds(0.7), star);
            fade.setFromValue(0);
            fade.setToValue(1);

            ScaleTransition scale = new ScaleTransition(Duration.seconds(0.5), star);
            scale.setFromX(0);
            scale.setFromY(0);
            scale.setToX(1);
            scale.setToY(1);

            ParallelTransition p = new ParallelTransition(fade, scale);
            p.setDelay(Duration.seconds(i * 0.3));
            p.play();
        }
    }

    @FXML
    private void onNextLevel() {
        ((Stage) nextLevelButton.getScene().getWindow()).close();
        onNext.run();
    }

    @FXML
    private void onRetry() {
        ((Stage) retryButton.getScene().getWindow()).close();
        onRetry.run();
    }

    @FXML
    private void onExit() {
        System.exit(0);
    }
}

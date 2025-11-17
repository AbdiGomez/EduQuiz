package com.example.eduquiz.controllers;

import com.example.eduquiz.model.Question;
import com.example.eduquiz.persistence.QuestionDAO;
import javafx.animation.*;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.media.AudioClip;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.net.URL;
import java.util.List;

public class QuizController {

    @FXML private VBox rootPane;
    @FXML private Label questionLabel;
    @FXML private Label feedbackLabel;
    @FXML private Label levelLabel;
    @FXML private Label questionCounterLabel;
    @FXML private Label timerLabel;
    @FXML private ProgressBar timerBar;
    @FXML private VBox timerBox;
    @FXML private RadioButton optionA;
    @FXML private RadioButton optionB;
    @FXML private RadioButton optionC;
    @FXML private RadioButton optionD;
    @FXML private ToggleGroup optionsGroup;
    @FXML private Button nextButton;
    @FXML private Button exitButton;

    private final QuestionDAO questionDAO = new QuestionDAO();
    private List<Question> currentLevelQuestions;
    private int currentIndex = 0;
    private int currentLevel = 1;
    private int score = 0;
    private int totalCorrect = 0;
    private int totalQuestions = 0;

    private Timeline timer;
    private AudioClip soundCorrect, soundIncorrect, soundLevelComplete, soundTimeUp;
    private boolean warningSoundPlayed = false;

    @FXML
    public void initialize() {
        // load sounds
        loadSounds();

        // initial UI
        exitButton.setVisible(false);
        timerBox.setVisible(false);

        loadLevelQuestions();
    }

    private void loadSounds() {
        soundCorrect = loadSound("sounds/correct.wav");
        soundIncorrect = loadSound("sounds/incorrect.wav");
        soundLevelComplete = loadSound("sounds/level_complete.wav");
        soundTimeUp = loadSound("sounds/time_up.wav");
    }

    private AudioClip loadSound(String path) {
        try {
            URL u = getClass().getResource("/" + path);
            if (u == null) {
                System.err.println("⚠ No se encontró: " + path);
                return null;
            }
            return new AudioClip(u.toExternalForm());
        } catch (Exception ex) {
            System.err.println("No se pudo cargar el sonido: " + path + " -> " + ex.getMessage());
            return null;
        }
    }

    // ----------------- Load and show questions -----------------
    private void loadLevelQuestions() {
        currentLevelQuestions = questionDAO.getQuestionsByLevel(currentLevel);
        currentIndex = 0;
        score = 0;
        levelLabel.setText("Nivel " + currentLevel);

        // show timer box only from level 2
        timerBox.setVisible(currentLevel >= 2);

        if (currentLevelQuestions == null || currentLevelQuestions.isEmpty()) {
            showFinalGameSummary();
            return;
        }

        totalQuestions += currentLevelQuestions.size();
        showQuestion();
    }

    private void showQuestion() {
        // safety
        stopTimer();
        if (soundTimeUp != null && soundTimeUp.isPlaying()) soundTimeUp.stop();
        warningSoundPlayed = false;

        Question q = currentLevelQuestions.get(currentIndex);
        questionLabel.setText(q.getText());
        optionA.setText(q.getOptionA());
        optionB.setText(q.getOptionB());
        optionC.setText(q.getOptionC());
        optionD.setText(q.getOptionD());
        optionsGroup.selectToggle(null);
        feedbackLabel.setText("");

        // update counters
        questionCounterLabel.setText("Pregunta " + (currentIndex + 1) + " / " + currentLevelQuestions.size());

        // reset timer UI
        timerBar.setProgress(1.0);
        timerBar.setStyle("-fx-accent: #00c853;"); // verde por defecto
        timerLabel.setText("");

        if (currentLevel >= 2) {
            startTimer();
        }
    }

    // ----------------- Timer logic (color & seconds) -----------------
    private void startTimer() {
        stopTimer();

        final int totalTime = (currentLevel == 2) ? 10 : 6;
        timerBar.setProgress(1.0);
        timerLabel.setText(totalTime + "s");
        warningSoundPlayed = false;

        // tick every 200ms for smoother progress and to allow sound trigger precisely
        final double tickSeconds = 0.2;
        final int steps = (int) Math.round(totalTime / tickSeconds);
        final double decrement = 1.0 / steps;

        timer = new Timeline(new KeyFrame(Duration.seconds(tickSeconds), ev -> {
            double cur = timerBar.getProgress() - decrement;
            if (cur < 0) cur = 0;
            timerBar.setProgress(cur);

            // update seconds label approximately
            int secondsLeft = (int) Math.ceil(cur * totalTime);
            timerLabel.setText(secondsLeft + "s");

            // change colors (green -> yellow -> red)
            if (cur >= 0.5) {
                timerBar.setStyle("-fx-accent: linear-gradient(#66bb6a, #2e7d32);"); // green
            } else if (cur >= 0.25) {
                timerBar.setStyle("-fx-accent: linear-gradient(#ffca28, #f57f17);"); // yellow
            } else {
                timerBar.setStyle("-fx-accent: linear-gradient(#ff7043, #d84315);"); // red
            }

            // warning sound triggers at configured moments:
            int warningAt = (currentLevel == 2) ? 5 : 3;
            if (!warningSoundPlayed && secondsLeft == warningAt) {
                warningSoundPlayed = true;
                if (soundTimeUp != null) soundTimeUp.play();
            }

            // time expired
            if (cur <= 0.0) {
                stopTimer();
                if (soundTimeUp != null && soundTimeUp.isPlaying()) soundTimeUp.stop();
                flashBackground(ColorType.TIME_OUT);
                feedbackLabel.setText("⏰ ¡Tiempo agotado!");
                Platform.runLater(this::handleTimeExpired); // advance automatically
            }
        }));
        timer.setCycleCount(Animation.INDEFINITE);
        timer.play();
    }

    private void stopTimer() {
        if (timer != null) {
            timer.stop();
            timer = null;
        }
        if (soundTimeUp != null && soundTimeUp.isPlaying()) {
            soundTimeUp.stop();
        }
    }

    // ----------------- User actions -----------------
    @FXML
    private void onNextClick(ActionEvent event) {
        stopTimer();

        RadioButton selected = (RadioButton) optionsGroup.getSelectedToggle();
        if (selected == null) {
            feedbackLabel.setText("⚠ Selecciona una respuesta.");
            return;
        }

        Question q = currentLevelQuestions.get(currentIndex);
        String chosen = selected.getText();

        if (chosen.equals(q.getCorrectAnswer())) {
            feedbackLabel.setText("✅ ¡Correcto!");
            score++;
            totalCorrect++;
            playSound(soundCorrect);
            flashBackground(ColorType.CORRECT);
        } else {
            feedbackLabel.setText("❌ Incorrecto. Respuesta: " + q.getCorrectAnswer());
            playSound(soundIncorrect);
            flashBackground(ColorType.INCORRECT);
            // shake the selected card (find parent HBox)
            Node parent = selected.getParent();
            if (parent instanceof HBox) {
                playShake((HBox) parent);
            }
        }

        currentIndex++;
        if (currentIndex >= currentLevelQuestions.size()) {
            showLevelSummary();
        } else {
            // slight delay so user sees feedback
            PauseTransition p = new PauseTransition(Duration.seconds(0.8));
            p.setOnFinished(e -> showQuestion());
            p.play();
        }
    }

    private void handleTimeExpired() {
        Question q = currentLevelQuestions.get(currentIndex);
        feedbackLabel.setText("⏰ ¡Se acabó el tiempo! Respuesta correcta: " + q.getCorrectAnswer());
        playSound(soundIncorrect);

        currentIndex++;
        if (currentIndex >= currentLevelQuestions.size()) {
            showLevelSummary();
        } else {
            PauseTransition p = new PauseTransition(Duration.seconds(0.9));
            p.setOnFinished(e -> showQuestion());
            p.play();
        }
    }

    // ----------------- End of level / game summaries -----------------
    private void showLevelSummary() {
        stopTimer();

        int total = currentLevelQuestions.size();
        double percentage = (total == 0) ? 0.0 : (score * 100.0 / total);

        String resumen = "Resultados del Nivel " + currentLevel + ":\n\n" +
                "✅ Correctas: " + score + "\n" +
                "❌ Incorrectas: " + (total - score) + "\n" +
                "📊 Porcentaje: " + String.format("%.1f", percentage) + "%";

        // style dialogs with our CSS
        if (score == total) {
            playSound(soundLevelComplete);
            Alert a = new Alert(Alert.AlertType.INFORMATION);
            a.setTitle("Nivel completado");
            a.setHeaderText("🎉 ¡Excelente trabajo!");
            a.setContentText(resumen + "\n\n¿Deseas avanzar?");
            dialogApplyStyles(a);
            ButtonType next = new ButtonType("Avanzar");
            ButtonType exit = new ButtonType("Salir");
            a.getButtonTypes().setAll(next, exit);
            a.showAndWait().ifPresent(type -> {
                if (type == next) {
                    currentLevel++;
                    score = 0;
                    loadLevelQuestions();
                } else {
                    onExitClick(null);
                }
            });
        } else {
            Alert a = new Alert(Alert.AlertType.WARNING);
            a.setTitle("Nivel no superado");
            a.setHeaderText("❌ No alcanzaste la puntuación perfecta");
            a.setContentText(resumen + "\n\n¿Reintentar o salir?");
            dialogApplyStyles(a);
            ButtonType retry = new ButtonType("Reintentar");
            ButtonType exit = new ButtonType("Salir");
            a.getButtonTypes().setAll(retry, exit);
            a.showAndWait().ifPresent(type -> {
                if (type == retry) {
                    // reload same level
                    score = 0;
                    currentIndex = 0;
                    loadLevelQuestions();
                } else {
                    onExitClick(null);
                }
            });
        }
    }

    private void showFinalGameSummary() {
        stopTimer();
        playSound(soundLevelComplete);

        double percentage = (totalQuestions == 0) ? 0.0 : (totalCorrect * 100.0 / totalQuestions);
        String resumenFinal = "🏁 ¡Has completado todos los niveles!\n\n" +
                "✅ Correctas totales: " + totalCorrect + "\n" +
                "❌ Incorrectas totales: " + (totalQuestions - totalCorrect) + "\n" +
                "📊 Puntuación final: " + String.format("%.1f", percentage) + "%";

        questionLabel.setText("🎓 Juego completado");
        feedbackLabel.setText(resumenFinal);
        nextButton.setVisible(false);

        // show nice fade-in for final summary
        FadeTransition ft = new FadeTransition(Duration.seconds(1), feedbackLabel);
        ft.setFromValue(0.0);
        ft.setToValue(1.0);
        ft.play();

        exitButton.setVisible(true);
    }

    // ----------------- Utils: dialog styling, sound, animations -----------------
    private void dialogApplyStyles(Dialog<?> dialog) {
        DialogPane dp = dialog.getDialogPane();
        dp.getStylesheets().add(getClass().getResource("/css/quiz.css").toExternalForm());
        dp.getStyleClass().add("dialog-pane");
    }

    private void playSound(AudioClip clip) {
        if (clip != null) {
            clip.stop();
            clip.play();
        }
    }

    private void playShake(Node node) {
        TranslateTransition tt = new TranslateTransition(Duration.millis(60), node);
        tt.setFromX(0);
        tt.setByX(8);
        tt.setCycleCount(6);
        tt.setAutoReverse(true);
        tt.play();
    }

    private void flashBackground(ColorType type) {
        String color;
        switch (type) {
            case CORRECT -> color = "#e6f4ea";
            case INCORRECT -> color = "#ffe7e7";
            default -> color = "#fff3e0";
        }
        rootPane.setStyle("-fx-background-color: " + color + "; -fx-padding: 24;");
        Timeline t = new Timeline(new KeyFrame(Duration.seconds(0.9), e -> resetBackground()));
        t.play();
    }

    private void resetBackground() {
        rootPane.setStyle("-fx-background-color: linear-gradient(to bottom right, #f5f7fa, #eef6f1); -fx-padding: 24;");
    }

    @FXML
    private void onExitClick(ActionEvent event) {
        // close window
        Stage s = (Stage) rootPane.getScene().getWindow();
        if (s != null) s.close();
    }

    private enum ColorType { CORRECT, INCORRECT, TIME_OUT }
}

















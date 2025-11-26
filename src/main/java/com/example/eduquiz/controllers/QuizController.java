package com.example.eduquiz.controllers;

import com.example.eduquiz.model.Question;
import com.example.eduquiz.persistence.QuestionDAO;
import javafx.animation.*;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.scene.media.AudioClip;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.net.URL;
import java.util.Collections;
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
        loadSounds();
        exitButton.setVisible(false);
        timerBox.setVisible(true);

        // Barra de tiempo más gruesa
        timerBar.setStyle("-fx-pref-height: 20px;");

        loadLevelQuestions();
    }

    private void loadSounds() {
        soundCorrect = loadSound("sounds/correct.wav");
        soundIncorrect = loadSound("sounds/incorrect.wav");
        soundLevelComplete = loadSound("sounds/level_complete.wav");
        soundTimeUp = loadSound("sounds/time_up.wav");
    }

    private AudioClip loadSound(String p) {
        try {
            URL u = getClass().getResource("/" + p);
            return (u != null) ? new AudioClip(u.toExternalForm()) : null;
        } catch (Exception e) {
            System.err.println("Error cargando sonido: " + p + " -> " + e.getMessage());
            return null;
        }
    }

    private void loadLevelQuestions() {
        currentLevelQuestions = questionDAO.getQuestionsByLevel(currentLevel);
        if (currentLevelQuestions == null || currentLevelQuestions.isEmpty()) {
            // No hay preguntas -> finalizar juego
            showFinalGameSummary();
            return;
        }

        // Barajar y tomar hasta 5 (si hay menos, tomar lo que haya)
        Collections.shuffle(currentLevelQuestions);
        if (currentLevelQuestions.size() > 5) {
            currentLevelQuestions = currentLevelQuestions.subList(0, 5);
        }

        currentIndex = 0;
        score = 0;
        totalQuestions += currentLevelQuestions.size();

        levelLabel.setText("Nivel " + currentLevel);

        showQuestion();
    }

    private void showQuestion() {
        // Detener/limpiar timer anterior por seguridad
        stopTimer();
        warningSoundPlayed = false;
        if (soundTimeUp != null && soundTimeUp.isPlaying()) soundTimeUp.stop();

        // Seguridad: índice dentro del rango
        if (currentLevelQuestions == null || currentIndex < 0 || currentIndex >= currentLevelQuestions.size()) {
            showLevelSummarySafe();
            return;
        }

        Question q = currentLevelQuestions.get(currentIndex);

        questionLabel.setText(q.getText());
        optionA.setText(q.getOptionA());
        optionB.setText(q.getOptionB());
        optionC.setText(q.getOptionC());
        optionD.setText(q.getOptionD());

        optionsGroup.selectToggle(null);
        feedbackLabel.setText("");

        questionCounterLabel.setText("Pregunta " + (currentIndex + 1) + " / " + currentLevelQuestions.size());

        timerBar.setProgress(1.0);
        timerLabel.setText("");

        // Inicio del timer si corresponde
        startTimer();
    }

    private void startTimer() {
        stopTimer(); // asegurar que no haya timer corriendo

        int totalTime;
        if (currentLevel == 1) totalTime = 12;
        else if (currentLevel == 2) totalTime = 10;
        else totalTime = 6;

        timerLabel.setText(totalTime + "s");

        final double tickSeconds = 0.2;
        final int steps = Math.max(1, (int) Math.round(totalTime / tickSeconds));
        final double decrement = 1.0 / steps;

        warningSoundPlayed = false;

        timer = new Timeline(new KeyFrame(Duration.seconds(tickSeconds), ev -> {
            double cur = timerBar.getProgress() - decrement;
            if (cur < 0) cur = 0;
            timerBar.setProgress(cur);

            int secondsLeft = (int) Math.ceil(cur * totalTime);
            timerLabel.setText(secondsLeft + "s");

            // cambiar color por rango (opcional; mantiene lógica similar)
            if (cur >= 0.5) timerBar.setStyle("-fx-accent: #00e676;");
            else if (cur >= 0.25) timerBar.setStyle("-fx-accent: #ffd600;");
            else timerBar.setStyle("-fx-accent: #ff3d00;");

            // warning sound (solo una vez)
            int warnAt = (currentLevel == 1) ? 5 : (currentLevel == 2 ? 5 : 3);
            if (!warningSoundPlayed && secondsLeft == warnAt) {
                warningSoundPlayed = true;
                if (soundTimeUp != null) {
                    soundTimeUp.stop();
                    soundTimeUp.play();
                }
            }

            // Si se llega a 0, detener y manejar expiración de tiempo
            if (cur <= 0.0) {
                // detener sonido de warning si aun suena y evitar repetición
                if (soundTimeUp != null && soundTimeUp.isPlaying()) soundTimeUp.stop();
                // detener y limpiar timer
                if (timer != null) {
                    timer.stop();
                }
                // manejar en el hilo de UI por seguridad
                Platform.runLater(this::handleTimeExpired);
            }
        }));

        timer.setCycleCount(Animation.INDEFINITE);
        timer.play();
    }

    private void stopTimer() {
        if (timer != null) {
            try {
                timer.stop();
            } catch (Exception ignored) {}
            timer = null;
        }
        if (soundTimeUp != null && soundTimeUp.isPlaying()) {
            try { soundTimeUp.stop(); } catch (Exception ignored) {}
        }
    }

    // -------------------- BOTÓN SIGUIENTE --------------------
    @FXML
    private void onNextClick(ActionEvent event) {
        // detener timer y sonido warning inmediatamente
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
            animateBackground("#52ff8b");
            playSound(soundCorrect);
            score++;
            totalCorrect++;
        } else {
            feedbackLabel.setText("❌ Incorrecto");
            animateBackground("#ff6b6b");
            playSound(soundIncorrect);
        }

        currentIndex++;

        // Si ya no hay más preguntas del nivel -> resumen
        if (currentIndex >= currentLevelQuestions.size()) {
            // Asegurar que el sonido warning esté detenido y el timer también
            stopTimer();
            animateBackground("#89f7fe");
            showLevelSummarySafe();
            return;
        }

        // esperar un segundo para que el usuario vea el feedback, luego siguiente pregunta
        PauseTransition p = new PauseTransition(Duration.seconds(1));
        p.setOnFinished(e -> {
            animateBackground("#89f7fe");
            showQuestion();
        });
        p.play();
    }

    // -------------------- TIEMPO AGOTADO --------------------
    private void handleTimeExpired() {
        // Este método siempre se ejecuta en el hilo de UI porque lo llamamos con Platform.runLater en el timer
        feedbackLabel.setText("⏰ ¡Tiempo agotado!");
        animateBackground("#ff6b6b");
        playSound(soundIncorrect); // suena incorrect cuando se agota

        currentIndex++;

        // detener cualquier sonido time_up
        if (soundTimeUp != null && soundTimeUp.isPlaying()) {
            try { soundTimeUp.stop(); } catch (Exception ignored) {}
        }

        // Si se acabaron las preguntas -> mostrar resumen
        if (currentIndex >= currentLevelQuestions.size()) {
            stopTimer();
            animateBackground("#89f7fe");
            showLevelSummarySafe();
            return;
        }

        // si aún quedan preguntas, mostrar la siguiente tras una pausa
        PauseTransition p = new PauseTransition(Duration.seconds(1));
        p.setOnFinished(e -> {
            animateBackground("#89f7fe");
            showQuestion();
        });
        p.play();
    }

    // -------------------- RESUMEN DEL NIVEL --------------------
    private void showLevelSummary() {
        // Este método debe correr en el hilo de UI
        stopTimer();

        if (soundTimeUp != null && soundTimeUp.isPlaying()) {
            try { soundTimeUp.stop(); } catch (Exception ignored) {}
        }

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Nivel " + currentLevel + " completado");
        alert.setHeaderText("Resumen del nivel");
        alert.setContentText("Correctas: " + score + "/" + currentLevelQuestions.size() +
                "\nPorcentaje: " + (score * 100 / (double) currentLevelQuestions.size()) + "%");

        ButtonType next = new ButtonType("Avanzar al siguiente");
        ButtonType repeat = new ButtonType("Repetir nivel");
        ButtonType exit = new ButtonType("Salir del juego");

        alert.getButtonTypes().setAll(next, repeat, exit);

        alert.showAndWait().ifPresent(b -> {
            if (b == next) {
                currentLevel++;
                loadLevelQuestions();
            } else if (b == repeat) {
                loadLevelQuestions();
            } else {
                onExitClick(null);
            }
        });
    }

    // Llamamos a este wrapper por seguridad desde hilos no-UI
    private void showLevelSummarySafe() {
        Platform.runLater(this::showLevelSummary);
    }

    private void showFinalGameSummary() {
        stopTimer();
        if (soundLevelComplete != null) {
            try { soundLevelComplete.play(); } catch (Exception ignored) {}
        }

        double percentage = (totalQuestions == 0) ? 0.0 : (totalCorrect * 100.0 / totalQuestions);

        questionLabel.setText("🎉 ¡Juego completado!");
        feedbackLabel.setText("Correctas totales: " + totalCorrect +
                "\nIncorrectas totales: " + (totalQuestions - totalCorrect) +
                "\nPuntaje final: " + String.format("%.1f", percentage) + "%");

        nextButton.setVisible(false);
        exitButton.setVisible(true);
    }

    // -------------------- UTILS --------------------
    private void animateBackground(String color) {
        // Mantener el fondo celeste por defecto y solo sobrescribir momentáneamente
        rootPane.setStyle("-fx-background-color: " + color + "; -fx-padding: 20;");
    }

    private void playSound(AudioClip clip) {
        if (clip != null) {
            try {
                clip.stop();
                clip.play();
            } catch (Exception ignored) { }
        }
    }

    @FXML
    private void onHoverOptionEnter(javafx.scene.input.MouseEvent e) {
        Node n = (Node) e.getSource();
        n.setScaleX(1.05);
        n.setScaleY(1.05);
    }

    @FXML
    private void onHoverOptionExit(javafx.scene.input.MouseEvent e) {
        Node n = (Node) e.getSource();
        n.setScaleX(1.0);
        n.setScaleY(1.0);
    }

    @FXML
    private void onExitClick(ActionEvent e) {
        Stage s = (Stage) rootPane.getScene().getWindow();
        if (s != null) s.close();
    }
}

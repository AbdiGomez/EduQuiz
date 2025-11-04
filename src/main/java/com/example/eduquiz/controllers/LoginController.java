package com.example.eduquiz.controllers;

import com.example.eduquiz.model.User;
import com.example.eduquiz.services.AuthService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

public class LoginController {

    @FXML private TextField emailField;
    @FXML private PasswordField passwordField;
    @FXML private Label statusLabel;
    @FXML private Button loginButton;
    @FXML private Button registerButton;

    private final AuthService authService = new AuthService();

    @FXML
    void onLogin(ActionEvent event) {
        String email = emailField.getText().trim();
        String password = passwordField.getText().trim();

        if (email.isEmpty() || password.isEmpty()) {
            statusLabel.setText("⚠️ Ingresa tu correo y contraseña.");
            return;
        }

        User user = authService.login(email, password);
        if (user != null) {
            statusLabel.setText("✅ Bienvenido, " + user.getUsername() + "!");
            try {
                // Cargar el juego (quiz.fxml)
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/quiz.fxml"));
                Parent root = loader.load();
                Stage stage = (Stage) loginButton.getScene().getWindow();
                stage.setScene(new Scene(root));
                stage.setTitle("EduQuiz - Nivel " + user.getCurrentLevel());
            } catch (Exception e) {
                e.printStackTrace();
                statusLabel.setText("❌ Error al abrir el juego.");
            }
        } else {
            statusLabel.setText("❌ Credenciales incorrectas.");
        }
    }

    @FXML
    void onGoToRegister(ActionEvent event) {
        try {
            Stage stage = (Stage) registerButton.getScene().getWindow();
            Parent root = FXMLLoader.load(getClass().getResource("/fxml/register.fxml"));
            stage.setScene(new Scene(root));
            stage.setTitle("Registrar Usuario");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

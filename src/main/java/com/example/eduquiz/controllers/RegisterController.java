package com.example.eduquiz.controllers;

import com.example.eduquiz.services.AuthService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

public class RegisterController {

    @FXML private TextField usernameField;
    @FXML private TextField emailField;
    @FXML private PasswordField passwordField;
    @FXML private PasswordField confirmPasswordField;
    @FXML private Label statusLabel;
    @FXML private Button registerButton;
    @FXML private Button backToLoginButton;

    private final AuthService authService = new AuthService();

    @FXML
    void onRegister(ActionEvent event) {
        String username = usernameField.getText().trim();
        String email = emailField.getText().trim();
        String password = passwordField.getText();
        String confirmPassword = confirmPasswordField.getText();

        if (username.isEmpty() || email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
            statusLabel.setText("Todos los campos son obligatorios.");
            return;
        }

        if (!password.equals(confirmPassword)) {
            statusLabel.setText("Las contraseñas no coinciden.");
            return;
        }

        boolean success = authService.register(username, email, password);

        if (success) {
            statusLabel.setText("Registro exitoso. Redirigiendo al login...");

            // Usamos un hilo para no congelar la interfaz
            new Thread(() -> {
                try {
                    Thread.sleep(1000);
                    javafx.application.Platform.runLater(this::goToLogin);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }).start();

        } else {
            statusLabel.setText("Error al registrar usuario. Correo ya existente.");
        }
    }

    @FXML
    void onBackToLogin(ActionEvent event) {
        goToLogin();
    }

    private void goToLogin() {
        try {
            Stage stage = (Stage) backToLoginButton.getScene().getWindow();
            Parent root = FXMLLoader.load(getClass().getResource("/fxml/login.fxml"));
            stage.setScene(new Scene(root));
            stage.setTitle("Iniciar Sesión");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

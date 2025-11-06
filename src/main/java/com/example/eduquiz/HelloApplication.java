package com.example.eduquiz;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class HelloApplication extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        // Carga el archivo FXML desde el classpath
        FXMLLoader loader = new FXMLLoader(HelloApplication.class.getResource("/fxml/login.fxml"));

        if (loader.getLocation() == null) {
            System.err.println("No se encontró /fxml/login.fxml en el classpath");
            System.err.println("Classpath: " + System.getProperty("java.class.path"));
            return;
        }

        Parent root = loader.load();
        Scene scene = new Scene(root, 600, 400);
        stage.setTitle("EduQuiz - Inicio de sesión");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
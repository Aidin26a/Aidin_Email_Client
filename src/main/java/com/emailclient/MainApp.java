package com.emailclient;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

public class MainApp extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        // This looks in src/main/resources/com/emailclient/view/
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/emailclient/view/login.fxml"));

        if (fxmlLoader.getLocation() == null) {
            throw new IllegalStateException("FXML file not found! Double-check the folder path in src/main/resources.");
        }

        Scene scene = new Scene(fxmlLoader.load(), 400, 300);
        stage.setTitle("Aidin E-mail Client - Login");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
module com.emailclient {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires org.xerial.sqlitejdbc;
    requires jbcrypt; // Fixes the BCrypt red lines
    requires jakarta.mail; // Required for Sprint 3
    requires javafx.web;
    requires jakarta.activation;

    // This allows JavaFX to see your controllers (Fixes FXML errors)
    opens com.emailclient.controller to javafx.fxml;
    opens com.emailclient.model to javafx.base;

    exports com.emailclient.model;
    exports com.emailclient;
    exports com.emailclient.controller;
    exports com.emailclient.service;
    exports com.emailclient.utils; // Allows the app to use SceneService
}
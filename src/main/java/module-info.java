module com.emailclient {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires jakarta.mail;

    // Allows JavaFX to load FXML files and inject into controllers
    opens com.emailclient.controller to javafx.fxml;
    // Allows JavaFX to access the resources in this package
    opens com.emailclient to javafx.fxml;

    exports com.emailclient;
}
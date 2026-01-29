package com.emailclient.model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class EmailMessage {
    private final StringProperty sender;
    private final StringProperty subject;
    private final StringProperty date;
    private final StringProperty content;

    public EmailMessage(String sender, String subject, String date, String content) {
        this.sender = new SimpleStringProperty(sender);
        this.subject = new SimpleStringProperty(subject);
        this.date = new SimpleStringProperty(date);
        this.content = new SimpleStringProperty(content);
    }

    // Getters for the Properties (JavaFX TableView needs these)
    public StringProperty senderProperty() { return sender; }
    public StringProperty subjectProperty() { return subject; }
    public StringProperty dateProperty() { return date; }
    public StringProperty contentProperty() { return content; }

    // Standard Getters
    public String getSender() { return sender.get(); }
    public String getSubject() { return subject.get(); }
    public String getContent() { return content.get(); }
}
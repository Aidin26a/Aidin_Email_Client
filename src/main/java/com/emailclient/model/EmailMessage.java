package com.emailclient.model;

import com.emailclient.model.EmailMessage;
import java.time.LocalDateTime;
import java.util.List;

public class EmailMessage {
    private String messageId; // Unique ID for tracking [cite: 49]
    private String sender;    // Email address of the sender [cite: 49]
    private List<String> recipients; // List of recipients [cite: 49]
    private String subject;   // Email subject line [cite: 41]
    private String body;      // Content of the email [cite: 41]
    private LocalDateTime timestamp; // When it was sent/received [cite: 49]
    private boolean isRead;   // Status tracker (Functional Req F6) [cite: 41, 49]

    // Constructor, Getters and Setters go here
}
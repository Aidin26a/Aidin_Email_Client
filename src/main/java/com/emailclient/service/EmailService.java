package com.emailclient.service;

import com.emailclient.model.EmailMessage;
import jakarta.mail.*;
import jakarta.mail.internet.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.io.File;
import java.util.Properties;

public class EmailService {

    public ObservableList<EmailMessage> fetchEmails(String email, String password) throws Exception {
        ObservableList<EmailMessage> emails = FXCollections.observableArrayList();
        Properties props = new Properties();
        props.put("mail.store.protocol", "imaps");
        props.put("mail.imaps.host", "imap.gmail.com");
        props.put("mail.imaps.port", "993");
        props.put("mail.imaps.ssl.enable", "true");
        props.put("mail.imaps.ssl.trust", "*");

        Session session = Session.getInstance(props);
        Store store = session.getStore("imaps");
        store.connect("imap.gmail.com", email, password);

        Folder inbox = store.getFolder("INBOX");
        inbox.open(Folder.READ_ONLY);

        Message[] messages = inbox.getMessages();
        int start = Math.max(0, messages.length - 15);

        for (int i = messages.length - 1; i >= start; i--) {
            Message msg = messages[i];
            emails.add(new EmailMessage(
                    msg.getFrom()[0].toString(),
                    msg.getSubject(),
                    msg.getSentDate().toString(),
                    getTextFromMessage(msg) // Fetches real HTML/Text
            ));
        }
        inbox.close(false);
        store.close();
        return emails;
    }

    public void sendEmail(String senderEmail, String password, String recipient, String subject, String body, File attachment) throws Exception {
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true"); // Requirement N1: Security
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        Session session = Session.getInstance(props, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(senderEmail, password);
            }
        });

        Message message = new MimeMessage(session);
        message.setFrom(new InternetAddress(senderEmail));
        message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipient));
        message.setSubject(subject);

        // Create the message part
        MimeBodyPart messageBodyPart = new MimeBodyPart();
        messageBodyPart.setText(body);

        // Create a multipart container
        Multipart multipart = new MimeMultipart();
        multipart.addBodyPart(messageBodyPart);

        // Requirement F3: Logic for adding the file attachment
        if (attachment != null) {
            MimeBodyPart attachmentPart = new MimeBodyPart();
            attachmentPart.attachFile(attachment);
            multipart.addBodyPart(attachmentPart);
        }

        // Put everything together
        message.setContent(multipart);

        // Send the email
        Transport.send(message);
        System.out.println("Email sent successfully with attachment!");
    }

    private String getTextFromMessage(Message message) throws Exception {
        if (message.isMimeType("text/*")) return message.getContent().toString();
        if (message.isMimeType("multipart/*")) {
            MimeMultipart mimeMultipart = (MimeMultipart) message.getContent();
            return getTextFromMimeMultipart(mimeMultipart);
        }
        return "";
    }

    private String getTextFromMimeMultipart(MimeMultipart mimeMultipart) throws Exception {
        String result = "";
        for (int i = 0; i < mimeMultipart.getCount(); i++) {
            BodyPart bodyPart = mimeMultipart.getBodyPart(i);
            if (bodyPart.isMimeType("text/plain")) {
                result = bodyPart.getContent().toString();
            } else if (bodyPart.isMimeType("text/html")) {
                // HTML is better for WebView, so we return it immediately if found
                return bodyPart.getContent().toString();
            }
        }
        return result;
    }
}
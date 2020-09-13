package main.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Properties;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;


@Service
public class MailService {

    @Value("${spring.mail.username}")
    private String senderEmail;
    @Value("${spring.mail.password}")
    private String senderPass;


    public void send(String email, String subject, String msg) throws MessagingException {
        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        Authenticator auth = new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(senderEmail, senderPass);
            }
        };
        Session session = Session.getDefaultInstance(props, auth);

        MimeMessage message = new MimeMessage(session); // email message
        message.setFrom(new InternetAddress(senderEmail)); // setting header fields
        message.addRecipient(Message.RecipientType.TO, new InternetAddress(email));
        message.setSubject(subject); // subject line
        message.setText(msg);
        Transport.send(message);
    }
}

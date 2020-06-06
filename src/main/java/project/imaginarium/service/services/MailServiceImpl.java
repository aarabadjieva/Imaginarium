package project.imaginarium.service.services;

import org.springframework.stereotype.Service;
import project.imaginarium.service.models.MailForm;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

@Service
public class MailServiceImpl implements MailService {

    private static final String USERNAME = "imaginarium.contacts@gmail.com";
    private static final String PASSWORD ="imaginarium2085";
    private static final String RECIPIENT = "aarabadjieva@students.softuni.bg";
    private static final String HOST = "smtp.gmail.com";

    @Override
    public void sendEmail(MailForm form) {

        Properties properties = System.getProperties();
        properties.setProperty("mail.smtp.host", HOST);
        properties.put("mail.smtp.starttls.enable", true);
        properties.put("mail.smtp.ssl.trust", HOST);
        properties.put("mail.smtp.auth", "true");



        Session session = Session.getDefaultInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(USERNAME, PASSWORD);
            }
        });

        try {
            MimeMessage message = new MimeMessage(session);
            message.setRecipient(Message.RecipientType.TO, new InternetAddress(RECIPIENT));
            message.setSubject(form.getSubject());
            message.setText("From: " + form.getEmail() + "\n" + form.getMessage());

            Transport.send(message);
            Thread.sleep(3000);
        } catch (MessagingException | InterruptedException mex) {
            mex.printStackTrace();
        }
    }

}

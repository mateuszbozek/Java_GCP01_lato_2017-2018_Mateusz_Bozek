package example.logger;


import example.model.Student;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class MailLogger implements Logger {


    private void sendMessage(String status, Student student) {
        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.socketFactory.port", "465");
        props.put("mail.smtp.socketFactory.class",
                "javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.port", "465");
        Session session = Session.getDefaultInstance(props,
                new javax.mail.Authenticator() {
                    @Override
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication("itexample77@gmail.com","informatyka");
                    }
                });

        try {

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("frommail@gmail.com"));
            message.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse("mateuszbozek077@gmail.com"));
            message.setSubject("Crawler notification ("+status+" person)");
            message.setText(student.toString()+" Crawler works fine");

            Transport.send(message);

        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }


    }

    @Override
    public void log(String status, Student student) {
        sendMessage(status, student);
    }
}

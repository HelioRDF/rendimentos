package org.example;
import javax.mail.*;
import javax.mail.internet.*;
import java.util.Properties;

public class EnviarEmailOutlook {
    public static void main(String[] args) {
        // Propriedades do servidor SMTP
        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp-mail.outlook.com");
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");

        // Sessão  SMTP
        Session session = Session.getDefaultInstance(props,
            new Authenticator() {
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication("heliordf@hotmail.com", "Fiserv#060323");
                }
            });

        try {
            // Mensagem
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("heliordf@outlook.com"));
            message.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse("heliordf@outlook.com"));
            message.setSubject("Assunto do email");
            message.setText("Corpo do email");

            // Enviar a mensagem
            Transport.send(message);

            System.out.println("Email enviado com sucesso!");
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}
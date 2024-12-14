package org.example;
import javax.mail.*;
import javax.mail.internet.MimeMessage;
import java.util.Properties;


// ... (resto do código)

public class SendEmailOutlookOAuth {
    public static void main(String[] args) throws Exception {
        // ... (configuração do GoogleClientSecrets)

        // Construir o fluxo de autorização
        GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(
                httpTransport, JSON_FACTORY, clientSecrets, SCOPES)
                .setDataStoreFactory(new FileDataStoreFactory(new java.io.File(DATA_STORE_DIR)))
                .setAccessType("offline")
                .build();

        // Obter o token de acesso
        LocalServerReceiver receiver = new LocalServerReceiver.Builder().setPort(8888).build();
        Credential credential = new AuthorizationCodeInstalledApp(flow, receiver).authorize("user");

        // Configurar a sessão de email
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.office365.com");
        props.put("mail.smtp.port", "587");

        Session session = Session.getDefaultInstance(props,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(credential.getAccessToken(), "");
                    }
                });

        // Criar e enviar a mensagem
        // ...
    }
}
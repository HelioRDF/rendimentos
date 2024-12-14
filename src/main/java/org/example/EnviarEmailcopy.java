package org.example;

import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.SimpleEmail;

public class EnviarEmailcopy {

	public static void main(String[] args) {
		SimpleEmail email = new SimpleEmail();

		String userName = "heliordf@gmail.com"; // usuário via token
		String pass = "!Killer017224!!"; // pass via token ou senha padrão
  
        try {
            // Configurações (conforme explicado anteriormente)
            email.setHostName("smtp.gmail.com");
            email.setSslSmtpPort("587");
			email.setSSLOnConnect(true); // Habilita TLS
            email.setFrom("seu_email@gmail.com");
            email.addTo("destinatario@exemplo.com");
            email.setSubject("Assunto do Email");
            email.setMsg("Corpo do Email");
            email.setAuthenticator(new DefaultAuthenticator(userName, pass));

            email.send();
            System.out.println("Email enviado com sucesso!");
        } catch (EmailException e) {
            System.err.println("Erro ao enviar email: " + e.getMessage());
            // Exemplo: para debug (opcional)
            e.printStackTrace();
        }
	}



}

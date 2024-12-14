package org.example;

import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.HtmlEmail;

public class EnviarEmail {

	private String apelido = "myUser";
	private String destino = "heliordf@gmail.com";
	private String apelidoDestino = "client";

	public static void main(String[] args) {
		EnviarEmail emailControl = new EnviarEmail();
		emailControl.emailOutlook("xxx", "x");
	}

	public void emailOutlook(String resultado, String titulo) {
		try {
			String smtp = "smtp.gmail.com";// "smtp.gmail.com" | "smtp-mail.outlook.com"
			String email = "helio.dafranca@fiserv.com";
			String userName = "heliordf@gmail.com"; // usuário via token
			String pass = "!Killer017224!!"; // pass via token ou senha padrão
			



			// Create the email message
			HtmlEmail configEmail = new HtmlEmail();

			configEmail.setDebug(true);
			configEmail.setHostName(smtp); // servidor SMTP para envio do e-mail |
			// configEmail.setSslSmtpPort("465"); //Usar para smtp gmail
			configEmail.setSmtpPort(587);
			configEmail.setAuthenticator(new DefaultAuthenticator(userName, pass));
			configEmail.addTo(destino, apelidoDestino);
			configEmail.setFrom(email, apelido);
			configEmail.setSubject(titulo);
			configEmail.setStartTLSEnabled(true);

			String textoHtml = "olá mundo!";
			// set the html message
			configEmail.setHtmlMsg(textoHtml);

			// set the alternative message
			configEmail.setTextMsg("Seu E-mail não suporta mensagem no formato HTML.");

			// send the email
			configEmail.send();

		} catch (Exception e) {
			System.out.println("Email office-365 não foi enviado----------------------------\n" + e);
			// emailHtmlGmail(resultado, titulo);
		}
	}

}



/* Referências
 * https://www.lifewire.com/what-are-the-gmail-smtp-settings-1170854
 * https://support.google.com/a/answer/176600?hl=pt-BR
 * http://www.devmedia.com.br/utilizando-a-api-commons-email-para-enviar-e-mails/3306
 */

// Adicionar no POM!
/*
 * <dependency>
 * <groupId>org.apache.commons</groupId>
 * <artifactId>commons-email</artifactId>
 * <version>1.3.3</version>
 * </dependency>
 * 
 */
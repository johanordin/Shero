package edu.upc.ase.rest.test;

import java.io.UnsupportedEncodingException;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.apache.log4j.Logger;

import com.googlecode.objectify.ObjectifyService;

import edu.upc.ase.domain.User;
import edu.upc.ase.domain.admin.EmailTemplate;

public class TestMailService {

	private static Logger logger = Logger.getLogger(TestMailService.class);

	public String sendWelcomeMail(User user) {

		Properties props = new Properties();
		Session session = Session.getDefaultInstance(props, null);

		try {
			Message msg = new MimeMessage(session);
			String msgText = getHtmlMessageText("WelcomeTemplate");
			// Replace placeholder USERNAME with real username
			msgText = msgText.replace("#USERNAME#", user.getFullname());
			msg.setContent(msgText, "text/html");

			msg.setFrom(new InternetAddress("shero.ase@gmail.com", "Shero Welcome Mail"));
			msg.addRecipient(Message.RecipientType.TO, new InternetAddress(user.getEmailAddress(), user.getFullname()));
			msg.setSubject("Welcome to Shero");
			Transport.send(msg);

		} catch (AddressException e) {
			logger.error("Address Error", e);
		} catch (MessagingException e) {
			logger.error("Message Error", e);
		} catch (UnsupportedEncodingException e) {
			logger.error("UnsupportedEncodingException", e);
		}

		return "{\"status\":\"successful\"}";
	}

	private String getHtmlMessageText(String templateName) {
		EmailTemplate emailTemplate = ObjectifyService.ofy().load().type(EmailTemplate.class)
				.filter("name", templateName).list().get(0);
		return emailTemplate.getHtmlText();
	}

}

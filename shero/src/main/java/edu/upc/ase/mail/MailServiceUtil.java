package edu.upc.ase.mail;

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

import edu.upc.ase.dao.ItemDao;
import edu.upc.ase.dao.UserDao;
import edu.upc.ase.domain.Item;
import edu.upc.ase.domain.Rental;
import edu.upc.ase.domain.User;
import edu.upc.ase.domain.admin.EmailTemplate;
import edu.upc.ase.rest.test.TestMailService;

public class MailServiceUtil {
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
	
	public String sendRentalMail(Rental rental) {
		
		//The Users who rented the item
		User renter = new UserDao().getUserById(rental.getUserId());
		
		//The rented Item
		Item rentedItem = new ItemDao().getItemById(rental.getItemId());
		
		//The owner of the Item
		User owner = new UserDao().getUserById(rentedItem.getOwnerId());

		Properties props = new Properties();
		Session session = Session.getDefaultInstance(props, null);

		
		//Send eMail to renter
		try {
			Message msg = new MimeMessage(session);
			String msgText = getHtmlMessageText("RenterTemplate");
			// Replace placeholder USERNAME with real username
			msgText = msgText.replace("#USERNAME#", renter.getFullname());
			msg.setContent(msgText, "text/html");

			msg.setFrom(new InternetAddress("shero.ase@gmail.com", "Shero, be a sharing Hero"));
			msg.addRecipient(Message.RecipientType.TO, new InternetAddress(renter.getEmailAddress(), renter.getFullname()));
			msg.setSubject("Congratulations, you rented "+rentedItem.getName());
			Transport.send(msg);

		} catch (AddressException e) {
			logger.error("Address Error", e);
		} catch (MessagingException e) {
			logger.error("Message Error", e);
		} catch (UnsupportedEncodingException e) {
			logger.error("UnsupportedEncodingException", e);
		}

		
		//Send eMail to owner
		try {
			Message msg = new MimeMessage(session);
			String msgText = getHtmlMessageText("OwnerTemplate");
			// Replace placeholder USERNAME with real username
			msgText = msgText.replace("#USERNAME#", owner.getFullname());
			msg.setContent(msgText, "text/html");

			msg.setFrom(new InternetAddress("shero.ase@gmail.com", "Shero, be a sharing Hero"));
			msg.addRecipient(Message.RecipientType.TO, new InternetAddress(owner.getEmailAddress(), owner.getFullname()));
			msg.setSubject("Congratulations, you rented out your Item: "+rentedItem.getName());
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

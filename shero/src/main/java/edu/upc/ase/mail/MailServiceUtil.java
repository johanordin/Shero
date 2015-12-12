package edu.upc.ase.mail;

import java.io.UnsupportedEncodingException;
import java.text.DateFormat;
import java.util.Date;
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
import edu.upc.ase.domain.Address;
import edu.upc.ase.domain.Item;
import edu.upc.ase.domain.Rental;
import edu.upc.ase.domain.User;
import edu.upc.ase.domain.admin.EmailTemplate;
import edu.upc.ase.domain.helper.RentalQuestion;
import edu.upc.ase.rest.test.TestMailService;

public class MailServiceUtil {
	private static Logger logger = Logger.getLogger(TestMailService.class);

	public String sendWelcomeMail(User user) {

		Properties props = new Properties();
		Session session = Session.getDefaultInstance(props, null);

		try {
			Message msg = new MimeMessage(session);
//			String msgText = getHtmlMessageText("WelcomeTemplate");
//			// Replace placeholder USERNAME with real username
//			msgText = msgText.replace("#USERNAME#", user.getFullname());
//			msg.setContent(msgText, "text/html");
			
			//String welcome 		 = "Welcome to Shero," + user.getFullname() + "\n";
			String welcome 		 = "<h1> Welcome to Shero, " + user.getFullname() + "</h1>";
			String start_sharing = "<p>Start sharing your items right now or search for Items near your location!</p>";
			
			String thanks		 = "<br><p> Share what’s spare - be a sharing hero </p>";
			String msgFull = welcome + start_sharing + thanks;
			msg.setContent(msgFull, "text/html");
			
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

		// The Users who rented the item
		User renter = new UserDao().getUserById(rental.getUserId());
		
		// The rented Item
		Item item = new ItemDao().getItemById(rental.getItemId());
		Address itemAddress = item.getAddress();
		String city = itemAddress.getCity();
		String street = itemAddress.getStreet();

		// The owner of the Item
		User owner = new UserDao().getUserById(item.getOwnerId());

		// Information about the rental
		String startDate = rental.getPeriod().get(0).toString();
		String endDate   = rental.getPeriod().get(rental.getPeriod().size()-1).toString();
		
		Properties props = new Properties();
		Session session = Session.getDefaultInstance(props, null);

		// Send eMail to renter
		try {
			Message msg = new MimeMessage(session);
//			String msgText = getHtmlMessageText("RenterTemplate");
//			// Replace placeholder USERNAME with real username
//			msgText = msgText.replace("#OWNER#", owner.getFullname());
//			msgText = msgText.replace("#RENTER#", renter.getFullname());
//			msgText = msgText.replace("#ITEMNAME#", item.getName());
//			msgText = msgText.replace("#STARTDATE#", rental.getPeriod().get(0).toString());
//			msgText = msgText.replace("#ENDDATE#", rental.getPeriod().get(rental.getPeriod().size()-1).toString());
//			msgText = msgText.replace("#ADDRESS#", item.getAddress().toString());
//			msg.setContent(msgText, "text/html");
			
			String heading 		 = "<h1> Congratulations, " + renter.getFullname() + "</h1>";
			String sucessfully   = "<p>You sucessfully out rented " + item.getName() + " from " + owner.getFullname() + "</p>";
			
			String pickup 		 = "<p> You can pick up the " + item.getName() + " on " + startDate + " at "+ street + ", " + city + "</p>";
			String bringitback   = "<p> And don't forget to bring it back on: " + endDate + "</p>";
			
			String thanks		 = "<br><p> Share what’s spare - be a sharing hero </p>";
			String msgFull = heading + sucessfully + pickup + bringitback + thanks;
			msg.setContent(msgFull, "text/html");

			msg.setFrom(new InternetAddress("shero.ase@gmail.com", "Shero, be a sharing Hero"));
			msg.addRecipient(Message.RecipientType.TO,
					new InternetAddress(renter.getEmailAddress(), renter.getFullname()));
			msg.setSubject("Congratulations, you rented " + item.getName());
			Transport.send(msg);

		} catch (AddressException e) {
			logger.error("Address Error", e);
		} catch (MessagingException e) {
			logger.error("Message Error", e);
		} catch (UnsupportedEncodingException e) {
			logger.error("UnsupportedEncodingException", e);
		}

		// Send eMail to owner
		try {
			Message msg = new MimeMessage(session);
//			String msgText = getHtmlMessageText("OwnerTemplate");
//			// Replace placeholder USERNAME with real username
//			msgText = msgText.replace("#OWNER#", owner.getFullname());
//			msgText = msgText.replace("#RENTER#", renter.getFullname());
//			msgText = msgText.replace("#ITEMNAME#", item.getName());
//			msgText = msgText.replace("#STARTDATE#", DateFormat.getDateInstance().format(rental.getPeriod().get(0)));
//			msgText = msgText.replace("#ENDDATE#", DateFormat.getDateInstance().format(rental.getPeriod().get(rental.getPeriod().size()-1)));
//			msgText = msgText.replace("#ADDRESS#", item.getAddress().toString());
//			msg.setContent(msgText, "text/html");
			
			String heading 		 = "<h1> Congratulations, " + owner.getFullname() + "</h1>";
			String sucessfully   = "<p>You sucessfully out rented one of your items.</p>";
			String information   = "<p>The user: " + renter.getFullname() + " rented your item: " +item.getName() + "</p>";
			String thanks		 = "<br><p> Share what’s spare - be a sharing hero </p>";
			String msgFull = heading + sucessfully + information + thanks;
			msg.setContent(msgFull, "text/html");
			
			msg.setFrom(new InternetAddress("shero.ase@gmail.com", "Shero, be a sharing Hero"));
			msg.addRecipient(Message.RecipientType.TO,
					new InternetAddress(owner.getEmailAddress(), owner.getFullname()));
			msg.setSubject("Congratulations, you rented out your Item: " + item.getName());
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

	public String sendRentalQuestionMail(RentalQuestion rentalQuestion) {

		// The Item to ask about
		Item item = new ItemDao().getItemById(rentalQuestion.getItemId());
		// The Users who rented the item
		User renter = new UserDao().getUserById(rentalQuestion.getRenterId());
		// The owner of the Item
		User owner = new UserDao().getUserById(item.getOwnerId());

		// Question
		String question = rentalQuestion.getText();
		
		Properties props = new Properties();
		Session session = Session.getDefaultInstance(props, null);

		// Send eMail to renter
		try {
			Message msg = new MimeMessage(session);
//			String msgText = getHtmlMessageText("QuestionTemplate");
//			// Replace placeholder USERNAME with real username
//			msgText = msgText.replace("#RENTER#", renter.getFullname());
//			msgText = msgText.replace("#QUESTION#", rentalQuestion.getText());
//			msg.setContent(msgText, "text/html");
			
			String heading 		   = "<h1> Hello, " + owner.getFullname() + "</h1>";
			
			String information     = "<p>The user: " + renter.getFullname() + " have a question about your item: " +item.getName() + "</p>";
			String question_html   = "<br><p>" + question + "</p>";
			
			String thanks		   = "<br><p> Share what’s spare - be a sharing hero </p>";
			String msgFull = heading + information + question_html + thanks;
			msg.setContent(msgFull, "text/html");	
			

			msg.setFrom(new InternetAddress("shero.ase@gmail.com", "Shero, be a sharing Hero"));
			msg.addRecipient(Message.RecipientType.TO,
					new InternetAddress(owner.getEmailAddress(), owner.getFullname()));
//			msg.addRecipient(Message.RecipientType.CC,
//					new InternetAddress(renter.getEmailAddress(), renter.getFullname()));			
			msg.setSubject("You hava a question about '" + item.getName() + "'");
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

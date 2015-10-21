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
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.google.gson.Gson;


@Path("/mailtest")
public class TestMailService {
		private static final Gson GSON = new Gson();

		@GET
		@Produces(MediaType.APPLICATION_JSON)
		public String testMail() throws UnsupportedEncodingException {
			
			Properties props = new Properties();
			Session session = Session.getDefaultInstance(props, null);

			String msgBody = "...";

			try {
			    Message msg = new MimeMessage(session);
			    msg.setFrom(new InternetAddress("shero.ase@gmail.com", "Example.com Admin"));
			    msg.addRecipient(Message.RecipientType.TO,
			     new InternetAddress("xpl@gmx.de", "Mr. User"));
			    msg.setSubject("Your Example.com account has been activated");
			    msg.setText(msgBody);
			    Transport.send(msg);

			} catch (AddressException e) {
			    // ...
			} catch (MessagingException e) {
			    // ...
			}

			
			return "{\"status\":\"successful\"}";
		}
		
}

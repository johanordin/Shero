package edu.upc.ase.serverstartup;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import java.util.logging.Logger;

import com.google.appengine.api.datastore.Blob;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.ObjectifyService;

import edu.upc.ase.domain.Image;
import edu.upc.ase.domain.Item;
import edu.upc.ase.domain.User;
import edu.upc.ase.domain.UserRating;
import edu.upc.ase.domain.admin.EmailTemplate;

public class SetupDatastoreUtil {
	
	private static final Logger logger = Logger.getLogger("SetupDatastoreUtil");

	//Initialize EMailTemplate
	public static void setupEMailTemplate() {

	List<EmailTemplate> emailTemplates = ObjectifyService.ofy().load().type(EmailTemplate.class)
			.filter("name", "WelcomeTemplate").list();
	
	if(emailTemplates.isEmpty()){
		//If not create now Email Template
		EmailTemplate emailTemplate = new EmailTemplate("WelcomeTemplate", HTML_TEMPLATE);
		ObjectifyService.ofy().save().entity(emailTemplate).now();
	}
	}
	
	public static void setupUser() {
		User from = new User("Max", "Mustermann", "max@mustermann.de");
		User to = new User("Peter", "Pan", "p@p.de");
		
		Key<User> fromUser = ObjectifyService.ofy().save().entity(from).now();
		Key<User> toUser = ObjectifyService.ofy().save().entity(to).now();
		
		UserRating r = new UserRating();
		r.setRatingValue(1);
		r.setFrom(fromUser.getId());
		r.setTo(toUser.getId());
		
		UserRating r2 = new UserRating(fromUser.getId(), toUser.getId(), 3);
		
		Item i = new Item("theItem", 11.00, "desc...");
		Key<Item> iKey = ObjectifyService.ofy().save().entity(i).now();
		
		ObjectifyService.ofy().save().entity(r).now();
		ObjectifyService.ofy().save().entity(r2).now();
		
		from.addItem(iKey);
		
		Key<User> newUser = ObjectifyService.ofy().save().entity(from).now();
		
		User user = ObjectifyService.ofy().load().type(User.class).id(newUser.getId()).now();
		logger.info("ratings: " + user.getReceivedRatings());
		logger.info("addresses: " + user.getAddresses());
		logger.info("items: " + user.getItems());
	}
	
	private static String HTML_TEMPLATE = "<html>" + "<head>" + "<title>Welcome to Shero!</title>" + "</head>"
			+ "<body>" + "<h1> Hello #USERNAME#</h1>" + "<p>Welcome to Shero, be a sharing Hero!<p/>" + "</body>"
			+ "</html>";
}

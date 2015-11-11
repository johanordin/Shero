package edu.upc.ase.serverstartup;

import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

import com.googlecode.objectify.Key;
import com.googlecode.objectify.ObjectifyService;

import edu.upc.ase.domain.Address;
import edu.upc.ase.domain.Availability;
import edu.upc.ase.domain.Item;
import edu.upc.ase.domain.ItemRating;
import edu.upc.ase.domain.Tag;
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
		
		Address addr = new Address("Barcelona","08001", "Avd. Diagonal","21");
		Key<Address> addrKey = ObjectifyService.ofy().save().entity(addr).now();
		
		Address addr2 = new Address("Madrid","10001", "Schinkenstrasse","213");
		Key<Address> addrKey2 = ObjectifyService.ofy().save().entity(addr2).now();
		
		from.addAddress(addrKey);
		from.addAddress(addrKey2);
		to.addAddress(addrKey2);
		
		UserRating r = new UserRating();
		r.setRatingValue(4);
		r.setFrom(fromUser.getId());
		r.setTo(toUser.getId());
		
		UserRating r2 = new UserRating(fromUser.getId(), toUser.getId(), 3);
		
		
		
		Date date = new Date(2015, 11, 24);
		Availability days = new Availability(date);
		ObjectifyService.ofy().save().entity(days).now();

		Item i = new Item("Surfboard", 150.0, "A surfboard is an elongated platform used in the sport of surfing. This surfboard is relatively light, but is strong enough to support an individual standing on them while riding a breaking wave. This surfboard was invented in ancient Hawaii.");
		Item i2 = new Item("Tennis ball", 2000.0, "A tennis ball is a ball designed for the sport of tennis. Tennis balls are yellow at major sporting events, but in recreational play can be virtually any color. Tennis balls are covered in a fibrous felt which modifies their aerodynamic properties, and each has a white curvilinear oval covering it.");
		Item i3 = new Item("Golf", 10.0, "A tennis ball is a ball designed for the sport of tennis. Tennis balls are yellow at major sporting events, but in recreational play can be virtually any color. Tennis balls are covered in a fibrous felt which modifies their aerodynamic properties, and each has a white curvilinear oval covering it.");
		i.setAddress(addr);
		i.addAvailableDay(days);
		i2.setAddress(addr);
		i3.setAddress(addr2);
		
		

		
		
		
		Key<Item> iKey = ObjectifyService.ofy().save().entity(i).now();
		Key<Item> iKey2 = ObjectifyService.ofy().save().entity(i2).now();
		
		i.setAddress(addr2);
		Key<Item> i2Key = ObjectifyService.ofy().save().entity(i2).now();
		

		ItemRating itemRating = new ItemRating(i.getId(),from.getId(),2);
		
		ObjectifyService.ofy().save().entity(itemRating).now();
		
		ObjectifyService.ofy().save().entity(r).now();
		ObjectifyService.ofy().save().entity(r2).now();
		
		from.addItem(iKey);
		from.addItem(i2Key);
		from.addItem(iKey2);
		
		Key<User> newUser = ObjectifyService.ofy().save().entity(from).now();
		
		User user = ObjectifyService.ofy().load().type(User.class).id(newUser.getId()).now();
		
		//Creating and saving new tag
		Tag tag = new Tag("testtag");
		Key<Tag> newTag = ObjectifyService.ofy().save().entity(tag).now();
		
		logger.info("ratings: " + user.getReceivedRatings());
		logger.info("addresses: " + user.getAddresses());
		logger.info("items: " + user.getItems());
	}
	
	private static String HTML_TEMPLATE = "<html>" + "<head>" + "<title>Welcome to Shero!</title>" + "</head>"
			+ "<body>" + "<h1> Hello #USERNAME#</h1>" + "<p>Welcome to Shero, be a sharing Hero!<p/>" + "</body>"
			+ "</html>";
}

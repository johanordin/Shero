package edu.upc.ase.rest.test;

import java.util.logging.Logger;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.google.gson.Gson;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.ObjectifyService;

import edu.upc.ase.domain.Item;
import edu.upc.ase.domain.User;
import edu.upc.ase.domain.UserRating;

@Path("/testusers")	
public class TestUserRestServices {
	private static final Gson GSON = new Gson();
	private static final Logger logger = Logger.getLogger("TestUserRestService");
	
	@GET
	@Path("/setupUser")
	@Produces(MediaType.APPLICATION_JSON)
	public String setupTestUser() {
		User test = new User("Max", "Mux", "max@mux.es");
		Key<User> newUser = ObjectifyService.ofy().save().entity(test).now();
		User user = ObjectifyService.ofy().load().type(User.class).id(newUser.getId()).now();
		return GSON.toJson(user);
	}
	
	@GET
	@Path("/relationTest")
	@Produces(MediaType.APPLICATION_JSON)
	public String testing() {
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
		return user.serialize();
	}
}

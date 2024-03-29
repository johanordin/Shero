package edu.upc.ase.rest.test;

import java.util.List;
import java.util.logging.Logger;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.fluttercode.datafactory.impl.DataFactory;

import com.google.gson.Gson;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.ObjectifyService;
import com.googlecode.objectify.Ref;

import edu.upc.ase.domain.Address;
import edu.upc.ase.domain.Item;
import edu.upc.ase.domain.ItemRating;
import edu.upc.ase.domain.Tag;
import edu.upc.ase.domain.User;
import edu.upc.ase.domain.UserRating;

@Path("/testitems")	
public class TestItemRestServices {
	private static final Gson GSON = new Gson();
	private static final Logger logger = Logger.getLogger("TestItemRestService");
	
	@GET
	@Path("/test")
	@Produces(MediaType.APPLICATION_JSON)
	public String getFromDB() {
		List<Item> items = ObjectifyService.ofy()
		          .load()
		          .type(Item.class) // We want only Greetings
		          //.order("-location")       // Most recent first - date is indexed.
		          .limit(5)             // Only show 5 of them.
		          .list();
		System.out.println(items.get(0));
		return GSON.toJson(items);
	}
	
	@GET
	@Path("/ratings")
	@Produces(MediaType.APPLICATION_JSON)
	public String getRatings() {
		List<ItemRating> ratings = ObjectifyService.ofy()
		          .load()
		          .type(ItemRating.class)
		          .list();		
		return GSON.toJson(ratings);
	}
	
	@GET
	@Path("/setupRatings")
	@Produces(MediaType.APPLICATION_JSON)
	public String setupRatings() {
		ItemRating r1 = new ItemRating();
		r1.setRatingValue(3);
		ItemRating r2 = new ItemRating();
		r2.setRatingValue(5);
		ObjectifyService.ofy().save().entities(r1,r2).now();
		
		return "{\"status\":\"r done\"}";
	}
	
	@GET
	@Path("/setupitem")
	@Produces(MediaType.APPLICATION_JSON)
	public String setupItem() {
		logger.info("Setup aufrufen");
		DataFactory df = DataFactory.create();
		User user = new User(df.getFirstName(),df.getLastName(), df.getEmailAddress());
		Key<User> userKey = ObjectifyService.ofy().save().entity(user).now();
		User user2 = new User(df.getFirstName(),df.getLastName(), df.getEmailAddress());
		Key<User> user2Key = ObjectifyService.ofy().save().entity(user2).now();
		
		
		Item item = new Item(df.getRandomWord(5, 20), new Double(df.getNumber()), "description");
		Address addr = new Address(df.getCity(),String.valueOf( df.getNumberBetween(10000, 99999)), df.getStreetName(), 
				String.valueOf(df.getNumberUpTo(300)));
		// need to store to db, so the address entity gets a key in the db that can be assigned to the item
		ObjectifyService.ofy().save().entity(addr).now();
		item.setAddress(addr);
		
		Tag tag1 = new Tag("sports");
		Tag tag2 = new Tag("summer");
		Key<Tag> tag1Key = ObjectifyService.ofy().save().entity(tag1).now();
		Key<Tag> tag2Key = ObjectifyService.ofy().save().entity(tag2).now();
		 
		item.addTag(Ref.create(tag1Key));
		item.addTag(Ref.create(tag2Key));
		
		Key<Item> itemKey = ObjectifyService.ofy().save().entity(item).now();
		user.addItem(item);
		ObjectifyService.ofy().save().entity(user).now();
		
		int ratingValue = df.getNumberBetween(0, 5);
		ItemRating itemRating = new ItemRating(itemKey.getId(), userKey.getId(), ratingValue);
		item.updateRatingCount(ratingValue);
		ObjectifyService.ofy().save().entity(itemRating).now();
		
		ratingValue = df.getNumberBetween(0, 5);
		ItemRating itemRating2 = new ItemRating(itemKey.getId(), user2Key.getId(), ratingValue);
		item.updateRatingCount(ratingValue);
		
		// save item again to store updated rating counts
		ObjectifyService.ofy().save().entity(item).now();
		
		ObjectifyService.ofy().save().entity(itemRating2).now();
		logger.info("Saved successfully.");
	    return "{\"status\":\"done\"}";
	}
	
	@GET
	@Path("/setupuser")
	@Produces(MediaType.APPLICATION_JSON)
	public String setupUser() {
		logger.info("Setup aufrufen");
		DataFactory df = DataFactory.create();
		
		User user = new User(df.getFirstName(),df.getLastName(), df.getEmailAddress());
		Key<User> userKey = ObjectifyService.ofy().save().entity(user).now();
		User user2 = new User(df.getFirstName(),df.getLastName(), df.getEmailAddress());
		Key<User> user2Key = ObjectifyService.ofy().save().entity(user2).now();
		
		Address addr = new Address(df.getCity(),String.valueOf( df.getNumberBetween(10000, 99999)), df.getStreetName(), 
				String.valueOf(df.getNumberUpTo(300)));
		// need to store to db, so the address entity gets a key in the db that can be assigned to the item
		Key<Address> key = ObjectifyService.ofy().save().entity(addr).now();
		
		Address addr2 = new Address(df.getCity(),String.valueOf( df.getNumberBetween(10000, 99999)), df.getStreetName(), 
				String.valueOf(df.getNumberUpTo(300)));
		// need to store to db, so the address entity gets a key in the db that can be assigned to the item
		Key<Address> key2 = ObjectifyService.ofy().save().entity(addr2).now();
		
		user.addAddress(key2);
		user2.addAddress(key);
		
		UserRating userRating = new UserRating(userKey.getId(), user2Key.getId(), df.getNumberBetween(0, 5));
		ObjectifyService.ofy().save().entity(userRating).now();
		
		UserRating userRating2 = new UserRating(userKey.getId(), user2Key.getId(), df.getNumberBetween(0, 5));
		ObjectifyService.ofy().save().entity(userRating2).now();
		
	    ObjectifyService.ofy().save().entity(user).now();
	    ObjectifyService.ofy().save().entity(user2).now();
	    logger.info("Saved user with address.");
		
	    return "{\"status\":\"done\"}";
	}
}

package edu.upc.ase.rest;

import java.util.List;
import java.util.Random;
import java.util.logging.Logger;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.fluttercode.datafactory.impl.DataFactory;

import com.google.gson.Gson;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.ObjectifyService;

import edu.upc.ase.domain.Address;
import edu.upc.ase.domain.Item;
import edu.upc.ase.domain.ItemRating;
import edu.upc.ase.domain.User;
import edu.upc.ase.domain.UserRating;
import edu.upc.ase.service.ItemService;

// The class registers its methods for the HTTP GET request using the @GET annotation. 
// Using the @Produces annotation, it defines that it can deliver several MIME types,
// text, XML and HTML. 

// The browser requests per default the HTML MIME type.

//Sets the path to base URL + /items
@Path("/items")
public class ItemRestService {
	private static final Gson GSON = new Gson();
	
	private static final Logger logger = Logger.getLogger("ItemRestService");

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public String getExampleJSONItem() {
		ItemService itemService = new ItemService();
		Item item = itemService.getExampleItem();
		return GSON.toJson(item);
	}

	@GET
	@Path("/example")
	@Produces(MediaType.APPLICATION_JSON)
	public String getJSONItem() {
		return "{\"id\":1,\"item\":\"Surfboard XYZ\"}";
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
	@Path("/item/{id}")
	public String getItemById(@PathParam("id") String id) {
		System.out.println("websafe: " + Key.create(id));
		// need to retrieve corresponding key first
		Key<Object> k = Key.create(id);
		
		Item i = ObjectifyService.ofy().load().type(Item.class).filterKey(k).first().now();
		
		
		logger.info("Strasse :" +i.getAddress().getStreet());
		System.out.println("item: " + i + "(id: " + id + ")");
		return GSON.toJson(i);
	}
	
	@GET
	@Path("/setupitem")
	@Produces(MediaType.APPLICATION_JSON)
	public String setupItem() {
		logger.info("Setup aufrufen");
		DataFactory df = DataFactory.create();
		
		Item item = new Item(df.getRandomWord(5, 20), new Double(df.getNumber()));
		Address addr = new Address(df.getCity(),String.valueOf( df.getNumberBetween(10000, 99999)), df.getStreetName(), 
				String.valueOf(df.getNumberUpTo(300)));
		// need to store to db, so the address entity gets a key in the db that can be assigned to the item
		Key<Address> key = ObjectifyService.ofy().save().entity(addr).now();
		item.setAddress(addr);
		
		ItemRating itemRating = new ItemRating(df.getNumberBetween(0, 5));
		
		Key<ItemRating> itemRatingKey = ObjectifyService.ofy().save().entity(itemRating).now();
		item.addItemRating(itemRatingKey);
		
		ItemRating itemRating2 = new ItemRating(df.getNumberBetween(0, 5));
		Key<ItemRating> itemRatingKey2= ObjectifyService.ofy().save().entity(itemRating2).now();
		item.addItemRating(itemRatingKey2);
		
	    // Use Objectify to save the greeting and now() is used to make the call synchronously as we
	    // will immediately get a new page using redirect and we want the data to be present.
	    ObjectifyService.ofy().save().entity(item).now();
	    
	    logger.info("Erfolgreich gespeichert");
		
	    return "{\"status\":\"done\"}";
	}
	
	@GET
	@Path("/setupuser")
	@Produces(MediaType.APPLICATION_JSON)
	public String setupUser() {
		logger.info("Setup aufrufen");
		DataFactory df = DataFactory.create();
		
		User user = new User(df.getFirstName(),df.getLastName(), df.getEmailAddress());
		
		Address addr = new Address(df.getCity(),String.valueOf( df.getNumberBetween(10000, 99999)), df.getStreetName(), 
				String.valueOf(df.getNumberUpTo(300)));
		// need to store to db, so the address entity gets a key in the db that can be assigned to the item
		Key<Address> key = ObjectifyService.ofy().save().entity(addr).now();
		
		Address addr2 = new Address(df.getCity(),String.valueOf( df.getNumberBetween(10000, 99999)), df.getStreetName(), 
				String.valueOf(df.getNumberUpTo(300)));
		// need to store to db, so the address entity gets a key in the db that can be assigned to the item
		Key<Address> key2 = ObjectifyService.ofy().save().entity(addr2).now();
		
		user.addAddress(key2);
		
		UserRating userRating = new UserRating(df.getNumberBetween(0, 5));
		Key<UserRating> ratingKey = ObjectifyService.ofy().save().entity(userRating).now();
		user.addUserRating(ratingKey);
		
		UserRating userRating2 = new UserRating(df.getNumberBetween(0, 5));
		Key<UserRating> ratingKey2 = ObjectifyService.ofy().save().entity(userRating2).now();
		user.addUserRating(ratingKey2);
		
	    // Use Objectify to save the greeting and now() is used to make the call synchronously as we
	    // will immediately get a new page using redirect and we want the data to be present.
	    ObjectifyService.ofy().save().entity(user).now();
	    
	    logger.info("Benutzer mit Adresse erfolgreich gespeichert");
		
	    return "{\"status\":\"done\"}";
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
	@Path("/getallitems")
	@Produces(MediaType.APPLICATION_JSON)
	public String getAllItems() {
		List<Item> items = ObjectifyService.ofy()
		          .load()
		          .type(Item.class)
		          .list();		
		return GSON.toJson(items);
	}
	
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
	
	// This method is called if TEXT_PLAIN is request
	@GET
	@Produces(MediaType.TEXT_PLAIN)
	public String getPlainTextItem() {
		return "Surfboard XYZ";
	}

	// This method is called if XML is request
	@GET
	@Produces(MediaType.TEXT_XML)
	public String getXMLItem() {
		return "<?xml version=\"1.0\"?>" + "<item> Surfboard XYZ" + "</item>";
	}

	// This method is called if HTML is request
	@GET
	@Produces(MediaType.TEXT_HTML)
	public String getHTMLItem() {
		return "<html> " + "<title>" + "Surfboard XYZ" + "</title>"
				+ "<body><h1>" + "Surfboard XYZ" + "</body></h1>" + "</html> ";
	}

}
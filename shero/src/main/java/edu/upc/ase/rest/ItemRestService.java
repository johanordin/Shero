package edu.upc.ase.rest;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.ObjectifyService;
import com.googlecode.objectify.Ref;
import com.googlecode.objectify.cmd.Query;
import com.googlecode.objectify.cmd.SimpleQuery;

import edu.upc.ase.dao.ItemDao;
import edu.upc.ase.domain.Image;
import edu.upc.ase.domain.Item;
import edu.upc.ase.helper.GsonUTCDateAdapter;
import edu.upc.ase.helper.Util;

@Path("/items")
public class ItemRestService {
	private static final Gson GSON = new Gson();
	private static final Logger logger = Logger.getLogger("ItemRestService");
	
	
	//For testing purposes
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/allitems")
	public String getItems() {
		List<Item> items = ObjectifyService.ofy().load().type(Item.class).list();
		return GSON.toJson(items);
	}
	
	/**
	 * see: https://github.com/objectify/objectify/wiki/Queries
	 * 
	 * Also:
	 * Properties used in inequality filters must be sorted first,
	 * otherwise the result may not be complete, as only the findings 
	 * until the first non-matching value will be contained.
	 * 
	 */
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public String getItemByExactName(@QueryParam("name") String itemName, @QueryParam("city") String cityName, @QueryParam("from") String from, @QueryParam("to") String to, @QueryParam("lo") String lo, @QueryParam("hi") String hi) {
		// if no additional filters are added, this retrieves all items
		Query<Item> q = ObjectifyService.ofy().load().type(Item.class);
		
		// filters are only added if the query parameter is specified
		if (itemName != null) {
			q = q.filter("name" , itemName); 
		}
		
		if (cityName != null) {
			q = q.filter("address.city =", cityName);
		}
		
		if (from != null && to != null) {
			Date start = new Date(Long.parseLong(from));
			Date end = new Date(Long.parseLong(to));
			
			// determine all dates in this time range
			List<Date> dates = new ArrayList<Date>();
			dates = Util.getPeriodDates(start, end);
			
			// filter if item is available on all specified dates
			for(Date d : dates) {
				q = q.filter("availabilityPeriods", d);
			}
		}
		
		// filter by price
		if (lo != null && hi != null) {
			q = q.filter("price >=" , Double.valueOf(lo)).filter("price <=", Double.valueOf(hi));
		}
		
		// execute query
		List<Item> results = q.list();
		
		logger.info("query: " + q);
		
		Gson gson = new GsonBuilder().registerTypeAdapter(Date.class, new GsonUTCDateAdapter()).create();
		return gson.toJson(results);  
	}
	
	@GET
	@Path("/{id}")
	public String getItem(@PathParam("id") String id) {
		Item item = ObjectifyService.ofy().load().type(Item.class).id(Long.parseLong(id)).now();
		return GSON.toJson(item);
	}
	
	/**
	 * Expected input from frontend: 
	 * e.g. {"name":"itemName","price":"somePrice","description":"some comment"}
	 */
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public String createItem(String jsonItem) {
		Item newItem = GSON.fromJson(jsonItem, Item.class);
		Key<Item> key = ObjectifyService.ofy().save().entity(newItem).now();
		Item item = ObjectifyService.ofy().load().type(Item.class).id(key.getId()).now();
		return GSON.toJson(item);
	}
	
	/**
	 * Expects existing item id:
	 * e.g. {"id":"itemId","name":"itemName","price":"somePrice","description":"some comment"}
	 */
	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/{id}")
	public String updateItem(String jsonItem) {
		// TODO: use id to retrieve item and update specific attributes,
		// avoid overriding everything that is not specified in request
		Item updatedItem = GSON.fromJson(jsonItem, Item.class);
		Key<Item> key = ObjectifyService.ofy().save().entity(updatedItem).now();
		Item item = ObjectifyService.ofy().load().type(Item.class).id(key.getId()).now();
		return GSON.toJson(item);
	}
	
	@DELETE
	@Produces(MediaType.APPLICATION_JSON)
	@Path("{id}")
	public String deleteItem(@PathParam("id") String id) {
		Key<Item> key = Key.create(Item.class, Long.parseLong(id));
		ObjectifyService.ofy().delete().key(key).now();
		// fix: returns successful even if entity does not exist
		return "{\"status\":\"successful\"}";
	}
	
	//Get image response by ID of Item
	@GET
	@Path("/image/{id}")
	@Produces("image/png")
	public Response getFullImage(@PathParam("id") String id) {

		Item item = new ItemDao().getItemById(id); 
		List<Ref<Image>> refs = item.getImageRefs();
		if (!refs.isEmpty()) {
			Image image = refs.get(0).getValue();
			
			//Image image = ImageDao.getImageById(id);
			
			// uncomment line below to send non-streamed
		    // return Response.ok(imageData).build();

		    // uncomment line below to send streamed
		     return Response.ok(new ByteArrayInputStream(image.getImage().getBytes())).build();
		} else {
			return Response.status(404).entity("Image not found.").build();
		}
	}
	
	@GET
	@Path("/itemsuggestions")
	public String getItemSuggestions() {
		
		SimpleQuery<Item> simpleQuery = ObjectifyService.ofy().load().type(Item.class);

		List<Item> items = simpleQuery.project("name").distinct(true).list();
		
		logger.info(items.toString());
		return GSON.toJson(items);
	}
}
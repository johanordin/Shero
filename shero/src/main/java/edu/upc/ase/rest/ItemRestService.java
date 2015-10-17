package edu.upc.ase.rest;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.google.gson.Gson;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.ObjectifyService;

import edu.upc.ase.domain.Item;

@Path("/items")
public class ItemRestService {
	private static final Gson GSON = new Gson();

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public String getItems() {
		List<Item> items = ObjectifyService.ofy().load().type(Item.class)
				.list();
		return GSON.toJson(items);
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

}
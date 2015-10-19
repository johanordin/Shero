package edu.upc.ase.rest;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.google.gson.Gson;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.ObjectifyService;

import edu.upc.ase.domain.ItemRating;

@Path("/itemratings")
public class ItemRatingRestService {

	private static final Gson GSON = new Gson();
	
	/**
	 * Expected input from frontend: 
	 * {"firstname":"fname","lastname":"lname","emailAddress":"abc@def.com","items":[],"addresses":[],"userRatings":[]}
	 */
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public String createItemRating(String jsonItemRating) {
		ItemRating newItemRating = GSON.fromJson(jsonItemRating, ItemRating.class);
		Key<ItemRating> key = ObjectifyService.ofy().save().entity(newItemRating).now();
		ItemRating itemRating = ObjectifyService.ofy().load().type(ItemRating.class).id(key.getId()).now();
		return GSON.toJson(itemRating);
	}
	
	@GET
	@Path("/{id}")
	public String getItemRating(@PathParam("id") String id) {
		ItemRating itemRating = ObjectifyService.ofy().load().type(ItemRating.class).id(Long.parseLong(id)).now();
		return GSON.toJson(itemRating);
	}
}

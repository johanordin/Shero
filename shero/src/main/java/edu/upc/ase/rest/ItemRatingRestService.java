package edu.upc.ase.rest;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.ObjectifyService;

import edu.upc.ase.domain.Item;
import edu.upc.ase.domain.ItemRating;
import edu.upc.ase.domain.Rental;

@Path("/itemratings")
public class ItemRatingRestService {
	private static final JsonParser JSON_PARSER = new JsonParser();
	private static final Gson GSON = new Gson();
	
	/**
	 * Expected format: {"rentalId": 141421415215, "rating":[1..5]}
	 * @return
	 */
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public String createItemRating(String jsonItemRating) {
		JsonObject jsonObj = JSON_PARSER.parse(jsonItemRating).getAsJsonObject();
		String rentalId = jsonObj.get("rentalId") != null ? jsonObj.get("rentalId").toString().replace("\"", "") : null;
		String ratingValue = jsonObj.get("rating") != null ? jsonObj.get("rating").toString().replace("\"", "") : null;
		
		ItemRating newRating = null;
		
		// all parameters have to be set
		if (rentalId != null && ratingValue != null) {
			// retrieve the rental
			Rental rental = ObjectifyService.ofy().load().type(Rental.class).filterKey(Key.create(Rental.class, Long.parseLong(rentalId))).first().now();
			
			if (!rental.getItemRated()) {
				// create a new rating for the given user with respect to the given item
				ItemRating itemRating = new ItemRating(rental.getItemId(), rental.getUserId(), Integer.valueOf(ratingValue));
				Key<ItemRating> ratingKey = ObjectifyService.ofy().save().entity(itemRating).now();
				newRating = ObjectifyService.ofy().load().type(ItemRating.class).id(ratingKey.getId()).now();
				
				// once rating has been successfully created, update rating counts on respective item
				Item item = ObjectifyService.ofy().load().type(Item.class).id(rental.getItemId()).now();
				item.updateRatingCount(Integer.valueOf(ratingValue));
				// save changes in database
				ObjectifyService.ofy().save().entity(item).now();
				
				// remember in rental, that it has been rated
				rental.setItemRated();
				ObjectifyService.ofy().save().entity(rental).now();
			}
				
			return GSON.toJson(newRating);
		}

		// in other cases return error
		return "{\"status\":\"error\"}";
	}
	
//	/**
//	 * Expected format: {"itemId": 141421415215, "userId": 315353155315, "rating":[1..5]}
//	 * @return
//	 */
//	@POST
//	@Consumes(MediaType.APPLICATION_JSON)
//	@Produces(MediaType.APPLICATION_JSON)
//	public String createItemRating(String jsonItemRating) {
//		JsonObject jsonObj = JSON_PARSER.parse(jsonItemRating).getAsJsonObject();
//		String itemId = jsonObj.get("itemId") != null ? jsonObj.get("itemId").toString().replace("\"", "") : null;
//		String userId = jsonObj.get("userId") != null ? jsonObj.get("userId").toString().replace("\"", "") : null;
//		String ratingValue = jsonObj.get("rating") != null ? jsonObj.get("rating").toString().replace("\"", "") : null;
//		
//		ItemRating itemRating;
//		
//		// all parameters have to be set
//		if (itemId != null && userId != null && ratingValue != null) {
//			itemRating = new ItemRating(Long.parseLong(itemId), Long.parseLong(userId), Integer.valueOf(ratingValue));
//			
//			Key<ItemRating> ratingKey = ObjectifyService.ofy().save().entity(itemRating).now();		
//			ItemRating newRating = ObjectifyService.ofy().load().type(ItemRating.class).id(ratingKey.getId()).now();
//			
//			// once rating has been successfully created, update rating counts on respective item
//			Item item = ObjectifyService.ofy().load().type(Item.class).id(Long.parseLong(itemId)).now();
//			item.updateRatingCount(Integer.valueOf(ratingValue));
//			
//			// remember in rental, that it has been rated
//			
//			return GSON.toJson(newRating);
//		}
//
//		// in other cases return error
//		return "{\"status\":\"error\"}";
//	}
	
	@GET
	@Path("/{id}")
	public String getItemRating(@PathParam("id") String id) {
		ItemRating itemRating = ObjectifyService.ofy().load().type(ItemRating.class).id(Long.parseLong(id)).now();
		return GSON.toJson(itemRating);
	}
}

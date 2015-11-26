package edu.upc.ase.rest;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.ObjectifyService;

import edu.upc.ase.domain.Item;
import edu.upc.ase.domain.Rental;
import edu.upc.ase.helper.GsonUTCDateAdapter;
import edu.upc.ase.mail.MailServiceUtil;

@Path("/rentals")
public class RentalRestService {
	private static final JsonParser JSON_PARSER = new JsonParser();

	/**
	 * In order to create a rental, the following parameters are needed:
	 * Long userId, Long itemId, List<Availability> period
	 */
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	public String createRental(String jsonRental) {
		JsonObject jsonObj = JSON_PARSER.parse(jsonRental).getAsJsonObject();
		
		String userId = "";
		String itemId = "";
		List<Date> period = new ArrayList<Date>();
		
		JsonElement jsonUserId = jsonObj.get("userId");
		if (jsonUserId != null) {
			userId = jsonUserId.toString().replace("\"", "");
		}
		
		JsonElement jsonItemId = jsonObj.get("itemId");
		if (jsonItemId != null) {
			itemId = jsonItemId.toString().replace("\"", "");
		}
		
		JsonArray jsonPeriod = jsonObj.getAsJsonArray("period");
		if (jsonPeriod != null) {
			for(int i = 0; i < jsonPeriod.size(); i++) {
				Date date = new Date(jsonPeriod.get(i).getAsLong());
				period.add(date);
			}
		}
		
		Rental newRental = null;
		if (userId != null && itemId != null && !period.isEmpty()) {
			Rental rental = new Rental(Long.parseLong(userId), Long.parseLong(itemId), period);
			
			// before storing the rental, make sure the item is really available on specified dates
			Item item = ObjectifyService.ofy().load().type(Item.class).id(Long.parseLong(itemId)).now();
			List<Date> availableDates = item.getAvailabilityPeriods();
			
			// TODO: should be done within a transaction
			if (availableDates.containsAll(period)) {
				// then update availability dates in item
				item.removeAvailabilityPeriod(period);
				ObjectifyService.ofy().save().entity(item).now();
				
				// save new rental
				Key<Rental> key = ObjectifyService.ofy().save().entity(rental).now();
				newRental = ObjectifyService.ofy().load().type(Rental.class).id(key.getId()).now();
			}
		}
		
		MailServiceUtil mailServiceUtil = new MailServiceUtil();
		mailServiceUtil.sendRentalMail(newRental);
		
		// return the newly created rental
		Gson gson = new GsonBuilder().registerTypeAdapter(Date.class, new GsonUTCDateAdapter()).create();
		return gson.toJson(newRental);
	}
}

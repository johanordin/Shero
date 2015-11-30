package edu.upc.ase.rest;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
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

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.ObjectifyService;
import com.googlecode.objectify.Ref;
import com.googlecode.objectify.Result;

import edu.upc.ase.domain.Address;
import edu.upc.ase.domain.Item;
import edu.upc.ase.domain.Rental;
import edu.upc.ase.domain.Tag;
import edu.upc.ase.domain.User;
import edu.upc.ase.helper.GsonUTCDateAdapter;
import edu.upc.ase.mail.MailServiceUtil;
import edu.upc.ase.rest.test.TestMailService;

@Path("/users")
public class UserRestService {
	private static final Gson GSON = new Gson();
	private static final JsonParser JSON_PARSER = new JsonParser();
	private static final Logger logger = Logger.getLogger("UserRestService");
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public String getUsers() {
		List<User> users = ObjectifyService.ofy().load().type(User.class)
				.list();
		return GSON.toJson(users);
	}
	
	@GET
	@Path("/id/{id}")
	public String getUser(@PathParam("id") String id) {
		User user = ObjectifyService.ofy().load().type(User.class).id(Long.parseLong(id)).now();
		return GSON.toJson(user);
	}
	
	@GET
	@Path("/mail/{mail}")
	public String getUserByMailAddress(@PathParam("mail") String mailAddress, @QueryParam("hashedPassword") String jsonPassword) {
		logger.info("jsonPassword: " + jsonPassword);
		String hashedPassword = JSON_PARSER.parse(jsonPassword).getAsString();
		
		// find user with this email address
		List<User> users = ObjectifyService.ofy().load().type(User.class).filter("emailAddress", mailAddress).limit(1).list();
		User user ;
		if (!users.isEmpty()) {
			user = users.get(0);
			// make sure the passwords match
			if (hashedPassword != null && hashedPassword.equals(user.getPasswordHash())) {
				return GSON.toJson(user);
			}
		}
		// in all other cases return error
		return "{\"status\":\"error\"}";
	}
	
	/**
	 * Expected input from frontend: 
	 * {"firstname":"fname","lastname":"lname","emailAddress":"abc@def.com","items":[],"addresses":[],"userRatings":[]}
	 * 
	 * or with address:
	 * {"firstname":"test1","lastname":"test2","emailAddress":"abc@def.com","items":[],
	 * "addresses":[{"country":"country","zipcode":"zip","street":"str","number":"1","city":"city"}],"userRatings":[]}
	 * 
	 */
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public String createUser(String jsonUser) {
		User newUser = GSON.fromJson(jsonUser, User.class);
		logger.info("newUser:" + newUser);
		
		// parse address(es) manually
		JsonObject jsonObj = JSON_PARSER.parse(jsonUser).getAsJsonObject();
		JsonArray addresses = jsonObj.getAsJsonArray("addresses");
		
		boolean addressesEmpty = false;
		if (addresses == null) {
			addressesEmpty = true;
		}
		
		if (!addressesEmpty) {
			// store promises to wait for asynchronous results on the newly saved entities' keys
			List<Result<Key<Address>>> promiseList = new ArrayList<Result<Key<Address>>>();
			
			for(int i = 0; i < addresses.size(); i++) {
				JsonObject address = addresses.get(i).getAsJsonObject();
				String country = address.get("country").toString().replace("\"", "");
				String city = address.get("city").toString().replace("\"", "");
				String zipcode = address.get("zipcode").toString().replace("\"", "");
				String street = address.get("street").toString().replace("\"", "");
				String number = address.get("number").toString().replace("\"", "");
				
				// not a mandatory field, therefore needs some extra verification
				String additional = address.get("additional") != null ? address.get("additional").toString().replace("\"", "") : null;
				
				Address newAddress = new Address(country, city, zipcode, street, number);
				if (additional != null) {
					newAddress.setAdditional(additional);
				}
				
				Result<Key<Address>> newAddrKey = ObjectifyService.ofy().save().entity(newAddress);
				promiseList.add(newAddrKey);
			}
			
			for(Result<Key<Address>> promise : promiseList) {
				Key<Address> addrKey = promise.now();
				
				// reference user with this address
				newUser.addAddress(addrKey);
			}
		}
		// finally, store user
		Key<User> key = ObjectifyService.ofy().save().entity(newUser).now();
		
		User user = ObjectifyService.ofy().load().type(User.class).id(key.getId()).now();
		// somehow need to fetch addresses manually to populate object not only with address data but also the keys
		if (!addressesEmpty) {
			user.getAddresses();
		}
		user.serialize();
		//Send welcome Mail to user
		MailServiceUtil mailService = new MailServiceUtil();
		mailService.sendWelcomeMail(user);
		
		return GSON.toJson(user);
	}
	
	/**
	 * Expects existing user id:
	 * {"id":"generatedId","firstname":"...","lastname":"...","emailAddress":"...","items":[],"addresses":[],"userRatings":[]}
	 */
	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/{id}")
	public String updateUser(@PathParam("id") String userId, String jsonUser) {
		// load respective user
		User user = ObjectifyService.ofy().load().type(User.class).id(Long.parseLong(userId)).now();
		
		JsonObject jsonObj = JSON_PARSER.parse(jsonUser).getAsJsonObject();
		
		String firstname = "";
		String lastname = "";
		String email = "";
		
		JsonElement fname = jsonObj.get("firstname");
		if (fname != null) {
			firstname = fname.toString().replace("\"", "");
		}
		
		JsonElement lname = jsonObj.get("lastname");
		if (lname != null) {
			lastname = lname.toString().replace("\"", "");
		}
		
		JsonElement mail = jsonObj.get("emailAddress");
		if (mail != null) {
			email = mail.toString().replace("\"", "");
		}
		
		if (!firstname.isEmpty()) {
			user.setFirstname(firstname);
		}
		
		if (!lastname.isEmpty()) {
			user.setLastname(lastname);
		}
		
		if (!email.isEmpty()) {
			user.setEmailAddress(email);
		}

		Key<User> key = ObjectifyService.ofy().save().entity(user).now();
		User updatedUser = ObjectifyService.ofy().load().type(User.class).id(key.getId()).now();
		return GSON.toJson(updatedUser);
	}
	
	@DELETE
	@Produces(MediaType.APPLICATION_JSON)
	@Path("{id}")
	public String deleteUser(@PathParam("id") String id) {
		Key<User> key = Key.create(User.class, Long.parseLong(id));
		ObjectifyService.ofy().delete().key(key).now();
		// fix: returns successful even if entity does not exist
		return "{\"status\":\"successful\"}";
	}
	
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Path("{id}/addresses")
	public String createAddress(@PathParam("id") String userId, String jsonAddress) {
		// load respective user
		User user = ObjectifyService.ofy().load().type(User.class).id(Long.parseLong(userId)).now();
		
		// save new address
		Address newAddress = GSON.fromJson(jsonAddress, Address.class);
		Key<Address> key = ObjectifyService.ofy().save().entity(newAddress).now();
		
		// reference new address in user
		user.addAddress(key);
		ObjectifyService.ofy().save().entity(user).now();
		
		Address address = ObjectifyService.ofy().load().type(Address.class).id(key.getId()).now();
		return GSON.toJson(address);
	}
	
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Path("{id}/items")
	public String createItem(@PathParam("id") String userId, String jsonItem) {
		// load respective user
		User user = ObjectifyService.ofy().load().type(User.class).id(Long.parseLong(userId)).now();
		
		// create the item
		Item item = GSON.fromJson(jsonItem, Item.class);
		logger.info("item: " + item);

		// set creation time
		Calendar cal = Calendar.getInstance();
		item.setCreated(cal.getTime());
				
		//****** RETRIEVE REFERENCED ENTITIES BY ID *******/
		
		// 1. retrieve address id from json
		JsonObject jsonObj = JSON_PARSER.parse(jsonItem).getAsJsonObject();
		String addressId = jsonObj.get("addressId").toString().replace("\"", "");

		// add address to item
		Key.create(Address.class, Long.parseLong(addressId));
		Address address = ObjectifyService.ofy().load().type(Address.class).id(Long.parseLong(addressId)).now();
		item.setAddress(address);

		// 2. retrieve tag ids from json
		// expected tag format:
		// [{"id":"12341","text":"tag"},{"id":"12415","text":"tag2"}]
		JsonArray tags = jsonObj.getAsJsonArray("tags");
		for (int i = 0; i < tags.size(); i++) {
			JsonObject tag = tags.get(i).getAsJsonObject();
			String tagId = tag.get("id") != null ? tag.get("id").toString().replace("\"", "") : null;
			
			// only add tags that already exist in database
			if (tagId != null) {
				Key<Tag> tagKey = Key.create(Tag.class,
						Long.parseLong(tagId.toString()));
				Ref<Tag> tagRef = Ref.create(tagKey);
				item.addTag(tagRef);
			}
		}
				
		// create and store availability entities
		JsonArray availabilityDates = jsonObj.getAsJsonArray("selectedDates");
		for (int i = 0; i < availabilityDates.size(); i++) {
			Date date = new Date(availabilityDates.get(i).getAsLong());
			item.addAvailableDay(date);
		}
		
		// save and add item to user
		ObjectifyService.ofy().save().entity(item).now();
		user.addItem(item);
		
		// save user
		Key<User> userKey = ObjectifyService.ofy().save().entity(user).now();
		User returnUser = ObjectifyService.ofy().load().type(User.class).id(userKey.getId()).now();

		// update items referenced in user entity
		returnUser.serialize();
		// update item cache? if not, item will not have referenced entities
		item.serialize();
		// return new item
		return GSON.toJson(item);
	}
	
	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/{id}/mail")
	public String updatePassword(@PathParam("id") String userId, String jsonPassword) {
		// load respective user
		User user = ObjectifyService.ofy().load().type(User.class).id(Long.parseLong(userId)).now();
		// parse password from request
		JsonObject jsonObj = JSON_PARSER.parse(jsonPassword).getAsJsonObject();
		String hashedPassword = jsonObj.get("hashedPassword").toString().replace("\"", "");
		
		// change password
		user.setPasswordHash(hashedPassword);
		
		// store user with changed password
		Key<User> key = ObjectifyService.ofy().save().entity(user).now();
		User returnUser = ObjectifyService.ofy().load().type(User.class).id(key.getId()).now();
		
		return GSON.toJson(returnUser);
	}
	
	/**
	 * Returns list of items rented by a user,
	 * including an attribute "itemRated" for every item,
	 * that indicates if this item has been rated already.
	 * 
	 * Note: the same item may appear multiple times,
	 * as it may have been rented multiple times. 
	 * A rating is therefore also allowed multiple times!
	 * 
	 */
	@GET
	@Path("/{id}/rentals/items")
	public String getRentalsByUserId(@PathParam("id") String userId) {
		// find rentals for a given userId
		List<Rental> rentals = ObjectifyService.ofy().load().type(Rental.class).filter("userId", Long.parseLong(userId)).list();
		
		// list of item keys involved in above rentals
		Set<Key<Item>> rentedItems = new HashSet<Key<Item>>();
		
		// one item might appear in multiple rentals, list keeps track in which it has been rated and in which not
		Map<Key<Item>, ArrayList<RentalInfo>> itemRated = new java.util.HashMap<Key<Item>, ArrayList<RentalInfo>>();
		
		for(Rental rental : rentals) {
			Key<Item> itemKey = Key.create(Item.class, rental.getItemId());
			
			// remember key for fetching item from db
			rentedItems.add(itemKey);
			
			// remember if this item has been rated or not
			if (!itemRated.containsKey(itemKey)) {
				itemRated.put(itemKey, new ArrayList<RentalInfo>());
			}
			List<RentalInfo> info = itemRated.get(itemKey);
			info.add(new RentalInfo(rental.getRentalId(), rental.getItemRated(), rental.getPeriod()));

		}
		
		// fetch rented items using the collected keys
		Set<Entry<Key<Item>, Item>> items = ObjectifyService.ofy().load().keys(rentedItems).entrySet();
		
		// prepare result list
		Gson gson = new GsonBuilder().registerTypeAdapter(Date.class, new GsonUTCDateAdapter()).create();
		JsonArray resultList = new JsonArray();
		
		Iterator<Entry<Key<Item>, Item>> it = items.iterator();
		while (it.hasNext()) {
			Entry<Key<Item>, Item> entry = it.next();
			Key<Item> itemKey = entry.getKey();
			Item item = entry.getValue();
			
			// since an item may have been rented multiple times,
			// every item that is put into the result list needs to be annotated
			// with information whether it has been rated or not
			List<RentalInfo> rentalInfos = itemRated.get(itemKey);
			for(RentalInfo info : rentalInfos) {
				// first convert item into json
				JsonObject jsonItem = gson.toJsonTree(item).getAsJsonObject();
				// add rentalId to item
				jsonItem.addProperty("rentalId", info.getRentalId());
				// then add boolean rated property
				jsonItem.addProperty("itemRated", info.getRated());
				// add actual rental period
				jsonItem.add("rentalPeriod", gson.toJsonTree(info.getRentalPeriod()).getAsJsonArray());
				// eventually add it to results
				resultList.add(jsonItem);
			}
		}
		
		return gson.toJson(resultList);
	}
	
	private class RentalInfo {
		private Long rentalId;
		private Boolean rated;
		private List<Date> rentalPeriod;
		
		public RentalInfo(Long rentalId, Boolean rated, List<Date> rentalPeriod) {
			this.rentalId = rentalId;
			this.rated = rated;
			this.rentalPeriod = rentalPeriod;
		}
		public Long getRentalId() {
			return rentalId;
		}
		public Boolean getRated() {
			return rated;
		}
		public List<Date> getRentalPeriod() {
			return rentalPeriod;
		}
	}
}

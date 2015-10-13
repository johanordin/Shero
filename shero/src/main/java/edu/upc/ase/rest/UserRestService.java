package edu.upc.ase.rest;

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
import javax.ws.rs.core.MediaType;

import com.google.gson.Gson;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.ObjectifyService;

import edu.upc.ase.domain.User;

@Path("/users")
public class UserRestService {
	private static final Gson GSON = new Gson();
	private static final Logger logger = Logger.getLogger("UserRestService");
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public String getUsers() {
		List<User> users = ObjectifyService.ofy().load().type(User.class)
				.list();
		return GSON.toJson(users);
	}
	
	@GET
	@Path("/{id}")
	public String getUser(@PathParam("id") String id) {
		Key<User> k = Key.create(null, User.class, Long.parseLong(id));
		User user = ObjectifyService.ofy().load().type(User.class).filterKey(k).first().now();
		return GSON.toJson(user);
	}
	
	/**
	 * Expected input from frontend: 
	 * {"firstname":"fname","lastname":"lname","emailAddress":"abc@def.com","items":[],"addresses":[],"userRatings":[]}
	 */
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public String createUser(String jsonUser) {
		User newUser = GSON.fromJson(jsonUser, User.class);
		Key<User> key = ObjectifyService.ofy().save().entity(newUser).now();
		User user = ObjectifyService.ofy().load().type(User.class).filterKey(key).first().now();
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
	public String updateUser(String jsonUser) {
		User updatedUser = GSON.fromJson(jsonUser, User.class);
		Key<User> key = ObjectifyService.ofy().save().entity(updatedUser).now();
		User user = ObjectifyService.ofy().load().type(User.class).filterKey(key).first().now();
		return GSON.toJson(user);
	}
	
	@DELETE
	@Produces(MediaType.APPLICATION_JSON)
	@Path("{id}")
	public String deleteUser(@PathParam("id") String id) {
		Key<User> key = Key.create(id);
		ObjectifyService.ofy().delete().key(key).now();
		// fix: returns successful even if entity does not exist
		return "{\"status\":\"successful\"}";
	}
	
	/***************** TEST *****************/
	@GET
	@Path("/setupUser")
	@Produces(MediaType.APPLICATION_JSON)
	public String setupTestUser() {
		User test = new User("Max", "Mux", "max@mux.es");
		Key<User> newUser = ObjectifyService.ofy().save().entity(test).now();
		User user = ObjectifyService.ofy().load().type(User.class).filterKey(newUser).first().now();
		return GSON.toJson(user);
	}
}

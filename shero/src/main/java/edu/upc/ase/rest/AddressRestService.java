package edu.upc.ase.rest;

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

import edu.upc.ase.domain.Address;
import edu.upc.ase.domain.User;

@Path("/addresses")
public class AddressRestService {
	private static final Gson GSON = new Gson();
	
	@GET
	@Path("/{id}")
	public String getAddress(@PathParam("id") String id) {
		Address address = ObjectifyService.ofy().load().type(Address.class).id(Long.parseLong(id)).now();
		return GSON.toJson(address);
	}
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public String createAddress(String jsonAddress) {
		Address newAddress = GSON.fromJson(jsonAddress, Address.class);
		Key<Address> key = ObjectifyService.ofy().save().entity(newAddress).now();
		Address address = ObjectifyService.ofy().load().type(Address.class).id(key.getId()).now();
		return GSON.toJson(address);
	}
	
	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/{id}")
	public String updateAddress(@PathParam("id") String addressId, String jsonAddress) {
		// load respective address
		//Address address = ObjectifyService.ofy().load().type(Address.class).id(Long.parseLong(addressId)).now();
		
		// parse new address from request
		Address updatedAddress = GSON.fromJson(jsonAddress, Address.class);
		updatedAddress.setId(Long.parseLong(addressId));
		
		Key<Address> key = ObjectifyService.ofy().save().entity(updatedAddress).now();
		Address address = ObjectifyService.ofy().load().type(Address.class).id(key.getId()).now();
		return GSON.toJson(address);
	}
	
	@DELETE
	@Produces(MediaType.APPLICATION_JSON)
	@Path("{id}")
	public String deleteAddress(@PathParam("id") String id) {
		Key<Address> key = Key.create(Address.class, Long.parseLong(id));
		ObjectifyService.ofy().delete().key(key).now();
		// fix: returns successful even if entity does not exist
		return "{\"status\":\"successful\"}";
	}
}

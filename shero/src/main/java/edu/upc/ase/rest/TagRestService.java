package edu.upc.ase.rest;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.google.gson.Gson;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.ObjectifyService;

import edu.upc.ase.domain.Tag;

@Path("/tags")
public class TagRestService {
	private static final Gson GSON = new Gson();

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public String getTags() {
		List<Tag> tags = ObjectifyService.ofy().load().type(Tag.class).list();
		return GSON.toJson(tags);
	}
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public String createTag(String jsonTag) {
		Tag newTag = GSON.fromJson(jsonTag, Tag.class);
		Key<Tag> key = ObjectifyService.ofy().save().entity(newTag).now();
		Tag tag = ObjectifyService.ofy().load().type(Tag.class).id(key.getId()).now();
		return GSON.toJson(tag);
	}
	
	@DELETE
	@Produces(MediaType.APPLICATION_JSON)
	@Path("{id}")
	public String deleteTag(@PathParam("id") String id) {
		Key<Tag> key = Key.create(Tag.class, Long.parseLong(id));
		ObjectifyService.ofy().delete().key(key).now();
		// fix: returns successful even if entity does not exist
		return "{\"status\":\"successful\"}";
	}
}

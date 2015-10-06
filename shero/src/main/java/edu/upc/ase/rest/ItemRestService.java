package edu.upc.ase.rest;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.google.gson.Gson;

import edu.upc.ase.domain.Item;
import edu.upc.ase.service.ItemService;

// The class registers its methods for the HTTP GET request using the @GET annotation. 
// Using the @Produces annotation, it defines that it can deliver several MIME types,
// text, XML and HTML. 

// The browser requests per default the HTML MIME type.

//Sets the path to base URL + /items
@Path("/items")
public class ItemRestService {
	private static final Gson GSON = new Gson();

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
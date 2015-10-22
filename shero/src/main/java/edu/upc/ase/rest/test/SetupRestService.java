package edu.upc.ase.rest.test;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.googlecode.objectify.ObjectifyService;

import edu.upc.ase.domain.admin.EmailTemplate;

@Path("/setup")
public class SetupRestService {
	
	@GET
	@Path("/setupemailtemplate")
	@Produces(MediaType.APPLICATION_JSON)
	public String setupItem() {

		String templateText = HTML_TEMPLATE;
		//String path = "/shero/simple-letterhead-leftlogo.html";
		
//		try(FileInputStream inputStream = new FileInputStream(path)) {
//		    templateText = IOUtils.toString(inputStream);
//		} catch (FileNotFoundException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (IOException e1) {
//			// TODO Auto-generated catch block
//			e1.printStackTrace();
//		}
		
		EmailTemplate emailTemplate = new EmailTemplate("WelcomeTemplate",templateText);
		ObjectifyService.ofy().save().entity(emailTemplate).now();
	    return "{\"status\":\"done\"}";
	}
	
	private static String HTML_TEMPLATE = 
    "<html>" +
    "<head>" + 
    "<title>Welcome to Shero!</title>" +
    "</head>" +
    "<body>" + 
    "<h1> Hello #USERNAME#</h1>" + 
    "<p>Welcome to Shero, be a sharing Hero!<p/>" +
    "</body>" +
    "</html>";
}

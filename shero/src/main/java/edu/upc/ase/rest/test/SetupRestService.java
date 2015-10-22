package edu.upc.ase.rest.test;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import edu.upc.ase.serverstartup.SetupDatastoreUtil;

@Path("/setup")
public class SetupRestService {
	
	@GET
	@Path("/setup")
	@Produces(MediaType.APPLICATION_JSON)
	public String setup() { 
		
		SetupDatastoreUtil.setupEMailTemplate();
		SetupDatastoreUtil.setupUser();
		SetupDatastoreUtil.setupImage();

		return "sucessfully set up the system";
	}
}

package edu.upc.ase.helper;

import java.io.IOException;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import com.googlecode.objectify.ObjectifyService;

import edu.upc.ase.domain.Address;
import edu.upc.ase.domain.Availability;
import edu.upc.ase.domain.Image;
import edu.upc.ase.domain.Item;
import edu.upc.ase.domain.ItemRating;
import edu.upc.ase.domain.Tag;
import edu.upc.ase.domain.User;
import edu.upc.ase.domain.UserRating;
import edu.upc.ase.domain.admin.EmailTemplate;
import edu.upc.ase.serverstartup.ServerStartupUtil;

/**
 * OfyHelper, a ServletContextListener, is setup in web.xml to run before a JSP
 * is run. This is required to let JSP's access Ofy.
 **/
public class OfyHelper implements ServletContextListener {
	public void contextInitialized(ServletContextEvent event) {
		// This will be invoked as part of a warmup request, or the first user
		// request if no warmup
		// request.
		ObjectifyService.register(Item.class);
		ObjectifyService.register(User.class);
		ObjectifyService.register(ItemRating.class);
		ObjectifyService.register(Availability.class);
		ObjectifyService.register(Address.class);
		ObjectifyService.register(UserRating.class);
		ObjectifyService.register(Tag.class);
		ObjectifyService.register(EmailTemplate.class);
		ObjectifyService.register(Image.class);
		
		
		//Doesnt work because of " java.lang.IllegalStateException: 
		//You have not started an Objectify context. You are probably missing the ObjectifyFilter.
		//If you are not running in the context of an http request, see the ObjectifyService.run() method."
		//TODO Fix later -> Now call rest service via Browser
		
		//Init Email Template
		//SetupDatastoreUtil.setupEMailTemplate();
		
		//Init User Data
		//SetupDatastoreUtil.setupUser();
		
		//Dirty Workaround
		//Calling HTTP Request to Setup Rest
		try {
			ServerStartupUtil.sendGET();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void contextDestroyed(ServletContextEvent event) {
		// App Engine does not currently invoke this method.
	}
}
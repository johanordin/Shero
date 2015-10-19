package edu.upc.ase.helper;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import com.googlecode.objectify.ObjectifyService;

import edu.upc.ase.domain.Address;
import edu.upc.ase.domain.Availability;
import edu.upc.ase.domain.Item;
import edu.upc.ase.domain.ItemRating;
import edu.upc.ase.domain.Tag;
import edu.upc.ase.domain.User;
import edu.upc.ase.domain.UserRating;

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
	}

	public void contextDestroyed(ServletContextEvent event) {
		// App Engine does not currently invoke this method.
	}
}
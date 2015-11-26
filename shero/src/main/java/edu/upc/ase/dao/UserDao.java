package edu.upc.ase.dao;

import com.googlecode.objectify.ObjectifyService;

import edu.upc.ase.domain.User;

public class UserDao {

	//Get User by ID
	public User getUserById(Long id) {
		User user = ObjectifyService.ofy().load().type(User.class).id(id).now();
		return user;
	}
}

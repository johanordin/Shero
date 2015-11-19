package edu.upc.ase.dao;

import com.googlecode.objectify.ObjectifyService;

import edu.upc.ase.domain.Image;

public class ImageDao {
	//Get Item by ID
	public static Image getImageById(String id) {
		Image image = ObjectifyService.ofy().load().type(Image.class).id(Long.parseLong(id)).now();
		return image;
	}
}

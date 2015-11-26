package edu.upc.ase.dao;

import java.util.List;

import com.googlecode.objectify.Key;
import com.googlecode.objectify.ObjectifyService;

import edu.upc.ase.domain.Item;

public class ItemDao {

	//Get Item by ID
	public Item getItemById(String id) {
		Item item = ObjectifyService.ofy().load().type(Item.class).id(Long.parseLong(id)).now();
		return item;
	}
	
	//Get Item by ID
	public Item getItemById(Long id) {
		Item item = ObjectifyService.ofy().load().type(Item.class).id(id).now();
		return item;
	}

	public Item updateItem(Item updatedItem) {
		Key<Item> key = ObjectifyService.ofy().save().entity(updatedItem).now();
		Item item = ObjectifyService.ofy().load().type(Item.class).id(key.getId()).now();
		return item;
	}

	public List<Item> getItems() {
		List<Item> items = ObjectifyService.ofy().load().type(Item.class).list();
		return items;
	}

}

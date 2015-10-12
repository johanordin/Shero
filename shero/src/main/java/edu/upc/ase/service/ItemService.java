package edu.upc.ase.service;

import edu.upc.ase.domain.Item;

public class ItemService {
	// connect to some database
	
	public Item getExampleItem() {
		// should retrieve item from DB in future, based on some id for example
		Item item = new Item("Surfboard XYZ", new Double(27.50));
		return item;
	}
}

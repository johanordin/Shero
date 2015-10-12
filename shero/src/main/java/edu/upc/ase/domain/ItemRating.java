package edu.upc.ase.domain;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;

@Entity
public class ItemRating {

	@Id Long id;
	public Item item;
	public Integer ratingValue; 
}

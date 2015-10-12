package edu.upc.ase.domain;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;

@Entity
public class ItemRating {

	@Id private Long id;
	//private Item item;
	private Integer ratingValue;
	
	public ItemRating() {
	}
	
	public Integer getRatingValue() {
		return ratingValue;
	}
	public void setRatingValue(Integer ratingValue) {
		this.ratingValue = ratingValue;
	} 
}

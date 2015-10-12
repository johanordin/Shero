package edu.upc.ase.domain;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;

@Entity
public class UserRating {

	@Id Long id;
	//private User user;
	private Integer ratingValue;
	
	public UserRating() {
	}
	
	public UserRating(Integer ratingValue) {
		this.ratingValue = ratingValue;
	}
	
	public Integer getRatingValue() {
		return ratingValue;
	}
	public void setRatingValue(Integer ratingValue) {
		this.ratingValue = ratingValue;
	}
}

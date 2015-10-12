package edu.upc.ase.domain;

import java.util.Map;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;

@Entity
public class User {
	
	@Id Long id;
	private String firstname;
	private String lastname;
	private String emailAddress;
	private Map<String, Item> items;
	private Map<String, Address> adresses;
	private Map<String, UserRating> ratings;
	
	public String getFirstname() {
		return firstname;
	}
	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}
	public String getLastname() {
		return lastname;
	}
	public void setLastname(String lastname) {
		this.lastname = lastname;
	}
	public String getEmailAddress() {
		return emailAddress;
	}
	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}
	public Map<String, Item> getItems() {
		return items;
	}
	public void setItems(Map<String, Item> items) {
		this.items = items;
	}
	public Map<String, Address> getAdresses() {
		return adresses;
	}
	public void setAdresses(Map<String, Address> adresses) {
		this.adresses = adresses;
	}
	public Map<String, UserRating> getRatings() {
		return ratings;
	}
	public void setRatings(Map<String, UserRating> ratings) {
		this.ratings = ratings;
	}

}

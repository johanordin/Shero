package edu.upc.ase.domain;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.googlecode.objectify.Key;
import com.googlecode.objectify.Ref;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;

@Entity
public class User {
	
	@Id private Long id;
	private String firstname;
	private String lastname;
	private String emailAddress;
	
	private Set<Ref<Item>> items = new HashSet<Ref<Item>>();
	private List<Key<Address>> addresses = new ArrayList<Key<Address>>();
	// on every load() or save() will fetch and store the entire list of referenced rating keys
	private List<Key<UserRating>> userRatings = new ArrayList<Key<UserRating>>();
	
	public String getFirstname() {
		return firstname;
	}
	
	public User(){
	}
	
	public User(String firstname, String lastname, String emailAddress) {
		this.firstname = firstname;
		this.lastname = lastname;
		this.emailAddress = emailAddress;
	}
	
	public void setId(Long id) {
		this.id = id;
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
	public Set<Ref<Item>> getItems() {
		return items;
	}
	public List<Key<Address>> getAddresses() {
		return addresses;
	}
	
	public void addAddress(Key<Address> address) {
		addresses.add(address);
	}
	
	public List<Key<UserRating>> getUserRatings() {
		return userRatings;
	}
	
	public void addUserRating(Key<UserRating> ratingKey){
		userRatings.add(ratingKey);
	}
	
}

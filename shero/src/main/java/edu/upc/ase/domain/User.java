package edu.upc.ase.domain;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.ObjectifyService;
import com.googlecode.objectify.Ref;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Ignore;
import com.googlecode.objectify.annotation.Index;
import com.googlecode.objectify.annotation.Load;
import com.googlecode.objectify.annotation.OnLoad;

@Entity
public class User {
	private static final Gson GSON = new Gson();
	
	@Id private Long id;
	private String firstname;
	private String lastname;
	
	@Index 
	private String emailAddress;
	
	// will not be serialized, but persisted in database
	private transient String passwordHash;
	
	// will not be persisted, but is important for serialization of entity values
	@Ignore
	private List<Item> items;
	@Ignore
	private List<UserRating> userRatings;
	@Ignore
	private List<Address> addresses;
	
	// live references to data store
	private transient List<Ref<Item>> itemRefs = new ArrayList<Ref<Item>>();
	private transient List<Ref<UserRating>> userRatingRefs = new ArrayList<Ref<UserRating>>();
	@Load
	private transient List<Ref<Address>> addressRefs = new ArrayList<Ref<Address>>();
	
	@OnLoad // Called after the POJO is populated with data
	private void onLoad() {
		items = this.getItems();
		addresses = this.getAddresses();
		userRatings = this.getUserRatings();
	}
	
	// no-arg constructor required by objectify
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
	public String getPasswordHash() {
		return passwordHash;
	}
	public void setPasswordHash(String passwordHash) {
		this.passwordHash = passwordHash;
	}
	public List<Item> getItems() {
		// perform batch load instead of single get() requests for every entity
		return new ArrayList<Item>(ObjectifyService.ofy().load().refs(itemRefs).values());
	}
	public List<UserRating> getUserRatings() {
		return new ArrayList<UserRating>(ObjectifyService.ofy().load().refs(userRatingRefs).values());
	}
	public List<Address> getAddresses() {
		return new ArrayList<Address>(ObjectifyService.ofy().load().refs(addressRefs).values());
	}
	public void addItem(Key<Item> item) {
		itemRefs.add(Ref.create(item));
	}
	public void addUserRating(Key<UserRating> userRating){
		userRatingRefs.add(Ref.create(userRating));
	}
	public void addAddress(Key<Address> address) {
		addressRefs.add(Ref.create(address));
	}

	@Override
	public String toString() {
		return "User [firstname=" + firstname + ", lastname=" + firstname + ", emailAddress="
				+ emailAddress + "]";
	}
	
	public String serialize() {
		items = this.getItems();
		addresses = this.getAddresses();
		userRatings = this.getUserRatings();
		return GSON.toJson(this);
    }
	
}

package edu.upc.ase.domain;

import java.util.ArrayList;
import java.util.List;

import com.googlecode.objectify.Key;
import com.googlecode.objectify.Ref;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Load;

@Entity
public class Item {

	@Id public Long id;
	private String name;
	private Double price;
	private String description;
	private String imagePath;
	
	// one item has exactly one address
	private @Load Ref<Address> address;

	
	@Ignore
	private Address addressFull;

	private List<Ref<Availability>> availabilityPeriods = new ArrayList<Ref<Availability>>();
	// on every load() or save() will fetch and store the entire list of referenced rating keys
	private List<Key<ItemRating>> itemRatings = new ArrayList<Key<ItemRating>>();
	
	public Item() {
	}
	
	public Item(String name, Double price) {
		this.name = name;
		this.price = price;
	}

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Double getPrice() {
		return price;
	}
	public void setPrice(Double price) {
		this.price = price;
	}
	public String getImagePath() {
		return imagePath;
	}
	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}
	public Address getAddress() {
		return address.get();
	}
	public void setAddress(Address address) {
		this.address = Ref.create(address);
	}
	public Address getAddressFull() {
		return addressFull;
	}

	public void setAddressFull(Address addressFull) {
		this.addressFull = addressFull;
	}

	public List<Ref<Availability>> getAvailabilityPeriods() {
		return availabilityPeriods;
	}
	public List<Key<ItemRating>> getItemRatings() {
		return itemRatings;
	}
	
	public void addItemRating(Key<ItemRating> itemRatingKey)
	{
		itemRatings.add(itemRatingKey);
	}
}

package edu.upc.ase.domain;

import java.math.BigDecimal;
import java.util.Map;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;

@Entity
public class Item {

	@Id Long id;
	private String name;
	private BigDecimal price;
	private String description;
	private String imagePath;
	private Address itemAdress;
	private Map<String, Availability> availabilities;
	private Map<String, Item> itemRatings;
	
	public Item() {
		super();
	}
	
	
	public Item(String name, BigDecimal price) {
		super();
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
	public BigDecimal getPrice() {
		return price;
	}
	public void setPrice(BigDecimal price) {
		this.price = price;
	}
	public String getImagePath() {
		return imagePath;
	}
	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}
	public Address getItemAdress() {
		return itemAdress;
	}
	public void setItemAdress(Address itemAdress) {
		this.itemAdress = itemAdress;
	}
	public Map<String, Availability> getAvailabilities() {
		return availabilities;
	}
	public void setAvailabilities(Map<String, Availability> availabilities) {
		this.availabilities = availabilities;
	}
	public Map<String, Item> getItemRatings() {
		return itemRatings;
	}
	public void setItemRatings(Map<String, Item> itemRatings) {
		this.itemRatings = itemRatings;
	}
}

package edu.upc.ase.domain;

/**
 *
 */
public class Item {
	private String name;
	private String location;
	private double price;
	
	public Item(String name, String location, double price) {
		this.name = name;
		this.location = location;
		this.price = price;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	
}

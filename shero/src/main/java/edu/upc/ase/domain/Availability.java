package edu.upc.ase.domain;

import org.joda.time.DateTime;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;

@Entity
public class Availability {

	@Id Long id;
	private Item item;
	private DateTime availableFrom;
	private DateTime availableTo;
	
	public Item getItem() {
		return item;
	}
	public void setItem(Item item) {
		this.item = item;
	}
	public DateTime getAvailableFrom() {
		return availableFrom;
	}
	public void setAvailableFrom(DateTime availableFrom) {
		this.availableFrom = availableFrom;
	}
	public DateTime getAvailableTo() {
		return availableTo;
	}
	public void setAvailableTo(DateTime availableTo) {
		this.availableTo = availableTo;
	}
}

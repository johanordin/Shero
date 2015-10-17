package edu.upc.ase.domain;

import java.util.Date;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;

@Entity
public class Availability {

	@Id Long id;
	private Date availableFrom;
	private Date availableTo;
	
	public Availability() {
	}
	
	public Availability(Date availableFrom, Date availableTo) {
		this.availableFrom = availableFrom;
		this.availableTo = availableTo;
	}
	
	public Date getAvailableFrom() {
		return availableFrom;
	}
	public void setAvailableFrom(Date availableFrom) {
		this.availableFrom = availableFrom;
	}
	public Date getAvailableTo() {
		return availableTo;
	}
	public void setAvailableTo(Date availableTo) {
		this.availableTo = availableTo;
	}
}

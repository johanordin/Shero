package edu.upc.ase.domain;

import java.util.Date;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;

@Entity
public class Availability {

	@Id Long id;
	// granularity of availability is day-wise
	private Date availabilityDate;
	
	public Availability() {
	}

	public Availability(Date availabilityDate) {
		this.availabilityDate = availabilityDate;
	}

	public Date getAvailabilityDate() {
		return availabilityDate;
	}

	public void setAvailabilityDate(Date availabilityDate) {
		this.availabilityDate = availabilityDate;
	}

	@Override
	public String toString() {
		return "Availability [id=" + id + ", availabilityDate="
				+ availabilityDate + "]";
	}
	
}

package edu.upc.ase.domain;

import java.util.Date;
import java.util.List;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;

/**
 * This entity keeps track of a rental.
 * A user (id) rents an item (id) for a fixed period of time.
 */
@Entity
public class Rental {
	@Id
	public Long rentalId;
	@Index
	private Long userId; // who is renting the item
	@Index
	private Long itemId; // which item is he renting
	private List<Date> period;
	
	public Rental(Long userId, Long itemId, List<Date> period) {
		this.userId = userId;
		this.itemId = itemId;
		this.period = period;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Long getItemId() {
		return itemId;
	}

	public void setItemId(Long itemId) {
		this.itemId = itemId;
	}

	public List<Date> getPeriod() {
		return period;
	}

	public void setPeriod(List<Date> period) {
		this.period = period;
	}

	public Long getRentalId() {
		return rentalId;
	}

	@Override
	public String toString() {
		return "Rental [rentalId=" + rentalId + ", userId=" + userId
				+ ", itemId=" + itemId + ", period=" + period + "]";
	}
}

package edu.upc.ase.domain;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;

@Entity
public class ItemRating {

	@Id
	private Long id;
	@Index
	private Long item;
	@Index
	private Long from;
	private Integer ratingValue;

	public ItemRating() {
	}

	public ItemRating(Long item, Long from, Integer ratingValue) {
		this.item = item;
		this.from = from;
		this.ratingValue = ratingValue;
	}

	public Long getItem() {
		return item;
	}

	public void setItem(Long item) {
		this.item = item;
	}

	public Long getFrom() {
		return from;
	}

	public void setFrom(Long from) {
		this.from = from;
	}

	public Integer getRatingValue() {
		return ratingValue;
	}

	public void setRatingValue(Integer ratingValue) {
		this.ratingValue = ratingValue;
	}
	
	@Override
	public String toString() {
		return "ItemRating [id=" + id + ", item=" + item + ", from=" + from
				+ ", ratingValue=" + ratingValue + "]";
	}
}

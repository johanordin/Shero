package edu.upc.ase.domain;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;

/**
 * User ratings are NOT attached to a user via any relationship. This makes it
 * easier to create and delete new user ratings. Ratings a user has written
 * (from) or received (to), can be retrieved using a simple query.
 */
@Entity
public class UserRating {

	@Id
	Long id;
	@Index
	private Long from;
	@Index
	private Long to;
	private Integer ratingValue;

	public UserRating() {
	}

	public UserRating(Long from, Long to, Integer ratingValue) {
		this.from = from;
		this.to = to;
		this.ratingValue = ratingValue;
	}

	public Integer getRatingValue() {
		return ratingValue;
	}

	public void setRatingValue(Integer ratingValue) {
		this.ratingValue = ratingValue;
	}

	public Long getFrom() {
		return from;
	}

	public void setFrom(Long from) {
		this.from = from;
	}

	public Long getTo() {
		return to;
	}

	public void setTo(Long to) {
		this.to = to;
	}

	@Override
	public String toString() {
		return "UserRating [id=" + id + ", from=" + from + ", to=" + to
				+ ", ratingValue=" + ratingValue + "]";
	}

}

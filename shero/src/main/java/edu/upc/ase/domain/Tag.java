package edu.upc.ase.domain;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;

@Entity
public class Tag {
	
	@Id private Long id;
	private String text;
	
	public Tag() {
	}
	
	public Tag(String tag) {
		this.text = tag;
	}

	public String getTag() {
		return text;
	}

	public void setTag(String tag) {
		this.text = tag;
	}

	public Long getId() {
		return id;
	}

	@Override
	public String toString() {
		return "Tag [id=" + id + ", text=" + text + "]";
	}
}

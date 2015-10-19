package edu.upc.ase.domain;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;

@Entity
public class Tag {
	
	@Id private Long id;
	private String tag;
	
	public Tag() {
	}
	
	public Tag(String tag) {
		this.tag = tag;
	}

	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}

	public Long getId() {
		return id;
	}

	@Override
	public String toString() {
		return "Tag [id=" + id + ", tag=" + tag + "]";
	}
}

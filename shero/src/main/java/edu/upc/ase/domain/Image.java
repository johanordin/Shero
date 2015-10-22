package edu.upc.ase.domain;

import com.google.appengine.api.datastore.Blob;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;

@Entity
public class Image {
	@Id
	public Long id;
	
	@Index
	private String name;
	private Blob image; 
	
	public Image(String name, Blob image) {
		super();
		this.name = name;
		this.image = image;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public Blob getImage() {
		return image;
	}

	public void setImage(Blob image) {
		this.image = image;
	}
}

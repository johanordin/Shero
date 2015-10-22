package edu.upc.ase.domain.admin;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;

@Entity
public class EmailTemplate {

	@Id Long id;
	
	@Index
	private String name;
	private String htmlText;
	
	public EmailTemplate() {
	}

	public EmailTemplate(String name, String htmlText) {
		super();
		this.name = name;
		this.htmlText = htmlText;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getHtmlText() {
		return htmlText;
	}
	public void setHtmlText(String htmlText) {
		this.htmlText = htmlText;
	}
}

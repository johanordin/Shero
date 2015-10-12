package edu.upc.ase.domain; 
 
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;

@Entity
public class Address {

	@Id Long id;
	private String city;
	private String zipcode;
	private String country;
	private String street;
	private String number; 
	private String additional;
	
	public Address() {
	}
	
	public Address(String city, String zipcode, String street, String number) {
		this.city = city;
		this.zipcode = zipcode;
		this.street = street;
		this.number = number;
	}
	
	public void setId(Long id) {
		this.id = id;
	}

	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getZipcode() {
		return zipcode;
	}
	public void setZipcode(String zipcode) {
		this.zipcode = zipcode;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public String getStreet() {
		return street;
	}
	public void setStreet(String street) {
		this.street = street;
	}
	public String getNumber() {
		return number;
	}
	public void setNumber(String number) {
		this.number = number;
	}
	public String getAdditional() {
		return additional;
	}
	public void setAdditional(String additional) {
		this.additional = additional;
	}
}

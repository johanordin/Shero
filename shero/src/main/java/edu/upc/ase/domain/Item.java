package edu.upc.ase.domain;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.googlecode.objectify.ObjectifyService;
import com.googlecode.objectify.Ref;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Ignore;
import com.googlecode.objectify.annotation.Index;
import com.googlecode.objectify.annotation.Load;
import com.googlecode.objectify.annotation.OnLoad;

@Entity
public class Item {
	private static final Gson GSON = new Gson();
	
	@Id
	public Long id;
	@Index
	private String name;
	@Index
	private Double price;
	private String description;
	private String imagePath;

	// one item has exactly one address
	@Ignore
	private Address address;
	@Ignore
	private List<Availability> availabilityPeriods;
	@Ignore
	private List<ItemRating> itemRatings;

	@Load
	private transient Ref<Address> addressRef;
	@Load
	@Index
	private transient List<Ref<Availability>> availabilityPeriodRefs = new ArrayList<Ref<Availability>>();
	@Load
	private transient List<Ref<ItemRating>> itemRatingRefs = new ArrayList<Ref<ItemRating>>();

	public Item() {
	}

	public Item(String name, Double price, String description) {
		this.name = name;
		this.price = price;
		this.description = description;
	}

	public Item(String name, Double price, String description, Address address,
			List<Availability> availabilityPeriods) {
		this.name = name;
		this.price = price;
		this.description = description;
		this.address = address;
		this.availabilityPeriods = availabilityPeriods;
	}

	@OnLoad
	private void onLoad() {
		this.getAddress();
		this.getAvailabilityPeriods();
		this.getItemRatings();
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public String getImagePath() {
		return imagePath;
	}

	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}

	public Long getId() {
		return id;
	}

	public Address getAddress() {
		this.address = addressRef == null ? null : addressRef.get();
		return address;
	}

	public List<Availability> getAvailabilityPeriods() {
		this.availabilityPeriods = new ArrayList<Availability>(ObjectifyService.ofy().load().refs(availabilityPeriodRefs).values());
		return availabilityPeriods;
	}

	public List<ItemRating> getItemRatings() {
		this.itemRatings = ObjectifyService.ofy().load().type(ItemRating.class).filter("item", this.id).list();
		return itemRatings;
	}

	public void setAddress(Address address) {
		this.addressRef = Ref.create(address);
	}

	@Override
	public String toString() {
		return "Item [id=" + id + ", name=" + name + ", price=" + price
				+ ", description=" + description + ", imagePath=" + imagePath
				+ ", address=" + address + ", availabilityPeriods="
				+ availabilityPeriods + ", itemRatings=" + itemRatings + "]";
	}
	
	public String serialize() {
		this.getAddress();
		this.getAvailabilityPeriods();
		this.getItemRatings();
		return GSON.toJson(this);
	}

}

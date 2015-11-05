package edu.upc.ase.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

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
	private static final Logger logger = Logger.getLogger("Item");
	
	@Id
	public Long id;
	@Index
	private String name;
	@Index
	private Double price;
	private String description;

	// one item has exactly one address
	@Index
	private Address address;
	@Ignore
	private List<Availability> availabilityPeriods;
	@Ignore
	private List<ItemRating> itemRatings;
	@Ignore
	private List<Tag> tags;

	@Load
	@Index
	private transient List<Ref<Availability>> availabilityPeriodRefs = new ArrayList<Ref<Availability>>();
	@Load
	private transient List<Ref<ItemRating>> itemRatingRefs = new ArrayList<Ref<ItemRating>>();
	@Load
	private transient List<Ref<Tag>> tagRefs = new ArrayList<Ref<Tag>>();
	
	@Load
	private transient List<Ref<Image>> imageRefs = new ArrayList<Ref<Image>>();
	
	public Item() {
	}

	public Item(String name, Double price, String description) {
		this.name = name;
		this.price = price;
		this.description = description;
	}

	public Item(String name, Double price, String description, Address address,
			List<Availability> availabilityPeriods, List<Tag> tags) {
		this.name = name;
		this.price = price;
		this.description = description;
		this.address = address;
		this.availabilityPeriods = availabilityPeriods;
		this.tags = tags;
	}

	@OnLoad
	private void onLoad() {
		logger.info("Item.class call onLoad()");
		this.getAddress();
		this.getAvailabilityPeriods();
		this.getItemRatings();
		this.getTags();
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

	public Long getId() {
		return id;
	}

	public Address getAddress() {
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

	public List<Tag> getTags() {
		this.tags = new ArrayList<Tag>(ObjectifyService.ofy().load().refs(tagRefs).values());
		return tags;
	}
	
	public List<Ref<Image>> getImageRefs() {
		return imageRefs;
	}

	public void setAddress(Address address) { 
		this.address = address;
	}
	
	public void addTag(Ref<Tag> tag) {
		this.tagRefs.add(tag);
	}
	
	public void addItemRating(Ref<ItemRating> itemRating) {
		this.itemRatingRefs.add(itemRating);
	}
	
	public void addAvailableDay(Ref<Availability> day) {
		this.availabilityPeriodRefs.add(day);
	}
	
	public void addImage(Ref<Image> image) {
		this.imageRefs.add(image);
	}
	
	@Override
	public String toString() {
		return "Item [id=" + id + ", name=" + name + ", price=" + price
				+ ", description=" + description + ", address=" + address + ", availabilityPeriods="
				+ availabilityPeriods + ", itemRatings=" + itemRatings + "]";
	}
	
	public void serialize() {
		this.getAddress();
		this.getAvailabilityPeriods();
		this.getItemRatings();
		this.getTags();
	}

}

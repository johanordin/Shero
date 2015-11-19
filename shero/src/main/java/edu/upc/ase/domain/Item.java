package edu.upc.ase.domain;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
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
	private Address address;
	@Index
	private List<Date> availabilityPeriods = new ArrayList<Date>();
	@Ignore
	private List<ItemRating> itemRatings;
	@Ignore
	private List<Tag> tags;
	
	private String imageId;
	
	private Long ownerId;
	private Date created;
	
	// count of all ratings
	private int numRatings;
	// sum of all ratings' values
	private int sumRatings;
	
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
		
		// set creation time
		Calendar cal = Calendar.getInstance();
		this.created = cal.getTime();
	}

	public Item(String name, Double price, String description, Address address,
			List<Date> availabilityPeriods, List<Tag> tags) {
		this(name, price, description);
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

	public List<Date> getAvailabilityPeriods() {
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
	
	public String getImageId() {
		return imageId;
	}

	public void setImageId(String imageId) {
		this.imageId = imageId;
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
	
	public void addAvailableDay(Date day) {
		this.availabilityPeriods.add(day);
	}
	
	public void removeAvailabilityPeriod(List<Date> period) {
		this.availabilityPeriods.removeAll(period);
	}
	
	public void addImage(Ref<Image> image) {
		this.imageRefs.add(image);
	}
	
	public Date getCreated() {
		return created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}
	
	public int getNumRatings() {
		return numRatings;
	}
	
	public void updateRatingCount(int value) {
		this.sumRatings += value;
		this.numRatings++;
	}
	
	public int getSumRatings() {
		return sumRatings;
	}
	
	public Long getOwnerId() {
		return ownerId;
	}

	public void setOwnerId(Long ownerId) {
		this.ownerId = ownerId;
	}

	@Override
	public String toString() {
		return "Item [id=" + id + ", name=" + name + ", price=" + price
				+ ", description=" + description + ", address=" + address
				+ ", availabilityPeriods=" + availabilityPeriods
				+ ", itemRatings=" + itemRatings + ", tags=" + tags
				+ ", imageId=" + imageId + ", ownerId=" + ownerId
				+ ", created=" + created + ", numRatings=" + numRatings
				+ ", sumRatings=" + sumRatings + "]";
	}

	public void serialize() {
		this.getAddress();
		this.getAvailabilityPeriods();
		this.getItemRatings();
		this.getTags();
	}

}

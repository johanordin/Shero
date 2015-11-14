package edu.upc.ase.serverstartup;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.fluttercode.datafactory.impl.DataFactory;

import com.googlecode.objectify.Key;
import com.googlecode.objectify.ObjectifyService;
import com.googlecode.objectify.Ref;

import edu.upc.ase.domain.Address;
import edu.upc.ase.domain.Availability;
import edu.upc.ase.domain.Item;
import edu.upc.ase.domain.ItemRating;
import edu.upc.ase.domain.Tag;
import edu.upc.ase.domain.User;
import edu.upc.ase.domain.admin.EmailTemplate;

public class SetupDatastoreUtil {
	
	//private static final Logger logger = Logger.getLogger("SetupDatastoreUtil");
		//Initialize EMailTemplate
		public static void setupEMailTemplate() {
	
		List<EmailTemplate> emailTemplates = ObjectifyService.ofy().load().type(EmailTemplate.class)
				.filter("name", "WelcomeTemplate").list();
		
		if(emailTemplates.isEmpty()){
			//If not create now Email Template
			EmailTemplate emailTemplate = new EmailTemplate("WelcomeTemplate", HTML_TEMPLATE);
			ObjectifyService.ofy().save().entity(emailTemplate).now();
		}
	}
	
	public static void setupUser() {
		DataFactory df = DataFactory.create();

		User u1 = new User ("Test", "User", "test@test.com");
		User u2 = new User ("Firstname", "Lastname", "first@last.com");
		User u3 = new User ("Nombre", "Apellido", "nombre@apellido.es");
		
		u1.setPasswordHash("9f86d081884c7d659a2feaa0c55ad015a3bf4f1b2b0b822cd15d6c15b0f00a08"); //test
		u2.setPasswordHash("a7937b64b8caa58f03721bb6bacf5c78cb235febe0e70b1b84cd99541461a08e"); //first
		u3.setPasswordHash("0bfe93928a499da280f6e6cd04ea31a72d8462c19c4278659bad27182b88e7b1"); //nombre
		
		Key<User> user1 = ObjectifyService.ofy().save().entity(u1).now();
		Key<User> user2 = ObjectifyService.ofy().save().entity(u2).now();
		Key<User> user3 = ObjectifyService.ofy().save().entity(u3).now();
		
		Address a1 = new Address ("United States", "San Antonio", "78212", "Crestview Terrace", "3891");
		Address a2 = new Address ("United States", "Columbia", "29223", "Emily Drive", "1110");
		Address a3 = new Address ("United States", "Los Angeles", "90017", "Gordon Street", "3517");
		Address a4 = new Address ("Germany", "Boebrach", "94255", "Kirchenallee", "8");
		Address a5 = new Address ("Germany", "Althuette", "71566", "Pasewalker Strasse", "46");
		Address a6 = new Address ("Spain", "Pradoluengo", "09260", "Reyes Catolicos", "81");
		
		Key<Address> address1 = ObjectifyService.ofy().save().entity(a1).now();
		Key<Address> address2 = ObjectifyService.ofy().save().entity(a2).now();
		Key<Address> address3 = ObjectifyService.ofy().save().entity(a3).now();
		Key<Address> address4 = ObjectifyService.ofy().save().entity(a4).now();
		Key<Address> address5 = ObjectifyService.ofy().save().entity(a5).now();
		Key<Address> address6 = ObjectifyService.ofy().save().entity(a6).now();
		
		u1.addAddress(address1);
		u1.addAddress(address2);
		u1.addAddress(address3);
		u2.addAddress(address4);
		u2.addAddress(address5);
		u3.addAddress(address6);
		
		Calendar calendar = Calendar.getInstance();
		calendar.set(2015, 10, 13, 0, 0, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		Date d1 = calendar.getTime();
		calendar.set(2015, 10, 14, 0, 0, 0);
		Date d2 = calendar.getTime();
		calendar.set(2015, 10, 15, 0, 0, 0);
		Date d3 = calendar.getTime();
		calendar.set(2015, 10, 16, 0, 0, 0);
		Date d4 = calendar.getTime();
		calendar.set(2015, 10, 20, 0, 0, 0);
		Date d5 = calendar.getTime();
		calendar.set(2015, 10, 21, 0, 0, 0);
		Date d6 = calendar.getTime();
		calendar.set(2015, 10, 22, 0, 0, 0);
		Date d7 = calendar.getTime();
		calendar.set(2015, 10, 23, 0, 0, 0);
		Date d8 = calendar.getTime();
		calendar.set(2015, 10, 27, 0, 0, 0);
		Date d9 = calendar.getTime();
		calendar.set(2015, 10, 28, 0, 0, 0);
		Date d10 = calendar.getTime();
		calendar.set(2015, 11, 1, 0, 0, 0);
		Date d11 = calendar.getTime();
		calendar.set(2015, 11, 2, 0, 0, 0);
		Date d12 = calendar.getTime();
		calendar.set(2015, 11, 3, 0, 0, 0);
		Date d13 = calendar.getTime();
		calendar.set(2015, 11, 10, 0, 0, 0);
		Date d14 = calendar.getTime();
		calendar.set(2015, 11, 11, 0, 0, 0);
		Date d15 = calendar.getTime();
		calendar.set(2015, 11, 12, 0, 0, 0);
		Date d16 = calendar.getTime();
		calendar.set(2015, 11, 31, 0, 0, 0);
		Date d17 = calendar.getTime();
		
		Availability days1 = new Availability(d1);
		Availability days2 = new Availability(d2);
		Availability days3 = new Availability(d3);
		Availability days4 = new Availability(d4);
		Availability days5 = new Availability(d5);
		Availability days6 = new Availability(d6);
		Availability days7 = new Availability(d7);
		Availability days8 = new Availability(d8);
		Availability days9 = new Availability(d9);
		Availability days10 = new Availability(d10);
		Availability days11 = new Availability(d11);
		Availability days12 = new Availability(d12);
		Availability days13 = new Availability(d13);
		Availability days14 = new Availability(d14);
		Availability days15 = new Availability(d15);
		Availability days16 = new Availability(d16);
		Availability days17 = new Availability(d17);
		
		ObjectifyService.ofy().save().entity(days1).now();
		ObjectifyService.ofy().save().entity(days2).now();
		ObjectifyService.ofy().save().entity(days3).now();
		ObjectifyService.ofy().save().entity(days4).now();
		ObjectifyService.ofy().save().entity(days5).now();
		ObjectifyService.ofy().save().entity(days6).now();
		ObjectifyService.ofy().save().entity(days7).now();
		ObjectifyService.ofy().save().entity(days8).now();
		ObjectifyService.ofy().save().entity(days9).now();
		ObjectifyService.ofy().save().entity(days10).now();
		ObjectifyService.ofy().save().entity(days11).now();
		ObjectifyService.ofy().save().entity(days12).now();
		ObjectifyService.ofy().save().entity(days13).now();
		ObjectifyService.ofy().save().entity(days14).now();
		ObjectifyService.ofy().save().entity(days15).now();
		ObjectifyService.ofy().save().entity(days16).now();
		ObjectifyService.ofy().save().entity(days17).now();
		
		Tag t1 = new Tag ("Sports");
		Tag t2 = new Tag ("Summer");
		Tag t3 = new Tag ("Fun");
		Tag t4 = new Tag ("Water");
		Tag t5 = new Tag ("Beach");
		
		Key<Tag> tag1 = ObjectifyService.ofy().save().entity(t1).now();
		Key<Tag> tag2 = ObjectifyService.ofy().save().entity(t2).now();
		Key<Tag> tag3 = ObjectifyService.ofy().save().entity(t3).now();
		Key<Tag> tag4 = ObjectifyService.ofy().save().entity(t4).now();
		Key<Tag> tag5 = ObjectifyService.ofy().save().entity(t5).now();

		Item i1 = new Item ("Surfboard", 
				100.0, 
				"A surfboard is an elongated platform used in the sport of surfing. Surfboards are relatively light, but are strong enough to support an individual standing on them while riding a breaking wave. They were invented in ancient Hawaii.");
		Item i2 = new Item ("Surfboard", 
				110.34, 
				"A surfboard is an elongated platform used in the sport of surfing. Surfboards are relatively light, but are strong enough to support an individual standing on them while riding a breaking wave. They were invented in ancient Hawaii.");
		Item i3 = new Item ("Surfboard", 
				222.10, 
				"A surfboard is an elongated platform used in the sport of surfing. Surfboards are relatively light, but are strong enough to support an individual standing on them while riding a breaking wave. They were invented in ancient Hawaii.");
		Item i4 = new Item ("Surfboard", 
				68.45, 
				"A surfboard is an elongated platform used in the sport of surfing. Surfboards are relatively light, but are strong enough to support an individual standing on them while riding a breaking wave. They were invented in ancient Hawaii.");
		Item i5 = new Item ("Racket",
				52.3,
				"A racket or racquet is a sports implement consisting of a handled frame with an open hoop across which a network of strings or catgut is stretched tightly. It is used for striking a ball in games such as squash, tennis, racquetball, and badminton. Collectively, these games are known as racquet sports.");
		Item i6 = new Item ("Racket",
				60.0,
				"A racket or racquet is a sports implement consisting of a handled frame with an open hoop across which a network of strings or catgut is stretched tightly. It is used for striking a ball in games such as squash, tennis, racquetball, and badminton. Collectively, these games are known as racquet sports.");
		Item i7 = new Item ("Golf club",
				78.21,
				"A golf club is a club used to hit a golf ball in a game of golf. Each club is composed of a shaft with a grip and a club head. Woods are mainly used for long-distance fairway or tee shots; irons, the most versatile class, are used for a variety of shots; hybrids that combine design elements of woods and irons are becoming increasingly popular; putters are used mainly on the green to roll the ball into the hole.");
		Item i8 = new Item ("Beach chair",
				45.45,
				"Strandkorb (German: literally beach basket; Danish: strandkurv; English sometimes beach chair) is a special chair designed to provide comfort and protection from sun, wind, rain, and sand on beaches frequented by tourists.");
		Item i9 = new Item ("Snorkel",
				10.0,
				"Snorkeling (British and Commonwealth English spelling: snorkelling) is the practice of swimming on or through a body of water while equipped with a diving mask, a shaped tube called a snorkel, and usually fins. In cooler waters, a wetsuit may also be worn. Use of this equipment allows the snorkeler to observe underwater attractions for extended periods with relatively little effort and to breathe while face-down at the surface.");
		Item i10 = new Item ("Lord of the Rings (all three books",
				67.12,
				"The Lord of the Rings is an epic high-fantasy novel written by English author J. R. R. Tolkien. The story began as a sequel to Tolkien's 1937 fantasy novel The Hobbit, but eventually developed into a much larger work. Written in stages between 1937 and 1949, The Lord of the Rings is the second best-selling novel ever written, with over 150 million copies sold.");
		Item i11 = new Item ("Umbrella",
				5.67,
				"An umbrella or parasol is a folding canopy supported by metal ribs, which is mounted on a wooden, metal or plastic pole. It is designed to protect a person against rain or sunlight. The word 'umbrella' typically refers to a device used for protection from rain. The word parasol usually refers to an item designed to protect from the sun. Often the difference is the material used for the canopy; some parasols are not waterproof. Umbrella canopies may be made of fabric or flexible plastic."); 
		
		i1.setAddress(a6);
		i2.setAddress(a5);
		i3.setAddress(a4);
		i4.setAddress(a1);
		i5.setAddress(a3);
		i6.setAddress(a2);
		i7.setAddress(a6);
		i8.setAddress(a2);
		i9.setAddress(a1);
		i10.setAddress(a3);
		i11.setAddress(a4);
		
		i1.addAvailableDay(days1);
		i1.addAvailableDay(days2);
		i1.addAvailableDay(days3);
		i1.addAvailableDay(days4);
		i1.addAvailableDay(days11);
		i1.addAvailableDay(days12);
		i1.addAvailableDay(days13);
		i2.addAvailableDay(days1);
		i2.addAvailableDay(days2);
		i2.addAvailableDay(days3);
		i2.addAvailableDay(days4);
		i3.addAvailableDay(days4);
		i3.addAvailableDay(days5);
		i3.addAvailableDay(days6);
		i3.addAvailableDay(days7);
		i3.addAvailableDay(days8);
		i3.addAvailableDay(days9);
		i4.addAvailableDay(days12);
		i5.addAvailableDay(days1);
		i5.addAvailableDay(days2);
		i5.addAvailableDay(days3);
		i5.addAvailableDay(days4);
		i5.addAvailableDay(days5);
		i5.addAvailableDay(days6);
		i5.addAvailableDay(days7);
		i5.addAvailableDay(days8);
		i5.addAvailableDay(days9);
		i6.addAvailableDay(days10);
		i6.addAvailableDay(days11);
		i6.addAvailableDay(days12);
		i6.addAvailableDay(days13);
		i6.addAvailableDay(days14);
		i6.addAvailableDay(days15);
		i6.addAvailableDay(days16);
		i6.addAvailableDay(days17);
		i7.addAvailableDay(days7);
		i7.addAvailableDay(days8);
		i7.addAvailableDay(days9);
		i7.addAvailableDay(days10);
		i7.addAvailableDay(days11);
		i7.addAvailableDay(days12);
		i7.addAvailableDay(days13);	
		i8.addAvailableDay(days1);
		i8.addAvailableDay(days2);
		i8.addAvailableDay(days3);
		i9.addAvailableDay(days1);
		i9.addAvailableDay(days3);
		i9.addAvailableDay(days5);
		i9.addAvailableDay(days7);
		i9.addAvailableDay(days9);	
		i10.addAvailableDay(days1);
		i10.addAvailableDay(days2);
		i10.addAvailableDay(days3);
		i10.addAvailableDay(days4);
		i10.addAvailableDay(days5);
		i10.addAvailableDay(days6);
		i10.addAvailableDay(days7);
		i10.addAvailableDay(days8);
		i10.addAvailableDay(days9);
		i10.addAvailableDay(days10);
		i10.addAvailableDay(days11);
		i10.addAvailableDay(days12);
		i10.addAvailableDay(days13);
		i10.addAvailableDay(days14);
		i10.addAvailableDay(days15);
		i10.addAvailableDay(days16);
		i10.addAvailableDay(days17);	
		i11.addAvailableDay(days17);
		
		i1.addTag(Ref.create(tag1));
		i1.addTag(Ref.create(tag2));
		i1.addTag(Ref.create(tag3));
		i1.addTag(Ref.create(tag4));
		i1.addTag(Ref.create(tag5));	
		i2.addTag(Ref.create(tag4));
		i2.addTag(Ref.create(tag5));
		i3.addTag(Ref.create(tag3));
		i3.addTag(Ref.create(tag4));
		i3.addTag(Ref.create(tag5));
		i4.addTag(Ref.create(tag1));
		i4.addTag(Ref.create(tag2));
		i4.addTag(Ref.create(tag3));
		i4.addTag(Ref.create(tag4));
		i5.addTag(Ref.create(tag1));
		i5.addTag(Ref.create(tag2));
		i5.addTag(Ref.create(tag3));	
		i6.addTag(Ref.create(tag1));	
		i7.addTag(Ref.create(tag1));
		i7.addTag(Ref.create(tag3));	
		i8.addTag(Ref.create(tag2));
		i8.addTag(Ref.create(tag5));	
		i9.addTag(Ref.create(tag4));
		i9.addTag(Ref.create(tag5));
		
		Key<Item> item1 = ObjectifyService.ofy().save().entity(i1).now();
		Key<Item> item2 = ObjectifyService.ofy().save().entity(i2).now();
		Key<Item> item3 = ObjectifyService.ofy().save().entity(i3).now();
		Key<Item> item4 = ObjectifyService.ofy().save().entity(i4).now();
		Key<Item> item5 = ObjectifyService.ofy().save().entity(i5).now();
		Key<Item> item6 = ObjectifyService.ofy().save().entity(i6).now();
		Key<Item> item7 = ObjectifyService.ofy().save().entity(i7).now();
		Key<Item> item8 = ObjectifyService.ofy().save().entity(i8).now();
		Key<Item> item9 = ObjectifyService.ofy().save().entity(i9).now();
		Key<Item> item10 = ObjectifyService.ofy().save().entity(i10).now();
		Key<Item> item11 = ObjectifyService.ofy().save().entity(i11).now();
		
		int ratingValue = df.getNumberBetween(0, 5);
		ItemRating ir1 = new ItemRating(i1.getId(), user1.getId(), ratingValue);
		i1.updateRatingCount(ratingValue);
		ratingValue = df.getNumberBetween(0, 5);
		ItemRating ir2 = new ItemRating(i1.getId(), user1.getId(), ratingValue);
		i1.updateRatingCount(ratingValue);
		ratingValue = df.getNumberBetween(0, 5);
		ItemRating ir3 = new ItemRating(i1.getId(), user2.getId(), ratingValue);
		i1.updateRatingCount(ratingValue);
		ratingValue = df.getNumberBetween(0, 5);
		ItemRating ir4 = new ItemRating(i3.getId(), user1.getId(), ratingValue);
		i3.updateRatingCount(ratingValue);
		ratingValue = df.getNumberBetween(0, 5);
		ItemRating ir5 = new ItemRating(i3.getId(), user3.getId(), ratingValue);
		i3.updateRatingCount(ratingValue);
		ratingValue = df.getNumberBetween(0, 5);
		ItemRating ir6 = new ItemRating(i4.getId(), user3.getId(), ratingValue);
		i4.updateRatingCount(ratingValue);
		ratingValue = df.getNumberBetween(0, 5);
		ItemRating ir7 = new ItemRating(i5.getId(), user2.getId(), ratingValue);
		i5.updateRatingCount(ratingValue);
		ratingValue = df.getNumberBetween(0, 5);
		ItemRating ir8 = new ItemRating(i5.getId(), user3.getId(), ratingValue);
		i5.updateRatingCount(ratingValue);
		ratingValue = df.getNumberBetween(0, 5);
		ItemRating ir9 = new ItemRating(i6.getId(), user2.getId(), ratingValue);
		i6.updateRatingCount(ratingValue);
		ratingValue = df.getNumberBetween(0, 5);
		ItemRating ir10 = new ItemRating(i6.getId(), user2.getId(), ratingValue);
		i6.updateRatingCount(ratingValue);
		ratingValue = df.getNumberBetween(0, 5);
		ItemRating ir11 = new ItemRating(i6.getId(), user3.getId(), ratingValue);
		i6.updateRatingCount(ratingValue);
		ratingValue = df.getNumberBetween(0, 5);
		ItemRating ir12 = new ItemRating(i6.getId(), user3.getId(), ratingValue);
		i6.updateRatingCount(ratingValue);
		ratingValue = df.getNumberBetween(0, 5);
		ItemRating ir13 = new ItemRating(i7.getId(), user1.getId(), ratingValue);
		i7.updateRatingCount(ratingValue);
		ratingValue = df.getNumberBetween(0, 5);
		ItemRating ir14 = new ItemRating(i7.getId(), user2.getId(), ratingValue);
		i7.updateRatingCount(ratingValue);
		ratingValue = df.getNumberBetween(0, 5);
		ItemRating ir15 = new ItemRating(i8.getId(), user3.getId(), ratingValue);
		i8.updateRatingCount(ratingValue);
		ratingValue = df.getNumberBetween(0, 5);
		ItemRating ir16 = new ItemRating(i9.getId(), user2.getId(), ratingValue);
		i9.updateRatingCount(ratingValue);
		ratingValue = df.getNumberBetween(0, 5);
		ItemRating ir17 = new ItemRating(i9.getId(), user2.getId(), ratingValue);
		i9.updateRatingCount(ratingValue);
		ratingValue = df.getNumberBetween(0, 5);
		ItemRating ir18 = new ItemRating(i10.getId(), user2.getId(), ratingValue);
		i10.updateRatingCount(ratingValue);
		ratingValue = df.getNumberBetween(0, 5);
		ItemRating ir19 = new ItemRating(i10.getId(), user3.getId(), ratingValue);
		i10.updateRatingCount(ratingValue);
		ratingValue = df.getNumberBetween(0, 5);
		ItemRating ir20 = new ItemRating(i11.getId(), user1.getId(), ratingValue);
		i11.updateRatingCount(ratingValue);
		
		ObjectifyService.ofy().save().entity(ir1).now();
		ObjectifyService.ofy().save().entity(ir2).now();
		ObjectifyService.ofy().save().entity(ir3).now();
		ObjectifyService.ofy().save().entity(ir4).now();
		ObjectifyService.ofy().save().entity(ir5).now();
		ObjectifyService.ofy().save().entity(ir6).now();
		ObjectifyService.ofy().save().entity(ir7).now();
		ObjectifyService.ofy().save().entity(ir8).now();
		ObjectifyService.ofy().save().entity(ir9).now();
		ObjectifyService.ofy().save().entity(ir10).now();
		ObjectifyService.ofy().save().entity(ir11).now();
		ObjectifyService.ofy().save().entity(ir12).now();
		ObjectifyService.ofy().save().entity(ir13).now();
		ObjectifyService.ofy().save().entity(ir14).now();
		ObjectifyService.ofy().save().entity(ir15).now();
		ObjectifyService.ofy().save().entity(ir16).now();
		ObjectifyService.ofy().save().entity(ir17).now();
		ObjectifyService.ofy().save().entity(ir18).now();
		ObjectifyService.ofy().save().entity(ir19).now();
		ObjectifyService.ofy().save().entity(ir20).now();
		
		ObjectifyService.ofy().save().entity(i1).now();
		ObjectifyService.ofy().save().entity(i2).now();
		ObjectifyService.ofy().save().entity(i3).now();
		ObjectifyService.ofy().save().entity(i4).now();
		ObjectifyService.ofy().save().entity(i5).now();
		ObjectifyService.ofy().save().entity(i6).now();
		ObjectifyService.ofy().save().entity(i7).now();
		ObjectifyService.ofy().save().entity(i8).now();
		ObjectifyService.ofy().save().entity(i9).now();
		ObjectifyService.ofy().save().entity(i10).now();
		ObjectifyService.ofy().save().entity(i11).now();
		
		u1.addItem(item4);
		u1.addItem(item5);
		u1.addItem(item6);
		u1.addItem(item8);
		u1.addItem(item9);
		u1.addItem(item10);
		u2.addItem(item2);
		u2.addItem(item3);
		u2.addItem(item11);
		u3.addItem(item1);
		u3.addItem(item7);
		
		ObjectifyService.ofy().save().entity(u1).now();
		ObjectifyService.ofy().save().entity(u2).now();
		ObjectifyService.ofy().save().entity(u3).now();
	}
	
	private static String HTML_TEMPLATE = "<html>" + "<head>" + "<title>Welcome to Shero!</title>" + "</head>"
			+ "<body>" + "<h1> Hello #USERNAME#</h1>" + "<p>Welcome to Shero, be a sharing Hero!<p/>" + "</body>"
			+ "</html>";
}

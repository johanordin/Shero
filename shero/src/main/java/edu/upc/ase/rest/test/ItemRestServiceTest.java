//package edu.upc.ase.rest.test;
//
//import static org.junit.Assert.fail;
//
//import org.junit.After;
//import org.junit.Before;
//import org.junit.Test;
//
//import com.google.appengine.tools.development.testing.LocalDatastoreServiceTestConfig;
//import com.google.appengine.tools.development.testing.LocalServiceTestHelper;
//import com.googlecode.objectify.ObjectifyService;
//import com.googlecode.objectify.util.Closeable;
//
//import edu.upc.ase.domain.Address;
//import edu.upc.ase.domain.Availability;
//import edu.upc.ase.domain.Item;
//import edu.upc.ase.domain.ItemRating;
//import edu.upc.ase.domain.User;
//import edu.upc.ase.domain.UserRating;
//import edu.upc.ase.rest.ItemRestService;
//
//public class ItemRestServiceTest {
//	
//	// maximum eventual consistency (see https://cloud.google.com/appengine/docs/java/tools/localunittesting)
//    private final LocalServiceTestHelper helper =
//        new LocalServiceTestHelper(new LocalDatastoreServiceTestConfig()
//            .setDefaultHighRepJobPolicyUnappliedJobPercentage(100));
//
//    private Closeable closeable;
//    
//    ItemRestService itemRestService = new ItemRestService();
//
//    @Before
//    public void setUp() {
//        helper.setUp();
//        //ObjectifyRegistrar.registerDataModel();
//
//        closeable = ObjectifyService.begin();
//        
//		ObjectifyService.register(Item.class);
//		ObjectifyService.register(User.class);
//		ObjectifyService.register(ItemRating.class);
//		ObjectifyService.register(Availability.class);
//		ObjectifyService.register(Address.class);
//		ObjectifyService.register(UserRating.class);
//    }
//
//    @After
//    public void tearDown() {
//        closeable.close();
//
//        helper.tearDown();
//    }
//	
//	@Test
//	public void testGetItems() {
//		testCreateItem();
//		
//		String itemsJson = itemRestService.getItems();
//		System.out.println(itemsJson);
//	}
//
//	@Test
//	public void testGetItem() {
//		String itemJson =  itemRestService.getItem("1");
//		System.out.println(itemJson);
//	}
//
//	@Test
//	public void testCreateItem() {
//		String jsonItem = "{\"name\":\"Junit Test item 2\",\"price\":1232.23}";
//		
//		String jsonResult = itemRestService.createItem(jsonItem);
//		System.out.println(jsonResult);
//		//assertEquals(, "Junit Test item");
//		
//	}
//
//	@Test
//	public void testUpdateItem() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	public void testDeleteItem() {
//		fail("Not yet implemented");
//	}
//
//}

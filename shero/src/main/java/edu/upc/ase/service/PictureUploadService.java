package edu.upc.ase.service;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItemIterator;
import org.apache.commons.fileupload.FileItemStream;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;

import com.google.appengine.api.datastore.Blob;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.ObjectifyService;
import com.googlecode.objectify.Ref;

import edu.upc.ase.domain.Image;
import edu.upc.ase.domain.Item;
import edu.upc.ase.domain.Tag; 

public class PictureUploadService extends HttpServlet {

	/**
	 *  
	 */
	private static Logger logger = Logger.getLogger(PictureUploadService.class);
	
	private static final long serialVersionUID = 1L;

	private boolean isMultipart;

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, java.io.IOException {
		
		logger.warn("doPost Servlet call");
		System.out.println("doPost Servlet call");
		// Check that we have a file upload request
		isMultipart = ServletFileUpload.isMultipartContent(request);

		
		  ServletFileUpload upload = new ServletFileUpload();
          try {
			FileItemIterator iterator = upload.getItemIterator(request);
		    
			String itemId = "";
			
			while (iterator.hasNext()) {
	            FileItemStream item = iterator.next();
	            InputStream stream = item.openStream();
	            
	            if ( item.isFormField() ) {
		          System.out.println("ContentType: " + item.getContentType());
	              System.out.println("Form field : " + item.getFieldName() );
	              
	              // parse the field with itemId
	              if ( item.getFieldName().equals("itemId") ) {
	            	// 
	                BufferedReader reader = new BufferedReader(new InputStreamReader(item.openStream()));
	                StringBuffer res = new StringBuffer(); 
	                String line;
	                while ((line = reader.readLine()) != null) {
	                    res.append(line);
	                }
	                reader.close();
	                
	                System.out.println("imageId: " + res);
	                itemId = res.toString();
//	                
//	                JSONObject jsonObj = new JSONObject(res);
//	                String count = jsonObj.getInt("count");
//	            	System.out.println("-#: " + data);
	            	  
	              }

	              
	            } else {
	              System.out.println("Got an uploaded file: " + item.getFieldName() + ", name = " + item.getName()+ "  content="+item.getContentType() + " header="+item.getHeaders());                  
	              byte[] bytes = IOUtils.toByteArray(stream);
	              
	              System.out.println("File length: " + bytes.length);
	              System.out.println("ItemId     : " + itemId);
	              
	              // Create image and store image in datastore 
	              Image image = new Image(item.getName(), new Blob( bytes));
	              Key<Image> key = ObjectifyService.ofy().save().entity(image).now();
	              
	              // get ImageId from the instance (not needed)
//	              Long imageId = image.getImageId();
//		  		  Key<Image> imageKey = Key.create(Image.class, Long.parseLong(imageId.toString()));
		  		  
				  Ref<Image> imageRef = Ref.create(key);
	              
				  //
	              Item item1 = ObjectifyService.ofy().load().type(Item.class).id(Long.parseLong(itemId)).now();
	              item1.addImage(imageRef); //item1.addImage(Ref.create(imageRef));
	              ObjectifyService.ofy().save().entity(item1).now();
	              
	            }
	          }
		} catch (FileUploadException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			
		} catch (Exception ex) {
			System.out.println(ex);
		}
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, java.io.IOException {

		throw new ServletException("GET method used with " + getClass().getName() + ": POST method required.");
	}
}
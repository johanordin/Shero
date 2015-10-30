package edu.upc.ase.service;

import java.io.InputStream;

import javax.servlet.ServletException;
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

import edu.upc.ase.domain.Image; 

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
		    while (iterator.hasNext()) {
	            FileItemStream item = iterator.next();
	            InputStream stream = item.openStream();
	            
	            if ( item.isFormField() ) {
		          logger.warn("--> " + item.toString());
		          System.out.println("-----> " + item.toString());
		          System.out.println("-----> " + item.getContentType());
	              logger.warn("Got a form field: " + item.getFieldName() + " value=" + item.getName() );
	              System.out.println("Got a form field: " + item.getFieldName() + " value=" + item.getName() );
	              String idForm= item.getFieldName();

	              
	            } else {
	              logger.warn("Got an uploaded file: " + item.getFieldName() +
	                          ", name = " + item.getName()+ "  content="+item.getContentType() + " header="+item.getHeaders());
	              System.out.println("Got an uploaded file: " + item.getFieldName() +
	                          ", name = " + item.getName()+ "  content="+item.getContentType() + " header="+item.getHeaders());
	              // here  save
	              //success = insertFile(String title,String mimeType, String filename, InputStream stream);                  
	              byte[] bytes = IOUtils.toByteArray(stream);
	              System.out.println("File lenght: " + bytes.length);
	              
	              Image image = new Image(item.getName(),new Blob( bytes));
	              Key<Image> key = ObjectifyService.ofy().save().entity(image).now();
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
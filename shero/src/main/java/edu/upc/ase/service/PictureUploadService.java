package edu.upc.ase.service;

// Import required java libraries
import java.io.File;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;

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
	private String filePath;
	private int maxFileSize = 50 * 1024;
	private int maxMemSize = 4 * 1024;

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, java.io.IOException {
		
		logger.warn("doPost Servlet call");
		// Check that we have a file upload request
		isMultipart = ServletFileUpload.isMultipartContent(request);

		try {
			
			  DiskFileItemFactory factory = new DiskFileItemFactory();
		      // maximum size that will be stored in memory
		      factory.setSizeThreshold(maxMemSize);
		      // Location to save data that is larger than maxMemSize.
		      factory.setRepository(new File("c:\\temp"));

		      // Create a new file upload handler
		      ServletFileUpload upload = new ServletFileUpload(factory);
		      
			// Parse the request to get file items.
			List<FileItem> fileItems = upload.parseRequest(request);

			// Process the uploaded file items
			Iterator<FileItem> i = fileItems.iterator();

			while (i.hasNext()) {
				FileItem fi = (FileItem) i.next();
				if (!fi.isFormField()) {
					// Get the uploaded file parameters
					String fieldName = fi.getFieldName();
					String fileName = fi.getName();
					String contentType = fi.getContentType();
					boolean isInMemory = fi.isInMemory();
					long sizeInBytes = fi.getSize();
					
					
					byte[] bytes = IOUtils.toByteArray(fi.getInputStream());
					
					Image newImage = new Image(fieldName,bytes);
					
					Key<Image> key = ObjectifyService.ofy().save().entity(newImage).now();
					Image image = ObjectifyService.ofy().load().type(Image.class).id(key.getId()).now();
				}
			}
			
		      response.setContentType("text/html");
		      java.io.PrintWriter out = response.getWriter( );
		      
		      out.println("<html>");
		      out.println("<head>");
		      out.println("<title>Servlet upload</title>");  
		      out.println("</head>");
		      out.println("<body>");
		      out.println("</body>");
		      out.println("</html>");
		      
		      out.write("Upload successful");
		} catch (Exception ex) {
			System.out.println(ex);
		}
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, java.io.IOException {

		throw new ServletException("GET method used with " + getClass().getName() + ": POST method required.");
	}
}
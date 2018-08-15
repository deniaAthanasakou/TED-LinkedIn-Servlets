package Servlets;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import JavaFiles.AESCrypt;
import database.dao.post.PostDAO;
import database.dao.post.PostDAOImpl;

/**
 * Servlet implementation class PostCreation
 */
@WebServlet("/PostCreation")
public class PostCreation extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private ServletFileUpload uploader = null;
    @Override
    public void init() throws ServletException{
        DiskFileItemFactory fileFactory = new DiskFileItemFactory();
        File filesDir = (File) getServletContext().getAttribute("FILES_DIR_FILE_POSTS");
        fileFactory.setRepository(filesDir);
        this.uploader = new ServletFileUpload(fileFactory);
    }
       
    public PostCreation() {
        super();
        // TODO Auto-generated constructor stub
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("doPost");
		
		PostDAO dao = new PostDAOImpl(true);
		ArrayList<FileItem> files = new ArrayList<FileItem>();
		
		Hashtable<String, String> fields = new Hashtable<String, String>();
		
		boolean hasImages = false;
		boolean hasVideo = false;
		boolean hasAudio = false;
		
		//get fields
		if(!ServletFileUpload.isMultipartContent(request)){
			throw new ServletException("Content type is not multipart/form-data");
		}

		try {
			List<FileItem> fileItemsList = uploader.parseRequest(request);
			Iterator<FileItem> fileItemsIterator = fileItemsList.iterator();
			while(fileItemsIterator.hasNext()){
				FileItem fileItem = fileItemsIterator.next();
				if (fileItem.isFormField()) {
	                // Process regular form field (input type="text|radio|checkbox|etc", select, etc).
					fields.put(fileItem.getFieldName(), fileItem.getString());
	            } else {
					System.out.println("name: " + fileItem.getName() + " " + fileItem.getFieldName());
					//images
					if(fileItem.getFieldName().equals("imagesUpload")) {
						hasImages = true;
					}else if(fileItem.getFieldName().equals("videoUpload")){	//videos
						hasVideo = true;
					}else if(fileItem.getFieldName().equals("audioUpload")){		//audios
						hasAudio = true;
					}
					files.add(fileItem);
	            }
			}
		} catch (FileUploadException e) {
			System.out.println("Exception in uploading file.");
			e.printStackTrace();
		} catch (Exception e) {
			System.out.println("Exception in uploading file.");
			e.printStackTrace();
		}
		
		//get text
		String text = (String) fields.get("text");
		
		System.out.println("Text: " + text);
		
		//get current time
		Date dNow = new Date( );
	    SimpleDateFormat ft = new SimpleDateFormat ("yyyy-MM-dd HH:mm:ss");
	    String currentDate = ft.format(dNow);
	    System.out.println("Current: " + currentDate);
		
		//insert new post to database
		int nextPost = dao.count() + 1;
		//get file names and save them
		for(FileItem file : files) {
			
		}
		/*String fileName = imageItem.getName();
		if (fileName != null) {
			fileName = FilenameUtils.getName(fileName);
		}*/
		//save files
		File idFolder = new File(request.getServletContext().getAttribute("FILES_DIR_POSTS") + File.separator + nextPost);
    	if(!idFolder.exists()) idFolder.mkdirs();
		/*File file = new File(idFolder + File.separator + fileName);
		try {
			imageItem.write(file);
		} catch (Exception e) {
			e.printStackTrace();
		}*/
    	
		String pathFiles = idFolder.getAbsolutePath();
		System.out.println("PathFiles: " + pathFiles);
		
		//encrypt path of files
		pathFiles = AESCrypt.encrypt(pathFiles);
		
		
		//create new post
		/*Post newPost = new Post();
		dao.create(newPost);
		
		//create alert
		
		//go home
		response.sendRedirect(request.getContextPath() + "/jsp_files/home.jsp");*/	
	}

}

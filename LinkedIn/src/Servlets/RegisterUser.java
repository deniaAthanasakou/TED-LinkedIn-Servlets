package Servlets;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.FilenameUtils;

import JavaFiles.AESCrypt;
import JavaFiles.VariousFunctions;

import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;

import database.dao.user.UserDAO;
import database.dao.user.UserDAOImpl;
import database.entities.User;

/**
 * Servlet implementation class RegisterUser
 */
@WebServlet("/RegisterUser")
public class RegisterUser extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	 private ServletFileUpload uploader = null;
     @Override
     public void init() throws ServletException{
         DiskFileItemFactory fileFactory = new DiskFileItemFactory();
         File filesDir = (File) getServletContext().getAttribute("FILES_DIR_FILE_USERS");
         fileFactory.setRepository(filesDir);
         this.uploader = new ServletFileUpload(fileFactory);
         uploader.setHeaderEncoding("UTF-8");
     }

       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RegisterUser() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		UserDAO dao = new UserDAOImpl(true);
		
		FileItem imageItem = null;
		Hashtable<String, String> fields = new Hashtable<String, String>();
		
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
					fields.put(fileItem.getFieldName(), fileItem.getString("UTF-8"));
	            } else {
					imageItem = fileItem;
	            }
			}
		} catch (FileUploadException e) {
			System.out.println("Exception in uploading file.");
			e.printStackTrace();
		} catch (Exception e) {
			System.out.println("Exception in uploading file.");
			e.printStackTrace();
		}
		
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = response.getWriter();
		
		String email = (String) fields.get("email");
		String password = (String) fields.get("password");
		String password2 = (String) fields.get("password2");
		String name = (String) fields.get("name");
		String surname = (String) fields.get("surname");
		String telephone =(String) fields.get("telephone");
		System.out.println("email: " + email);
		
		if(telephone!=null) {
			if(telephone.equals("")) {
				telephone=null;
			}
		}
		
		//check email
		Boolean validMail = VariousFunctions.isValidEmailAddress(email);
		if(!validMail) {
 			out.println("<script type=\"text/javascript\">");
			out.println("alert('Error! Invalid email address was given as input.');");
			out.println("window.history.back()");
			out.println("</script>");
			return;
		}
		
		//check passwords
		if(!password2.equals(password)) {
			out.println("<script type=\"text/javascript\">");
			out.println("alert('Error! Different passwords were given as input.');");
			out.println("window.history.back()");
			out.println("</script>");
			return;
		}
		
		//check phone number
		if(telephone!=null) {	
			telephone=telephone.replaceAll("[\\D]","");
			System.out.println(telephone);
			if(telephone.length()!=10 ) {
				out.println("<script type=\"text/javascript\">");
				out.println("alert('Error! Invalid phone number was given as input.');");
				out.println("window.history.back()");
				out.println("</script>");
				return;
			}
		}
		
		List<User> ulist = dao.list();
		int flagUserExists=0;
		if (ulist != null) {
			for (User user: ulist) {		
				if(user.getEmail().equals(email)) {
					flagUserExists=1;
					break;
				}		
			}
		}
		
		if(flagUserExists==1) {
			out.println("<script type=\"text/javascript\">");
			out.println("alert('Error! User already exists.');");
			out.println("window.history.back()");
			out.println("</script>");
			return;
		}
		else {		//must insert user to database
			int nextUser = dao.count() + 1;
			String fileName = imageItem.getName();
			if (fileName != null) {
				fileName = FilenameUtils.getName(fileName);
			} 
			//save image
			File idFolder = new File(request.getServletContext().getAttribute("FILES_DIR_USERS") + File.separator + nextUser);
	    	if(!idFolder.exists()) idFolder.mkdirs();
			File file = new File(idFolder + File.separator + fileName);
			try {
				imageItem.write(file);
			} catch (Exception e) {
				e.printStackTrace();
			}
			String photoURL = file.getAbsolutePath();
			
			//encrypt password
			password = AESCrypt.encrypt(password);
			//encrypt photoUrl
			photoURL = AESCrypt.encrypt(photoURL);
			
			User newUser = new User(null, null, null, email, 0, 0, name, password, photoURL, surname, telephone,null);
			dao.create(newUser);
			
			//create new session
			request.getSession(true);
			HttpSession session = request.getSession();
			//set values
			session.setAttribute("id",String.valueOf(newUser.getId()));
			session.setAttribute("name",newUser.getName());
			session.setAttribute("surname",newUser.getSurname());
			session.setAttribute("image",AESCrypt.decrypt(newUser.getPhotoURL()));
			//go to home
			response.sendRedirect(request.getContextPath() + "/jsp_files/home.jsp");
		}
	}

}

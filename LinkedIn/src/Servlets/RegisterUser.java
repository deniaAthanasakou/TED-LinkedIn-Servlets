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

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import JavaFiles.VariousFunctions;

import java.util.Date;
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
         File filesDir = (File) getServletContext().getAttribute("FILES_DIR_FILE");
         fileFactory.setRepository(filesDir);
         this.uploader = new ServletFileUpload(fileFactory);
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
		// TODO Auto-generated method stub
		//doGet(request, response);
		
		UserDAO dao = new UserDAOImpl(true);
		
		RequestDispatcher displayPage = getServletContext().getRequestDispatcher("/jsp_files/home.jsp");			//page where new info will be displayed on
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = response.getWriter();
		
		String email = request.getParameter("email");
		String password = request.getParameter("password");
		String password2 = request.getParameter("password2");
		String name = request.getParameter("name");
		String surname = request.getParameter("surname");
		String telephone = request.getParameter("telephone");
		if(telephone!=null) {
			if(telephone.equals("")) {
				telephone=null;
			}
		}
		String photoURL = request.getParameter("imgInp");
		if(photoURL!=null) {
			if(photoURL.equals("")) {
				photoURL=null;
			}
		}
		
		//check email
		Boolean validMail = VariousFunctions.isValidEmailAddress(email);
		if(!validMail) {
 			out.println("<script type=\"text/javascript\">");
			out.println("alert('Error! Invalid email address was given as input.');");
			out.println("location='WelcomePage.jsp';");
			out.println("</script>");
			return;
		}
		
		//check passwords
		if(!password2.equals(password)) {
			request.setAttribute("register", "different passwords");
			displayPage.forward(request, response);
	
			out.println("<script type=\"text/javascript\">");
			out.println("alert('Error! Different passwords were given as input.');");
			out.println("location='WelcomePage.jsp';");
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
				out.println("location='WelcomePage.jsp';");
				out.println("</script>");
				return;
			}
		}
		
		//save image
		/*
		if(!ServletFileUpload.isMultipartContent(request)){
			throw new ServletException("Content type is not multipart/form-data");
		}

		try {
			List<FileItem> fileItemsList = uploader.parseRequest(request);
			Iterator<FileItem> fileItemsIterator = fileItemsList.iterator();
			while(fileItemsIterator.hasNext()){
				FileItem fileItem = fileItemsIterator.next();
				System.out.println("FieldName="+fileItem.getFieldName());
				System.out.println("FileName="+fileItem.getName());
				System.out.println("ContentType="+fileItem.getContentType());
				System.out.println("Size in bytes="+fileItem.getSize());
				File file = new File(request.getServletContext().getAttribute("FILES_DIR")+File.separator+fileItem.getName());
				System.out.println("Absolute Path at server="+file.getAbsolutePath());
				fileItem.write(file);
			}
		} catch (FileUploadException e) {
			System.out.println("Exception in uploading file.");
			e.printStackTrace();
		} catch (Exception e) {
			System.out.println("Exception in uploading file.");
			e.printStackTrace();
		}*/
		
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
			out.println("location='WelcomePage.jsp';");
			out.println("</script>");
			return;
		}
		else {		//must insert user to database
			
			User newUser = new User(null, null, null, email, 0, 0, name, password, photoURL, surname, telephone,null);
			dao.create(newUser);
			
			//request.setAttribute("id", "user with email "+newUser.getEmail() );
		}
		
		displayPage.forward(request, response);
		
	}

}

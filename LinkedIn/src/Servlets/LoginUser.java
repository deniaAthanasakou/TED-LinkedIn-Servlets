package Servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import JavaFiles.VariousFunctions;

import java.util.List;

import database.dao.user.UserDAO;
import database.dao.user.UserDAOImpl;
import database.entities.User;


/**
 * Servlet implementation class LoginUser
 */
@WebServlet("/LoginUser")
public class LoginUser extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String ID_USER_TAG = "id";
	private static final String NAME_USER_TAG = "name";
	private static final String SURNAME_USER_TAG = "surname";
	private static final String IMAGE_USER_TAG = "image";

       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoginUser() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//response.getWriter().append("Served at: ").append(request.getContextPath());
		response.sendRedirect(request.getHeader("WelcomePage.jsp"));
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//doGet(request, response);
		
		UserDAO dao = new UserDAOImpl(true);
		
		//page where user will go after login
		RequestDispatcher displayPage;	

		String email = request.getParameter("email");
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = response.getWriter();
		//check email
		Boolean validMail = VariousFunctions.isValidEmailAddress(email);
		if(!validMail) {
 			out.println("<script type=\"text/javascript\">");
			out.println("alert('Error! Invalid email address was given as input.');");
			out.println("location='WelcomePage.jsp';");
			out.println("</script>");
			return;
		}
		String password = request.getParameter("password");
		
		/*byte[] encryptedPassword=null;
		
		MessageDigest md;
		try {
			md = MessageDigest.getInstance("MD5");
			md.update(password.getBytes());
			encryptedPassword = md.digest();
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		
		
		//System.err.println(email);
		//System.err.println(password);
		
		List<User> ulist = dao.list();
		User loggedInUser=null;
		if (ulist != null) {
			for (User user: ulist) {		
				if(user.getEmail().equals(email) && user.getPassword().equals(password)) {
					loggedInUser=user;
					break;
				}		
			}
		}
		
		if(loggedInUser!=null) {
			request.setAttribute(ID_USER_TAG, loggedInUser.getId());
			request.setAttribute(NAME_USER_TAG, loggedInUser.getName());
			request.setAttribute(SURNAME_USER_TAG, loggedInUser.getSurname());
			request.setAttribute(IMAGE_USER_TAG, loggedInUser.getPhotoURL());
			displayPage = getServletContext().getRequestDispatcher("/jsp_files/home.jsp");		
		}
		else {
			request.setAttribute("loginError", "User doesn't exist.");
			displayPage = getServletContext().getRequestDispatcher("/WelcomePage.jsp");
		}
		
		displayPage.forward(request, response);
		
	}

}

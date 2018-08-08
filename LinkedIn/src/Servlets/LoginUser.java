package Servlets;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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
		
		RequestDispatcher displayPage = getServletContext().getRequestDispatcher("/jsp_files/testLogin.jsp");			//page where new info will be displayed on

		String email = request.getParameter("email");
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
			request.setAttribute("login", "user with id "+loggedInUser.getId());
		}
		else {
			request.setAttribute("login", "user not found");
		}
		
		displayPage.forward(request, response);
		
	}

}

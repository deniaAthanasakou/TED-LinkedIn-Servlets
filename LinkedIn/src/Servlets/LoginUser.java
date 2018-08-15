package Servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import JavaFiles.AESCrypt;
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
		UserDAO dao = new UserDAOImpl(true);
		String email = request.getParameter("email");
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = response.getWriter();
		//check email
		Boolean validMail = VariousFunctions.isValidEmailAddress(email);
		if(!validMail) {
 			out.println("<script type=\"text/javascript\">");
			out.println("alert('Error! Invalid email address was given as input.');");
			out.println("window.history.back()");
			out.println("</script>");
			return;
		}
		String password = request.getParameter("password");
		
		//encrypt password
		password = AESCrypt.encrypt(password);
		
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
			//create new session
			request.getSession(true);
			HttpSession session = request.getSession();
			//set values
			session.setAttribute("id",String.valueOf(loggedInUser.getId()));
			session.setAttribute("name",loggedInUser.getName());
			session.setAttribute("surname",loggedInUser.getSurname());
			session.setAttribute("image",AESCrypt.decrypt(loggedInUser.getPhotoURL()));
			//go to home
			response.sendRedirect(request.getContextPath() + "/jsp_files/home.jsp");
		}
		else {
			request.setAttribute("loginError", "User doesn't exist.");
			RequestDispatcher displayPage = getServletContext().getRequestDispatcher("/WelcomePage.jsp");
			displayPage.forward(request, response);
		}
	}

}

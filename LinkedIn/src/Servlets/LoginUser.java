package Servlets;

import java.io.IOException;


import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import JavaFiles.AESCrypt;
import JavaFiles.VariousFunctions;

import java.util.List;

import database.dao.post.PostDAO;
import database.dao.post.PostDAOImpl;
import database.dao.user.UserDAO;
import database.dao.user.UserDAOImpl;
import database.entities.Post;
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
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {		
		UserDAO dao = new UserDAOImpl(true);
		String email = request.getParameter("email");
		response.setContentType("text/html;charset=UTF-8");
		//check email
		Boolean validMail = VariousFunctions.isValidEmailAddress(email);
		if(!validMail) {
			request.setAttribute("loginError", "Invalid email address was given as input.");
			RequestDispatcher displayPage = getServletContext().getRequestDispatcher("/WelcomePage.jsp");
			displayPage.forward(request, response);
			return;
		}
		String password = request.getParameter("password");
		
		//encrypt password
		password = AESCrypt.encrypt(password);
		
		User loggedInUser = dao.matchUserLogin(email,password);
		
		if(loggedInUser!=null) {
			//create new session
			request.getSession(true);
			HttpSession session = request.getSession();
			//set values
			session.setAttribute("id",String.valueOf(loggedInUser.getId()));
			session.setAttribute("email",loggedInUser.getEmail());
			session.setAttribute("name",loggedInUser.getName());
			session.setAttribute("surname",loggedInUser.getSurname());
			session.setAttribute("image",loggedInUser.getPhotoURL());
			//go to home or admin page
			RequestDispatcher displayPage;
			if(loggedInUser.getIsAdmin()==1) {
				displayPage = getServletContext().getRequestDispatcher("/jsp_files/admin_page.jsp");
			}else {
				displayPage = getServletContext().getRequestDispatcher("/jsp_files/home.jsp");
			} 
			displayPage.forward(request, response);
		}
		else {
			request.setAttribute("loginError", "User doesn't exist.");
			RequestDispatcher displayPage = getServletContext().getRequestDispatcher("/WelcomePage.jsp");
			displayPage.forward(request, response);
		}
	}

}

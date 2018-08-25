package Servlets;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import JavaFiles.VariousFunctions;
import database.dao.user.UserDAO;
import database.dao.user.UserDAOImpl;

/**
 * Servlet implementation class Settings
 */
@WebServlet("/Settings")
public class Settings extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Settings() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		RequestDispatcher displayPage = getServletContext().getRequestDispatcher("/jsp_files/Settings.jsp");
		

		String email = request.getParameter("email");
		String password = request.getParameter("password");
		String password2 = request.getParameter("password2");
		
		System.out.println(email+"!");
		System.out.println(password+"!");
		System.out.println(password+"!");
		
		//check email
		Boolean validMail = VariousFunctions.isValidEmailAddress(email);
		if(!validMail) {

			request.setAttribute("inputError", "Invalid email address was given as input.");
			displayPage.forward(request, response);
			return;
		}
		
		//check passwords
		if(!password2.equals(password)) {
			request.setAttribute("inputError", "Different passwords were given as input.");
			displayPage.forward(request, response);
			return;
		}
		
		//must update database
		int user_id=Integer.valueOf((String) request.getSession().getAttribute("id"));
		UserDAO dao = new UserDAOImpl(true);
		int result = dao.updateSettings(user_id, email, password);
		
		if(result==0) {
			request.setAttribute("updateError", "Oups! Something went wrong.");
		}
		else {
			request.setAttribute("correctUpdate", "done");
		}
		
		displayPage.forward(request, response);
		
		
		
	}

}

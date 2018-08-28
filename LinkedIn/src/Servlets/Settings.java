package Servlets;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import JavaFiles.AESCrypt;
import JavaFiles.VariousFunctions;
import database.dao.user.UserDAO;
import database.dao.user.UserDAOImpl;
import database.entities.User;

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
		
		//return credentials
		System.out.println("again in get");
		
		String displayPage="/jsp_files/Settings.jsp";
		request.setAttribute("redirect", "StopLoop");	
		
			
		UserDAO dao = new UserDAOImpl(true);
		int user_id=Integer.valueOf((String) request.getSession().getAttribute("id"));
		
		User user = dao.find(user_id);
		user.setPassword(AESCrypt.decrypt(user.getPassword()));
		
		System.out.println(user.getPassword()+"!!");
		
		request.setAttribute("user", user);
		
		if(user==null) {
			request.setAttribute("inputError", "Oups! Something went wrong.");
		}
        
		 RequestDispatcher view = request.getRequestDispatcher(displayPage);
	     view.forward(request, response);
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
				request.setAttribute("redirect", "StopLoop");	

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
			doGet(request, response);
			return;
		}
		
		//check passwords
		if(!password2.equals(password)) {
			request.setAttribute("inputError", "Different passwords were given as input.");
			doGet(request, response);
			return;
		}
		
		//must update database
		
		//encrypt password
		password = AESCrypt.encrypt(password);
		
		int user_id=Integer.valueOf((String) request.getSession().getAttribute("id"));
		UserDAO dao = new UserDAOImpl(true);
		int result = dao.updateSettings(user_id, email, password);
		
		if(result==0) {
			request.setAttribute("updateError", "Oups! Something went wrong.");
		}
		else {
			request.setAttribute("correctUpdate", "done");
		}
		
		doGet(request, response);
		
		
		
	}

}

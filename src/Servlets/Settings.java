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

@WebServlet("/Settings")
public class Settings extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    private UserDAO dao = new UserDAOImpl(true);
    
    public Settings() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//show credentials
		String displayPage="/WEB-INF/jsp_files/settings.jsp";
		int user_id=Integer.valueOf((String) request.getSession().getAttribute("id"));
		
		User user = dao.find(user_id);
		user.setPassword(AESCrypt.decrypt(user.getPassword()));
				
		request.setAttribute("user", user);
		
		RequestDispatcher view = request.getRequestDispatcher(displayPage);
	     view.forward(request, response);	
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//change credentials
		String email = request.getParameter("email");
		String password = request.getParameter("password");
		String password2 = request.getParameter("password2");
		
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
		
		//check user exists
		if(!email.equals(request.getSession().getAttribute("email"))) {
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
				request.setAttribute("inputError", "User already exists.");
				doGet(request, response);
				return;
			}
		}
		
		//must update database
		
		//encrypt password
		password = AESCrypt.encrypt(password);
		
		int user_id=Integer.valueOf((String) request.getSession().getAttribute("id"));
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

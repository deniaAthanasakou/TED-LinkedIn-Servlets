package Servlets;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import database.dao.user.UserDAO;
import database.dao.user.UserDAOImpl;
import database.entities.User;

@WebServlet("/PublicProfile")
public class PublicProfile extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    private UserDAO dao = new UserDAOImpl(true);
    
    public PublicProfile() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//show profile of connected user
		String displayPage="/jsp_files/publicProfile.jsp";
		request.setAttribute("redirect", "StopLoop");	
		
		int user_id=Integer.valueOf((String) request.getParameter("id"));
		User user=dao.getUserProfile(user_id);
		request.setAttribute("user", user);

		RequestDispatcher view = request.getRequestDispatcher(displayPage);
	    view.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}

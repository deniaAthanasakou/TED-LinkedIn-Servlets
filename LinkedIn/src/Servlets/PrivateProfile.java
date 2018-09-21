package Servlets;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import database.dao.connection.ConnectionDAO;
import database.dao.connection.ConnectionDAOImpl;
import database.dao.user.UserDAO;
import database.dao.user.UserDAOImpl;
import database.entities.User;

/**
 * Servlet implementation class PrivateProfile
 */
@WebServlet("/PrivateProfile")
public class PrivateProfile extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    private UserDAO dao = new UserDAOImpl(true);
    private ConnectionDAO cnxDao = new ConnectionDAOImpl(true);
    
    public PrivateProfile() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		//show profile of not-connected user
		String displayPage="/jsp_files/privateProfile.jsp";
		request.setAttribute("redirect", "StopLoop");	

		int user_id=Integer.valueOf((String) request.getParameter("id"));
		String pending=(String) request.getParameter("pending");
		String sentRequest=(String) request.getParameter("sentRequest");
		
		request.setAttribute("sentRequest", sentRequest);
		request.setAttribute("pending", pending);

		User user=dao.getUserProfile(user_id);
		request.setAttribute("user", user);
    
		RequestDispatcher view = request.getRequestDispatcher(displayPage);
	    view.forward(request, response);
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
				
		int loggedInUser=Integer.valueOf((String) request.getSession().getAttribute("id"));
		int otherUser=Integer.valueOf((String) request.getParameter("id"));		
		
		request.setAttribute("msg", "Your action was completed successfully.");
		request.setAttribute("redirect", "null");	
		
		request.setAttribute("fromPrivateProfilePost", "notnull");
		
		String displayPage="/jsp_files/network.jsp";
		
		//accept or reject connection
		if (request.getParameter("rejectButton") != null) {
			cnxDao.rejectConnection(loggedInUser, otherUser );
        } 
		else{		//accept
			cnxDao.acceptConnection(loggedInUser, otherUser );
        }
		RequestDispatcher view = request.getRequestDispatcher(displayPage);
 	    view.forward(request, response);
	}

}

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
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public PrivateProfile() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		System.out.println("in profile get");
		
		String displayPage="/jsp_files/privateProfile.jsp";
		request.setAttribute("redirect", "StopLoop");	
			
		UserDAO dao = new UserDAOImpl(true);
		
		
		int user_id=Integer.valueOf((String) request.getParameter("id"));
		String pending=(String) request.getParameter("pending");
		String sentRequest=(String) request.getParameter("sentRequest");
		System.out.println("pending "+ pending);
		User user=dao.getUserProfile(user_id);
		
		request.setAttribute("sentRequest", sentRequest);
		request.setAttribute("pending", pending);
		request.setAttribute("user", user);

    
		RequestDispatcher view = request.getRequestDispatcher(displayPage);
	    view.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		System.out.println("in post");
		
		int loggedInUser=Integer.valueOf((String) request.getSession().getAttribute("id"));
		System.out.println("loggedInUser " + loggedInUser);
		int otherUser=Integer.valueOf((String) request.getParameter("id"));
		System.out.println("otherUser " + otherUser);
		
		ConnectionDAO dao = new ConnectionDAOImpl(true);
		
		if (request.getParameter("rejectButton") != null) {

        } else if (request.getParameter("acceptButton") != null) {
        	 dao.acceptConnection(loggedInUser, otherUser );
        }
		
		request.setAttribute("msg", "Η ενέργειά σας πραγματοποιήθηκε με επιτυχία.");
		
		
		doGet(request, response);
	}

}

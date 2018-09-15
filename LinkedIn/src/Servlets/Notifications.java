package Servlets;

import java.io.IOException;
import java.util.List;

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
 * Servlet implementation class Notifications
 */
@WebServlet("/Notifications")
public class Notifications extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Notifications() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		System.out.println(" in get notifications");
		
		String displayPage="/jsp_files/notifications.jsp";
		request.setAttribute("redirect", "StopLoop");	
			
		//get friend requests pending
		ConnectionDAO dao = new ConnectionDAOImpl(true);
		int user_id=Integer.valueOf((String) request.getSession().getAttribute("id"));
		
		
		List<User> ulist = dao.getConnectionRequestsPending(user_id);
		System.out.println("id is "+ user_id);
		request.setAttribute("usersWithRequests", ulist);
		
		if(ulist==null || ulist.size()==0) {
			request.setAttribute("requests", "noRequests");
		}
		
		//get likes and comments
		UserDAO userDao = new UserDAOImpl(true);
		List<User> usersForPost = userDao.getLikesAndComments(user_id);
		request.setAttribute("usersWithPosts", usersForPost);
		
		if(usersForPost==null || usersForPost.size()==0) {
			request.setAttribute("postNotifications", "noPostNotifications");
		}
        
		RequestDispatcher view = request.getRequestDispatcher(displayPage);
	    view.forward(request, response);
	     
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		System.out.println("in notifications post");
		
		int loggedInUser=Integer.valueOf((String) request.getSession().getAttribute("id"));
		System.out.println("loggedInUser " + loggedInUser);
		int otherUser=Integer.valueOf((String) request.getParameter("id"));
		System.out.println("otherUser " + otherUser);
		
		ConnectionDAO dao = new ConnectionDAOImpl(true);
		
		request.setAttribute("msg", "Η ενέργειά σας πραγματοποιήθηκε με επιτυχία.");
		request.setAttribute("redirect", "null");	
		
		request.setAttribute("fromPrivateProfilePost", "notnull");
		
		String displayPage="/jsp_files/notifications.jsp";
		if (request.getParameter("rejectButton") != null) {
			dao.rejectConnection(loggedInUser, otherUser );
			System.out.println("rejected go to network");
        } 
		else{		//accept
        	dao.acceptConnection(loggedInUser, otherUser );
        	System.out.println("accepted go to network");
        }
		//RequestDispatcher view = request.getRequestDispatcher(displayPage);
 	    //view.forward(request, response);
		doGet(request, response);
	}

}

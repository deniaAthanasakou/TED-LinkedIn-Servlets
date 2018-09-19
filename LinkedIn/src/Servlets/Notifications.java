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
       
    private ConnectionDAO dao = new ConnectionDAOImpl(true);
    private UserDAO userDao = new UserDAOImpl(true);
    
    public Notifications() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
				
		String displayPage="/jsp_files/notifications.jsp";
		request.setAttribute("redirect", "StopLoop");	
			
		//get friend requests pending
		int user_id=Integer.valueOf((String) request.getSession().getAttribute("id"));
		List<User> ulist = dao.getConnectionRequestsPending(user_id);
		request.setAttribute("usersWithRequests", ulist);
		
		if(ulist==null || ulist.size()==0) {
			request.setAttribute("requests", "noRequests");
		}
		
		//get likes and comments
		List<User> usersForPost = userDao.getLikesAndComments(user_id);
		request.setAttribute("usersWithPosts", usersForPost);
		
		if(usersForPost==null || usersForPost.size()==0) {
			request.setAttribute("postNotifications", "noPostNotifications");
		}
        
		RequestDispatcher view = request.getRequestDispatcher(displayPage);
	    view.forward(request, response);	
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {		
		//accept or reject friend request
		int loggedInUser=Integer.valueOf((String) request.getSession().getAttribute("id"));
		int otherUser=Integer.valueOf((String) request.getParameter("id"));
				
		request.setAttribute("msg", "Your action was completed successfully.");
		request.setAttribute("redirect", "null");	
		
		request.setAttribute("fromPrivateProfilePost", "notnull");
	
		if (request.getParameter("rejectButton") != null) {
			dao.rejectConnection(loggedInUser, otherUser );
        } 
		else{		//accept
        	dao.acceptConnection(loggedInUser, otherUser );
        }
		doGet(request, response);
	}

}

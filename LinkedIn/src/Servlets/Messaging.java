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
 * Servlet implementation class Messaging
 */
@WebServlet("/Messaging")
public class Messaging extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Messaging() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		//show last conversation
		
		System.out.println("again in get");
		
		String displayPage="/jsp_files/Messaging.jsp";
		request.setAttribute("redirect", "StopLoop");	
		
			
		ConnectionDAO dao = new ConnectionDAOImpl(true);
		int user_id=Integer.valueOf((String) request.getSession().getAttribute("id"));
		List<User> ulist = dao.getConnections(user_id);		//get all connected users
		System.out.println("id is "+ user_id);
		request.setAttribute("users", ulist);
		
		if(ulist==null || ulist.size()==0) {
			request.setAttribute("getUsers", "noFriends");
		}
		else {
			request.setAttribute("getUsers", "friends");
		}
        
		 RequestDispatcher view = request.getRequestDispatcher(displayPage);
	     view.forward(request, response);
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}

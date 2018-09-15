package Servlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.bouncycastle.crypto.tls.ConnectionEnd;

import JavaFiles.AESCrypt;
import JavaFiles.VariousFunctions;
import database.dao.connection.ConnectionDAOImpl;
import database.dao.connection.ConnectionDAO;
import database.dao.user.UserDAO;
import database.dao.user.UserDAOImpl;
import database.entities.User;

/**
 * Servlet implementation class Network
 */
@WebServlet("/Network")
public class Network extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Network() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		//show connnections
		System.out.println("again in get network");
		
		String displayPage="/jsp_files/network.jsp";
		request.setAttribute("redirect", "StopLoop");	
			
		ConnectionDAO dao = new ConnectionDAOImpl(true);
		int user_id=Integer.valueOf((String) request.getSession().getAttribute("id"));
		List<User> ulist = dao.getConnections(user_id);
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
		//show search results
	
		
		System.out.println(" in post network");
		
	/*	if(request.getParameter("searchIt") != null) {
			System.out.println("searchIt is null");
			//request.setAttribute("fromPrivateProfilePost", "null");
			
			doGet(request, response);
			return;
		}*/
		
		
		
		ConnectionDAO dao = new ConnectionDAOImpl(true);
		
		if (request.getParameter("connect") != null) {
			int user_id1=Integer.valueOf((String) request.getSession().getAttribute("id"));
			int user_id2=Integer.valueOf(request.getParameter("userId"));
			dao.connectToUser(user_id1, user_id2);
            
			request.setAttribute("connectionCompleted", "done");
            doGet(request, response);
            return;
		}
		
		request.setAttribute("fromPrivateProfilePost", "null");
		
		String search = request.getParameter("search");
		String displayPage="/jsp_files/network.jsp";
		
		request.setAttribute("redirect", "StopLoop");	
		System.out.println(search+"!");
		
		
		VariousFunctions vf = new VariousFunctions();   
		
		if(vf.isBlank(search)) {

			request.setAttribute("getUsers", "usersFromSearch");
			request.setAttribute("users", dao.listWithConnectedPendingField(Integer.valueOf((String) request.getSession().getAttribute("id"))));	//getAllUsers with connected or pending field
			
			RequestDispatcher view = request.getRequestDispatcher(displayPage);
		    view.forward(request, response);
			
		    
		    
			System.out.println("null search");
			
		    return;
		}
		
		String[] splitStr = search.trim().split(" ");
		List<User> users=null;
		
		if(splitStr.length==1) {
			System.out.println("one item");
			List<User> users1= dao.searchByName(splitStr[0],Integer.valueOf((String) request.getSession().getAttribute("id")));
			List<User> users2= dao.searchBySurname(splitStr[0],Integer.valueOf((String) request.getSession().getAttribute("id")));
			users = new ArrayList<>();
			users.addAll(users1);
			users.addAll(users2);
		}
		else if(splitStr.length==2) {
			
			System.out.println("two items");
			
			List<User> users1= dao.searchByNameAndSurname(splitStr[0], splitStr[1],Integer.valueOf((String) request.getSession().getAttribute("id")));
			List<User> users2= dao.searchByNameAndSurname(splitStr[1], splitStr[0],Integer.valueOf((String) request.getSession().getAttribute("id")));
			users = new ArrayList<>();
			users.addAll(users1);
			users.addAll(users2);
		}
		
		request.setAttribute("users", users);	
		
		
		if (users != null) {
			for (User user: users) {		
				System.out.println(user.getName() + " " + user.getSurname() + " " + user.getEmail());		
			}
		}
		
		if(users==null || users.size()==0)
			request.setAttribute("getUsers", "noUsersFromSearch");
		else
			request.setAttribute("getUsers", "usersFromSearch");
		
		RequestDispatcher view = request.getRequestDispatcher(displayPage);
	    view.forward(request, response);
	    		
	}

}

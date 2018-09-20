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

import JavaFiles.VariousFunctions;
import database.dao.connection.ConnectionDAOImpl;
import database.dao.connection.ConnectionDAO;
import database.entities.User;

/**
 * Servlet implementation class Network
 */
@WebServlet("/Network")
public class Network extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	private ConnectionDAO dao = new ConnectionDAOImpl(true);

    public Network() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {		
		//show connections		
		String displayPage="/jsp_files/network.jsp";
		request.setAttribute("redirect", "StopLoop");	
			
		
		int user_id=Integer.valueOf((String) request.getSession().getAttribute("id"));
		List<User> ulist = dao.getConnections(user_id);
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
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//show search results		
		if (request.getParameter("connect") != null) {
			int user_id1=Integer.valueOf((String) request.getSession().getAttribute("id"));
			int user_id2=Integer.valueOf((String)request.getParameter("userId"));
			dao.connectToUser(user_id1, user_id2);
            
			request.setAttribute("connectionCompleted", "done");
            doGet(request, response);
            return;
		}
		request.setAttribute("fromPrivateProfilePost", "null");
		
		String search = request.getParameter("search");
		String displayPage="/jsp_files/network.jsp";
		
		request.setAttribute("redirect", "StopLoop");			
		
		VariousFunctions vf = new VariousFunctions();   
		
		//no input in search
		if(vf.isBlank(search)) {

			request.setAttribute("getUsers", "usersFromSearch");
			request.setAttribute("users", dao.listWithConnectedPendingField(Integer.valueOf((String) request.getSession().getAttribute("id"))));	//getAllUsers with connected or pending field
			
			RequestDispatcher view = request.getRequestDispatcher(displayPage);
		    view.forward(request, response);  
					
		    return;
		}
		
		String[] splitStr = search.trim().split(" ");
		List<User> users=null;
		
		//one word was given (either name or surname)
		if(splitStr.length==1) {
			List<User> users1= dao.searchByName(splitStr[0],Integer.valueOf((String) request.getSession().getAttribute("id")));
			List<User> users2= dao.searchBySurname(splitStr[0],Integer.valueOf((String) request.getSession().getAttribute("id")));
			users = new ArrayList<>();
			users.addAll(users1);
			users.addAll(users2);
		}
		else if(splitStr.length==2) { //name and surname were given
						
			List<User> users1= dao.searchByNameAndSurname(splitStr[0], splitStr[1],Integer.valueOf((String) request.getSession().getAttribute("id")));
			List<User> users2= dao.searchByNameAndSurname(splitStr[1], splitStr[0],Integer.valueOf((String) request.getSession().getAttribute("id"))); 	//oposite order
			users = new ArrayList<>();
			users.addAll(users1);
			users.addAll(users2);
		}
		
		request.setAttribute("users", users);	
		
		if(users==null || users.size()==0)
			request.setAttribute("getUsers", "noUsersFromSearch");
		else
			request.setAttribute("getUsers", "usersFromSearch");
		
		RequestDispatcher view = request.getRequestDispatcher(displayPage);
	    view.forward(request, response);
	    		
	}

}

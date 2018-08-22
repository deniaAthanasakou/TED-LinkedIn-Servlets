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

import JavaFiles.AESCrypt;
import JavaFiles.VariousFunctions;
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
		//response.getWriter().append("Served at: ").append(request.getContextPath());
		
		System.out.println("again in get");
		
		String displayPage="/jsp_files/network.jsp";
		String action = request.getParameter("action");
		request.setAttribute("redirect", "StopLoop");	
		
		if (action.equalsIgnoreCase("getUsers")){
			
			UserDAO dao = new UserDAOImpl(true);
			List<User> ulist = dao.list();
			request.setAttribute("users", ulist);	
            
        } else {
        	System.out.println("Error in network.");
        	
        } 
		
		 RequestDispatcher view = request.getRequestDispatcher(displayPage);
	     view.forward(request, response);
		
	}
	

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//doGet(request, response);
		
		System.out.println(" in post");
		
		UserDAO dao = new UserDAOImpl(true);
		String search = request.getParameter("search");
		String displayPage="/jsp_files/network.jsp";
		
		request.setAttribute("redirect", "StopLoop");	
		System.out.println(search+"!");
		
		
		VariousFunctions vf = new VariousFunctions();   
		
		if(vf.isBlank(search)) {

			
			//response.sendRedirect(request.getContextPath() + "/jsp_files/network.jsp");
			request.setAttribute("getUsersFromSearch", "notnull");
			request.setAttribute("users", dao.list());	
			
			RequestDispatcher view = request.getRequestDispatcher(displayPage);
		    view.forward(request, response);
			
		    
		    
			System.out.println("null search");
			
		    return;
		}
		
		String[] splitStr = search.trim().split(" ");
		List<User> users=null;
		
		if(splitStr.length==1) {
			System.out.println("one item");
			List<User> users1= dao.searchByName(splitStr[0]);
			List<User> users2= dao.searchBySurname(splitStr[0]);
			users = new ArrayList<>();
			users.addAll(users1);
			users.addAll(users2);
		}
		else if(splitStr.length==2) {
			
			System.out.println("two items");
			
			List<User> users1= dao.searchByNameAndSurname(splitStr[0], splitStr[1]);
			List<User> users2= dao.searchByNameAndSurname(splitStr[1], splitStr[0]);
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
			request.setAttribute("getUsersFromSearch", "null");
		else
			request.setAttribute("getUsersFromSearch", "notnull");
		
		RequestDispatcher view = request.getRequestDispatcher(displayPage);
	    view.forward(request, response);
	    
	   // response.sendRedirect(request.getContextPath() + "/jsp_files/network.jsp");
		
	}

}

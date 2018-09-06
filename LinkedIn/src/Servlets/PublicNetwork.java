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
 * Servlet implementation class PublicNetwork
 */
@WebServlet("/PublicNetwork")
public class PublicNetwork extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public PublicNetwork() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//show connnections
		System.out.println("again in get");
		
		String displayPage="/jsp_files/publicNetwork.jsp";
		request.setAttribute("redirect", "StopLoop");	
		
			
		ConnectionDAO dao = new ConnectionDAOImpl(true);
		int user_id=Integer.valueOf((String) request.getParameter("id"));
		List<User> ulist = dao.getConnections(user_id);
		
		//check for connections with logged in user
		ulist= dao.existingListWithConnectedField(Integer.valueOf((String) request.getSession().getAttribute("id")), ulist);

		System.out.println("id is "+ user_id);
		request.setAttribute("users", ulist);

	        
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

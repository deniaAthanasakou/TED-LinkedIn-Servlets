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
import database.entities.User;

@WebServlet("/PublicNetwork")
public class PublicNetwork extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    private ConnectionDAO dao = new ConnectionDAOImpl(true);
    
    public PublicNetwork() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//show connnections	of connected user
		String displayPage="/jsp_files/publicNetwork.jsp";
		request.setAttribute("redirect", "StopLoop");	

		int user_id=Integer.valueOf((String) request.getParameter("id"));
		List<User> ulist = dao.getConnections(user_id);
		
		//check for connections with logged in user
		ulist= dao.existingListWithConnectedField(Integer.valueOf((String) request.getSession().getAttribute("id")), ulist);

		request.setAttribute("users", ulist);
	        
		RequestDispatcher view = request.getRequestDispatcher(displayPage);
	    view.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}

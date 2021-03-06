package Servlets;

import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import database.dao.user.UserDAO;
import database.dao.user.UserDAOImpl;

@WebServlet("/ListUsers")
public class ListUsers extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
	private UserDAO dao = new UserDAOImpl(true);
	
    public ListUsers() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		if(request.getParameter("action")!=null) {
			if(request.getParameter("action").equals("getUsers")) {
				request.setAttribute("users", dao.list());
				
				//display page
				RequestDispatcher displayPage = getServletContext().getRequestDispatcher("/WEB-INF/jsp_files/admin_page.jsp");
				displayPage.forward(request, response);
				return;
			}
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if(request.getParameter("action")!=null) {
			if(request.getParameter("action").equals("getUsers")) {
				request.setAttribute("users", dao.list());
				
				//display page
				RequestDispatcher displayPage = getServletContext().getRequestDispatcher("/WEB-INF/jsp_files/admin_page.jsp");
				displayPage.forward(request, response);
				return;
			}
		}
	}

}

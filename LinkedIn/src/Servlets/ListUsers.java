package Servlets;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import database.dao.post.PostDAO;
import database.dao.post.PostDAOImpl;
import database.dao.user.UserDAO;
import database.dao.user.UserDAOImpl;

/**
 * Servlet implementation class ListUsers
 */
@WebServlet("/ListUsers")
public class ListUsers extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public ListUsers() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		UserDAO dao = new UserDAOImpl(true);
		if(request.getParameter("action")!=null) {
			if(request.getParameter("action").equals("getUsers")) {
				request.setAttribute("redirectList", "StopLoopList");
				request.setAttribute("users", dao.list());
				//display page
				RequestDispatcher displayPage = getServletContext().getRequestDispatcher("/jsp_files/admin_page.jsp");
				displayPage.forward(request, response);
				return;
			}
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		UserDAO dao = new UserDAOImpl(true);
		if(request.getParameter("action")!=null) {
			if(request.getParameter("action").equals("getUsers")) {
				request.setAttribute("redirectList", "StopLoopList");
				request.setAttribute("users", dao.list());
				//display page
				RequestDispatcher displayPage = getServletContext().getRequestDispatcher("/jsp_files/admin_page.jsp");
				displayPage.forward(request, response);
				return;
			}
		}
	}

}

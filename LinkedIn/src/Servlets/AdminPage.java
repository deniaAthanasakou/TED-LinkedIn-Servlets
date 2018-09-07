package Servlets;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import JavaFiles.VariousFunctions;
import database.dao.user.UserDAO;
import database.dao.user.UserDAOImpl;
import database.entities.User;

/**
 * Servlet implementation class AdminPage
 */
@WebServlet("/AdminPage")
public class AdminPage extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AdminPage() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String actionDownload = request.getParameter("actionDownload");
		String actionProfile = request.getParameter("actionProfile");

		if (actionProfile != null) {
			int id = Integer.valueOf(actionProfile);
		    RequestDispatcher displayPage = getServletContext().getRequestDispatcher("/Profile?fromAdmin=" + id);
			displayPage.forward(request, response);
			return;
		} else if (actionDownload != null) {
			String[] selectedUserIds = request.getParameterValues("selected");
			if(selectedUserIds != null) {
				for(int i=0;i<selectedUserIds.length;i++) {
					System.out.println(selectedUserIds[i]);
				}
				//get selected users
				UserDAO dao = new UserDAOImpl(true);
				List<User> users = dao.getSelectedUsers(selectedUserIds);
				System.out.println(users.get(0).getName());
				//generate xml file
				String xml = VariousFunctions.generateXML(users);
				response.setContentType("text/xml");
			    response.setHeader("Content-Disposition", "attachment; filename=\"userXMLs.xml\"");
				OutputStream out = response.getOutputStream();
				out.write(xml.getBytes());
				out.flush();
				out.close();
			}else {
				request.setAttribute("noUsersSelected", "You didn't select any users for download.");
				RequestDispatcher displayPage = getServletContext().getRequestDispatcher("/jsp_files/admin_page.jsp");
				displayPage.forward(request, response);
			}
			return;
		}
	}

}

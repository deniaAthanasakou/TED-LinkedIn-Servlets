package Servlets;

import java.io.IOException;
import java.util.Calendar;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import database.dao.user.UserDAO;
import database.dao.user.UserDAOImpl;
import database.entities.User;

@WebServlet("/Profile")
public class Profile extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    private UserDAO dao = new UserDAOImpl(true);
    
    public Profile() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//show logged-in user's profile
		String displayPage="/jsp_files/profile.jsp";
		request.setAttribute("redirect", "StopLoop");	
		int user_id=Integer.valueOf((String) request.getSession().getAttribute("id"));

		User user=dao.getUserProfile(user_id);
		request.setAttribute("user", user);
		RequestDispatcher view = request.getRequestDispatcher(displayPage);
	    view.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//edit profile	
		String displayPage = null;
		
		String fromAdminId = request.getParameter("fromAdmin");
		int user_id = 0;
		if(fromAdminId == null) {
			user_id=Integer.valueOf((String) request.getSession().getAttribute("id"));
			displayPage="/jsp_files/edit_profile.jsp";
		}else {
			user_id=Integer.valueOf((String) fromAdminId);
			displayPage="/jsp_files/edit_profile_admin.jsp";
		}
		
		User user=dao.getUserProfile(user_id);
		request.setAttribute("user", user);
		
		int day=1;
		int month=1;
		int year=2018;
		
		if(user.getDateOfBirth()!=null) {
			Calendar cal = Calendar.getInstance(); 
			cal.setTime(user.getDateOfBirth());
			
			day=cal.get(Calendar.DAY_OF_MONTH);
			month=cal.get(Calendar.MONTH)+1;		//zero based
			year=cal.get(Calendar.YEAR);	 
		}
		
		request.setAttribute("day", day);
		request.setAttribute("month",month );
		request.setAttribute("year", year);
		
		RequestDispatcher view = request.getRequestDispatcher(displayPage);
		view.forward(request, response);
		
	}

}

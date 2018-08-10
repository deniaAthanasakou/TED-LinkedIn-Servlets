package Servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.util.List;

import database.dao.user.UserDAO;
import database.dao.user.UserDAOImpl;
import database.entities.User;

import JavaFiles.VariusFunctions;

/**
 * Servlet implementation class RegisterUser
 */
@WebServlet("/RegisterUser")
public class RegisterUser extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RegisterUser() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//doGet(request, response);
		
		UserDAO dao = new UserDAOImpl(true);
		
		RequestDispatcher displayPage = getServletContext().getRequestDispatcher("/jsp_files/testRegister.jsp");	//page where new info will be displayed on
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = response.getWriter();
		
		
		String email = request.getParameter("email");
		String password = request.getParameter("password");
		String password2 = request.getParameter("password2");
		String name = request.getParameter("name");
		String surname = request.getParameter("surname");
		String telephone = request.getParameter("telephone");
		if(telephone.equals("")) {
			telephone=null;
		}
		String photoURL = request.getParameter("imgInp");
		if(photoURL!=null) {
			if(photoURL.equals("")) {
				photoURL=null;
			}
		}
		
		//check email
		Boolean validMail = VariusFunctions.isValidEmailAddress(email);
		if(!validMail) {

			out.println("<script type=\"text/javascript\">");
			out.println("alert('Error! Invalid email address was given as input.');");
			out.println("location='WelcomePage.jsp';");
			out.println("</script>");
			return;
		}
		
		
		//check passwords
		if(!password2.equals(password)) {
	
			out.println("<script type=\"text/javascript\">");
			out.println("alert('Error! Different passwords were given as input.');");
			out.println("location='WelcomePage.jsp';");
			out.println("</script>");
			return;
		}
		
		//check phone number
		if(telephone!=null) {	
			telephone=telephone.replaceAll("[\\D]","");
			System.out.println(telephone);
			if(telephone.length()!=10 ) {
				
				out.println("<script type=\"text/javascript\">");
				out.println("alert('Error! Invalid phone number was given as input.');");
				out.println("location='WelcomePage.jsp';");
				out.println("</script>");
		
				return;
			}
		}
		
		List<User> ulist = dao.list();
		int flagUserExists=0;
		if (ulist != null) {
			for (User user: ulist) {		
				if(user.getEmail().equals(email)) {
					flagUserExists=1;
					break;
				}		
			}
		}
		
		if(flagUserExists==1) {
			
			out.println("<script type=\"text/javascript\">");
			out.println("alert('Error! User already exists.');");
			out.println("location='WelcomePage.jsp';");
			out.println("</script>");
	
			return;
		}
		else {		//must insert user to database
			
			User newUser = new User(null, null, null, email, 0, 0, name, password, photoURL, surname, telephone);
			dao.create(newUser);
			
			request.setAttribute("register", "user with email "+newUser.getEmail() );
		}
		
		displayPage.forward(request, response);
		
	}
	
	

}

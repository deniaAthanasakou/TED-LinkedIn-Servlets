package Servlets;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import database.dao.conversation.ConversationDAO;
import database.dao.conversation.ConversationDAOImpl;
import database.entities.Conversation;

/**
 * Servlet implementation class Conversation
 */
@WebServlet("/ConversationHandler")
public class ConversationHandler extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private ConversationDAO dao = new ConversationDAOImpl(true);
       
    public ConversationHandler() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("Go to conversation " + request.getParameter("action"));
		if(request.getParameter("action") != null) {
			if(request.getParameter("action").equals("conversation")) {
				//get conversation if exists, else create
				Long idClicked = Long.valueOf((String) request.getParameter("id"));
				Long sessionId = Long.valueOf((String) request.getSession().getAttribute("id"));
				Conversation checkConv = dao.findConversation(sessionId, idClicked);
				if(checkConv == null) {
					System.out.println("Conversation doesnt exist ->  create");
					dao.create(sessionId, idClicked);
					checkConv = dao.findConversation(sessionId, idClicked);
				}else {
					System.out.println("Conversation exist");
					checkConv = dao.findConversation(sessionId, idClicked);
				}
				//display page
				request.setAttribute("conversation", checkConv);
				RequestDispatcher displayPage = getServletContext().getRequestDispatcher("/jsp_files/Messaging.jsp");
				displayPage.forward(request, response);
				return;
			}
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

	}

}

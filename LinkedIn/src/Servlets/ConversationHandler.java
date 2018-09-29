package Servlets;

import java.io.IOException;
import java.util.Date;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import database.dao.conversation.ConversationDAO;
import database.dao.conversation.ConversationDAOImpl;
import database.entities.Conversation;

@WebServlet("/ConversationHandler")
public class ConversationHandler extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private ConversationDAO dao = new ConversationDAOImpl(true);
       
    public ConversationHandler() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if(request.getParameter("action") != null) {
			if(request.getParameter("action").equals("conversation")) {
				//get conversation if exists, else create
				int idClicked = Integer.valueOf((String) request.getParameter("id"));
				int sessionId = Integer.valueOf((String) request.getSession().getAttribute("id"));
				Conversation checkConv = dao.findConversation(sessionId, idClicked);
				if(checkConv == null) {
					dao.create(sessionId, idClicked, new Date());
					checkConv = dao.findConversation(sessionId, idClicked);
				}else {
					checkConv = dao.findConversation(sessionId, idClicked);
				}
				//display page
				response.sendRedirect(request.getContextPath() + "/MessageHandler?action=getMessages&user1="+ checkConv.getId().getUserId1() + "&user2=" + checkConv.getId().getUserId2());
				return;
			}
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

	}

}

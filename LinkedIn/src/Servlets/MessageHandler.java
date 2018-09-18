package Servlets;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import database.dao.conversation.ConversationDAO;
import database.dao.conversation.ConversationDAOImpl;
import database.dao.message.MessageDAO;
import database.dao.message.MessageDAOImpl;
import database.entities.Conversation;
import database.entities.ConversationPK;
import database.entities.Message;


@WebServlet("/MessageHandler")
public class MessageHandler extends HttpServlet {
	private static final long serialVersionUID = 1L;
  
	private MessageDAO dao = new MessageDAOImpl(true);
	private ConversationDAO connDAO = new ConversationDAOImpl(true);
	
    public MessageHandler() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//get to conversations
		if(request.getParameter("action") != null) {
			List<Conversation> conversations = connDAO.findAllConversations(Long.valueOf((String) request.getSession().getAttribute("id")));
			request.setAttribute("conversations", conversations);
			if(request.getParameter("action").equals("getMessages")) {
				request.setAttribute("redirect", "stopRedirectMessages");
				String user1 = request.getParameter("user1");
				String user2 = request.getParameter("user2");
				System.out.println("get specific conversation with: !" +user1+ "! !" + user2 + "!");
				if(user1.equals("")) {
					user1 = null;
				}
				if(user2.equals("")) {
					user2 = null;
				}
				if(user1 != null && user2!= null) {
					//get specific conversation
					Long userId1 = Long.valueOf((String)request.getParameter("user1"));
					Long userId2 = Long.valueOf((String)request.getParameter("user2"));
					System.out.println("get specific conversation with: " + userId1 + " " + userId2);
					//get conversation
					Conversation checkConv = connDAO.findConversation(userId1, userId2);
					request.setAttribute("conversation", checkConv);
					request.setAttribute("getSpecific", "get");
				}else {
					//just get last message and last conversation
					System.out.println("get last message and conversation.");
				}
			}
			RequestDispatcher displayPage = getServletContext().getRequestDispatcher("/jsp_files/Messaging.jsp");
			displayPage.forward(request, response);
			return;
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//post new message
		request.setCharacterEncoding("UTF-8");
		Long userId1 = Long.valueOf((String)request.getParameter("userId1"));
		Long userId2 = Long.valueOf((String)request.getParameter("userId2"));
		String text = request.getParameter("message");
		//create message
		System.out.println("create message");
		Message message = new Message();
		message.setText(text);
		message.setDate(new Date());
		Long sessionId = Long.valueOf((String) request.getSession().getAttribute("id"));
		byte sender;
		if(sessionId == userId1) {
			sender = 0;
		}else {
			sender = 1;
		}
		message.setSender(sender);
		Conversation conversation = new Conversation();
		ConversationPK id = new ConversationPK();
		id.setUserId1(userId1.intValue());
		id.setUserId2(userId2.intValue());
		conversation.setId(id);
		message.setConversation(conversation);
		//insert to db
		dao.create(message);
		//display page
		//get conversation
		Conversation checkConv = connDAO.findConversation(userId1, userId2);
		request.setAttribute("conversation", checkConv);
		request.setAttribute("getSpecific", "get");
		RequestDispatcher displayPage = getServletContext().getRequestDispatcher("/jsp_files/Messaging.jsp");
		displayPage.forward(request, response);
		return;
	}

}

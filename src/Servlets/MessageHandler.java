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
			List<Conversation> conversations = connDAO.findAllConversations(Integer.valueOf((String) request.getSession().getAttribute("id")));
			request.setAttribute("conversations", conversations);
			if(conversations.size() == 0) {
				request.setAttribute("noConversations", "not empty");
			}
			if(request.getParameter("action").equals("getMessages")) {
				request.setAttribute("redirect", "stopRedirectMessages");
				String user1 = request.getParameter("user1");
				String user2 = request.getParameter("user2");
				if(user1.equals("")) {
					user1 = null;
				}
				if(user2.equals("")) {
					user2 = null;
				}
				if(user1 != null && user2!= null) {
					//get specific conversation
					int userId1 = Integer.valueOf((String)request.getParameter("user1"));
					int userId2 = Integer.valueOf((String)request.getParameter("user2"));
					
					//find position in all conversations
					for(int i=0;i<conversations.size();i++) {
						if(userId1 == conversations.get(i).getId().getUserId1() && userId2 == conversations.get(i).getId().getUserId2()) {
							request.setAttribute("pressedConversation",i);
						}
					}
					//get conversation
					Conversation checkConv = connDAO.findConversation(userId1, userId2);
					request.setAttribute("conversation", checkConv);
					request.setAttribute("getSpecific", "get");
				}else {
					//just get last message and last conversation
					if(conversations.size() > 0) {
						request.setAttribute("conversation", conversations.get(0));
					}
					request.setAttribute("getSpecific", "get");
					request.setAttribute("pressedConversation",0);
				}
			}
			RequestDispatcher displayPage = getServletContext().getRequestDispatcher("/jsp_files/messaging.jsp");
			displayPage.forward(request, response);
			return;
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//post new message
		request.setCharacterEncoding("UTF-8");
		int userId1 = Integer.valueOf((String)request.getParameter("userId1"));
		int userId2 = Integer.valueOf((String)request.getParameter("userId2"));
		String text = request.getParameter("message");
		Date dNow = new Date();
		
		//create message
		Message message = new Message();
		message.setText(text);
		message.setDate(dNow);
		int sessionId = Integer.valueOf((String) request.getSession().getAttribute("id"));
		byte sender;
		if(sessionId == userId1) {
			sender = 0;
		}else {
			sender = 1;
		}
		message.setSender(sender);
		Conversation conversation = new Conversation();
		ConversationPK id = new ConversationPK();
		id.setUserId1(userId1);
		id.setUserId2(userId2);
		conversation.setId(id);
		message.setConversation(conversation);
		
		//insert to db
		dao.create(message);
		
		//update conversation
		connDAO.updateLastDate(dNow, userId1, userId2);
		
		//get conversation
		Conversation checkConv = connDAO.findConversation(userId1, userId2);
		request.setAttribute("conversation", checkConv);
		
		//get all conversations
		List<Conversation> conversations = connDAO.findAllConversations(Integer.valueOf((String) request.getSession().getAttribute("id")));
		request.setAttribute("conversations", conversations);
		
		//find position in all conversations
		for(int i=0;i<conversations.size();i++) {
			if(userId1 == conversations.get(i).getId().getUserId1() && userId2 == conversations.get(i).getId().getUserId2()) {
				request.setAttribute("pressedConversation",i);
			}
		}
		request.setAttribute("getSpecific", "get");
		
		//display page
		RequestDispatcher displayPage = getServletContext().getRequestDispatcher("/jsp_files/messaging.jsp");
		displayPage.forward(request, response);
		return;
	}

}

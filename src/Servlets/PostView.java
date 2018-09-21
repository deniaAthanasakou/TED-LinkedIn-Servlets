package Servlets;

import java.io.IOException;
import java.util.Date;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import database.dao.comment.CommentDAO;
import database.dao.comment.CommentDAOImpl;
import database.dao.like.LikeDAO;
import database.dao.like.LikeDAOImpl;
import database.dao.post.PostDAO;
import database.dao.post.PostDAOImpl;
import database.entities.Comment;
import database.entities.Post;

@WebServlet("/PostView")
public class PostView extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    private CommentDAO commentsDao = new CommentDAOImpl(true);
    private PostDAO dao = new PostDAOImpl(true);
    private LikeDAO likeDao = new LikeDAOImpl(true);
    
    public PostView() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String displayPage="/jsp_files/postPage.jsp";
		request.setAttribute("redirect", "StopLoop");	
		
		int user_id=Integer.valueOf((String) request.getSession().getAttribute("id"));
		int post_id=Integer.valueOf((String) request.getParameter("post_id"));
		
		Post post=dao.getPost(post_id);
		request.setAttribute("post", post);
		
		//get if liked from user
		post.setLiked(likeDao.checkLiked(user_id,post.getId()));
		request.setAttribute("post", post);
		RequestDispatcher view = request.getRequestDispatcher(displayPage);
	    view.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setAttribute("redirect", "null");	
		
		//get post id
		int postId = Integer.valueOf(request.getParameter("post_id"));
		
		if(request.getParameter("action")!=null) {
			if(request.getParameter("action").equals("insertLike")) {
				//insert like
				likeDao.insertLike(Integer.valueOf((String) request.getSession().getAttribute("id")),postId);
			}else if(request.getParameter("action").equals("deleteLike")) {
				//delete like
				likeDao.deleteLike(Integer.valueOf((String) request.getSession().getAttribute("id")),postId);
			}else if(request.getParameter("action").equals("comment")) {
				
				//get text
				String text = request.getParameter("comment");
				if(text.equals("")) {
					text = null;
				}
				if(text == null) {
					request.setAttribute("commentError", "Your comment is empty.");
					doGet(request, response);
					return;
				}
				//get current time
				Date dNow = new Date();
				
				//get user id
				int userId = Integer.valueOf((String) request.getSession().getAttribute("id"));
				
				//create comment
				Comment comment = new Comment();
				comment.setDatePosted(dNow);
				comment.setText(text);
				
				commentsDao.create(comment,userId,postId);
			}
		}
		doGet(request, response);
	}

}

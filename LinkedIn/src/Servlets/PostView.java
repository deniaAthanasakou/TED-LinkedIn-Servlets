package Servlets;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import JavaFiles.AESCrypt;
import JavaFiles.VariousFunctions;
import database.dao.comment.CommentDAO;
import database.dao.comment.CommentDAOImpl;
import database.dao.connection.ConnectionDAO;
import database.dao.connection.ConnectionDAOImpl;
import database.dao.post.PostDAO;
import database.dao.post.PostDAOImpl;
import database.dao.user.UserDAO;
import database.dao.user.UserDAOImpl;
import database.entities.Comment;
import database.entities.Post;
import database.entities.User;

/**
 * Servlet implementation class PostView
 */
@WebServlet("/PostView")
public class PostView extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public PostView() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		System.out.println("in postView get");
		
		String displayPage="/jsp_files/postPage.jsp";
		request.setAttribute("redirect", "StopLoop");	
		
		int user_id=Integer.valueOf((String) request.getSession().getAttribute("id"));
			
		PostDAO dao = new PostDAOImpl(true);
		
		int post_id=Integer.valueOf((String) request.getParameter("post_id"));
		
		Post post=dao.getPost(post_id);
		
		request.setAttribute("post", post);
		
		//get & set comments & edit post
		CommentDAO commentsDao = new CommentDAOImpl(true);

		//set comments
		List<Comment> comments = commentsDao.findComments((long)post_id);
		//set Date interval in specific format for comments and specific user
		for(Comment comment: comments) {
			comment.setDateInterval(VariousFunctions.getDateInterval(comment.getDatePosted()));
		}
		post.setComments(comments);
		//set no of comments
		post.setNoComments(comments.size());
		//set Date interval in specific format
		post.setDateInterval(VariousFunctions.getDateInterval(post.getDatePosted()));
		//decrypt path and set lists of images,videos,audios
		if(post.getPathFiles() != null) {
			String folderPath = AESCrypt.decrypt(post.getPathFiles());
			VariousFunctions.setFilePathsFromFolders(folderPath,post);
		}
		//set likes
		post.setLikes(dao.countLikes(Long.valueOf(post.getId())));
		//get if liked from user
		post.setLiked(dao.checkLiked(Long.valueOf(user_id), Long.valueOf(post.getId())));
	
		request.setAttribute("post", post);

		RequestDispatcher view = request.getRequestDispatcher(displayPage);
	    view.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}

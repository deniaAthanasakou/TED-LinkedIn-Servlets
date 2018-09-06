package database.entities;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the post database table.
 * 
 */
@Entity
@NamedQuery(name="Post.findAll", query="SELECT p FROM Post p")
public class Post implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private int id;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="date_posted")
	private Date datePosted;

	private byte hasAudio;

	private byte hasImages;

	private byte hasVideos;

	private int likes;

	@Column(name="path_files")
	private String pathFiles;

	private String text;

	//bi-directional many-to-one association to Comment
	@OneToMany(mappedBy="post")
	private List<Comment> comments;

	//bi-directional many-to-one association to User
	@ManyToOne
	private User user;

	//bi-directional many-to-many association to User
	@ManyToMany(mappedBy="posts2")
	private List<User> users;

	public Post() {
	}
	
	public Post(String text, Date datePosted, String pathFiles, byte hasAudio, byte hasImages, byte hasVideos, int likes, User user) {
		super();
		this.text = text;
		this.datePosted = datePosted;
		this.hasAudio = hasAudio;
		this.hasImages = hasImages;
		this.hasVideos = hasVideos;
		this.pathFiles = pathFiles;
		this.user = user;
		this.likes = likes;
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Date getDatePosted() {
		return this.datePosted;
	}

	public void setDatePosted(Date datePosted) {
		this.datePosted = datePosted;
	}

	public byte getHasAudio() {
		return this.hasAudio;
	}

	public void setHasAudio(byte hasAudio) {
		this.hasAudio = hasAudio;
	}

	public byte getHasImages() {
		return this.hasImages;
	}

	public void setHasImages(byte hasImages) {
		this.hasImages = hasImages;
	}

	public byte getHasVideos() {
		return this.hasVideos;
	}

	public void setHasVideos(byte hasVideos) {
		this.hasVideos = hasVideos;
	}

	public int getLikes() {
		return this.likes;
	}

	public void setLikes(int likes) {
		this.likes = likes;
	}

	public String getPathFiles() {
		return this.pathFiles;
	}

	public void setPathFiles(String pathFiles) {
		this.pathFiles = pathFiles;
	}

	public String getText() {
		return this.text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public List<Comment> getComments() {
		return this.comments;
	}

	public void setComments(List<Comment> comments) {
		this.comments = comments;
	}

	public Comment addComment(Comment comment) {
		getComments().add(comment);
		comment.setPost(this);

		return comment;
	}

	public Comment removeComment(Comment comment) {
		getComments().remove(comment);
		comment.setPost(null);

		return comment;
	}

	public User getUser() {
		return this.user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public List<User> getUsers() {
		return this.users;
	}

	public void setUsers(List<User> users) {
		this.users = users;
	}
	
	//local fields for populate
		@Transient
		private int noComments;

		public int getNoComments() {
			return noComments;
		}

		public void setNoComments(int noComments) {
			this.noComments = noComments;
		}
		
		@Transient
		private String dateInterval;

		public String getDateInterval() {
			return dateInterval;
		}

		public void setDateInterval(String dateInterval) {
			this.dateInterval = dateInterval;
		}
		
		
		@Transient
		private List<String> listImages;
		@Transient
		private List<String> listVideos;
		@Transient
		private List<String> listAudios;

		public List<String> getListImages() {
			return listImages;
		}

		public void setListImages(List<String> listImages) {
			this.listImages = listImages;
		}

		public List<String> getListVideos() {
			return listVideos;
		}

		public void setListVideos(List<String> listVideos) {
			this.listVideos = listVideos;
		}

		public List<String> getListAudios() {
			return listAudios;
		}

		public void setListAudios(List<String> listAudios) {
			this.listAudios = listAudios;
		}
		
		@Transient
		private List<String> listAudiosNames;

		public List<String> getListAudiosNames() {
			return listAudiosNames;
		}

		public void setListAudiosNames(List<String> listAudiosNames) {
			this.listAudiosNames = listAudiosNames;
		}	
		
		@Transient
		private int liked;

		public int getLiked() {
			return liked;
		}

		public void setLiked(int liked) {
			this.liked = liked;
		}
		

}
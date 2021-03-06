package database.entities;

import java.io.Serializable;
import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

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

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="date_posted")
	private Date datePosted;

	@JsonIgnore
	private byte hasAudio;

	@JsonIgnore
	private byte hasImages;

	@JsonIgnore
	private byte hasVideos;

	@JsonIgnore
	@Column(name="path_files")
	private String pathFiles;

	private String text;

	//bi-directional many-to-one association to Comment
	@JacksonXmlProperty(localName = "Comment")
	@JacksonXmlElementWrapper(localName = "Comments")
	@OneToMany(mappedBy="post")
	private List<Comment> comments;

	//bi-directional many-to-one association to Like
	@JacksonXmlProperty(localName = "Like")
	@JacksonXmlElementWrapper(localName = "Likes")
	@OneToMany(mappedBy="post")
	private List<Like> likesSet;

	//bi-directional many-to-one association to User
	@JsonIgnore
	@ManyToOne
	private User user;

	//bi-directional many-to-many association to User
	@JsonIgnore
	@ManyToMany(mappedBy="posts2")
	private List<User> users;

	public Post() {
	}
	
	public Post(String text, Date datePosted, String pathFiles, byte hasAudio, byte hasImages, byte hasVideos, User user) {
		super();
		this.text = text;
		this.datePosted = datePosted;
		this.hasAudio = hasAudio;
		this.hasImages = hasImages;
		this.hasVideos = hasVideos;
		this.pathFiles = pathFiles;
		this.user = user;
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

	public List<Like> getLikesSet() {
		return this.likesSet;
	}

	public void setLikesSet(List<Like> likesSet) {
		this.likesSet = likesSet;
	}

	public Like addLikesSet(Like likesSet) {
		getLikesSet().add(likesSet);
		likesSet.setPost(this);

		return likesSet;
	}

	public Like removeLikesSet(Like likesSet) {
		getLikesSet().remove(likesSet);
		likesSet.setPost(null);

		return likesSet;
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
	
	@Transient
	private int noComments;

	public int getNoComments() {
		return noComments;
	}

	public void setNoComments(int noComments) {
		this.noComments = noComments;
	}

	@JsonIgnore
	@Transient
	private String dateInterval;

	public String getDateInterval() {
		return dateInterval;
	}

	public void setDateInterval(String dateInterval) {
		this.dateInterval = dateInterval;
	}


	@JacksonXmlProperty(localName = "listImage")
	@JacksonXmlElementWrapper(localName = "listImages")
	@Transient
	private List<String> listImages;
	
	@JacksonXmlProperty(localName = "listVideo")
	@JacksonXmlElementWrapper(localName = "listVideos")
	@Transient
	private List<String> listVideos;
	
	@JacksonXmlProperty(localName = "listAudio")
	@JacksonXmlElementWrapper(localName = "listAudios")
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

	@JsonIgnore
	@Transient
	private List<String> listAudiosNames;

	public List<String> getListAudiosNames() {
		return listAudiosNames;
	}

	public void setListAudiosNames(List<String> listAudiosNames) {
		this.listAudiosNames = listAudiosNames;
	}	

	@JsonIgnore
	@Transient
	private int liked;

	public int getLiked() {
		return liked;
	}

	public void setLiked(int liked) {
		this.liked = liked;
	}	

	@Transient
	private int likes;

	public int getLikes() {
		return likes;
	}

	public void setLikes(int likes) {
		this.likes = likes;
	}
	
	@Transient
	private String pathOfFiles;

	public String getPathOfFiles() {
		return pathOfFiles;
	}

	public void setPathOfFiles(String pathOfFiles) {
		this.pathOfFiles = pathOfFiles;
	}
}

package database.entities;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the user database table.
 * 
 */
@Entity
@NamedQuery(name="User.findAll", query="SELECT u FROM User u")
public class User implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private int id;

	private String city;

	private String country;

	@Temporal(TemporalType.DATE)
	private Date dateOfBirth;

	private String email;

	private int gender;

	private byte hasImage;

	private int isAdmin;

	private byte isConnected;

	private String name;

	private String password;

	private String photoURL;

	private String surname;

	private String tel;

	//bi-directional many-to-one association to Comment
	@OneToMany(mappedBy="user")
	private List<Comment> comments;

	//bi-directional many-to-one association to Post
	@OneToMany(mappedBy="user")
	private List<Post> posts;

	//bi-directional many-to-many association to User
	@ManyToMany
	@JoinTable(
		name="connection"
		, joinColumns={
			@JoinColumn(name="connectedUser_id")
			}
		, inverseJoinColumns={
			@JoinColumn(name="user_id")
			}
		)
	private List<User> users1;

	//bi-directional many-to-many association to User
	@ManyToMany(mappedBy="users1")
	private List<User> users2;

	//bi-directional many-to-many association to User
	@ManyToMany
	@JoinTable(
		name="conversation"
		, joinColumns={
			@JoinColumn(name="user_id2")
			}
		, inverseJoinColumns={
			@JoinColumn(name="user_id1")
			}
		)
	private List<User> users3;

	//bi-directional many-to-many association to User
	@ManyToMany(mappedBy="users3")
	private List<User> users4;

	public User() {
	}
	
	public User(String city, String country, Date dateOfBirth, String email, int gender, byte isAdmin,
			String name, String password, String photoURL, String surname, String tel, byte hasImage, List<Post> posts) {
		super();
		this.city = city;
		this.country = country;
		this.dateOfBirth = dateOfBirth;
		this.email = email;
		this.gender = gender;
		this.isAdmin = isAdmin;
		this.name = name;
		this.password = password;
		this.photoURL = photoURL;
		this.surname = surname;
		this.tel = tel;
		this.hasImage = hasImage;
		this.posts = posts;
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getCity() {
		return this.city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getCountry() {
		return this.country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public Date getDateOfBirth() {
		return this.dateOfBirth;
	}

	public void setDateOfBirth(Date dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public int getGender() {
		return this.gender;
	}

	public void setGender(int gender) {
		this.gender = gender;
	}

	public byte getHasImage() {
		return this.hasImage;
	}

	public void setHasImage(byte hasImage) {
		this.hasImage = hasImage;
	}

	public int getIsAdmin() {
		return this.isAdmin;
	}

	public void setIsAdmin(int isAdmin) {
		this.isAdmin = isAdmin;
	}

	public byte getIsConnected() {
		return this.isConnected;
	}

	public void setIsConnected(byte isConnected) {
		this.isConnected = isConnected;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPhotoURL() {
		return this.photoURL;
	}

	public void setPhotoURL(String photoURL) {
		this.photoURL = photoURL;
	}

	public String getSurname() {
		return this.surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public String getTel() {
		return this.tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public List<Comment> getComments() {
		return this.comments;
	}

	public void setComments(List<Comment> comments) {
		this.comments = comments;
	}

	public Comment addComment(Comment comment) {
		getComments().add(comment);
		comment.setUser(this);

		return comment;
	}

	public Comment removeComment(Comment comment) {
		getComments().remove(comment);
		comment.setUser(null);

		return comment;
	}

	public List<Post> getPosts() {
		return this.posts;
	}

	public void setPosts(List<Post> posts) {
		this.posts = posts;
	}

	public Post addPost(Post post) {
		getPosts().add(post);
		post.setUser(this);

		return post;
	}

	public Post removePost(Post post) {
		getPosts().remove(post);
		post.setUser(null);

		return post;
	}

	public List<User> getUsers1() {
		return this.users1;
	}

	public void setUsers1(List<User> users1) {
		this.users1 = users1;
	}

	public List<User> getUsers2() {
		return this.users2;
	}

	public void setUsers2(List<User> users2) {
		this.users2 = users2;
	}

	public List<User> getUsers3() {
		return this.users3;
	}

	public void setUsers3(List<User> users3) {
		this.users3 = users3;
	}

	public List<User> getUsers4() {
		return this.users4;
	}

	public void setUsers4(List<User> users4) {
		this.users4 = users4;
	}

}
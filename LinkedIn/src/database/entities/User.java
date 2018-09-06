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

	private String education;

	private String email;

	private int gender;

	private byte hasImage;

	private String institution;

	private int isAdmin;

	private byte isConnected;

	private byte isPending;

	private String name;

	private String password;

	private String photoURL;

	private byte privateCity;

	private byte privateCountry;

	private byte privateDateOfBirth;

	private byte privateEducation;

	private byte privateEmail;

	private byte privateGender;

	private byte privateInstitution;

	private byte privateProfExp;

	private byte privateSkills;

	private byte privateTelephone;

	private byte privateWorkPos;

	@Column(name="prof_exp")
	private String profExp;

	private byte sentConnectionRequest;

	private String skills;

	private String surname;

	private String tel;

	private String workPos;

	//bi-directional many-to-one association to Comment
	@OneToMany(mappedBy="user")
	private List<Comment> comments;

	//bi-directional many-to-one association to Connection
	@OneToMany(mappedBy="user1")
	private List<Connection> connections1;

	//bi-directional many-to-one association to Connection
	@OneToMany(mappedBy="user2")
	private List<Connection> connections2;

	//bi-directional many-to-one association to Post
	@OneToMany(mappedBy="user")
	private List<Post> posts1;

	//bi-directional many-to-many association to Post
	@ManyToMany
	@JoinTable(
		name="like"
		, joinColumns={
			@JoinColumn(name="user_id")
			}
		, inverseJoinColumns={
			@JoinColumn(name="post_id")
			}
		)
	private List<Post> posts2;

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
			String name, String password, String photoURL, String surname, String tel, byte hasImage, List<Post> posts, String profExp, String skills, String education, String workPos, String institution,
			byte privateCity, byte privateCountry, byte privateDateOfBirth, byte privateEducation, byte privateEmail, byte privateGender, byte privateSkills, byte privateProfExp, byte privateTelephone, byte privateWorkPos, byte privateInstitution) {
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
		this.skills = skills;
		this.education = education;
		this.privateCity = privateCity;
		this.privateCountry = privateCountry;
		this.privateDateOfBirth = privateDateOfBirth;
		this.privateEducation = privateEducation;
		this.privateEmail = privateEmail;
		this.privateGender = privateGender;
		this.privateSkills = privateSkills;
		this.privateProfExp = privateProfExp;
		this.privateTelephone = privateTelephone;
		this.profExp = profExp;
		this.workPos = workPos;
		this.institution = institution;
		this.privateWorkPos = privateWorkPos;
		this.privateInstitution = privateInstitution;
		this.posts1 = posts;
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

	public String getEducation() {
		return this.education;
	}

	public void setEducation(String education) {
		this.education = education;
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

	public String getInstitution() {
		return this.institution;
	}

	public void setInstitution(String institution) {
		this.institution = institution;
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

	public byte getIsPending() {
		return this.isPending;
	}

	public void setIsPending(byte isPending) {
		this.isPending = isPending;
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

	public byte getPrivateCity() {
		return this.privateCity;
	}

	public void setPrivateCity(byte privateCity) {
		this.privateCity = privateCity;
	}

	public byte getPrivateCountry() {
		return this.privateCountry;
	}

	public void setPrivateCountry(byte privateCountry) {
		this.privateCountry = privateCountry;
	}

	public byte getPrivateDateOfBirth() {
		return this.privateDateOfBirth;
	}

	public void setPrivateDateOfBirth(byte privateDateOfBirth) {
		this.privateDateOfBirth = privateDateOfBirth;
	}

	public byte getPrivateEducation() {
		return this.privateEducation;
	}

	public void setPrivateEducation(byte privateEducation) {
		this.privateEducation = privateEducation;
	}

	public byte getPrivateEmail() {
		return this.privateEmail;
	}

	public void setPrivateEmail(byte privateEmail) {
		this.privateEmail = privateEmail;
	}

	public byte getPrivateGender() {
		return this.privateGender;
	}

	public void setPrivateGender(byte privateGender) {
		this.privateGender = privateGender;
	}

	public byte getPrivateInstitution() {
		return this.privateInstitution;
	}

	public void setPrivateInstitution(byte privateInstitution) {
		this.privateInstitution = privateInstitution;
	}

	public byte getPrivateProfExp() {
		return this.privateProfExp;
	}

	public void setPrivateProfExp(byte privateProfExp) {
		this.privateProfExp = privateProfExp;
	}

	public byte getPrivateSkills() {
		return this.privateSkills;
	}

	public void setPrivateSkills(byte privateSkills) {
		this.privateSkills = privateSkills;
	}

	public byte getPrivateTelephone() {
		return this.privateTelephone;
	}

	public void setPrivateTelephone(byte privateTelephone) {
		this.privateTelephone = privateTelephone;
	}

	public byte getPrivateWorkPos() {
		return this.privateWorkPos;
	}

	public void setPrivateWorkPos(byte privateWorkPos) {
		this.privateWorkPos = privateWorkPos;
	}

	public String getProfExp() {
		return this.profExp;
	}

	public void setProfExp(String profExp) {
		this.profExp = profExp;
	}

	public byte getSentConnectionRequest() {
		return this.sentConnectionRequest;
	}

	public void setSentConnectionRequest(byte sentConnectionRequest) {
		this.sentConnectionRequest = sentConnectionRequest;
	}

	public String getSkills() {
		return this.skills;
	}

	public void setSkills(String skills) {
		this.skills = skills;
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

	public String getWorkPos() {
		return this.workPos;
	}

	public void setWorkPos(String workPos) {
		this.workPos = workPos;
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

	public List<Connection> getConnections1() {
		return this.connections1;
	}

	public void setConnections1(List<Connection> connections1) {
		this.connections1 = connections1;
	}

	public Connection addConnections1(Connection connections1) {
		getConnections1().add(connections1);
		connections1.setUser1(this);

		return connections1;
	}

	public Connection removeConnections1(Connection connections1) {
		getConnections1().remove(connections1);
		connections1.setUser1(null);

		return connections1;
	}

	public List<Connection> getConnections2() {
		return this.connections2;
	}

	public void setConnections2(List<Connection> connections2) {
		this.connections2 = connections2;
	}

	public Connection addConnections2(Connection connections2) {
		getConnections2().add(connections2);
		connections2.setUser2(this);

		return connections2;
	}

	public Connection removeConnections2(Connection connections2) {
		getConnections2().remove(connections2);
		connections2.setUser2(null);

		return connections2;
	}

	public List<Post> getPosts1() {
		return this.posts1;
	}

	public void setPosts1(List<Post> posts1) {
		this.posts1 = posts1;
	}

	public Post addPosts1(Post posts1) {
		getPosts1().add(posts1);
		posts1.setUser(this);

		return posts1;
	}

	public Post removePosts1(Post posts1) {
		getPosts1().remove(posts1);
		posts1.setUser(null);

		return posts1;
	}

	public List<Post> getPosts2() {
		return this.posts2;
	}

	public void setPosts2(List<Post> posts2) {
		this.posts2 = posts2;
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
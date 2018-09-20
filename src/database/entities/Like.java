package database.entities;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the like database table.
 * 
 */
@Entity
@NamedQuery(name="Like.findAll", query="SELECT l FROM Like l")
public class Like implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private LikePK id;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="date_liked")
	private Date dateLiked;

	//bi-directional many-to-one association to Post
	@ManyToOne
	private Post post;

	//bi-directional many-to-one association to User
	@ManyToOne
	private User user;

	public Like() {
	}

	public LikePK getId() {
		return this.id;
	}

	public void setId(LikePK id) {
		this.id = id;
	}

	public Date getDateLiked() {
		return this.dateLiked;
	}

	public void setDateLiked(Date dateLiked) {
		this.dateLiked = dateLiked;
	}

	public Post getPost() {
		return this.post;
	}

	public void setPost(Post post) {
		this.post = post;
	}

	public User getUser() {
		return this.user;
	}

	public void setUser(User user) {
		this.user = user;
	}

}
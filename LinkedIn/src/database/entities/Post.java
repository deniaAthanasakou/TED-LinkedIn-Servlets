package database.entities;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


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

	@Column(name="path_files")
	private String pathFiles;

	private String text;

	//bi-directional many-to-one association to User
	@ManyToOne
	private User user;

	public Post() {
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

	public User getUser() {
		return this.user;
	}

	public void setUser(User user) {
		this.user = user;
	}

}
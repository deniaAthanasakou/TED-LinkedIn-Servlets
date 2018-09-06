package database.entities;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the connection database table.
 * 
 */
@Entity
@NamedQuery(name="Connection.findAll", query="SELECT c FROM Connection c")
public class Connection implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private ConnectionPK id;

	private byte approved;

	//bi-directional many-to-one association to User
	@ManyToOne
	@JoinColumn(name="user_id")
	private User user1;

	//bi-directional many-to-one association to User
	@ManyToOne
	@JoinColumn(name="connectedUser_id")
	private User user2;

	public Connection() {
	}

	public ConnectionPK getId() {
		return this.id;
	}

	public void setId(ConnectionPK id) {
		this.id = id;
	}

	public byte getApproved() {
		return this.approved;
	}

	public void setApproved(byte approved) {
		this.approved = approved;
	}

	public User getUser1() {
		return this.user1;
	}

	public void setUser1(User user1) {
		this.user1 = user1;
	}

	public User getUser2() {
		return this.user2;
	}

	public void setUser2(User user2) {
		this.user2 = user2;
	}

}
package database.entities;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the connection database table.
 * 
 */
@Embeddable
public class ConnectionPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name="user_id", insertable=false, updatable=false)
	private int userId;

	@Column(insertable=false, updatable=false)
	private int connectedUser_id;

	public ConnectionPK() {
	}
	public int getUserId() {
		return this.userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public int getConnectedUser_id() {
		return this.connectedUser_id;
	}
	public void setConnectedUser_id(int connectedUser_id) {
		this.connectedUser_id = connectedUser_id;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof ConnectionPK)) {
			return false;
		}
		ConnectionPK castOther = (ConnectionPK)other;
		return 
			(this.userId == castOther.userId)
			&& (this.connectedUser_id == castOther.connectedUser_id);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.userId;
		hash = hash * prime + this.connectedUser_id;
		
		return hash;
	}
}
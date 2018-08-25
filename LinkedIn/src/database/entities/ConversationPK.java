package database.entities;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the conversation database table.
 * 
 */
@Embeddable
public class ConversationPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name="user_id1", insertable=false, updatable=false)
	private int userId1;

	@Column(name="user_id2", insertable=false, updatable=false)
	private int userId2;

	public ConversationPK() {
	}
	public int getUserId1() {
		return this.userId1;
	}
	public void setUserId1(int userId1) {
		this.userId1 = userId1;
	}
	public int getUserId2() {
		return this.userId2;
	}
	public void setUserId2(int userId2) {
		this.userId2 = userId2;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof ConversationPK)) {
			return false;
		}
		ConversationPK castOther = (ConversationPK)other;
		return 
			(this.userId1 == castOther.userId1)
			&& (this.userId2 == castOther.userId2);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.userId1;
		hash = hash * prime + this.userId2;
		
		return hash;
	}
}
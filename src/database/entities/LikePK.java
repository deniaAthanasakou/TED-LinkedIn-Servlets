package database.entities;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the like database table.
 * 
 */
@Embeddable
public class LikePK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name="user_id", insertable=false, updatable=false)
	private int userId;

	@Column(name="post_id", insertable=false, updatable=false)
	private int postId;

	public LikePK() {
	}
	public int getUserId() {
		return this.userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public int getPostId() {
		return this.postId;
	}
	public void setPostId(int postId) {
		this.postId = postId;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof LikePK)) {
			return false;
		}
		LikePK castOther = (LikePK)other;
		return 
			(this.userId == castOther.userId)
			&& (this.postId == castOther.postId);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.userId;
		hash = hash * prime + this.postId;
		
		return hash;
	}
}
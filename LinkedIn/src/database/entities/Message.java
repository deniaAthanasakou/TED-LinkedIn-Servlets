package database.entities;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the message database table.
 * 
 */
@Entity
@NamedQuery(name="Message.findAll", query="SELECT m FROM Message m")
public class Message implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="message_id")
	private int messageId;

	@Temporal(TemporalType.TIMESTAMP)
	private Date date;

	private String text;

	//bi-directional many-to-one association to Conversation
	@ManyToOne
	@JoinColumns({
		@JoinColumn(name="from_user", referencedColumnName="user_id1"),
		@JoinColumn(name="to_user", referencedColumnName="user_id2")
		})
	private Conversation conversation;

	public Message() {
	}

	public int getMessageId() {
		return this.messageId;
	}

	public void setMessageId(int messageId) {
		this.messageId = messageId;
	}

	public Date getDate() {
		return this.date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getText() {
		return this.text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public Conversation getConversation() {
		return this.conversation;
	}

	public void setConversation(Conversation conversation) {
		this.conversation = conversation;
	}

}
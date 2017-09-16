package com.vowme.model;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;




/**
 * The persistent class for the email database table.
 * 
 */
@Entity
@Table(name="email")
@NamedQuery(name="Email.findAll", query="SELECT e FROM Email e")
@JsonIgnoreProperties({"boardcasts","hibernateLazyInitializer", "handler"})

public class Email extends BaseModel implements Serializable {
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The id. */
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;

	/** The created at. */
	@Column(name="created_at")
	private int createdAt;

	/** The message. */
	@Column
	private String message;

	/** The recipient email. */
	@Column(name="recipient_email", nullable=false, length=200)
	private String recipientEmail;

	/** The success. */
	@Column
	private Byte success;

	/** The title. */
	@Column(nullable=false, length=200)
	private String title;

	/** The updated at. */
	@Column(name="updated_at")
	private int updatedAt;

	/** The boardcasts. */
	//bi-directional many-to-one association to Boardcast
	@OneToMany(mappedBy="email")
	private Set<Boardcast> boardcasts;

	/**
	 * Instantiates a new email.
	 */
	public Email() {
	}

	/**
	 * Gets the id.
	 *
	 * @return the id
	 */
	public Long getId() {
		return this.id;
	}

	/**
	 * Sets the id.
	 *
	 * @param id the new id
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * Gets the created at.
	 *
	 * @return the created at
	 */
	public int getCreatedAt() {
		return this.createdAt;
	}

	/**
	 * Sets the created at.
	 *
	 * @param createdAt the new created at
	 */
	public void setCreatedAt(int createdAt) {
		this.createdAt = createdAt;
	}

	/**
	 * Gets the message.
	 *
	 * @return the message
	 */
	public String getMessage() {
		return this.message;
	}

	/**
	 * Sets the message.
	 *
	 * @param message the new message
	 */
	public void setMessage(String message) {
		this.message = message;
	}

	/**
	 * Gets the recipient email.
	 *
	 * @return the recipient email
	 */
	public String getRecipientEmail() {
		return this.recipientEmail;
	}

	/**
	 * Sets the recipient email.
	 *
	 * @param recipientEmail the new recipient email
	 */
	public void setRecipientEmail(String recipientEmail) {
		this.recipientEmail = recipientEmail;
	}

	/**
	 * Gets the success.
	 *
	 * @return the success
	 */
	public Byte getSuccess() {
		return this.success;
	}

	/**
	 * Sets the success.
	 *
	 * @param success the new success
	 */
	public void setSuccess(Byte success) {
		this.success = success;
	}

	/**
	 * Gets the title.
	 *
	 * @return the title
	 */
	public String getTitle() {
		return this.title;
	}

	/**
	 * Sets the title.
	 *
	 * @param title the new title
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * Gets the updated at.
	 *
	 * @return the updated at
	 */
	public int getUpdatedAt() {
		return this.updatedAt;
	}

	/**
	 * Sets the updated at.
	 *
	 * @param updatedAt the new updated at
	 */
	public void setUpdatedAt(int updatedAt) {
		this.updatedAt = updatedAt;
	}

	/**
	 * Gets the boardcasts.
	 *
	 * @return the boardcasts
	 */
	public Set<Boardcast> getBoardcasts() {
		return this.boardcasts;
	}

	/**
	 * Sets the boardcasts.
	 *
	 * @param boardcasts the new boardcasts
	 */
	public void setBoardcasts(Set<Boardcast> boardcasts) {
		this.boardcasts = boardcasts;
	}

	/**
	 * Adds the boardcast.
	 *
	 * @param boardcast the boardcast
	 * @return the boardcast
	 */
	public Boardcast addBoardcast(Boardcast boardcast) {
		getBoardcasts().add(boardcast);
		boardcast.setEmail(this);

		return boardcast;
	}

	/**
	 * Removes the boardcast.
	 *
	 * @param boardcast the boardcast
	 * @return the boardcast
	 */
	public Boardcast removeBoardcast(Boardcast boardcast) {
		getBoardcasts().remove(boardcast);
		boardcast.setEmail(null);

		return boardcast;
	}

}
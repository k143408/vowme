package com.vowme.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;


/**
 * The persistent class for the feedback database table.
 * 
 */
@Entity
@JsonIgnoreProperties({"cause"})
public class Feedback extends BaseModel implements Serializable {
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The id. */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	/** The created at. */
	@Column(name = "created_at")
	private Long createdAt;

	/** The feedback. */
	@Column
	private String feedback;

	/** The updated at. */
	@Column(name = "updated_at")
	private Long updatedAt;

	/** The cause. */
	// bi-directional many-to-one association to Cause
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "causeid")
	private Cause cause;

	/** The receiver. */
	// bi-directional many-to-one association to User
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "userid")
	private User receiver;

	/** The commenter. */
	// bi-directional many-to-one association to User
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "given_by")
	private User commenter;

	/**
	 * Instantiates a new feedback.
	 */
	public Feedback() {
	}
	

	
	public Feedback(String feedback, Cause cause, User receiver, User commenter) {
		super();
		this.feedback = feedback;
		this.cause = cause;
		this.receiver = receiver;
		this.commenter = commenter;
	}



	/**
	 * Gets the id.
	 *
	 * @return the id
	 */
	public Long getId() {
		return id;
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
	public Long getCreatedAt() {
		return createdAt;
	}

	/**
	 * Sets the created at.
	 *
	 * @param createdAt the new created at
	 */
	public void setCreatedAt(Long createdAt) {
		this.createdAt = createdAt;
	}

	/**
	 * Gets the feedback.
	 *
	 * @return the feedback
	 */
	public String getFeedback() {
		return feedback;
	}

	/**
	 * Sets the feedback.
	 *
	 * @param feedback the new feedback
	 */
	public void setFeedback(String feedback) {
		this.feedback = feedback;
	}

	/**
	 * Gets the updated at.
	 *
	 * @return the updated at
	 */
	public Long getUpdatedAt() {
		return updatedAt;
	}

	/**
	 * Sets the updated at.
	 *
	 * @param updatedAt the new updated at
	 */
	public void setUpdatedAt(Long updatedAt) {
		this.updatedAt = updatedAt;
	}

	/**
	 * Gets the cause.
	 *
	 * @return the cause
	 */
	public Cause getCause() {
		return cause;
	}

	/**
	 * Sets the cause.
	 *
	 * @param cause the new cause
	 */
	public void setCause(Cause cause) {
		this.cause = cause;
	}

	/**
	 * Gets the receiver.
	 *
	 * @return the receiver
	 */
	public User getReceiver() {
		return receiver;
	}

	/**
	 * Sets the receiver.
	 *
	 * @param receiver the new receiver
	 */
	public void setReceiver(User receiver) {
		this.receiver = receiver;
	}

	/**
	 * Gets the commenter.
	 *
	 * @return the commenter
	 */
	public User getCommenter() {
		return commenter;
	}

	/**
	 * Sets the commenter.
	 *
	 * @param commenter the new commenter
	 */
	public void setCommenter(User commenter) {
		this.commenter = commenter;
	}



	
}
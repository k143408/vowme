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
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;




/**
 * The persistent class for the boardcast database table.
 * 
 */
@Entity
@Table(name="boardcast")
@JsonIgnoreProperties({"cause","hibernateLazyInitializer", "handler"})
public class Boardcast extends BaseModel implements Serializable {
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The id. */
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(unique=true, nullable=false)
	private Long id;

	/** The cause. */
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="causeid")
	private Cause cause;

	/** The created at. */
	@Column(name="created_at")
	private Long createdAt;

	/** The updated at. */
	@Column(name="updated_at")
	private Long updatedAt;

	/** The email. */
	//bi-directional many-to-one association to Email
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="emailid")
	private Email email;

	/**
	 * Instantiates a new boardcast.
	 */
	public Boardcast() {
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
	public Long getCreatedAt() {
		return this.createdAt;
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
	 * Gets the updated at.
	 *
	 * @return the updated at
	 */
	public Long getUpdatedAt() {
		return this.updatedAt;
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
	 * Gets the email.
	 *
	 * @return the email
	 */
	public Email getEmail() {
		return this.email;
	}

	/**
	 * Sets the email.
	 *
	 * @param email the new email
	 */
	public void setEmail(Email email) {
		this.email = email;
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

}
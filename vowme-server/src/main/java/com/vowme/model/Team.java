package com.vowme.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;


/**
 * The Class Team.
 */
@Entity
@JsonIgnoreProperties({"cause","hibernateLazyInitializer", "handler"})
public class Team extends BaseModel implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -69463835550376206L;

	/** The id. */
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;

	/** The cause. */
	@ManyToOne
	@JoinColumn(name="causeid")
	private Cause cause;

	/** The user. */
	@ManyToOne
	@JoinColumn(name="team_member")
	private User user;
	
	/** The is disabled. */
	@Column(name="is_disabled")
	private Byte isDisabled;

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
	 * Gets the user.
	 *
	 * @return the user
	 */
	public User getUser() {
		return user;
	}

	/**
	 * Sets the user.
	 *
	 * @param user the new user
	 */
	public void setUser(User user) {
		this.user = user;
	}

	/**
	 * Gets the checks if is disabled.
	 *
	 * @return the checks if is disabled
	 */
	public Byte getIsDisabled() {
		return isDisabled;
	}

	/**
	 * Sets the checks if is disabled.
	 *
	 * @param isDisabled the new checks if is disabled
	 */
	public void setIsDisabled(Byte isDisabled) {
		this.isDisabled = isDisabled;
	}
	
}

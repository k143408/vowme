package com.vowme.model;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.vowme.dto.EoiDTO;



/**
 * The persistent class for the approvals database table.
 * 
 */
@Entity
@Table(name = "approvals")
@NamedQuery(name = "Approval.findAll", query = "SELECT a FROM Approval a")
@JsonIgnoreProperties({"cause","hibernateLazyInitializer", "handler"})
public class Approval extends BaseModel implements Serializable {
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The id. */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(unique = true, nullable = false)
	private Long id;

	/** The actionby. */
	@ManyToOne()
	@JoinColumn(name = "actionby")
	private User actionby;

	/** The cause. */
	// bi-directional many-to-one association to User
	@ManyToOne()
	@JoinColumn(name = "causeid")
	private Cause cause;

	/** The created at. */
	@Column(name = "created_at")
	private Long createdAt;

	/** The description. */
	@Column(length = 200)
	private String description;

	/** The is approved. */
	@Column(name = "is_approved")
	private Byte isApproved;
	
	@Column(name = "override")
	private Byte override;

	/** The updated at. */
	@Column(name = "updated_at")
	private Long updatedAt;

	/** The user. */
	// bi-directional many-to-one association to User
	@ManyToOne()
	@JoinColumn(name = "userid")
	private User user;

	/**
	 * Instantiates a new approval.
	 */
	public Approval() {
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
	 * Gets the description.
	 *
	 * @return the description
	 */
	public String getDescription() {
		return this.description;
	}

	/**
	 * Sets the description.
	 *
	 * @param description the new description
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * Gets the checks if is approved.
	 *
	 * @return the checks if is approved
	 */
	public Byte getIsApproved() {
		return this.isApproved;
	}

	/**
	 * Sets the checks if is approved.
	 *
	 * @param isApproved the new checks if is approved
	 */
	public void setIsApproved(Byte isApproved) {
		this.isApproved = isApproved;
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
	 * Gets the user.
	 *
	 * @return the user
	 */
	public User getUser() {
		return this.user;
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
	 * Gets the actionby.
	 *
	 * @return the actionby
	 */
	public User getActionby() {
		return actionby;
	}

	/**
	 * Sets the actionby.
	 *
	 * @param actionby the new actionby
	 */
	public void setActionby(User actionby) {
		this.actionby = actionby;
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

	public Byte getOverride() {
		return override;
	}

	public void setOverride(Byte override) {
		this.override = override;
	}

	public Approval(EoiDTO eoi, Cause cause,User user) {
		super();
		this.actionby = cause.getUser();
		this.cause = cause;
		this.description = eoi.getQualification();
		//this.createdAt = new Timestamp(System.currentTimeMillis()).getTime();
		this.user = user;
		this.isApproved = new Byte("2");
	}
	
	

}
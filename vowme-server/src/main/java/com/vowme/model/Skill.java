package com.vowme.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;


/**
 * The persistent class for the skill database table.
 * 
 */
@Entity
@JsonIgnoreProperties({ "causeSkills", "userSkills", "handler", "hibernateLazyInitializer" })
public class Skill implements Serializable {
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The id. */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	/** The created at. */
	@Column(name = "created_at")
	private Long createdAt;

	/** The name. */
	@Column
	private String name;

	/** The updated at. */
	@Column(name = "updated_at")
	private Long updatedAt;

	/** The cause skills. */
	// bi-directional many-to-one association to CauseSkill
	@OneToMany(mappedBy = "skill", fetch = FetchType.LAZY)
	private List<CauseSkill> causeSkills;

	/** The user skills. */
	// bi-directional many-to-one association to UserSkill
	@OneToMany(mappedBy = "skill", fetch = FetchType.LAZY)
	private List<UserSkill> userSkills;

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
	 * Gets the name.
	 *
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Sets the name.
	 *
	 * @param name the new name
	 */
	public void setName(String name) {
		this.name = name;
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
	 * Gets the cause skills.
	 *
	 * @return the cause skills
	 */
	public List<CauseSkill> getCauseSkills() {
		return causeSkills;
	}

	/**
	 * Sets the cause skills.
	 *
	 * @param causeSkills the new cause skills
	 */
	public void setCauseSkills(List<CauseSkill> causeSkills) {
		this.causeSkills = causeSkills;
	}

	/**
	 * Gets the user skills.
	 *
	 * @return the user skills
	 */
	public List<UserSkill> getUserSkills() {
		return userSkills;
	}

	/**
	 * Sets the user skills.
	 *
	 * @param userSkills the new user skills
	 */
	public void setUserSkills(List<UserSkill> userSkills) {
		this.userSkills = userSkills;
	}

}
package com.vowme.model;

import java.io.Serializable;

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
 * The persistent class for the cause_skill database table.
 * 
 */
@Entity
@Table(name="cause_skill")
@JsonIgnoreProperties({ "cause" })
public class CauseSkill implements Serializable {
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The id. */
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int id;

	/** The cause. */
	//bi-directional many-to-one association to Cause
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="causeid")
	private Cause cause;

	/** The skill. */
	//bi-directional many-to-one association to Skill
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="skillid")
	private Skill skill;

	/**
	 * Gets the id.
	 *
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	/**
	 * Sets the id.
	 *
	 * @param id the new id
	 */
	public void setId(int id) {
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
	 * Gets the skill.
	 *
	 * @return the skill
	 */
	public Skill getSkill() {
		return skill;
	}

	/**
	 * Sets the skill.
	 *
	 * @param skill the new skill
	 */
	public void setSkill(Skill skill) {
		this.skill = skill;
	}

	
	
}
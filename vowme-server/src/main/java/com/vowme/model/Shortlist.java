package com.vowme.model;

import java.io.Serializable;
import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * The persistent class for the shortlist database table.
 * 
 */
@Entity
@NamedQuery(name = "Shortlist.findAll", query = "SELECT s FROM Shortlist s")
@JsonIgnoreProperties({"cause","user","hibernateLazyInitializer", "handler"})
public class Shortlist extends BaseModel implements Serializable {
	private static final long serialVersionUID = 1L;

	public Shortlist(Cause cause, User user) {
		super();
		this.cause = cause;
		this.user = user;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	// bi-directional many-to-one association to Cause
	@ManyToOne
	@JoinColumn(name = "causeid")
	private Cause cause;

	// bi-directional many-to-one association to User
	@ManyToOne
	@JoinColumn(name = "userid")
	private User user;

	public Shortlist() {
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Cause getCause() {
		return this.cause;
	}

	public void setCause(Cause cause) {
		this.cause = cause;
	}

	public User getUser() {
		return this.user;
	}

	public void setUser(User user) {
		this.user = user;
	}

}
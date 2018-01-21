package com.vowme.model;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.vowme.dto.DateParam;

import java.util.Date;
import java.util.Locale;

/**
 * The persistent class for the timesheet database table.
 * 
 */
@Entity
@NamedQuery(name = "Timesheet.findAll", query = "SELECT t FROM Timesheet t")
@JsonIgnoreProperties({ "cause", "user", "hibernateLazyInitializer", "handler" })
public class Timesheet extends BaseModel implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Temporal(TemporalType.DATE)
	@Column(name = "create_at")
	private Date createAt;

	@Column
	private Integer hours;

	@Column
	private Integer minutes;

	// bi-directional many-to-one association to Cause
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "causeid")
	private Cause cause;

	// bi-directional many-to-one association to User
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "userid")
	private User user;

	private transient DateFormat readformater = new SimpleDateFormat("dd/MM/yyyy");
	

	public Timesheet() {
	}

	public Timesheet(DateParam dateParam, User findById, Cause causesById) {
		this.cause = causesById;
		this.user = findById;
		this.hours = dateParam.getHours();
		this.id = new Long (dateParam.getId().longValue());
		try {
			this.createAt = readformater.parse(dateParam.getDate());
		} catch (ParseException e) {
			this.createAt = new Date();
		}
		this.minutes = dateParam.getMinutes();

	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getCreateAt() {
		return createAt;
	}

	public void setCreateAt(Date createAt) {
		this.createAt = createAt;
	}

	public Integer getHours() {
		return hours;
	}

	public void setHours(Integer hours) {
		this.hours = hours;
	}

	public Integer getMinutes() {
		return minutes;
	}

	public void setMinutes(Integer minutes) {
		this.minutes = minutes;
	}

	public Cause getCause() {
		return cause;
	}

	public void setCause(Cause cause) {
		this.cause = cause;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

}
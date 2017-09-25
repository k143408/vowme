package com.vowme.model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;


/**
 * The persistent class for the cause database table.
 * 
 */
@Entity
@Table(name = "cause")
@JsonIgnoreProperties({ "" })
public class Cause extends BaseModel implements Serializable {
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The id. */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(unique = true, nullable = false)
	private Long id;
	
	/** The address. */
	@Column(length = 200)
	private String address;
	
	/** The city. */
	@Column(length = 50)
	private String city;
	
	/** The created at. */
	@Column(name = "created_at")
	private Long createdAt;
	
	/** The description. */
	@Column
	private String description;
	
	/** The email. */
	@Column(length = 50)
	private String email;
	
	/** The enddate. */
	@Column
	private Long enddate;
	
	/** The endhour. */
	@Column
	private Long endhour;
	
	/** The endminute. */
	@Column
	private Long endminute;
	
	/** The guestbringalongs. */
	@Column
	private Byte guestbringalongs;
	
	/** The guestsallowed. */
	@Column
	private Byte guestsallowed;
	
	/** The guestsinvitationallowed. */
	@Column
	private Byte guestsinvitationallowed;
	
	/** The info. */
	@Column(length = 1000)
	private String info;
	
	/** The location. */
	@Column(length = 100)
	private String location;
	
	/** The latlong. */
	@Column
	private String latlong;
	/** The maxattendees. */
	@Column
	private Long maxattendees;
	
	/** The name. */
	@Column(length = 45)
	private String name;
	
	/** The phone. */
	@Column(length = 50)
	private String phone;
	
	/** The registrationdate. */
	@Column
	private Long registrationdate;
	
	/** The registrationdeadline. */
	@Column
	private Long registrationdeadline;
	
	/** The startdate. */
	@Column
	private Long startdate;
	
	/** The starthour. */
	@Column
	private Long starthour;
	
	/** The startminute. */
	@Column
	private Long startminute;
	
	/** The updated at. */
	@Column(name = "updated_at")
	private Long updatedAt;
	
	/** The user. */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "userid")
	private User user;
	
	/** The visibilitystatus. */
	@Column
	private Byte visibilitystatus;
	
	/** The wwwaddress. */
	@Column(length = 200)
	private String wwwaddress;
	
	/** The zipcode. */
	@Column
	private Long zipcode;
	
	/** The participates. */
	// bi-directional many-to-one association to Participate
	@OneToMany(mappedBy = "cause")
	private Set<Participate> participates;

	/** The causetypes. */
	// bi-directional many-to-many association to Causetype
	@OneToMany(mappedBy = "cause")
	private Set<Causetype> causetypes;

	/** The backouts. */
	// bi-directional many-to-one association to Backout
	@OneToMany(mappedBy = "cause")
	private Set<Backout> backouts;

	/** The approvals. */
	// bi-directional many-to-one association to Approval
	@OneToMany(mappedBy = "cause")
	private Set<Approval> approvals;

	/** The boardcasts. */
	@OneToMany(mappedBy = "cause")
	private Set<Boardcast> boardcasts;

	/** The teams. */
	@OneToMany(mappedBy="cause")
	private Set<Team> teams;

	/** The feedbacks. */
	@OneToMany(mappedBy="cause")
	private Set<Feedback> feedbacks;

	/** The cause skills. */
	@OneToMany(mappedBy="cause")
	private Set<CauseSkill> causeSkills;

	/**
	 * Gets the cause skills.
	 *
	 * @return the cause skills
	 */
	public Set<CauseSkill> getCauseSkills() {
		return causeSkills;
	}

	/**
	 * Sets the cause skills.
	 *
	 * @param causeSkills the new cause skills
	 */
	public void setCauseSkills(Set<CauseSkill> causeSkills) {
		this.causeSkills = causeSkills;
	}

	/**
	 * Gets the feedbacks.
	 *
	 * @return the feedbacks
	 */
	public Set<Feedback> getFeedbacks() {
		return feedbacks;
	}

	/**
	 * Sets the feedbacks.
	 *
	 * @param feedbacks the new feedbacks
	 */
	public void setFeedbacks(Set<Feedback> feedbacks) {
		this.feedbacks = feedbacks;
	}

	/**
	 * Instantiates a new cause.
	 */
	public Cause() {
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
	 * Gets the address.
	 *
	 * @return the address
	 */
	public String getAddress() {
		return this.address;
	}

	/**
	 * Sets the address.
	 *
	 * @param address the new address
	 */
	public void setAddress(String address) {
		this.address = address;
	}

	/**
	 * Gets the city.
	 *
	 * @return the city
	 */
	public String getCity() {
		return this.city;
	}

	/**
	 * Sets the city.
	 *
	 * @param city the new city
	 */
	public void setCity(String city) {
		this.city = city;
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
	 * Gets the email.
	 *
	 * @return the email
	 */
	public String getEmail() {
		return this.email;
	}

	/**
	 * Sets the email.
	 *
	 * @param email the new email
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * Gets the enddate.
	 *
	 * @return the enddate
	 */
	public Long getEnddate() {
		return this.enddate;
	}

	/**
	 * Sets the enddate.
	 *
	 * @param enddate the new enddate
	 */
	public void setEnddate(Long enddate) {
		this.enddate = enddate;
	}

	/**
	 * Gets the endhour.
	 *
	 * @return the endhour
	 */
	public Long getEndhour() {
		return this.endhour;
	}

	/**
	 * Sets the endhour.
	 *
	 * @param endhour the new endhour
	 */
	public void setEndhour(Long endhour) {
		this.endhour = endhour;
	}

	/**
	 * Gets the endminute.
	 *
	 * @return the endminute
	 */
	public Long getEndminute() {
		return this.endminute;
	}

	/**
	 * Sets the endminute.
	 *
	 * @param endminute the new endminute
	 */
	public void setEndminute(Long endminute) {
		this.endminute = endminute;
	}

	/**
	 * Gets the guestbringalongs.
	 *
	 * @return the guestbringalongs
	 */
	public Byte getGuestbringalongs() {
		return this.guestbringalongs;
	}

	/**
	 * Sets the guestbringalongs.
	 *
	 * @param guestbringalongs the new guestbringalongs
	 */
	public void setGuestbringalongs(Byte guestbringalongs) {
		this.guestbringalongs = guestbringalongs;
	}

	/**
	 * Gets the guestsallowed.
	 *
	 * @return the guestsallowed
	 */
	public Byte getGuestsallowed() {
		return this.guestsallowed;
	}

	/**
	 * Sets the guestsallowed.
	 *
	 * @param guestsallowed the new guestsallowed
	 */
	public void setGuestsallowed(Byte guestsallowed) {
		this.guestsallowed = guestsallowed;
	}

	/**
	 * Gets the guestsinvitationallowed.
	 *
	 * @return the guestsinvitationallowed
	 */
	public Byte getGuestsinvitationallowed() {
		return this.guestsinvitationallowed;
	}

	/**
	 * Sets the guestsinvitationallowed.
	 *
	 * @param guestsinvitationallowed the new guestsinvitationallowed
	 */
	public void setGuestsinvitationallowed(Byte guestsinvitationallowed) {
		this.guestsinvitationallowed = guestsinvitationallowed;
	}

	/**
	 * Gets the info.
	 *
	 * @return the info
	 */
	public String getInfo() {
		return this.info;
	}

	/**
	 * Sets the info.
	 *
	 * @param info the new info
	 */
	public void setInfo(String info) {
		this.info = info;
	}

	/**
	 * Gets the location.
	 *
	 * @return the location
	 */
	public String getLocation() {
		return this.location;
	}

	/**
	 * Sets the location.
	 *
	 * @param location the new location
	 */
	public void setLocation(String location) {
		this.location = location;
	}

	/**
	 * Gets the maxattendees.
	 *
	 * @return the maxattendees
	 */
	public Long getMaxattendees() {
		return this.maxattendees;
	}

	/**
	 * Sets the maxattendees.
	 *
	 * @param maxattendees the new maxattendees
	 */
	public void setMaxattendees(Long maxattendees) {
		this.maxattendees = maxattendees;
	}

	/**
	 * Gets the name.
	 *
	 * @return the name
	 */
	public String getName() {
		return this.name;
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
	 * Gets the phone.
	 *
	 * @return the phone
	 */
	public String getPhone() {
		return this.phone;
	}

	/**
	 * Sets the phone.
	 *
	 * @param phone the new phone
	 */
	public void setPhone(String phone) {
		this.phone = phone;
	}

	/**
	 * Gets the registrationdate.
	 *
	 * @return the registrationdate
	 */
	public Long getRegistrationdate() {
		return this.registrationdate;
	}

	/**
	 * Sets the registrationdate.
	 *
	 * @param registrationdate the new registrationdate
	 */
	public void setRegistrationdate(Long registrationdate) {
		this.registrationdate = registrationdate;
	}

	/**
	 * Gets the registrationdeadline.
	 *
	 * @return the registrationdeadline
	 */
	public Long getRegistrationdeadline() {
		return this.registrationdeadline;
	}

	/**
	 * Sets the registrationdeadline.
	 *
	 * @param registrationdeadline the new registrationdeadline
	 */
	public void setRegistrationdeadline(Long registrationdeadline) {
		this.registrationdeadline = registrationdeadline;
	}

	/**
	 * Gets the startdate.
	 *
	 * @return the startdate
	 */
	public Long getStartdate() {
		return this.startdate;
	}

	/**
	 * Sets the startdate.
	 *
	 * @param startdate the new startdate
	 */
	public void setStartdate(Long startdate) {
		this.startdate = startdate;
	}

	/**
	 * Gets the starthour.
	 *
	 * @return the starthour
	 */
	public Long getStarthour() {
		return this.starthour;
	}

	/**
	 * Sets the starthour.
	 *
	 * @param starthour the new starthour
	 */
	public void setStarthour(Long starthour) {
		this.starthour = starthour;
	}

	/**
	 * Gets the startminute.
	 *
	 * @return the startminute
	 */
	public Long getStartminute() {
		return this.startminute;
	}

	/**
	 * Sets the startminute.
	 *
	 * @param startminute the new startminute
	 */
	public void setStartminute(Long startminute) {
		this.startminute = startminute;
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
	 * Gets the visibilitystatus.
	 *
	 * @return the visibilitystatus
	 */
	public Byte getVisibilitystatus() {
		return this.visibilitystatus;
	}

	/**
	 * Sets the visibilitystatus.
	 *
	 * @param visibilitystatus the new visibilitystatus
	 */
	public void setVisibilitystatus(Byte visibilitystatus) {
		this.visibilitystatus = visibilitystatus;
	}

	/**
	 * Gets the wwwaddress.
	 *
	 * @return the wwwaddress
	 */
	public String getWwwaddress() {
		return this.wwwaddress;
	}

	/**
	 * Sets the wwwaddress.
	 *
	 * @param wwwaddress the new wwwaddress
	 */
	public void setWwwaddress(String wwwaddress) {
		this.wwwaddress = wwwaddress;
	}

	/**
	 * Gets the zipcode.
	 *
	 * @return the zipcode
	 */
	public Long getZipcode() {
		return this.zipcode;
	}

	/**
	 * Sets the zipcode.
	 *
	 * @param zipcode the new zipcode
	 */
	public void setZipcode(Long zipcode) {
		this.zipcode = zipcode;
	}

	/**
	 * Gets the participates.
	 *
	 * @return the participates
	 */
	public Set<Participate> getParticipates() {
		return this.participates == null ? new HashSet<>() : this.participates;
	}

	/**
	 * Sets the participates.
	 *
	 * @param participates the new participates
	 */
	public void setParticipates(Set<Participate> participates) {
		this.participates = participates;
	}

	/**
	 * Adds the participate.
	 *
	 * @param participate the participate
	 * @return the participate
	 */
	public Participate addParticipate(Participate participate) {
		getParticipates().add(participate);
		participate.setCause(this);

		return participate;
	}

	/**
	 * Removes the participate.
	 *
	 * @param participate the participate
	 * @return the participate
	 */
	public Participate removeParticipate(Participate participate) {
		getParticipates().remove(participate);
		participate.setCause(null);

		return participate;
	}

	/**
	 * Gets the causetypes.
	 *
	 * @return the causetypes
	 */
	public Set<Causetype> getCausetypes() {
		return this.causetypes;
	}

	/**
	 * Sets the causetypes.
	 *
	 * @param causetypes the new causetypes
	 */
	public void setCausetypes(Set<Causetype> causetypes) {
		this.causetypes = causetypes;
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
	 * Gets the backouts.
	 *
	 * @return the backouts
	 */
	public Set<Backout> getBackouts() {
		return backouts;
	}

	/**
	 * Sets the backouts.
	 *
	 * @param backouts the new backouts
	 */
	public void setBackouts(Set<Backout> backouts) {
		this.backouts = backouts;
	}

	/**
	 * Gets the approvals.
	 *
	 * @return the approvals
	 */
	public Set<Approval> getApprovals() {
		return approvals;
	}

	/**
	 * Sets the approvals.
	 *
	 * @param approvals the new approvals
	 */
	public void setApprovals(Set<Approval> approvals) {
		this.approvals = approvals;
	}

	/**
	 * Gets the boardcasts.
	 *
	 * @return the boardcasts
	 */
	public Set<Boardcast> getBoardcasts() {
		return boardcasts;
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
	 * Gets the teams.
	 *
	 * @return the teams
	 */
	public Set<Team> getTeams() {
		return teams;
	}

	/**
	 * Sets the teams.
	 *
	 * @param teams the new teams
	 */
	public void setTeams(Set<Team> teams) {
		this.teams = teams;
	}
	
	public Team addTeams(Team team) {
		getTeams().add(team);
		team.setCause(this);
		return team;
	}
	
	public void addTeams(Set<Team> teams) {
		getTeams().addAll(teams);
		teams.stream().forEach(team -> {team.setCause(this); team.setId(null);});
	}

	/**
	 * Gets the latlong.
	 *
	 * @return the latlong
	 */
	public String getLatlong() {
		return latlong;
	}

	/**
	 * Sets the latlong.
	 *
	 * @param latlong the new latlong
	 */
	public void setLatlong(String latlong) {
		this.latlong = latlong;
	}

}
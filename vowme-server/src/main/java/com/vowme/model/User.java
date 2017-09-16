package com.vowme.model;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.vowme.util.AESEncryptionUtils;



/**
 * The persistent class for the user database table.
 * 
 */
@Entity
@Table(name="user")
@JsonIgnoreProperties({"feedbacks1","feedbacks2","teams","backouts","cause","approvals","actionby", "participates","hibernateLazyInitializer", "handler"})
public class User extends BaseModel implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The id. */
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(unique=true, nullable=false)
	private Long id;

	/** The is active. */
	@Column(name="is_active", nullable=false)
	private Byte isActive;

	/** The cnic. */
	@Column(length=45)
	private String cnic;

	/** The cnic verified. */
	@Column(name="cnic_verified")
	private Byte cnicVerified;

	/** The created at. */
	@Column(name="created_at")
	private Long createdAt;

	/** The email. */
	@Column(length=100)
	private String email;

	/** The email verified. */
	@Column(name="email_verified")
	private Byte emailVerified;

	/** The firstname. */
	@Column(length=45)
	private String firstname;

	/** The lastname. */
	@Column(length=45)
	private String lastname;

	/** The password. */
	@Column(length=45)
	private String password;

	/** The updated at. */
	@Column(name="updated_at")
	private Long updatedAt;

	/** The username. */
	@Column(length=45)
	private String username;
	
	/** The rank. */
	@Column
	private Float rank;

	/** The approvals. */
	//bi-directional many-to-one association to Approval
	@OneToMany(mappedBy="user")
	private Set<Approval> approvals;
	
	/** The actionby. */
	@OneToMany(mappedBy="actionby")
	private Set<Approval> actionby;

	/** The cause. */
	@OneToMany(mappedBy="user")
	private List<Cause> cause;
	
	/** The backouts. */
	//bi-directional many-to-one association to Backout
	@OneToMany(mappedBy="user")
	private Set<Backout> backouts;

	/** The participates. */
	//bi-directional many-to-one association to Participate
	@OneToMany(mappedBy="user")
	private Set<Participate> participates;

	/** The teams. */
	@OneToMany(mappedBy="user")
	private List<Team> teams;
	
	/** The user info. */
	@OneToOne(mappedBy="user")
	private UserInfo userInfo;

	/** The feedbacks 1. */
	//bi-directional many-to-one association to Feedback
	@OneToMany(mappedBy="receiver")
	private List<Feedback> feedbacks1;

	/** The feedbacks 2. */
	//bi-directional many-to-one association to Feedback
	@OneToMany(mappedBy="commenter")
	private List<Feedback> feedbacks2;

	/** The user skills. */
	//bi-directional many-to-one association to UserSkill
	@OneToMany(mappedBy="user")
	private List<UserSkill> userSkills;

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

	/**
	 * Gets the feedbacks 1.
	 *
	 * @return the feedbacks 1
	 */
	public List<Feedback> getFeedbacks1() {
		return feedbacks1;
	}

	/**
	 * Sets the feedbacks 1.
	 *
	 * @param feedbacks1 the new feedbacks 1
	 */
	public void setFeedbacks1(List<Feedback> feedbacks1) {
		this.feedbacks1 = feedbacks1;
	}

	/**
	 * Gets the feedbacks 2.
	 *
	 * @return the feedbacks 2
	 */
	public List<Feedback> getFeedbacks2() {
		return feedbacks2;
	}

	/**
	 * Sets the feedbacks 2.
	 *
	 * @param feedbacks2 the new feedbacks 2
	 */
	public void setFeedbacks2(List<Feedback> feedbacks2) {
		this.feedbacks2 = feedbacks2;
	}

	/**
	 * Instantiates a new user.
	 */
	public User() {
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
	 * Gets the cnic.
	 *
	 * @return the cnic
	 */
	public String getCnic() {
		return this.cnic;
	}

	/**
	 * Sets the cnic.
	 *
	 * @param cnic the new cnic
	 */
	public void setCnic(String cnic) {
		this.cnic = cnic;
	}

	/**
	 * Gets the cnic verified.
	 *
	 * @return the cnic verified
	 */
	public Byte getCnicVerified() {
		return this.cnicVerified;
	}

	/**
	 * Sets the cnic verified.
	 *
	 * @param cnicVerified the new cnic verified
	 */
	public void setCnicVerified(Byte cnicVerified) {
		this.cnicVerified = cnicVerified;
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
	 * Gets the email verified.
	 *
	 * @return the email verified
	 */
	public Byte getEmailVerified() {
		return this.emailVerified;
	}

	/**
	 * Sets the email verified.
	 *
	 * @param emailVerified the new email verified
	 */
	public void setEmailVerified(Byte emailVerified) {
		this.emailVerified = emailVerified;
	}

	/**
	 * Gets the firstname.
	 *
	 * @return the firstname
	 */
	public String getFirstname() {
		return this.firstname;
	}

	/**
	 * Sets the firstname.
	 *
	 * @param firstname the new firstname
	 */
	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	
	/**
	 * Gets the lastname.
	 *
	 * @return the lastname
	 */
	public String getLastname() {
		return this.lastname;
	}

	/**
	 * Sets the lastname.
	 *
	 * @param lastname the new lastname
	 */
	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	/**
	 * Gets the password.
	 *
	 * @return the password
	 */
	public String getPassword() {
		return this.password;
	}

	/**
	 * Sets the password.
	 *
	 * @param password the new password
	 */
	public void setPassword(String password) {
		this.password = AESEncryptionUtils.encrypt(password);
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
	 * Gets the username.
	 *
	 * @return the username
	 */
	public String getUsername() {
		return this.username;
	}

	/**
	 * Sets the username.
	 *
	 * @param username the new username
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	/**
	 * Gets the approvals.
	 *
	 * @return the approvals
	 */
	public Set<Approval> getApprovals() {
		return this.approvals;
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
	 * Adds the approval.
	 *
	 * @param approval the approval
	 * @return the approval
	 */
	public Approval addApproval(Approval approval) {
		getApprovals().add(approval);
		approval.setUser(this);
		return approval;
	}

	/**
	 * Removes the approval.
	 *
	 * @param approval the approval
	 * @return the approval
	 */
	public Approval removeApproval(Approval approval) {
		getApprovals().remove(approval);
		approval.setUser(null);

		return approval;
	}

	/**
	 * Gets the backouts.
	 *
	 * @return the backouts
	 */
	public Set<Backout> getBackouts() {
		return this.backouts;
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
	 * Adds the backout.
	 *
	 * @param backout the backout
	 * @return the backout
	 */
	public Backout addBackout(Backout backout) {
		getBackouts().add(backout);
		backout.setUser(this);

		return backout;
	}

	/**
	 * Removes the backout.
	 *
	 * @param backout the backout
	 * @return the backout
	 */
	public Backout removeBackout(Backout backout) {
		getBackouts().remove(backout);
		backout.setUser(null);

		return backout;
	}

	/**
	 * Gets the participates.
	 *
	 * @return the participates
	 */
	public Set<Participate> getParticipates() {
		return this.participates;
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
		participate.setUser(this);

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
		participate.setUser(null);

		return participate;
	}

	/**
	 * Gets the actionby.
	 *
	 * @return the actionby
	 */
	public Set<Approval> getActionby() {
		return actionby;
	}

	/**
	 * Sets the actionby.
	 *
	 * @param actionby the new actionby
	 */
	public void setActionby(Set<Approval> actionby) {
		this.actionby = actionby;
	}

	/**
	 * Gets the checks if is active.
	 *
	 * @return the checks if is active
	 */
	public Byte getIsActive() {
		return isActive;
	}

	/**
	 * Sets the checks if is active.
	 *
	 * @param isActive the new checks if is active
	 */
	public void setIsActive(Byte isActive) {
		this.isActive = isActive;
	}

	/**
	 * Gets the cause.
	 *
	 * @return the cause
	 */
	public List<Cause> getCause() {
		return cause;
	}

	/**
	 * Sets the cause.
	 *
	 * @param cause the new cause
	 */
	public void setCause(List<Cause> cause) {
		this.cause = cause;
	}


	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		User other = (User) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	/**
	 * Gets the user info.
	 *
	 * @return the user info
	 */
	public UserInfo getUserInfo() {
		return userInfo;
	}

	/**
	 * Sets the user info.
	 *
	 * @param userInfo the new user info
	 */
	public void setUserInfo(UserInfo userInfo) {
		this.userInfo = userInfo;
	}

	/**
	 * Gets the teams.
	 *
	 * @return the teams
	 */
	public List<Team> getTeams() {
		return teams;
	}

	/**
	 * Sets the teams.
	 *
	 * @param teams the new teams
	 */
	public void setTeams(List<Team> teams) {
		this.teams = teams;
	}

	/**
	 * Gets the rank.
	 *
	 * @return the rank
	 */
	public Float getRank() {
		return rank;
	}

	/**
	 * Sets the rank.
	 *
	 * @param rank the new rank
	 */
	public void setRank(Float rank) {
		this.rank = rank;
	}

}
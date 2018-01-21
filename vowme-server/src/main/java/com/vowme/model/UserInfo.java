package com.vowme.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;



/**
 * The Class UserInfo.
 */
@Entity
@Table(name="user_info")
@JsonIgnoreProperties({"user","hibernateLazyInitializer", "handler"})

public class UserInfo {
	
	/** The userid. */
	@Id
	private Long userid;

	/** The address. */
	@Column
	private String address;
	
	/** The city. */
	@Column
	private String city;

	/** The contact number 1. */
	@Column(name="contact_number1")
	private String contactNumber1;

	/** The contact number 2. */
	@Column(name="contact_number2")
	private String contactNumber2;

	/** The created at. */
	@Column(name="created_at")
	private Long createdAt;

	/** The is organizer. */
	@Column(name="is_organizer")
	private Boolean isOrganizer;

	/** The updated at. */
	@Column(name="updated_at")
	private Long updatedAt;
	
	/** The zipcode. */
	@Column
	private Integer zipcode;

	/** The about me. */
	@Column(name="about_me")
	private String aboutMe;
	
	/** The organization name. */
	@Column(name="organization_name")
	private String organizationName;
	
	@Column
	private String qualification;
	
	public String getQualification() {
		return qualification;
	}

	public void setQualification(String qualification) {
		this.qualification = qualification;
	}

	/**
	 * Gets the organization name.
	 *
	 * @return the organization name
	 */
	public String getOrganizationName() {
		return organizationName;
	}

	/**
	 * Sets the organization name.
	 *
	 * @param organizationName the new organization name
	 */
	public void setOrganizationName(String organizationName) {
		this.organizationName = organizationName;
	}

	/**
	 * Gets the about me.
	 *
	 * @return the about me
	 */
	public String getAboutMe() {
		return aboutMe;
	}

	/**
	 * Sets the about me.
	 *
	 * @param aboutMe the new about me
	 */
	public void setAboutMe(String aboutMe) {
		this.aboutMe = aboutMe;
	}

	/** The user. */
	//bi-directional one-to-one association to User
	@OneToOne
	@JoinColumn(name="userid")
	private User user;

	/**
	 * Gets the address.
	 *
	 * @return the address
	 */
	public String getAddress() {
		return address;
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
		return city;
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
	 * Gets the contact number 1.
	 *
	 * @return the contact number 1
	 */
	public String getContactNumber1() {
		return contactNumber1;
	}

	/**
	 * Sets the contact number 1.
	 *
	 * @param contactNumber1 the new contact number 1
	 */
	public void setContactNumber1(String contactNumber1) {
		this.contactNumber1 = contactNumber1;
	}

	/**
	 * Gets the contact number 2.
	 *
	 * @return the contact number 2
	 */
	public String getContactNumber2() {
		return contactNumber2;
	}

	/**
	 * Sets the contact number 2.
	 *
	 * @param contactNumber2 the new contact number 2
	 */
	public void setContactNumber2(String contactNumber2) {
		this.contactNumber2 = contactNumber2;
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
	 * Gets the checks if is organizer.
	 *
	 * @return the checks if is organizer
	 */
	public Boolean getIsOrganizer() {
		return isOrganizer;
	}

	/**
	 * Sets the checks if is organizer.
	 *
	 * @param isOrganizer the new checks if is organizer
	 */
	public void setIsOrganizer(Boolean isOrganizer) {
		this.isOrganizer = isOrganizer;
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
	 * Gets the zipcode.
	 *
	 * @return the zipcode
	 */
	public Integer getZipcode() {
		return zipcode;
	}

	/**
	 * Sets the zipcode.
	 *
	 * @param zipcode the new zipcode
	 */
	public void setZipcode(Integer zipcode) {
		this.zipcode = zipcode;
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

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((user == null) ? 0 : user.hashCode());
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
		UserInfo other = (UserInfo) obj;
		if (user == null) {
			if (other.user != null)
				return false;
		} else if (!user.equals(other.user))
			return false;
		return true;
	}

}

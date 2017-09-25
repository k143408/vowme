package com.vowme.util.helper;

import java.io.Serializable;

import com.vowme.util.DateUtils;


/**
 * The Class CauseShortDetail.
 */
public class CauseShortDetail implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 7201686691835241037L;
	
	/** The id. */
	private Long id;
	
	/** The cause name. */
	private String causeName;
	
	/** The description. */
	private String description;
	
	/** The registration date. */
	private String registrationdate;
	
	/** The registration deadline. */
	private String registrationdeadline;
	
	/** The info. */
	private String info; 
		
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
	 * Gets the cause name.
	 *
	 * @return the cause name
	 */
	public String getCauseName() {
		return causeName;
	}

	/**
	 * Sets the cause name.
	 *
	 * @param causeName the new cause name
	 */
	public void setCauseName(String causeName) {
		this.causeName = causeName;
	}

	/**
	 * Instantiates a new cause short detail.
	 *
	 * @param id the id
	 * @param causeName the cause name
	 */
	public CauseShortDetail(Long id, String causeName) {
		super();
		this.id = id;
		this.causeName = causeName;
	}

	/**
	 * Gets the registrationdate.
	 *
	 * @return the registrationdate
	 */
	public String getRegistrationdate() {
		return registrationdate;
	}

	/**
	 * Sets the registrationdate.
	 *
	 * @param registrationdate the new registrationdate
	 */
	public void setRegistrationdate(String registrationdate) {
		this.registrationdate = registrationdate;
	}

	/**
	 * Gets the registrationdeadline.
	 *
	 * @return the registrationdeadline
	 */
	public String getRegistrationdeadline() {
		return registrationdeadline;
	}

	/**
	 * Sets the registrationdeadline.
	 *
	 * @param registrationdeadline the new registrationdeadline
	 */
	public void setRegistrationdeadline(String registrationdeadline) {
		this.registrationdeadline = registrationdeadline;
	}

	/**
	 * Gets the info.
	 *
	 * @return the info
	 */
	public String getInfo() {
		return info;
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
	 * Gets the description.
	 *
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * Sets the description.
	 *
	 * @param description the new description
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	public CauseShortDetail(Long id, String causeName, String description, Long registrationdate,
			Long registrationdeadline, String info) {
		super();
		this.id = id;
		this.causeName = causeName;
		this.description = description;
		this.registrationdate = DateUtils.convertLongIntoString(registrationdate);
		this.registrationdeadline = DateUtils.convertLongIntoString(registrationdeadline);
		this.info = info;
	}
}

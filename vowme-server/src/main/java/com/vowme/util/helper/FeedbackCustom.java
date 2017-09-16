package com.vowme.util.helper;

import java.io.Serializable;


/**
 * The Class FeedbackCustom.
 */
public class FeedbackCustom implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -8926776397565522892L;
	
	/** The id. */
	private Long id;
	
	/** The feedback. */
	private String feedback;
	
	/** The cause name. */
	private String causeName;
	
	/** The first name. */
	private String firstName;
	
	/** The last name. */
	private String lastName;

	
	/**
	 * Instantiates a new feedback custom.
	 *
	 * @param id the id
	 * @param feedback the feedback
	 * @param causeName the cause name
	 * @param firstName the first name
	 * @param lastName the last name
	 */
	public FeedbackCustom(Long id, String feedback, String causeName, String firstName, String lastName) {
		super();
		this.id = id;
		this.feedback = feedback;
		this.causeName = causeName;
		this.firstName = firstName;
		this.lastName = lastName;
	}

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
	 * Gets the feedback.
	 *
	 * @return the feedback
	 */
	public String getFeedback() {
		return feedback;
	}

	/**
	 * Sets the feedback.
	 *
	 * @param feedback the new feedback
	 */
	public void setFeedback(String feedback) {
		this.feedback = feedback;
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
	 * Gets the first name.
	 *
	 * @return the first name
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * Sets the first name.
	 *
	 * @param firstName the new first name
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	/**
	 * Gets the last name.
	 *
	 * @return the last name
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * Sets the last name.
	 *
	 * @param lastName the new last name
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

}

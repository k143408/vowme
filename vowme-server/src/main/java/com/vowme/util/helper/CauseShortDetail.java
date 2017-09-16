package com.vowme.util.helper;

import java.io.Serializable;


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

}

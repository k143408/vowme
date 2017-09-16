package com.vowme.util.helper;

import java.io.Serializable;


/**
 * The Class KeyValue.
 */
public class KeyValue implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -4463201464120704932L;
	
	/** The id. */
	private Long id;
	
	/** The name. */
	private String name;

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
	 * Instantiates a new key value.
	 *
	 * @param id the id
	 * @param name the name
	 */
	public KeyValue(Long id, String name) {
		super();
		this.id = id;
		this.name = name;
	}

}

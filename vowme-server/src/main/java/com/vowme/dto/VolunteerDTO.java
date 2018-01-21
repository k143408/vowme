package com.vowme.dto;

import java.io.Serializable;

public class VolunteerDTO extends BaseDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5156286779156705853L;

	private Long volunteerId;
	
	private Boolean existingVolunteer;
	
	

	public VolunteerDTO(Long volunteerId, Boolean existingVolunteer) {
		this.volunteerId = volunteerId;
		this.existingVolunteer = existingVolunteer;
	}

	public Long getVolunteerId() {
		return volunteerId;
	}

	public void setVolunteerId(Long volunteerId) {
		this.volunteerId = volunteerId;
	}

	public Boolean getExistingVolunteer() {
		return existingVolunteer;
	}

	public void setExistingVolunteer(Boolean existingVolunteer) {
		this.existingVolunteer = existingVolunteer;
	}

}

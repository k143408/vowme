package com.vowme.util.helper;

import java.io.Serializable;

public class MutliModel implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Long id;
	private String firstname;
	private String lastname;
	private String username;
	private String email;
	private String cnic;
	private String causeName;
	private String causeId;

	public MutliModel(Long id, String firstName, String lastName, String username, String email, String cnic,
			String causeName, Long causeId) {
		super();
		this.id = id;
		this.firstname = firstName;
		this.lastname = lastName;
		this.username = username;
		this.email = email;
		this.cnic = cnic;
		this.causeName = causeName;
		this.causeId = causeId.toString();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}


	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getCnic() {
		return cnic;
	}

	public void setCnic(String cnic) {
		this.cnic = cnic;
	}

	public String getCauseName() {
		return causeName;
	}

	public void setCauseName(String causeName) {
		this.causeName = causeName;
	}

	public String getCauseId() {
		return causeId;
	}

	public void setCauseId(String causeId) {
		this.causeId = causeId;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((causeId == null) ? 0 : causeId.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		MutliModel other = (MutliModel) obj;
		if (causeId == null) {
			if (other.causeId != null)
				return false;
		} else if (!causeId.equals(other.causeId))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

}

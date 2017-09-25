package com.vowme.model;

import java.io.Serializable;
import javax.persistence.*;

import org.hibernate.annotations.Where;

import com.vowme.util.DateUtils;


/**
 * The persistent class for the team_notification database table.
 * 
 */
@Entity
@Where(clause = "status = -1")
@Table(name="team_notification")
public class TeamNotification extends BaseModel implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;

	@Column(name="created_at")
	private Long createdAt;

	private String email;

	private String message;

	private Integer notified;

	private String parameters;

	private Integer status;

	private Integer type;

	@Column(name="updated_at")
	private Long updatedAt;

	public TeamNotification() {
	}
	
	

	public TeamNotification(String email, String message, Integer type,Long causeId) {
		super();
		this.createdAt = DateUtils.getCurrentTime();
		this.email = email;
		this.message = message;
		this.type = type;
		this.parameters = String.format("cause:%s", causeId);
		this.notified = 0;
		this.status = -1;
	}



	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Long createdAt) {
		this.createdAt = createdAt;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Integer getNotified() {
		return notified;
	}

	public void setNotified(Integer notified) {
		this.notified = notified;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Long getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(Long updatedAt) {
		this.updatedAt = updatedAt;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
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
		TeamNotification other = (TeamNotification) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	public String getParameters() {
		return parameters;
	}

	public void setParameters(String parameters) {
		this.parameters = parameters;
	}

	
}
package com.vowme.dto;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "date", "hours", "id", "minutes", "positionId" })
public class DateParam implements Serializable {

	@JsonProperty("dateAsString")
	private String date;
	@JsonProperty("hours")
	private Integer hours;
	@JsonProperty("id")
	private Integer id;
	@JsonProperty("minutes")
	private Integer minutes;
	@JsonProperty("positionId")
	private Integer positionId;
	private final static long serialVersionUID = -8134143972707350061L;

	@JsonProperty("dateAsString")
	public String getDate() {
		return date;
	}

	@JsonProperty("dateAsString")
	public void setDate(String date) {
		this.date = date;
	}

	@JsonProperty("hours")
	public Integer getHours() {
		return hours;
	}

	@JsonProperty("hours")
	public void setHours(Integer hours) {
		this.hours = hours;
	}

	@JsonProperty("id")
	public Integer getId() {
		return id;
	}

	@JsonProperty("id")
	public void setId(Integer id) {
		this.id = id;
	}

	@JsonProperty("minutes")
	public Integer getMinutes() {
		return minutes;
	}

	@JsonProperty("minutes")
	public void setMinutes(Integer minutes) {
		this.minutes = minutes;
	}

	@JsonProperty("positionId")
	public Integer getPositionId() {
		return positionId;
	}

	@JsonProperty("positionId")
	public void setPositionId(Integer positionId) {
		this.positionId = positionId;
	}

}
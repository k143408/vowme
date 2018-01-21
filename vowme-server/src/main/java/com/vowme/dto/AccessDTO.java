package com.vowme.dto;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "date", "description", "disabledAccess", "duration", "id", "isExpired", "isShortlisted", "name",
		"orgServiceFocus", "organisationId", "organisationName", "serviceFocus", "source", "state", "stateCode",
		"status", "suburb" })

public class AccessDTO implements Serializable {

	@JsonProperty("date")
	private String date;
	@JsonProperty("description")
	private String description;
	@JsonProperty("disabledAccess")
	private Boolean disabledAccess;
	@JsonProperty("duration")
	private String duration;
	@JsonProperty("id")
	private Integer id;
	@JsonProperty("isExpired")
	private Boolean isExpired;
	@JsonProperty("isShortlisted")
	private Boolean isShortlisted;
	@JsonProperty("name")
	private String name;
	@JsonProperty("orgServiceFocus")
	private String orgServiceFocus;
	@JsonProperty("organisationId")
	private Integer organisationId;
	@JsonProperty("organisationName")
	private String organisationName;
	@JsonProperty("serviceFocus")
	private String serviceFocus;
	@JsonProperty("source")
	private String source;
	@JsonProperty("state")
	private String state;
	@JsonProperty("stateCode")
	private String stateCode;
	@JsonProperty("status")
	private String status;
	@JsonProperty("suburb")
	private String suburb;
	private final static long serialVersionUID = 2890369829098235099L;

	@JsonProperty("date")
	public String getDate() {
		return date;
	}

	@JsonProperty("date")
	public void setDate(String date) {
		this.date = date;
	}

	@JsonProperty("description")
	public String getDescription() {
		return description;
	}

	@JsonProperty("description")
	public void setDescription(String description) {
		this.description = description;
	}

	@JsonProperty("disabledAccess")
	public Boolean getDisabledAccess() {
		return disabledAccess;
	}

	@JsonProperty("disabledAccess")
	public void setDisabledAccess(Boolean disabledAccess) {
		this.disabledAccess = disabledAccess;
	}

	@JsonProperty("duration")
	public String getDuration() {
		return duration;
	}

	@JsonProperty("duration")
	public void setDuration(String duration) {
		this.duration = duration;
	}

	@JsonProperty("id")
	public Integer getId() {
		return id;
	}

	@JsonProperty("id")
	public void setId(Integer id) {
		this.id = id;
	}

	@JsonProperty("isExpired")
	public Boolean getIsExpired() {
		return isExpired;
	}

	@JsonProperty("isExpired")
	public void setIsExpired(Boolean isExpired) {
		this.isExpired = isExpired;
	}

	@JsonProperty("isShortlisted")
	public Boolean getIsShortlisted() {
		return isShortlisted;
	}

	@JsonProperty("isShortlisted")
	public void setIsShortlisted(Boolean isShortlisted) {
		this.isShortlisted = isShortlisted;
	}

	@JsonProperty("name")
	public String getName() {
		return name;
	}

	@JsonProperty("name")
	public void setName(String name) {
		this.name = name;
	}

	@JsonProperty("orgServiceFocus")
	public String getOrgServiceFocus() {
		return orgServiceFocus;
	}

	@JsonProperty("orgServiceFocus")
	public void setOrgServiceFocus(String orgServiceFocus) {
		this.orgServiceFocus = orgServiceFocus;
	}

	@JsonProperty("organisationId")
	public Integer getOrganisationId() {
		return organisationId;
	}

	@JsonProperty("organisationId")
	public void setOrganisationId(Integer organisationId) {
		this.organisationId = organisationId;
	}

	@JsonProperty("organisationName")
	public String getOrganisationName() {
		return organisationName;
	}

	@JsonProperty("organisationName")
	public void setOrganisationName(String organisationName) {
		this.organisationName = organisationName;
	}

	@JsonProperty("serviceFocus")
	public String getServiceFocus() {
		return serviceFocus;
	}

	@JsonProperty("serviceFocus")
	public void setServiceFocus(String serviceFocus) {
		this.serviceFocus = serviceFocus;
	}

	@JsonProperty("source")
	public String getSource() {
		return source;
	}

	@JsonProperty("source")
	public void setSource(String source) {
		this.source = source;
	}

	@JsonProperty("state")
	public String getState() {
		return state;
	}

	@JsonProperty("state")
	public void setState(String state) {
		this.state = state;
	}

	@JsonProperty("stateCode")
	public String getStateCode() {
		return stateCode;
	}

	@JsonProperty("stateCode")
	public void setStateCode(String stateCode) {
		this.stateCode = stateCode;
	}

	@JsonProperty("status")
	public String getStatus() {
		return status;
	}

	@JsonProperty("status")
	public void setStatus(String status) {
		this.status = status;
	}

	@JsonProperty("suburb")
	public String getSuburb() {
		return suburb;
	}

	@JsonProperty("suburb")
	public void setSuburb(String suburb) {
		this.suburb = suburb;
	}

}
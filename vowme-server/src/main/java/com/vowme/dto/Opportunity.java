
package com.vowme.dto;

import java.io.Serializable;
import java.util.List;

public class Opportunity implements Serializable
{

    private String createDate;
    private String description;
    private Boolean disabledAccess;
    private Boolean displayOnMap;
    private String duration;
    private String id;
    private Boolean isAgencyUninsured;
    private Integer minimumAge;
    private String name;
    private String organisationDescription;
    private String organisationId;
    private Object organisationLogo;
    private Object organisationLogoAltTag;
    private String organisationName;
    private Integer organisationPositionNumber;
    private String organisationType;
    private List<String> questions;
    private String reimbursement;
    private List<String> requirements = null;
    private List<String> requirementsAgencySupported = null;
    private String requirementsDescription;
    private String serviceFocus;
    private String shortDescription;
    private List<String> skills = null;
    private List<String> suitabilities = null;
    private String timeRequired;
    private String training;
    private String image;
    private String imagePremium;
    private Integer postcode;
    private String state;
    private String stateCode;
    private String suburb;
    private final static long serialVersionUID = -6892458240969322505L;
	public String getCreateDate() {
		return createDate;
	}
	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Boolean getDisabledAccess() {
		return disabledAccess;
	}
	public void setDisabledAccess(Boolean disabledAccess) {
		this.disabledAccess = disabledAccess;
	}
	public Boolean getDisplayOnMap() {
		return displayOnMap;
	}
	public void setDisplayOnMap(Boolean displayOnMap) {
		this.displayOnMap = displayOnMap;
	}
	public String getDuration() {
		return duration;
	}
	public void setDuration(String duration) {
		this.duration = duration;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public Boolean getIsAgencyUninsured() {
		return isAgencyUninsured;
	}
	public void setIsAgencyUninsured(Boolean isAgencyUninsured) {
		this.isAgencyUninsured = isAgencyUninsured;
	}
	public Integer getMinimumAge() {
		return minimumAge;
	}
	public void setMinimumAge(Integer minimumAge) {
		this.minimumAge = minimumAge;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getOrganisationDescription() {
		return organisationDescription;
	}
	public void setOrganisationDescription(String organisationDescription) {
		this.organisationDescription = organisationDescription;
	}
	public String getOrganisationId() {
		return organisationId;
	}
	public void setOrganisationId(String organisationId) {
		this.organisationId = organisationId;
	}
	public Object getOrganisationLogo() {
		return organisationLogo;
	}
	public void setOrganisationLogo(Object organisationLogo) {
		this.organisationLogo = organisationLogo;
	}
	public Object getOrganisationLogoAltTag() {
		return organisationLogoAltTag;
	}
	public void setOrganisationLogoAltTag(Object organisationLogoAltTag) {
		this.organisationLogoAltTag = organisationLogoAltTag;
	}
	public String getOrganisationName() {
		return organisationName;
	}
	public void setOrganisationName(String organisationName) {
		this.organisationName = organisationName;
	}
	public Integer getOrganisationPositionNumber() {
		return organisationPositionNumber;
	}
	public void setOrganisationPositionNumber(Integer organisationPositionNumber) {
		this.organisationPositionNumber = organisationPositionNumber;
	}
	public String getOrganisationType() {
		return organisationType;
	}
	public void setOrganisationType(String organisationType) {
		this.organisationType = organisationType;
	}
	public List<String> getQuestions() {
		return questions;
	}
	public void setQuestions(List<String> questions) {
		this.questions = questions;
	}
	public String getReimbursement() {
		return reimbursement;
	}
	public void setReimbursement(String reimbursement) {
		this.reimbursement = reimbursement;
	}
	public List<String> getRequirements() {
		return requirements;
	}
	public void setRequirements(List<String> requirements) {
		this.requirements = requirements;
	}
	public List<String> getRequirementsAgencySupported() {
		return requirementsAgencySupported;
	}
	public void setRequirementsAgencySupported(List<String> requirementsAgencySupported) {
		this.requirementsAgencySupported = requirementsAgencySupported;
	}
	public String getRequirementsDescription() {
		return requirementsDescription;
	}
	public void setRequirementsDescription(String requirementsDescription) {
		this.requirementsDescription = requirementsDescription;
	}
	public String getServiceFocus() {
		return serviceFocus;
	}
	public void setServiceFocus(String serviceFocus) {
		this.serviceFocus = serviceFocus;
	}
	public String getShortDescription() {
		return shortDescription;
	}
	public void setShortDescription(String shortDescription) {
		this.shortDescription = shortDescription;
	}
	public List<String> getSkills() {
		return skills;
	}
	public void setSkills(List<String> skills) {
		this.skills = skills;
	}
	public List<String> getSuitabilities() {
		return suitabilities;
	}
	public void setSuitabilities(List<String> suitabilities) {
		this.suitabilities = suitabilities;
	}
	public String getTimeRequired() {
		return timeRequired;
	}
	public void setTimeRequired(String timeRequired) {
		this.timeRequired = timeRequired;
	}
	public String getTraining() {
		return training;
	}
	public void setTraining(String training) {
		this.training = training;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	public String getImagePremium() {
		return imagePremium;
	}
	public void setImagePremium(String imagePremium) {
		this.imagePremium = imagePremium;
	}
	public Integer getPostcode() {
		return postcode;
	}
	public void setPostcode(Integer postcode) {
		this.postcode = postcode;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getStateCode() {
		return stateCode;
	}
	public void setStateCode(String stateCode) {
		this.stateCode = stateCode;
	}
	public String getSuburb() {
		return suburb;
	}
	public void setSuburb(String suburb) {
		this.suburb = suburb;
	}

}

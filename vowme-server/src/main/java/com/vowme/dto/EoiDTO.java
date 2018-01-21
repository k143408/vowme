package com.vowme.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "Email", "Findus", "FirstName", "Gender", "HasChronicHealthCondition",
		"HasNonEnglishSpeackingBackgroung", "IsDisabled", "IsIndigenous", "IsJobSeeker", "LastName", "OnLowIncome",
		"PhoneNumber", "Postcode", "Qualification", "Questions", "SendConfirmation", "SiteSource", "YearOfBirth" })
public class EoiDTO {

	@JsonProperty("Email")
	private String email;
	@JsonProperty("Findus")
	private String findus;
	@JsonProperty("FirstName")
	private String firstName;
	@JsonProperty("Gender")
	private String gender;
	@JsonProperty("HasChronicHealthCondition")
	private Boolean hasChronicHealthCondition;
	@JsonProperty("HasNonEnglishSpeackingBackgroung")
	private Boolean hasNonEnglishSpeackingBackgroung;
	@JsonProperty("IsDisabled")
	private Boolean isDisabled;
	@JsonProperty("IsIndigenous")
	private Boolean isIndigenous;
	@JsonProperty("IsJobSeeker")
	private Boolean isJobSeeker;
	@JsonProperty("LastName")
	private String lastName;
	@JsonProperty("OnLowIncome")
	private Boolean onLowIncome;
	@JsonProperty("PhoneNumber")
	private String phoneNumber;
	@JsonProperty("Postcode")
	private String postcode;
	@JsonProperty("Qualification")
	private String qualification;
	@JsonProperty("Questions")
	private String questions;
	@JsonProperty("SendConfirmation")
	private Boolean sendConfirmation;
	@JsonProperty("SiteSource")
	private String siteSource;
	@JsonProperty("YearOfBirth")
	private Integer yearOfBirth;
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getFindus() {
		return findus;
	}
	public void setFindus(String findus) {
		this.findus = findus;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public Boolean getHasChronicHealthCondition() {
		return hasChronicHealthCondition;
	}
	public void setHasChronicHealthCondition(Boolean hasChronicHealthCondition) {
		this.hasChronicHealthCondition = hasChronicHealthCondition;
	}
	public Boolean getHasNonEnglishSpeackingBackgroung() {
		return hasNonEnglishSpeackingBackgroung;
	}
	public void setHasNonEnglishSpeackingBackgroung(Boolean hasNonEnglishSpeackingBackgroung) {
		this.hasNonEnglishSpeackingBackgroung = hasNonEnglishSpeackingBackgroung;
	}
	public Boolean getIsDisabled() {
		return isDisabled;
	}
	public void setIsDisabled(Boolean isDisabled) {
		this.isDisabled = isDisabled;
	}
	public Boolean getIsIndigenous() {
		return isIndigenous;
	}
	public void setIsIndigenous(Boolean isIndigenous) {
		this.isIndigenous = isIndigenous;
	}
	public Boolean getIsJobSeeker() {
		return isJobSeeker;
	}
	public void setIsJobSeeker(Boolean isJobSeeker) {
		this.isJobSeeker = isJobSeeker;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public Boolean getOnLowIncome() {
		return onLowIncome;
	}
	public void setOnLowIncome(Boolean onLowIncome) {
		this.onLowIncome = onLowIncome;
	}
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	public String getPostcode() {
		return postcode;
	}
	public void setPostcode(String postcode) {
		this.postcode = postcode;
	}
	public String getQualification() {
		return qualification;
	}
	public void setQualification(String qualification) {
		this.qualification = qualification;
	}
	public String getQuestions() {
		return questions;
	}
	public void setQuestions(String questions) {
		this.questions = questions;
	}
	public Boolean getSendConfirmation() {
		return sendConfirmation;
	}
	public void setSendConfirmation(Boolean sendConfirmation) {
		this.sendConfirmation = sendConfirmation;
	}
	public String getSiteSource() {
		return siteSource;
	}
	public void setSiteSource(String siteSource) {
		this.siteSource = siteSource;
	}
	public Integer getYearOfBirth() {
		return yearOfBirth;
	}
	public void setYearOfBirth(Integer yearOfBirth) {
		this.yearOfBirth = yearOfBirth;
	}
	
	

}

package com.vowme.dto;

import java.io.Serializable;

public class LoginDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1757534915163522963L;

	private String emailAddress;
	private String firstName;
	private String lastName;
	private String loginProvider;
	private boolean mobileApp;
	private String password;
	private String postcode;
	private String providerKey;
	private String userName;
	private String yearOfBirth;
	private String cnic;
	

	public String getEmailAddress() {
		return emailAddress;
	}

	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getLoginProvider() {
		return loginProvider;
	}

	public void setLoginProvider(String loginProvider) {
		this.loginProvider = loginProvider;
	}

	public boolean isMobileApp() {
		return mobileApp;
	}

	public void setMobileApp(boolean mobileApp) {
		this.mobileApp = mobileApp;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPostcode() {
		return postcode;
	}

	public void setPostcode(String postcode) {
		this.postcode = postcode;
	}

	public String getProviderKey() {
		return providerKey;
	}

	public void setProviderKey(String providerKey) {
		this.providerKey = providerKey;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getYearOfBirth() {
		return yearOfBirth;
	}

	public void setYearOfBirth(String yearOfBirth) {
		this.yearOfBirth = yearOfBirth;
	}

	@Override
	public String toString() {
		return String.format(
				"LoginDTO [emailAddress=%s, firstName=%s, lastName=%s, loginProvider=%s, mobileApp=%s, password=%s, postcode=%s, providerKey=%s, userName=%s, yearOfBirth=%s]",
				emailAddress, firstName, lastName, loginProvider, mobileApp, password, postcode, providerKey, userName,
				yearOfBirth);
	}

	public String getCnic() {
		return cnic;
	}

	public void setCnic(String cnic) {
		this.cnic = cnic;
	}

}

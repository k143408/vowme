package com.vowme.app.models.api;

public class LocalVolunteerRegistrationModel extends PostApiModel {
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

    public LocalVolunteerRegistrationModel() {
    }

    public LocalVolunteerRegistrationModel(String userName, String firstName, String lastName, String emailAddress, String yearOfBirth, String postcode, String password, String loginProvider, String providerKey, boolean mobileApp) {
        this.userName = userName;
        this.firstName = firstName;
        this.lastName = lastName;
        this.emailAddress = emailAddress;
        this.yearOfBirth = yearOfBirth;
        this.postcode = postcode;
        this.password = password;
        this.loginProvider = loginProvider;
        this.providerKey = providerKey;
        this.mobileApp = mobileApp;
    }

    public String getUserName() {
        return this.userName;
    }

    public String getPassword() {
        return this.password;
    }
}

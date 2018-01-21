package com.vowme.app.models.api;

import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

public class VolunteerEoiModel extends PostApiModel {
    private String Email;
    private String Findus;
    private String FirstName;
    private String Gender;
    private boolean HasChronicHealthCondition;
    private boolean HasNonEnglishSpeackingBackgroung;
    private boolean IsDisabled;
    private boolean IsIndigenous;
    private boolean IsJobSeeker;
    private String LastName;
    private boolean OnLowIncome;
    private String PhoneNumber;
    private String Postcode;
    private String Qualification;
    private String Questions;
    private boolean SendConfirmation;
    private String SiteSource;
    private Integer YearOfBirth;

    public VolunteerEoiModel(JSONObject object, String source) {
        try {
            this.FirstName = object.getString("firstname");
            this.LastName = object.getString("lastname");
            this.Email = object.getString("email");
            this.YearOfBirth = Integer.valueOf(object.getInt("yearofbirth"));
            this.Postcode = object.getString("cnic");
            this.Gender = object.getString("gender") == "null" ? "" : object.getString("gender");
            this.PhoneNumber = object.getJSONObject("userInfo").getString("contactNumber1");
            this.Qualification = object.getJSONArray("userSkills").toString();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        this.SiteSource = source;
        this.Findus = "";
        this.SendConfirmation = false;
        this.IsIndigenous = false;
        this.HasNonEnglishSpeackingBackgroung = false;
        this.IsDisabled = false;
        this.OnLowIncome = false;
        this.IsJobSeeker = false;
        this.HasChronicHealthCondition = false;
        this.Questions = "";
    }

    public String getFirstName() {
        return this.FirstName;
    }

    public String getLastName() {
        return this.LastName;
    }

    public String getEmail() {
        return this.Email;
    }

    public Integer getYearOfBirth() {
        return this.YearOfBirth;
    }

    public void setYearOfBirth(Integer yearOfBirth) {
        this.YearOfBirth = yearOfBirth;
    }

    public String getPostcode() {
        return this.Postcode;
    }

    public void setPostcode(String postcode) {
        this.Postcode = postcode;
    }

    public String getPhoneNumber() {
        return this.PhoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.PhoneNumber = phoneNumber;
    }

    public String getGender() {
        return this.Gender;
    }

    public void setGender(String gender) {
        this.Gender = gender;
    }

    public String getQualification() {
        return this.Qualification;
    }

    public void setQualification(String qualification) {
        this.Qualification = qualification;
    }

    public JSONObject toJsonObject() {
        try {
            return new JSONObject("{\"eoi\":" + new Gson().toJson((Object) this) + "}");
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }
}

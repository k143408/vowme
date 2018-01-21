package com.vowme.app.models;

import com.vowme.app.models.api.PostApiModel;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class OpportunityItem extends PostApiModel {
    private String date;
    private Date dateObject;
    private String description;
    private boolean disabledAccess;
    private String duration;
    private int id;
    private boolean isExpired;
    private boolean isShortlisted;
    private String name;
    private String orgServiceFocus;
    private int organisationId;
    private String organisationName;
    private String serviceFocus;
    private String shortDescription;
    private String state;
    private String stateCode;
    private String suburb;

    public OpportunityItem() {

    }

    public OpportunityItem(JSONObject object, boolean isAutenticatedSearch) {
        try {
            id = object.getInt("id");
            name = object.getString("name");
            description = object.getString("description");
            serviceFocus = object.getJSONArray("causeSkills").toString();
            duration = setDuration(object.getInt("registrationdate") - object.getInt("registrationdeadline"));
            int visibile = object.getInt("visibilitystatus");
            disabledAccess = visibile == 1 ? true : false;
            suburb = object.getString("address");
            state = object.getString("city");
            stateCode = object.getString("zipcode");
            organisationId = object.getInt("id");
            organisationName = object.getJSONObject("user").getJSONObject("userInfo").getString("organizationName");
            shortDescription = object.getString("info");
            orgServiceFocus = object.getJSONArray("causetypes").toString();
            isExpired = false;
            isShortlisted = object.getJSONArray("shortlists").length() == 0 ? false:true ;
            date = object.getString("createdAt");
            SimpleDateFormat dateFormat = new SimpleDateFormat("EE dd MMM yy", Locale.ENGLISH);
            if (date == null || "null".equals(date) || "0".equals(date)) {
                dateObject = new Date();
                return;
            }
            dateObject = new Date(new Long(date));
            return;
        } catch (JSONException e) {
            e.printStackTrace();
            return;
        }
    }

    private String setDuration(int i) {
        if (i > 100) {
            return "Long Term";
        } else {
            return "Short Term";
        }
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getServiceFocus() {
        return this.serviceFocus;
    }

    public void setServiceFocus(String serviceFocus) {
        this.serviceFocus = serviceFocus;
    }

    public String getDuration() {
        return this.duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public boolean isDisabledAccess() {
        return this.disabledAccess;
    }

    public void setDisabledAccess(boolean disabledAccess) {
        this.disabledAccess = disabledAccess;
    }

    public String getSuburb() {
        return this.suburb;
    }

    public void setSuburb(String suburb) {
        this.suburb = suburb;
    }

    public String getState() {
        return this.state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getStateCode() {
        return this.stateCode;
    }

    public void setStateCode(String stateCode) {
        this.stateCode = stateCode;
    }

    public int getOrganisationId() {
        return this.organisationId;
    }

    public void setOrganisationId(int organisationId) {
        this.organisationId = organisationId;
    }

    public String getOrganisationName() {
        return this.organisationName;
    }

    public void setOrganisationName(String organisationName) {
        this.organisationName = organisationName;
    }

    public String getOrgServiceFocus() {
        return this.orgServiceFocus;
    }

    public void setOrgServiceFocus(String orgServiceFocus) {
        this.orgServiceFocus = orgServiceFocus;
    }

    public boolean isExpired() {
        return this.isExpired;
    }

    public void setIsExpired(boolean isExpired) {
        this.isExpired = isExpired;
    }

    public boolean isShortlisted() {
        return this.isShortlisted;
    }

    public void setIsShortlisted(boolean isShortlisted) {
        this.isShortlisted = isShortlisted;
    }

    public String getDate() {
        return this.date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getShortDescription() {
        return this.shortDescription;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    public Date getDateObject() {
        return this.dateObject;
    }

    public void setDateObject(Date dateObject) {
        this.dateObject = dateObject;
    }
}

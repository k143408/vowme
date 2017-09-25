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
            if (isAutenticatedSearch) {
                id = object.getInt("id");
                name = object.getString("name");
                description = object.getString("description");
                serviceFocus = object.getString("serviceFocus");
                duration = object.getString("duration");
                disabledAccess = object.getBoolean("disabledAccess");
                suburb = object.getString("suburb");
                state = object.getString("state");
                stateCode = object.getString("stateCode");
                organisationId = object.getInt("organisationId");
                organisationName = object.getString("organisationName");
                shortDescription = object.getString("description");
                orgServiceFocus = object.getString("orgServiceFocus");
                isExpired = object.getBoolean("isExpired");
                isShortlisted = object.getBoolean("isShortlisted");
                date = object.getString("date");
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd\'T\'HH:mm:ss");
                dateObject = dateFormat.parse(date);
                return;
            }
            id = object.getInt("Id");
            name = object.getString("Name");
            description = object.getString("Description");
            serviceFocus = object.getString("ServiceFocus");
            duration = object.getString("Duration");
            disabledAccess = object.getBoolean("DisabledAccess");
            suburb = object.getString("Suburb");
            state = object.getString("State");
            stateCode = object.getString("StateCode");
            organisationId = object.getInt("OrganisationId");
            organisationName = object.getString("OrganisationName");
            shortDescription = object.getString("ShortDescription");
            orgServiceFocus = object.getString("OrganisationType");
            isExpired = false;
            isShortlisted = false;
            date = object.getString("CreateDate");
            SimpleDateFormat dateFormat = new SimpleDateFormat("EE dd MMM yy", Locale.ENGLISH);
            if ("New".equals(date)) {
                dateObject = new Date();
                return;
            }
            dateObject = dateFormat.parse(date);
            return;
        } catch (JSONException e) {
            e.printStackTrace();
            return;
        } catch (ParseException e) {
            e.printStackTrace();
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

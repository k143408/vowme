package com.vowme.app.models.api;

import com.vowme.app.models.OpportunityItem;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class OpportunityDetails extends OpportunityItem {
    private boolean displayOnMap;
    private boolean hasExpressedInterest;
    private List<String> interests = new ArrayList();
    private Double latitude;
    private Double longitude;
    private String organisationDescription;
    private String reimbursement;
    private String timeRequired;
    private String training;

    public OpportunityDetails() {
        interests = new ArrayList();
    }

    public OpportunityDetails(JSONObject object, boolean isAutenticatedSearch) {
        super(object, isAutenticatedSearch);
        try {
            if (isAutenticatedSearch) {

                this.timeRequired = object.getString("timeRequired") == "null" ? "" : object.getString("timeRequired");
                this.training = object.getString("training") == "null" ? "" : object.getString("training");
                addInterest(object.getJSONArray("interests"));
                this.reimbursement = object.getString("reimbursement") == "null" ? "" : object.getString("reimbursement");
                this.latitude = Double.valueOf(object.getDouble("latitude"));
                this.longitude = Double.valueOf(object.getDouble("longitude"));
                this.organisationDescription = object.getString("organisationDescription");
                this.hasExpressedInterest = object.getBoolean("hasExpressedInterest");
                this.displayOnMap = true;
                return;
            }
            this.timeRequired = object.getString("TimeRequired") == "null" ? "" : object.getString("TimeRequired");
            this.training = object.getString("Training") == "null" ? "" : object.getString("Training");
            addInterest(object.getJSONArray("Skills"));
            this.reimbursement = object.getString("Reimbursement") == "null" ? "" : object.getString("Reimbursement");
            this.latitude = Double.valueOf(object.getDouble("Latitude"));
            this.longitude = Double.valueOf(object.getDouble("Longitude"));
            this.organisationDescription = object.getString("OrganisationDescription");
            this.displayOnMap = object.getBoolean("DisplayOnMap");
            this.hasExpressedInterest = false;
        } catch (JSONException e) {
            e.printStackTrace();
            return;
        }
    }

    private void addInterest(JSONArray list) {
        int i = 0;
        while (i < list.length()) {
            try {
                this.interests.add(list.getString(i));
                i++;
            } catch (JSONException e) {
                e.printStackTrace();
                return;
            }
        }
    }

    public String getTimeRequired() {
        return this.timeRequired;
    }

    public List<String> getInterests() {
        return this.interests;
    }

    public String getTraining() {
        return this.training;
    }

    public String getReimbursement() {
        return this.reimbursement;
    }

    public Double getLatitude() {
        return this.latitude;
    }

    public Double getLongitude() {
        return this.longitude;
    }

    public String getOrganisationDescription() {
        return this.organisationDescription;
    }

    public boolean isDisplayOnMap() {
        return this.displayOnMap;
    }

    public boolean isHasExpressedInterest() {
        return this.hasExpressedInterest;
    }

    public void setHasExpressedInterest(boolean hasExpressedInterest) {
        this.hasExpressedInterest = hasExpressedInterest;
    }
}

package com.vowme.app.models.api;

import org.json.JSONException;
import org.json.JSONObject;

public class VolunteerOnCallAvailableModel extends PostApiModel {
    public boolean availabileAllTimes;
    public boolean availableForEmergencyResponse;
    public boolean availableForGeneralVolunteering;
    public boolean availableForSpecialEvents;
    public int id;

    public VolunteerOnCallAvailableModel() {
    }

    public VolunteerOnCallAvailableModel(JSONObject object) {
        try {
            this.id = object.getInt("id");
            this.availableForGeneralVolunteering = object.getBoolean("availableForGeneralVolunteering");
            this.availableForSpecialEvents = object.getBoolean("availableForSpecialEvents");
            this.availableForEmergencyResponse = object.getBoolean("availableForEmergencyResponse");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}

package com.vowme.app.models.api;

import org.json.JSONException;
import org.json.JSONObject;

public class OpportunityExperience extends PostApiModel {
    public int id;
    public boolean isDisplayedExperience;
    public String name;
    public String organisationName;

    public OpportunityExperience(JSONObject object) {
        try {
            this.id = object.getInt("id");
            this.name = object.getString("name") == "null" ? "" : object.getString("name");
            this.organisationName = object.getString("organisationName") == "null" ? "" : object.getString("organisationName");
            this.isDisplayedExperience = object.getBoolean("isDisplayedExperience");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}

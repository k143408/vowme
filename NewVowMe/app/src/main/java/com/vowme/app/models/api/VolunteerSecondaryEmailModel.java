package com.vowme.app.models.api;

import org.json.JSONException;
import org.json.JSONObject;

public class VolunteerSecondaryEmailModel extends PostApiModel {
    public String oldSecondaryEmail;
    public String primaryEmail;
    public String secondaryEmail;

    public VolunteerSecondaryEmailModel(JSONObject object) {
        try {
            this.oldSecondaryEmail = object.getString("oldSecondaryEmail");
            this.primaryEmail = object.getString("primaryEmail");
            this.secondaryEmail = object.getString("secondaryEmail");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}

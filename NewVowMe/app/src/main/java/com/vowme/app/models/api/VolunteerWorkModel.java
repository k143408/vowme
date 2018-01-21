package com.vowme.app.models.api;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class VolunteerWorkModel extends PostApiModel {
    public String compagny;
    public List<OpportunityExperience> experiences = new ArrayList<>();
    public int id;
    public String jobTitle;
    public String linkedin;
    public String workExperience;

    public VolunteerWorkModel() {

    }

    public VolunteerWorkModel(JSONObject object) {
        try {
            this.id = object.getInt("id");
            this.jobTitle = object.getString("jobTitle") == "null" ? "" : object.getString("jobTitle");
            this.compagny = object.getString("compagny") == "null" ? "" : object.getString("compagny");
            this.workExperience = object.getString("workExperience") == "null" ? "" : object.getString("workExperience");
            this.linkedin = object.getString("linkedin") == "null" ? "" : object.getString("linkedin");
            this.experiences = extractVolunteerExperiences(object.getJSONArray("experiences"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    protected List<OpportunityExperience> extractVolunteerExperiences(JSONArray list) {
        List<OpportunityExperience> result = new ArrayList();
        for (int i = 0; i < list.length(); i++) {
            try {
                result.add(new OpportunityExperience(list.getJSONObject(i)));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return result;
    }
}

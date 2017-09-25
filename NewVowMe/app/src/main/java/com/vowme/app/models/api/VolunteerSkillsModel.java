package com.vowme.app.models.api;

import org.json.JSONException;
import org.json.JSONObject;

public class VolunteerSkillsModel extends PostApiModel {
    public Integer classificationId;
    public Integer experienceId;
    public Integer subClassificationId;

    public VolunteerSkillsModel(int classificationId, int subClassificationId, int experienceId) {
        this.classificationId = Integer.valueOf(classificationId);
        this.subClassificationId = Integer.valueOf(subClassificationId);
        this.experienceId = Integer.valueOf(experienceId);
    }

    public VolunteerSkillsModel(JSONObject object) {
        try {
            this.classificationId = Integer.valueOf(object.getInt("classificationId"));
            this.subClassificationId = Integer.valueOf(object.getInt("subClassificationId"));
            this.experienceId = Integer.valueOf(object.getInt("experienceId"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}

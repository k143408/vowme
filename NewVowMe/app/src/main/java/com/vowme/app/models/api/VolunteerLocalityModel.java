package com.vowme.app.models.api;

import com.vowme.app.models.lookUp.Lookup;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class VolunteerLocalityModel extends PostApiModel {
    public String location;
    public List<Lookup> suburbs;
    public boolean surroundingAreas;

    public VolunteerLocalityModel(String location, boolean surroundingAreas) {
        this.location = location;
        this.surroundingAreas = surroundingAreas;
        this.suburbs = new ArrayList();
    }

    public VolunteerLocalityModel(JSONObject object) {
        try {
            this.location = object.getString("location");
            this.surroundingAreas = object.getBoolean("surroundingAreas");
            this.suburbs = new ArrayList();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}

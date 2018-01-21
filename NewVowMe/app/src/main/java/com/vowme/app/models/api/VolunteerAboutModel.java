package com.vowme.app.models.api;

import com.vowme.app.models.lookUp.Lookup;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class VolunteerAboutModel extends PostApiModel {
    public String hobbies = "";
    public int id;
    public String qualification = "";
    public List<Lookup> requirements = new ArrayList();
    List<Lookup> languages = new ArrayList();

    public VolunteerAboutModel(){
    }
    public VolunteerAboutModel(JSONObject object) {
        try {
            this.id = object.getInt("id");
            this.qualification = object.getString("qualification") == "null" ? "" : object.getString("qualification");
            this.hobbies = object.getString("hobbies") == "null" ? "" : object.getString("hobbies");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}

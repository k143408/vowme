package com.vowme.app.models.api;

import com.vowme.app.models.lookUp.Lookup;
import com.vowme.app.models.lookUp.LookupChild;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class VolunteerHomeProfileModel extends PostApiModel {
    public VolunteerAvailableModel avaibilities = new VolunteerAvailableModel();
    public VolunteerBaseModel bases;
    public List<Lookup> causes = new ArrayList<>();
    public List<Lookup> durations = new ArrayList<>();
    public List<Lookup> interests = new ArrayList<>();
    public List<Lookup> languages = new ArrayList<>();
    public VolunteerLocalityModel locality = new VolunteerLocalityModel();
    public List<Lookup> programs = new ArrayList<>();
    public List<Lookup> requirements = new ArrayList<>();
    public VolunteerAboutModel skillHobbies = new VolunteerAboutModel();
    public List<VolunteerSkillsModel> skills = new ArrayList<>();
    public List<LookupChild> subClassifications = new ArrayList<>();
    public VolunteerWorkModel work = new VolunteerWorkModel();

    public VolunteerHomeProfileModel() {

    }

    public VolunteerHomeProfileModel(JSONObject object) {
        try {
            this.bases = new VolunteerBaseModel(object);
            if (object.has("avaibilities"))
            this.avaibilities = new VolunteerAvailableModel(object.getJSONObject("avaibilities"));
            if (object.has("locality"))
            this.locality = new VolunteerLocalityModel(object.getJSONObject("locality"));
            if (object.has("skillHobbies"))
            this.skillHobbies = new VolunteerAboutModel(object.getJSONObject("skillHobbies"));
            if (object.has("work"))
            this.work = new VolunteerWorkModel(object.getJSONObject("work"));
            if (object.has("durations"))
            this.durations = extractLookupData(object.getJSONArray("durations"));
            if (object.has("causes"))
            this.causes = extractLookupData(object.getJSONArray("causes"));
            if (object.has("interests"))
            this.interests = extractLookupData(object.getJSONArray("interests"));
            if (object.has("requirements"))
            this.requirements = extractLookupData(object.getJSONArray("requirements"));
            if (object.has("languages"))
            this.languages = extractLookupData(object.getJSONArray("languages"));
            if (object.has("programs"))
            this.programs = extractLookupData(object.getJSONArray("programs"));
            this.subClassifications = new ArrayList();
            if (object.has("skills"))
            this.skills = extractVolunteerSkillsModelData(object.getJSONArray("skills"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    protected List<VolunteerSkillsModel> extractVolunteerSkillsModelData(JSONArray list) {
        List<VolunteerSkillsModel> result = new ArrayList();
        for (int i = 0; i < list.length(); i++) {
            try {
                result.add(new VolunteerSkillsModel(list.getJSONObject(i)));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    protected List<Lookup> extractLookupData(JSONArray list) {
        List<Lookup> result = new ArrayList();
        for (int i = 0; i < list.length(); i++) {
            try {
                result.add(new Lookup(list.getJSONObject(i)));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return result;
    }
}

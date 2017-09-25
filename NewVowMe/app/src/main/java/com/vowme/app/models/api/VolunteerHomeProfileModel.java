package com.vowme.app.models.api;

import com.vowme.app.models.lookUp.Lookup;
import com.vowme.app.models.lookUp.LookupChild;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class VolunteerHomeProfileModel extends PostApiModel {
    public VolunteerAvailableModel avaibilities;
    public VolunteerBaseModel bases;
    public List<Lookup> causes;
    public List<Lookup> durations;
    public List<Lookup> interests;
    public List<Lookup> languages;
    public VolunteerLocalityModel locality;
    public List<Lookup> programs;
    public List<Lookup> requirements;
    public VolunteerAboutModel skillHobbies;
    public List<VolunteerSkillsModel> skills;
    public List<LookupChild> subClassifications;
    public VolunteerWorkModel work;

    public VolunteerHomeProfileModel(JSONObject object) {
        try {
            this.bases = new VolunteerBaseModel(object.getJSONObject("bases"));
            this.avaibilities = new VolunteerAvailableModel(object.getJSONObject("avaibilities"));
            this.locality = new VolunteerLocalityModel(object.getJSONObject("locality"));
            this.skillHobbies = new VolunteerAboutModel(object.getJSONObject("skillHobbies"));
            this.work = new VolunteerWorkModel(object.getJSONObject("work"));
            this.durations = extractLookupData(object.getJSONArray("durations"));
            this.causes = extractLookupData(object.getJSONArray("causes"));
            this.interests = extractLookupData(object.getJSONArray("interests"));
            this.requirements = extractLookupData(object.getJSONArray("requirements"));
            this.languages = extractLookupData(object.getJSONArray("languages"));
            this.programs = extractLookupData(object.getJSONArray("programs"));
            this.subClassifications = new ArrayList();
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

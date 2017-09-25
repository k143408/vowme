package com.vowme.app.models.api;

import com.vowme.app.utilities.helpers.JSONHelper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class VolunteerAvailableModel extends PostApiModel {
    public List<Integer> afternoon = new ArrayList();
    public boolean availabileAllTimes;
    public long availability;
    public boolean availableForEmergencyResponse;
    public boolean availableForGeneralVolunteering;
    public boolean availableForSpecialEvents;
    public List<Integer> evening = new ArrayList();
    public int id;
    public List<Integer> morning = new ArrayList();
    public List<List<String>> sentenceAvaibility = new ArrayList();

    public VolunteerAvailableModel(JSONObject object) {
        try {
            this.id = object.getInt("id");
            this.availability = object.getLong("availability");
            addInt(object.getJSONArray("morning"), this.morning);
            addInt(object.getJSONArray("afternoon"), this.afternoon);
            addInt(object.getJSONArray("evening"), this.evening);
            this.availabileAllTimes = object.getBoolean("availabileAllTimes");
            this.availableForGeneralVolunteering = object.getBoolean("availableForGeneralVolunteering");
            this.availableForSpecialEvents = object.getBoolean("availableForSpecialEvents");
            this.availableForEmergencyResponse = object.getBoolean("availableForEmergencyResponse");
            createSentenceList(object.getJSONArray("sentenceAvaibility"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void addInt(JSONArray list, List<Integer> listToUpdate) {
        int i = 0;
        while (i < list.length()) {
            try {
                listToUpdate.add(Integer.valueOf(list.getInt(i)));
                i++;
            } catch (JSONException e) {
                e.printStackTrace();
                return;
            }
        }
    }

    private void createSentenceList(JSONArray list) {
        int i = 0;
        while (i < list.length()) {
            try {
                JSONArray values = JSONHelper.getJSONArrayFromString(list.getString(i));
                List<String> tmp = new ArrayList();
                for (int j = 0; j < values.length(); j++) {
                    tmp.add(values.getString(j));
                }
                this.sentenceAvaibility.add(tmp);
                i++;
            } catch (JSONException e) {
                e.printStackTrace();
                return;
            }
        }
    }
}

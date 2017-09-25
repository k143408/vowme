package com.vowme.app.models.api;

import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class SearchOpportunitiesParameter extends PostApiModel {
    public AdvanceSearch AdvanceSearchFilters;
    public List<String> Durations;
    public List<String> Interests;
    public boolean IsWidenLocation;
    public String Keywords;
    public List<String> Locations;
    public String WidenLocation;

    public SearchOpportunitiesParameter() {
        this.Interests = new ArrayList();
        this.Durations = new ArrayList();
        this.Locations = new ArrayList();
        this.AdvanceSearchFilters = new AdvanceSearch();
        this.Keywords = "";
        this.WidenLocation = "";
    }

    public SearchOpportunitiesParameter(JSONObject object) {
        this.Interests = new ArrayList();
        this.Durations = new ArrayList();
        this.Locations = new ArrayList();
        try {
            this.Keywords = object.getString("Keywords");
            this.WidenLocation = object.getString("WidenLocation");
            this.IsWidenLocation = object.getBoolean("IsWidenLocation");
            addString(object.getJSONArray("Interests"), this.Interests);
            addString(object.getJSONArray("Durations"), this.Durations);
            addString(object.getJSONArray("Locations"), this.Locations);
            this.AdvanceSearchFilters = new AdvanceSearch(object.getJSONObject("AdvanceSearchFilters"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public JSONObject toJsonObject() {
        try {
            return new JSONObject("{\"search\":" + new Gson().toJson((Object) this) + "}");
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }
}

package com.vowme.app.models.api;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class AdvanceSearch extends PostApiModel {
    public String AvailableDay;
    public String AvailableEvening;
    public String AvailableWeekday;
    public String AvailableWeekend;
    public boolean DoBlackList;
    public String EventId;
    public String EventVolunteering;
    public String IncludeCorporateOrganisationOpportunities;
    public String IsAccessible;
    public String OrganisationId;
    public List<String> Organisations;
    public List<String> Suitabilities;
    public List<String> Types;
    public boolean VrcOrganisation;

    public AdvanceSearch() {
        this.Organisations = new ArrayList();
        this.Suitabilities = new ArrayList();
        this.Types = new ArrayList();
        this.AvailableDay = "";
        this.AvailableEvening = "";
        this.AvailableWeekday = "";
        this.AvailableWeekend = "";
        this.OrganisationId = "";
        this.EventId = "";
        this.EventVolunteering = "";
        this.IsAccessible = "";
        this.IncludeCorporateOrganisationOpportunities = "";
    }

    public AdvanceSearch(JSONObject object) {
        this.Organisations = new ArrayList();
        this.Suitabilities = new ArrayList();
        this.Types = new ArrayList();
        try {
            this.AvailableDay = object.getString("AvailableDay");
            this.AvailableEvening = object.getString("AvailableEvening");
            this.AvailableWeekday = object.getString("AvailableWeekday");
            this.AvailableWeekend = object.getString("AvailableWeekend");
            this.OrganisationId = object.getString("OrganisationId");
            this.EventId = object.getString("EventId");
            this.EventVolunteering = object.getString("EventVolunteering");
            this.IsAccessible = object.getString("IsAccessible");
            this.IncludeCorporateOrganisationOpportunities = object.getString("IncludeCorporateOrganisationOpportunities");
            this.VrcOrganisation = object.getBoolean("VrcOrganisation");
            this.DoBlackList = object.getBoolean("DoBlackList");
            addString(object.getJSONArray("Organisations"), this.Organisations);
            addString(object.getJSONArray("Suitabilities"), this.Suitabilities);
            addString(object.getJSONArray("Types"), this.Types);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}

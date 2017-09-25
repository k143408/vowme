package com.vowme.app.models.api;

import org.json.JSONException;
import org.json.JSONObject;

public class AddressBaseModel extends PostApiModel {
    public int id;
    public Integer postcode;
    public int stateId;
    public String stateName;
    public String street;
    public int suburbId;
    public String suburbName;

    public AddressBaseModel(JSONObject object) {
        try {
            this.id = object.getInt("id");
            this.suburbId = object.getInt("suburbId");
            this.street = object.getString("street") == "null" ? "" : object.getString("street");
            this.suburbName = object.getString("suburbName") == "null" ? "" : object.getString("suburbName");
            this.stateName = object.getString("stateName");
            this.postcode = Integer.valueOf(object.getInt("postcode"));
            this.stateId = object.getInt("stateId");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}

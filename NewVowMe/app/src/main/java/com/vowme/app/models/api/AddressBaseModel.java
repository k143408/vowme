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
            this.id = 0;//object.getInt("id");
            this.suburbId = 0;//object.getInt("suburbId");
            this.street = object.getString("address") == "null" ? "" : object.getString("address");
            this.suburbName = "";//object.getString("suburbName") == "null" ? "" : object.getString("suburbName");
            this.stateName = object.getString("city");
            this.postcode = Integer.valueOf(object.getInt("zipcode"));
            this.stateId = 0;//object.getInt("stateId");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}

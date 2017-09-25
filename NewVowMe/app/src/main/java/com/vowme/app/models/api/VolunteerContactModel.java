package com.vowme.app.models.api;

import org.json.JSONException;
import org.json.JSONObject;

public class VolunteerContactModel extends PostApiModel {
    public AddressBaseModel address;
    public String email;
    public String emergencyName;
    public String emergencyPhone;
    public int id;
    public PhoneModel phone;

    public VolunteerContactModel(JSONObject object) {
        try {
            this.id = object.getInt("id");
            this.email = object.getString("email") == "null" ? "" : object.getString("email");
            this.phone = new PhoneModel(object.getJSONObject("phone"));
            this.address = new AddressBaseModel(object.getJSONObject("address"));
            this.emergencyName = object.getString("emergencyName") == "null" ? "" : object.getString("emergencyName");
            this.emergencyPhone = object.getString("emergencyPhone") == "null" ? "" : object.getString("emergencyPhone");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}

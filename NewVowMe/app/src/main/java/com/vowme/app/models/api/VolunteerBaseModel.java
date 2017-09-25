package com.vowme.app.models.api;

import org.json.JSONException;
import org.json.JSONObject;

public class VolunteerBaseModel extends PostApiModel {
    public AddressBaseModel address;
    public String age;
    public String avatar;
    public String email;
    public String enteredIntoSystemDate;
    public String everydayTitle;
    public String firstName;
    public String gender;
    public Integer id;
    public String lastName;
    public boolean matchFound;
    public int status;
    public String title;

    public VolunteerBaseModel(JSONObject object) {
        try {
            this.id = Integer.valueOf(object.getInt("id"));
            this.status = object.getInt("status");
            this.email = object.getString("email") == "null" ? "" : object.getString("email");
            this.title = object.getString("title") == "null" ? "" : object.getString("title");
            this.firstName = object.getString("firstName") == "null" ? "" : object.getString("firstName");
            this.lastName = object.getString("lastName") == "null" ? "" : object.getString("lastName");
            this.age = object.getString("age") == "null" ? "" : object.getString("age");
            this.gender = object.getString("gender") == "null" ? "" : object.getString("gender");
            this.address = new AddressBaseModel(object.getJSONObject("address"));
            this.enteredIntoSystemDate = object.getString("enteredIntoSystemDate") == "null" ? "" : object.getString("enteredIntoSystemDate");
            this.everydayTitle = object.getString("everydayTitle") == "null" ? "" : object.getString("everydayTitle");
            this.matchFound = object.getBoolean("matchFound");
            this.avatar = object.getString("avatar") == "null" ? "" : object.getString("avatar");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}

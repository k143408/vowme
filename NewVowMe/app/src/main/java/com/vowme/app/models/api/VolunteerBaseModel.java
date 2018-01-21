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
            this.status = object.getBoolean("verified") ? 1 : 0;
            this.email = object.getString("email") == "null" ? "" : object.getString("email");
            this.title = object.getString("username") == "null" ? "" : object.getString("username");
            this.firstName = object.getString("firstname") == "null" ? "" : object.getString("firstname");
            this.lastName = object.getString("lastname") == "null" ? "" : object.getString("lastname");
            this.age = object.getString("yearofbirth") == "null" ? "" : object.getString("yearofbirth");
            this.gender = object.getString("gender") == "null" ? "" : object.getString("gender");
            this.address = new AddressBaseModel(object.getJSONObject("userInfo"));
            this.enteredIntoSystemDate = object.getString("createdAt") == "0" ? "" : object.getString("createdAt");
            this.everydayTitle = "";//object.getString("everydayTitle") == "null" ? "" : object.getString("everydayTitle");
            this.matchFound = true;//object.getBoolean("matchFound");
            if (object.has("avatar"))
            this.avatar = object.getString("avatar") == "null" ? "" : object.getString("avatar");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}

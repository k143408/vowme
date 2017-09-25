package com.vowme.app.models.api;

import org.json.JSONException;
import org.json.JSONObject;

public class UserAccountAPI extends PostApiModel {
    public String email;
    public boolean hasPassword;
    public int userId;
    public String userName;

    public UserAccountAPI(JSONObject object) {
        try {
            this.userName = object.getString("userName");
            this.email = object.getString("email");
            this.userId = object.getInt("userId");
            this.hasPassword = object.getBoolean("hasPassword");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}

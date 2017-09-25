package com.vowme.app.models.api;

import org.json.JSONException;
import org.json.JSONObject;

public class AppUserLoginAPI extends PostApiModel {
    public String loginProvider;
    public String providerKey;
    public int userId;

    public AppUserLoginAPI(JSONObject object) {
        try {
            this.loginProvider = object.getString("loginProvider");
            this.providerKey = object.getString("providerKey");
            this.userId = 0;
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}

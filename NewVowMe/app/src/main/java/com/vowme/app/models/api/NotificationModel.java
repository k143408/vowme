package com.vowme.app.models.api;

import org.json.JSONException;
import org.json.JSONObject;

public class NotificationModel extends PostApiModel {
    public Integer frequencyId;
    public int notificationId;

    public NotificationModel(int notificationId, Integer frequencyId) {
        this.notificationId = notificationId;
        this.frequencyId = frequencyId;
    }

    public NotificationModel(JSONObject object) {
        try {
            this.notificationId = object.getInt("notificationId");
            this.frequencyId = Integer.valueOf(object.getInt("frequencyId"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}

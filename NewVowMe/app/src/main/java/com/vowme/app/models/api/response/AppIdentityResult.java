package com.vowme.app.models.api.response;

import com.facebook.internal.AnalyticsEvents;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class AppIdentityResult {
    public List<String> errors;
    public boolean succeeded;

    public AppIdentityResult() {
        this.errors = new ArrayList();
        this.succeeded = false;
    }

    public AppIdentityResult(final String errors, boolean succeeded) {
        this.errors = new ArrayList<String>() {
        };
        this.succeeded = succeeded;
    }

    public AppIdentityResult(JSONObject object) {
        this.errors = new ArrayList();
        try {
            //addString(object.getJSONArray(Errors.), this.errors);
            this.succeeded = object.getBoolean(AnalyticsEvents.PARAMETER_SHARE_OUTCOME_SUCCEEDED);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void addString(JSONArray list, List<String> listToUpdate) {
        int i = 0;
        while (i < list.length()) {
            try {
                listToUpdate.add(list.getString(i));
                i++;
            } catch (JSONException e) {
                e.printStackTrace();
                return;
            }
        }
    }
}

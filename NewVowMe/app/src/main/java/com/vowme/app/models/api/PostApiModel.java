package com.vowme.app.models.api;

import com.vowme.app.utilities.helpers.JSONHelper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class PostApiModel {
    protected static final String NULL = "null";

    public JSONObject toJsonObject() {
        return JSONHelper.ToJSONObject(this);
    }

    protected void addString(JSONArray list, List<String> listToUpdate) {
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

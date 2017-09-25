package com.vowme.app.utilities.helpers;

import android.text.TextUtils;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class JSONHelper {
    public static JSONArray getJSONArrayFromString(String result) {
        if (TextUtils.isEmpty(result)) {
            result = "{\"Values\":[]}";
        } else {
            result = "{\"Values\":" + result + "}";
        }
        try {
            return new JSONArray(new JSONObject(result).getString("Values"));
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static JSONObject ToJSONObject(Object obj) {
        try {
            return new JSONObject(new Gson().toJson(obj));
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static JSONArray ToJSONArray(Object obj) {
        try {
            return new JSONArray(new Gson().toJson(obj));
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }
}

package com.vowme.app.utilities.helpers.sharedPreferences;

import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class UserShortlistSharedDataHelper {
    private static final String SHORTLISTED = "SHORTLISTED";

    public static void putShortlist(SharedPreferences userDefaults, JSONArray opportunities) {
        List<String> result = new ArrayList();
        for (int i = 0; i < opportunities.length(); i++) {
            try {
                result.add(String.valueOf(opportunities.getJSONObject(i).getInt("id")));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        Editor editor = userDefaults.edit();
        editor.putStringSet(SHORTLISTED, new HashSet(result));
        editor.commit();
    }

    public static List<String> getShortlist(SharedPreferences userDefaults) {
        List<String> result = new ArrayList();
        result.addAll(userDefaults.getStringSet(SHORTLISTED, new HashSet()));
        return result;
    }

    public static void removeFromShortlist(SharedPreferences userDefaults, Integer oppId) {
        List result = new ArrayList();
        result.addAll(userDefaults.getStringSet(SHORTLISTED, new HashSet()));
        result.remove(oppId.toString());
        putShortlist(userDefaults, result);
    }

    public static void addToShortlist(SharedPreferences userDefaults, Integer oppId) {
        List result = new ArrayList();
        result.addAll(userDefaults.getStringSet(SHORTLISTED, new HashSet()));
        result.add(oppId.toString());
        putShortlist(userDefaults, result);
    }

    public static boolean isShortlisted(SharedPreferences userDefaults, Integer oppId) {
        List<String> result = new ArrayList();
        result.addAll(userDefaults.getStringSet(SHORTLISTED, new HashSet()));
        return result.contains(oppId.toString());
    }

    public static void clearShortlisted(SharedPreferences userDefaults) {
        putShortlist(userDefaults, new ArrayList());
    }

    private static void putShortlist(SharedPreferences userDefaults, List<String> opportunities) {
        Editor editor = userDefaults.edit();
        editor.putStringSet(SHORTLISTED, new HashSet(opportunities));
        editor.commit();
    }
}

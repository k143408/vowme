package com.vowme.app.utilities.helpers.sharedPreferences;

import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class UserSearchSharedLocationFieldHelper extends BaseSharedDataHelper {
    private static final String LOCATIONS = "locations_filter";
    private static final String LOCATIONS_NAME = "locations_name_filter";
    private static final String RECENTS = "recents";

    public static void clearUserDatas(SharedPreferences userDefaults) {
        Editor editor = userDefaults.edit();
        putRecents(userDefaults, new ArrayList());
        putNameLocations(userDefaults, new ArrayList());
        editor.commit();
    }

    public static void putRecents(SharedPreferences userDefaults, List<String> recents) {
        BaseSharedDataHelper.putData(userDefaults, recents, RECENTS);
    }

    public static List<String> getRecents(SharedPreferences userDefaults) {
        List<String> result = new ArrayList();
        result.addAll(userDefaults.getStringSet(RECENTS, new HashSet()));
        return result;
    }

    public static void addToRecents(SharedPreferences userDefaults, String recent) {
        List<String> result = new ArrayList();
        result.addAll(userDefaults.getStringSet(RECENTS, new HashSet()));
        if (!result.contains(recent)) {
            result.add(recent);
        }
        putRecents(userDefaults, result);
    }

    public static void putNameLocations(SharedPreferences userDefaults, List<String> locations) {
        BaseSharedDataHelper.putData(userDefaults, locations, LOCATIONS_NAME);
    }

    public static List<String> getNameLocations(SharedPreferences userDefaults) {
        return BaseSharedDataHelper.getData(userDefaults, LOCATIONS_NAME);
    }
}

package com.vowme.app.utilities.helpers.sharedPreferences;

import android.content.SharedPreferences;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class UserBookmarkSharedDataHelper extends BaseSharedDataHelper {
    private static final String BOOKMARK = "BOOKMARK";

    public static List<String> getBookmarks(SharedPreferences userDefaults) {
        List<String> result = new ArrayList();
        result.addAll(userDefaults.getStringSet(BOOKMARK, new HashSet()));
        return result;
    }

    public static void addToBookmark(SharedPreferences userDefaults, String recent) {
        List<String> result = new ArrayList();
        result.addAll(userDefaults.getStringSet(BOOKMARK, new HashSet()));
        if (!result.contains(recent)) {
            result.add(recent);
        }
        BaseSharedDataHelper.putData(userDefaults, result, BOOKMARK);
    }

    public static void deleteFromBookmark(SharedPreferences userDefaults, String recent) {
        List<String> result = new ArrayList();
        result.addAll(userDefaults.getStringSet(BOOKMARK, new HashSet()));
        if (result.contains(recent)) {
            result.remove(recent);
        }
        BaseSharedDataHelper.putData(userDefaults, result, BOOKMARK);
    }

    public static void removeFromBookmark() {
    }
}

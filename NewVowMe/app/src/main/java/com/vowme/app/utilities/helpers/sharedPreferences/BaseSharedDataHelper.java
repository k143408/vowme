package com.vowme.app.utilities.helpers.sharedPreferences;

import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class BaseSharedDataHelper {
    protected static void putData(SharedPreferences userDefaults, List<String> values, String keyData) {
        Editor editor = userDefaults.edit();
        editor.putStringSet(keyData, new HashSet(values));
        editor.commit();
    }

    protected static List<String> getData(SharedPreferences userDefaults, String keyData) {
        List<String> result = new ArrayList();
        result.addAll(userDefaults.getStringSet(keyData, new HashSet()));
        return result;
    }
}

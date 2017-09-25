package com.vowme.app.utilities.helpers.sharedPreferences;

import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class UserAdjustmentSharedDataHelper {
    private static final String CAUSES = "causes";
    private static final String CAUSES_NAME = "causes_name";
    private static final String INTERESTS = "interests";
    private static final String INTERESTS_NAME = "interests_name";

    public static void putUserDatas(SharedPreferences userDefaults, List<String> causes, List<String> interests, List<String> causesName, List<String> interestsName) {
        putCauses(userDefaults, causes);
        putInterests(userDefaults, interests);
        putNameCauses(userDefaults, causesName);
        putNameInterests(userDefaults, interestsName);
    }

    public static void clearUserDatas(SharedPreferences userDefaults) {
        putCauses(userDefaults, new ArrayList());
        putInterests(userDefaults, new ArrayList());
        putNameCauses(userDefaults, new ArrayList());
        putNameInterests(userDefaults, new ArrayList());
    }

    public static void putUserCausesData(SharedPreferences userDefaults, List<String> causes, List<String> causesName) {
        putCauses(userDefaults, causes);
        putNameCauses(userDefaults, causesName);
    }

    public static void clearUserCausesDatas(SharedPreferences userDefaults) {
        putCauses(userDefaults, new ArrayList());
        putNameCauses(userDefaults, new ArrayList());
    }

    public static void putUserInterestsData(SharedPreferences userDefaults, List<String> interests, List<String> interestsName) {
        putInterests(userDefaults, interests);
        putNameInterests(userDefaults, interestsName);
    }

    public static void clearUserInterestsDatas(SharedPreferences userDefaults) {
        putInterests(userDefaults, new ArrayList());
        putNameInterests(userDefaults, new ArrayList());
    }

    private static void putCauses(SharedPreferences userDefaults, List<String> causes) {
        Editor editor = userDefaults.edit();
        editor.putStringSet(CAUSES, new HashSet(causes));
        editor.commit();
    }

    private static void putInterests(SharedPreferences userDefaults, List<String> interests) {
        Editor editor = userDefaults.edit();
        editor.putStringSet(INTERESTS, new HashSet(interests));
        editor.commit();
    }

    private static void putNameCauses(SharedPreferences userDefaults, List<String> causesName) {
        Editor editor = userDefaults.edit();
        editor.putStringSet(CAUSES_NAME, new HashSet(causesName));
        editor.commit();
    }

    private static void putNameInterests(SharedPreferences userDefaults, List<String> interestsName) {
        Editor editor = userDefaults.edit();
        editor.putStringSet(INTERESTS_NAME, new HashSet(interestsName));
        editor.commit();
    }

    public static List<String> getCauses(SharedPreferences userDefaults) {
        List<String> result = new ArrayList();
        result.addAll(userDefaults.getStringSet(CAUSES, new HashSet()));
        return result;
    }

    public static List<String> getInterests(SharedPreferences userDefaults) {
        List<String> result = new ArrayList();
        result.addAll(userDefaults.getStringSet(INTERESTS, new HashSet()));
        return result;
    }

    public static List<String> getNameCauses(SharedPreferences userDefaults) {
        List<String> result = new ArrayList();
        result.addAll(userDefaults.getStringSet(CAUSES_NAME, new HashSet()));
        return result;
    }

    public static List<String> getNameInterests(SharedPreferences userDefaults) {
        List<String> result = new ArrayList();
        result.addAll(userDefaults.getStringSet(INTERESTS_NAME, new HashSet()));
        return result;
    }
}

package com.vowme.app.utilities.helpers.sharedPreferences;

import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class UserSearchFilterSharedDataHelper extends BaseSharedDataHelper {
    private static final String AVAILABILITY = "availability_filter";
    private static final String AVAILABILITY_NAME = "availability_name_filter";
    private static final String CAUSES = "causes_filter";
    private static final String CAUSES_NAME = "causes_name_filter";
    private static final String DURATIONS = "durations_filter";
    private static final String DURATIONS_NAME = "durations_name_filter";
    private static final String INTERESTS = "interests_filter";
    private static final String INTERESTS_NAME = "interests_name_filter";
    private static final String LOCATIONS = "locations_filter";
    private static final String LOCATIONS_NAME = "locations_name_filter";
    private static final String PROGRAMS = "programs_filter";
    private static final String PROGRAMS_NAME = "programs_name_filter";
    private static final String RECENTS = "recents";
    private static final String RECENTS_KEYWORD = "recent_keywords";
    private static final String WHEELCHAIR_ACCESS = "wheelchair_access_filter";

    public static void putFilters(SharedPreferences userDefaults, List<String> causes, List<String> interests, List<String> durations, List<String> programs, List<String> availabilities, List<String> locations, List<String> causesName, List<String> interestsName, List<String> durationsName, List<String> programsName, List<String> availabilitiesName, List<String> locationsName, boolean wheelchairAccess) {
        putCauses(userDefaults, causes);
        putInterests(userDefaults, interests);
        putDurations(userDefaults, durations);
        putPrograms(userDefaults, programs);
        putLocations(userDefaults, locations);
        putAvailabilities(userDefaults, availabilities);
        putNameCauses(userDefaults, causesName);
        putNameInterests(userDefaults, interestsName);
        putNameDurations(userDefaults, durationsName);
        putNamePrograms(userDefaults, programsName);
        putNameLocations(userDefaults, locationsName);
        putNameAvailabilities(userDefaults, availabilitiesName);
        putWheelchair(userDefaults, wheelchairAccess);
    }

    public static void clearUserDatas(SharedPreferences userDefaults) {
        Editor editor = userDefaults.edit();
        putCauses(userDefaults, new ArrayList());
        putInterests(userDefaults, new ArrayList());
        putDurations(userDefaults, new ArrayList());
        putPrograms(userDefaults, new ArrayList());
        putLocations(userDefaults, new ArrayList());
        putAvailabilities(userDefaults, new ArrayList());
        putNameCauses(userDefaults, new ArrayList());
        putNameInterests(userDefaults, new ArrayList());
        putNameDurations(userDefaults, new ArrayList());
        putNamePrograms(userDefaults, new ArrayList());
        putNameLocations(userDefaults, new ArrayList());
        putNameAvailabilities(userDefaults, new ArrayList());
        putWheelchair(userDefaults, false);
        editor.commit();
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
        BaseSharedDataHelper.putData(userDefaults, result, RECENTS);
    }

    public static void clearRecents(SharedPreferences userDefaults) {
        BaseSharedDataHelper.putData(userDefaults, new ArrayList(), RECENTS);
    }

    public static List<String> getRecentKeywords(SharedPreferences userDefaults) {
        List<String> result = new ArrayList();
        result.addAll(userDefaults.getStringSet(RECENTS_KEYWORD, new HashSet()));
        return result;
    }

    public static void addToRecentKeywords(SharedPreferences userDefaults, String recent) {
        List<String> result = new ArrayList();
        result.addAll(userDefaults.getStringSet(RECENTS_KEYWORD, new HashSet()));
        if (!result.contains(recent)) {
            result.add(recent);
        }
        BaseSharedDataHelper.putData(userDefaults, result, RECENTS_KEYWORD);
    }

    private static void putCauses(SharedPreferences userDefaults, List<String> causes) {
        BaseSharedDataHelper.putData(userDefaults, causes, CAUSES);
    }

    private static void putInterests(SharedPreferences userDefaults, List<String> interests) {
        BaseSharedDataHelper.putData(userDefaults, interests, INTERESTS);
    }

    private static void putDurations(SharedPreferences userDefaults, List<String> durations) {
        BaseSharedDataHelper.putData(userDefaults, durations, DURATIONS);
    }

    private static void putPrograms(SharedPreferences userDefaults, List<String> programs) {
        BaseSharedDataHelper.putData(userDefaults, programs, PROGRAMS);
    }

    private static void putLocations(SharedPreferences userDefaults, List<String> locations) {
        BaseSharedDataHelper.putData(userDefaults, locations, LOCATIONS);
    }

    private static void putAvailabilities(SharedPreferences userDefaults, List<String> availabilities) {
        BaseSharedDataHelper.putData(userDefaults, availabilities, AVAILABILITY);
    }

    private static void putWheelchair(SharedPreferences userDefaults, boolean wheelchairAccess) {
        Editor editor = userDefaults.edit();
        editor.putBoolean(WHEELCHAIR_ACCESS, wheelchairAccess);
        editor.commit();
    }

    private static void putNameCauses(SharedPreferences userDefaults, List<String> causesName) {
        BaseSharedDataHelper.putData(userDefaults, causesName, CAUSES_NAME);
    }

    private static void putNameInterests(SharedPreferences userDefaults, List<String> interestsName) {
        BaseSharedDataHelper.putData(userDefaults, interestsName, INTERESTS_NAME);
    }

    private static void putNameDurations(SharedPreferences userDefaults, List<String> durationsName) {
        BaseSharedDataHelper.putData(userDefaults, durationsName, DURATIONS_NAME);
    }

    private static void putNamePrograms(SharedPreferences userDefaults, List<String> programsName) {
        BaseSharedDataHelper.putData(userDefaults, programsName, PROGRAMS_NAME);
    }

    private static void putNameLocations(SharedPreferences userDefaults, List<String> locations) {
        BaseSharedDataHelper.putData(userDefaults, locations, LOCATIONS_NAME);
    }

    private static void putNameAvailabilities(SharedPreferences userDefaults, List<String> availabilities) {
        BaseSharedDataHelper.putData(userDefaults, availabilities, AVAILABILITY_NAME);
    }

    public static List<String> getCauses(SharedPreferences userDefaults) {
        return BaseSharedDataHelper.getData(userDefaults, CAUSES);
    }

    public static List<String> getInterests(SharedPreferences userDefaults) {
        return BaseSharedDataHelper.getData(userDefaults, INTERESTS);
    }

    public static List<String> getDurations(SharedPreferences userDefaults) {
        return BaseSharedDataHelper.getData(userDefaults, DURATIONS);
    }

    public static List<String> getPrograms(SharedPreferences userDefaults) {
        return BaseSharedDataHelper.getData(userDefaults, PROGRAMS);
    }

    public static List<String> getLocations(SharedPreferences userDefaults) {
        return BaseSharedDataHelper.getData(userDefaults, LOCATIONS);
    }

    public static List<String> getAvailabilities(SharedPreferences userDefaults) {
        return BaseSharedDataHelper.getData(userDefaults, AVAILABILITY);
    }

    public static boolean getWheelchair(SharedPreferences userDefaults) {
        return userDefaults.getBoolean(WHEELCHAIR_ACCESS, false);
    }

    public static List<String> getNameCauses(SharedPreferences userDefaults) {
        return BaseSharedDataHelper.getData(userDefaults, CAUSES_NAME);
    }

    public static List<String> getNameInterests(SharedPreferences userDefaults) {
        return BaseSharedDataHelper.getData(userDefaults, INTERESTS_NAME);
    }

    public static List<String> getNameDurations(SharedPreferences userDefaults) {
        return BaseSharedDataHelper.getData(userDefaults, DURATIONS_NAME);
    }

    public static List<String> getNamePrograms(SharedPreferences userDefaults) {
        return BaseSharedDataHelper.getData(userDefaults, PROGRAMS_NAME);
    }

    public static List<String> getNameLocations(SharedPreferences userDefaults) {
        return BaseSharedDataHelper.getData(userDefaults, LOCATIONS_NAME);
    }

    public static List<String> getNameAvailabilities(SharedPreferences userDefaults) {
        return BaseSharedDataHelper.getData(userDefaults, AVAILABILITY_NAME);
    }
}

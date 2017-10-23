package com.vowme.app.utilities.helpers.sharedPreferences;

import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class UserNavigationSharedDataHelper {
    private static final String HAVE_DONE_WIZARD = "have_done_wizard";
    private static final String HAVE_SEEN_WELCOME_SCREEN = "have_seen_welcome_screen";
    private static final String IS_FROM_EXPRESS_INTEREST = "is_from_express_interest";
    private static final String NEED_UPDATE_PROFILE = "need_update_profile";
    private static final String NEED_UPDATE_RECOMMENDED = "need_update_recommended";
    private static final String NEED_UPDATE_SHORTLIST = "need_update_shortlist";

    public static void putUserHasSeenWelcomeScreen(SharedPreferences userDefaults, boolean hasSeenWelcomeScreen) {
        Editor editor = userDefaults.edit();
        editor.putBoolean(HAVE_SEEN_WELCOME_SCREEN, hasSeenWelcomeScreen);
        editor.commit();
    }

    public static boolean getUserHasSeenWelcomeScreen(SharedPreferences userDefaults) {
        return userDefaults.getBoolean(HAVE_SEEN_WELCOME_SCREEN, false);
    }

    public static void putUserIsFromExpressInterest(SharedPreferences userDefaults, boolean hasSeenWelcomeScreen) {
        Editor editor = userDefaults.edit();
        editor.putBoolean(IS_FROM_EXPRESS_INTEREST, hasSeenWelcomeScreen);
        editor.commit();
    }

    public static boolean getUserIsFromExpressInterest(SharedPreferences userDefaults) {
        return userDefaults.getBoolean(IS_FROM_EXPRESS_INTEREST, false);
    }

    public static void putUserNeedUpdateRecommended(SharedPreferences userDefaults, boolean needUpdateRecommended) {
        Editor editor = userDefaults.edit();
        editor.putBoolean(NEED_UPDATE_RECOMMENDED, needUpdateRecommended);
        editor.commit();
    }

    public static boolean getUserNeedUpdateRecommended(SharedPreferences userDefaults) {
        return userDefaults.getBoolean(NEED_UPDATE_RECOMMENDED, false);
    }

    public static void putUserNeedUpdateShortlist(SharedPreferences userDefaults, boolean needUpdateShortlist) {
        Editor editor = userDefaults.edit();
        editor.putBoolean(NEED_UPDATE_SHORTLIST, needUpdateShortlist);
        editor.commit();
    }

    public static boolean getUserNeedUpdateShortlist(SharedPreferences userDefaults) {
        return userDefaults.getBoolean(NEED_UPDATE_SHORTLIST, false);
    }

    public static void putUserHaveDoneWizard(SharedPreferences userDefaults, boolean needUpdateShortlist) {
        Editor editor = userDefaults.edit();
        editor.putBoolean(HAVE_DONE_WIZARD, needUpdateShortlist);
        editor.commit();
    }

    public static boolean getUserHaveDoneWizard(SharedPreferences userDefaults) {
        Editor editor = userDefaults.edit();
        editor.putBoolean(HAVE_DONE_WIZARD, false);
        editor.commit();
        return userDefaults.getBoolean(HAVE_DONE_WIZARD, false);
    }

    public static void putUserNeedUpdateProfile(SharedPreferences userDefaults, boolean needUpdateShortlist) {
        Editor editor = userDefaults.edit();
        editor.putBoolean(NEED_UPDATE_PROFILE, needUpdateShortlist);
        editor.commit();
    }

    public static boolean getUserNeedUpdateProfile(SharedPreferences userDefaults) {
        return userDefaults.getBoolean(NEED_UPDATE_PROFILE, false);
    }
}

package com.vowme.app.utilities.helpers.sharedPreferences;

import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class UserOAuthSharedDataHelper {
    private static final String APLI_OAUTH_ACCESS_EXPIRES = "apli_expires";
    private static final String APLI_OAUTH_ACCESS_ISSUED = "apli_issued";
    private static final String APLI_OAUTH_ACCESS_TOKEN = "apli_access_token";
    private static final String APLI_OAUTH_REFRESH_TOKEN = "apli_refresh_token";
    private static final String GCM_REGISTRATION_TOKEN = "gcm_registration_token";
    private static final String OAUTH_ACCESS_EXPIRES = ".expires";
    private static final String OAUTH_ACCESS_ISSUED = ".issued";
    private static final String OAUTH_ACCESS_TOKEN = "access_token";
    private static final String OAUTH_LOGGED_IN = "logged_in";
    private static final String OAUTH_REFRESH_TOKEN = "refresh_token";

    public static void putUserAccessToken(SharedPreferences userDefaults, JSONObject json, boolean isLoggedIn) {
        Editor editor = userDefaults.edit();
        try {
            editor.putString("access_token", json.getString("access_token"));
            editor.putString(OAUTH_ACCESS_ISSUED, json.getString(OAUTH_ACCESS_ISSUED));
            editor.putString(OAUTH_ACCESS_EXPIRES, json.getString(OAUTH_ACCESS_EXPIRES));
            editor.putString(OAUTH_REFRESH_TOKEN, json.getString(OAUTH_REFRESH_TOKEN));
            editor.putBoolean(OAUTH_LOGGED_IN, isLoggedIn);
            editor.commit();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public static void clearUserAccessToken(SharedPreferences userDefaults) {
        Editor editor = userDefaults.edit();
        editor.putString("access_token", "");
        editor.putString(OAUTH_ACCESS_ISSUED, "");
        editor.putString(OAUTH_ACCESS_EXPIRES, "");
        editor.putString(OAUTH_REFRESH_TOKEN, "");
        editor.putBoolean(OAUTH_LOGGED_IN, false);
        editor.commit();
    }

    public static void putGCMAccessToken(SharedPreferences userDefaults, String token) {
        Editor editor = userDefaults.edit();
        editor.putString(GCM_REGISTRATION_TOKEN, token);
        editor.commit();
    }

    public static void putApliAccessToken(SharedPreferences userDefaults, JSONObject json) {
        Editor editor = userDefaults.edit();
        try {
            editor.putString(APLI_OAUTH_ACCESS_TOKEN, json.getString("access_token"));
            editor.putString(APLI_OAUTH_ACCESS_ISSUED, json.getString(OAUTH_ACCESS_ISSUED));
            editor.putString(APLI_OAUTH_ACCESS_EXPIRES, json.getString(OAUTH_ACCESS_EXPIRES));
            editor.putString(APLI_OAUTH_REFRESH_TOKEN, json.getString(OAUTH_REFRESH_TOKEN));
            editor.commit();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public static boolean isUserTokenExpired(SharedPreferences userDefaults) {
        String expiresDate = userDefaults.getString(OAUTH_ACCESS_EXPIRES, "");
        if (expiresDate == null || expiresDate.length() == 0) {
            return true;
        }
        Date today = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("EE, dd MMM yyyy HH:mm:ss", Locale.ENGLISH);
        dateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
        try {
            if (dateFormat.parse(expiresDate).before(today)) {
                return true;
            }
            return false;
        } catch (ParseException e) {
            e.printStackTrace();
            return true;
        }
    }

    public static boolean isApliTokenExpired(SharedPreferences userDefaults) {
        String expiresDate = userDefaults.getString(APLI_OAUTH_ACCESS_EXPIRES, "");
        if (expiresDate == null || expiresDate.length() == 0) {
            return true;
        }
        Date today = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("EE, dd MMM yyyy HH:mm:ss", Locale.ENGLISH);
        dateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
        try {
            if (dateFormat.parse(expiresDate).before(today)) {
                return true;
            }
            return false;
        } catch (ParseException e) {
            e.printStackTrace();
            return true;
        }
    }

    public static boolean isUserLoggedIn(SharedPreferences userDefaults) {
        return userDefaults.getBoolean(OAUTH_LOGGED_IN, false);
    }

    public static String getUserRefreshToken(SharedPreferences userDefaults) {
        return userDefaults.getString(OAUTH_REFRESH_TOKEN, "");
    }

    public static String getUserAccessToken(SharedPreferences userDefaults) {
        return userDefaults.getString("access_token", "");
    }

    public static String getApliAccessToken(SharedPreferences userDefaults) {
        return userDefaults.getString(APLI_OAUTH_ACCESS_TOKEN, "");
    }

    public static String getGCMAccessToken(SharedPreferences userDefaults) {
        return userDefaults.getString(GCM_REGISTRATION_TOKEN, "");
    }
}

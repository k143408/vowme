package com.vowme.app.utilities.gcm;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import com.vowme.vol.app.R;
import com.vowme.app.models.Enum.HttpRequestType;
import com.vowme.app.models.Enum.SharedPreferencesKeys;
import com.vowme.app.models.api.OpportunityShortList;
import com.vowme.app.utilities.api.ApiRestFullRequest;
import com.vowme.app.utilities.helpers.sharedPreferences.UserNavigationSharedDataHelper;
import com.vowme.app.utilities.helpers.sharedPreferences.UserOAuthSharedDataHelper;
import com.vowme.app.utilities.helpers.sharedPreferences.UserShortlistSharedDataHelper;

import org.json.JSONObject;

public class MyBroadcastReceiver extends BroadcastReceiver {

    private class AddToShortList extends ApiRestFullRequest {
        public AddToShortList(JSONObject params, Context context) {
            super(HttpRequestType.POST, context.getString(R.string.apiVolunteerURL), "api/opportunity/shortlist", params, MyBroadcastReceiver.this.getUserAccessToken(context));
        }
    }

    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        String oppId = intent.getStringExtra("android.intent.extra.TEXT");
        boolean added = false;
        if ("SHORTLIST_ACTION".equals(action)) {
            if (isUserLoggedIn(context)) {
                new AddToShortList(new OpportunityShortList(Integer.parseInt(oppId), "Mobile App").toJsonObject(), context).execute(new Void[0]);
                UserShortlistSharedDataHelper.addToShortlist(getUserShortlistSharedData(context), Integer.valueOf(Integer.parseInt(oppId)));
                added = true;
            } else if (UserShortlistSharedDataHelper.getShortlist(getUserShortlistSharedData(context)).size() < 5) {
                UserShortlistSharedDataHelper.addToShortlist(getUserShortlistSharedData(context), Integer.valueOf(Integer.parseInt(oppId)));
                added = true;
            }
            putUserNeedUpdateShortlist(context, added);
            putUserNeedUpdateRecommended(context, added);
        }
    }

    private boolean isUserLoggedIn(Context context) {
        return UserOAuthSharedDataHelper.isUserLoggedIn(getUserOAuthSharedData(context));
    }

    private String getUserAccessToken(Context context) {
        return UserOAuthSharedDataHelper.getUserAccessToken(getUserOAuthSharedData(context));
    }

    private void putUserNeedUpdateRecommended(Context context, boolean needUpdateRecommended) {
        UserNavigationSharedDataHelper.putUserNeedUpdateRecommended(getUserNavigationSharedData(context), needUpdateRecommended);
    }

    private void putUserNeedUpdateShortlist(Context context, boolean needUpdateShortlist) {
        UserNavigationSharedDataHelper.putUserNeedUpdateShortlist(getUserNavigationSharedData(context), needUpdateShortlist);
    }

    private SharedPreferences getUserOAuthSharedData(Context context) {
        return context.getSharedPreferences(SharedPreferencesKeys.USER_OAUTH_SHARED_DATA.getValue(), 0);
    }

    private SharedPreferences getUserShortlistSharedData(Context context) {
        return context.getSharedPreferences(SharedPreferencesKeys.USER_SHORTLIST_SHARED_DATA.getValue(), 0);
    }

    private SharedPreferences getUserNavigationSharedData(Context context) {
        return context.getSharedPreferences(SharedPreferencesKeys.USER_NAVIGATION_SHARED_DATA.getValue(), 0);
    }
}

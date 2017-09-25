package com.vowme.app.utilities.gcm;

import android.app.IntentService;
import android.content.Intent;
import android.content.SharedPreferences;
import android.provider.Settings.Secure;

import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.google.android.gms.iid.InstanceID;
import com.vowme.vol.app.R;
import com.vowme.app.models.Enum.HttpRequestType;
import com.vowme.app.models.Enum.SharedPreferencesKeys;
import com.vowme.app.utilities.api.ApiRestFullRequest;
import com.vowme.app.utilities.helpers.sharedPreferences.UserOAuthSharedDataHelper;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Collections;

import io.fabric.sdk.android.services.network.HttpRequest;
import io.fabric.sdk.android.services.network.HttpRequest.Base64;

public class RegistrationIntentService extends IntentService {

    private class PostVolunteerPushNotifications extends ApiRestFullRequest {
        public PostVolunteerPushNotifications(String encodeAndroidId, String androidDeviceId) {
            super(HttpRequestType.POST, RegistrationIntentService.this.getString(R.string.apiVolunteerURL), "api/volunteer/pushnotification/" + encodeAndroidId + "/" + androidDeviceId + "/Android", RegistrationIntentService.this.getUserAccessToken());
        }

        protected void onPostExecuteBody(String result) {
        }
    }

    public RegistrationIntentService(String name) {
        super(name);
    }

    protected void onHandleIntent(Intent intent) {
        try {
            String token = InstanceID.getInstance(this).getToken(getString(R.string.gcm_defaultSenderId), GoogleCloudMessaging.INSTANCE_ID_SCOPE, null);
            String androidDeviceId = Secure.getString(getApplicationContext().getContentResolver(), "android_id");
            String encodedBytes = "";
            try {
                encodedBytes = Base64.encodeBytes(token.getBytes(HttpRequest.CHARSET_UTF8));
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            new PostVolunteerPushNotifications(encodedBytes, androidDeviceId).execute( (Void[]) Collections.EMPTY_LIST.toArray() );
            UserOAuthSharedDataHelper.putGCMAccessToken(getUserOAuthSharedData(), token);
        } catch (IOException e2) {
            e2.printStackTrace();
        }
    }

    private SharedPreferences getUserOAuthSharedData() {
        return getSharedPreferences(SharedPreferencesKeys.USER_OAUTH_SHARED_DATA.getValue(), 0);
    }

    private String getUserAccessToken() {
        return UserOAuthSharedDataHelper.getUserAccessToken(getUserOAuthSharedData());
    }
}

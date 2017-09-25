package com.vowme.vol.app.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.helper.ItemTouchHelper.Callback;

import com.vowme.vol.app.R;
import com.vowme.app.models.Enum.PostAccessTokenType;
import com.vowme.app.models.api.response.OauthRequestResponse;
import com.vowme.app.utilities.activities.BaseActivity;
import com.vowme.app.utilities.api.oauth.PostAccessToken;
import com.vowme.vol.app.activities.start.GetStarted;

import org.json.JSONException;
import org.json.JSONObject;

public class SplashActivity extends BaseActivity {

    private class GetRefreshAccessTokenUser extends PostAccessToken {
        public GetRefreshAccessTokenUser(String refreshToken) {
            super(SplashActivity.this.getString(R.string.apiVolunteerURL), "oauth/token", PostAccessTokenType.REFRESH, SplashActivity.this.getBaseContext(), refreshToken);
        }

        protected void onPostExecuteBody(OauthRequestResponse result) {
            Intent launchIntent = new Intent();
            try {
                JSONObject json = new JSONObject(result.getJsonAsString());
                if (result.getErrorCode() == Callback.DEFAULT_DRAG_ANIMATION_DURATION) {
                    SplashActivity.this.putAccessTokenInfos(json, true);
                    launchIntent.setClass(SplashActivity.this.getApplicationContext(), MainActivity.class);
                    SplashActivity.this.startActivity(launchIntent);
                    SplashActivity.this.finish();
                    return;
                }
                launchIntent.setClass(SplashActivity.this.getApplicationContext(), GetStarted.class);
                SplashActivity.this.startActivity(launchIntent);
                SplashActivity.this.finish();
            } catch (JSONException e) {
                launchIntent.setClass(SplashActivity.this.getApplicationContext(), GetStarted.class);
                SplashActivity.this.startActivity(launchIntent);
                SplashActivity.this.finish();
            }
        }
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent launchIntent = new Intent();
        if (!isUserLoggedIn()) {
            if (getUserHaveDoneWizard()) {
                launchIntent.setClass(getApplicationContext(), MainActivity.class);
            } else {
                launchIntent.setClass(getApplicationContext(), GetStarted.class);
            }
            startActivity(launchIntent);
            finish();
        } else if (isUserExpired()) {
            new GetRefreshAccessTokenUser(getRefreshToken()).execute(new Void[0]);
        } else {
            launchIntent.setClass(getApplicationContext(), MainActivity.class);
            startActivity(launchIntent);
            finish();
        }
    }
}

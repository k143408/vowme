package com.vowme.vol.app.activities.settings;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.Settings.Secure;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.MenuItem;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.Switch;

import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.google.android.gms.iid.InstanceID;
import com.vowme.app.models.Enum.HttpRequestType;
import com.vowme.app.utilities.activities.BaseActivity;
import com.vowme.app.utilities.api.ApiRestFullRequest;
import com.vowme.vol.app.R;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import io.fabric.sdk.android.services.network.HttpRequest;
import io.fabric.sdk.android.services.network.HttpRequest.Base64;

public class PushNotificationActivity extends BaseActivity {
    private String androidDeviceId;
    private String androidId;
    private boolean isPushNotificationOn;
    private Switch switchNotifications;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((int) R.layout.activity_push_notification);
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar_no_tabs));
        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);
        ab.setHomeAsUpIndicator((int) R.mipmap.ic_arrow_back_white_24dp);
        this.isPushNotificationOn = getIntent().getBooleanExtra("android.intent.extra.TEXT", false);
        this.switchNotifications = (Switch) findViewById(R.id.switch_notifications);
        this.switchNotifications.setChecked(this.isPushNotificationOn);
        this.androidDeviceId = Secure.getString(getApplicationContext().getContentResolver(), "android_id");
        this.switchNotifications.setOnCheckedChangeListener(new C08531());
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case 16908332:
                Intent intent = new Intent();
                intent.putExtra("android.intent.extra.TEXT", this.isPushNotificationOn);
                setResult(-1, intent);
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    class C08531 implements OnCheckedChangeListener {
        C08531() {
        }

        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            PushNotificationActivity.this.androidId = PushNotificationActivity.this.getGCMAccessToken();
            String encodedBytes = "";
            try {
                encodedBytes = Base64.encodeBytes(PushNotificationActivity.this.androidId.getBytes(HttpRequest.CHARSET_UTF8));
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            if (!isChecked) {
                new DeleteVolunteerPushNotifications(encodedBytes).execute(new Void[0]);
            } else if (TextUtils.isEmpty(PushNotificationActivity.this.androidId)) {
                new GetDeviceToken().execute(new Void[0]);
            } else {
                new PutVolunteerPushNotifications(encodedBytes, PushNotificationActivity.this.androidDeviceId).execute(new Void[0]);
            }
        }
    }

    private class DeleteVolunteerPushNotifications extends ApiRestFullRequest {
        public DeleteVolunteerPushNotifications(String encodeAndroidId) {
            super(HttpRequestType.DELETE, PushNotificationActivity.this.getString(R.string.apiVolunteerURL), "api/volunteer/pushnotification/" + encodeAndroidId + "/" + PushNotificationActivity.this.androidDeviceId + "/Android", PushNotificationActivity.this.getUserAccessToken());
        }

        protected void onPostExecuteBody(String result) {
            if (TextUtils.isEmpty(result)) {
                PushNotificationActivity.this.isPushNotificationOn = false;
            }
            PushNotificationActivity.this.switchNotifications.setChecked(PushNotificationActivity.this.isPushNotificationOn);
        }
    }

    private class GetDeviceToken extends AsyncTask<Void, Void, String> {
        private GetDeviceToken() {
        }

        protected String doInBackground(Void... params) {
            String token = "";
            try {
                token = InstanceID.getInstance(PushNotificationActivity.this).getToken(PushNotificationActivity.this.getString(R.string.gcm_defaultSenderId), GoogleCloudMessaging.INSTANCE_ID_SCOPE, null);
                String encodedBytes = "";
                try {
                    encodedBytes = Base64.encodeBytes(token.getBytes(HttpRequest.CHARSET_UTF8));
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                new PutVolunteerPushNotifications(encodedBytes, PushNotificationActivity.this.androidDeviceId).execute(new Void[0]);
                PushNotificationActivity.this.putGCMAccessToken(token);
                PushNotificationActivity.this.androidId = PushNotificationActivity.this.getGCMAccessToken();
            } catch (IOException e2) {
                e2.printStackTrace();
            }
            return token;
        }
    }

    private class PutVolunteerPushNotifications extends ApiRestFullRequest {
        public PutVolunteerPushNotifications(String encodeAndroidId, String androidDeviceId) {
            super(HttpRequestType.PUT, PushNotificationActivity.this.getString(R.string.apiVolunteerURL), "api/volunteer/pushnotification/" + encodeAndroidId + "/" + androidDeviceId + "/Android", PushNotificationActivity.this.getUserAccessToken());
        }

        protected void onPostExecuteBody(String result) {
            if (TextUtils.isEmpty(result)) {
                PushNotificationActivity.this.isPushNotificationOn = true;
            }
            PushNotificationActivity.this.switchNotifications.setChecked(PushNotificationActivity.this.isPushNotificationOn);
        }
    }
}

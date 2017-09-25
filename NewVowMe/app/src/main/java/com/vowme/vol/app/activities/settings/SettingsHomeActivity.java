package com.vowme.vol.app.activities.settings;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Build.VERSION;
import android.os.Build.VERSION_CODES;
import android.os.Bundle;
import android.provider.Settings.Secure;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.vowme.app.models.Enum.ActivityCode;
import com.vowme.app.models.Enum.HttpRequestType;
import com.vowme.app.models.api.NotificationModel;
import com.vowme.app.utilities.activities.BaseActivity;
import com.vowme.app.utilities.activities.WebViewActivity;
import com.vowme.app.utilities.api.ApiRestFullRequest;
import com.vowme.app.utilities.helpers.JSONHelper;
import com.vowme.vol.app.R;
import com.vowme.vol.app.VowMeApplication;
import com.vowme.vol.app.activities.start.GetStarted;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;

import io.fabric.sdk.android.services.network.HttpRequest;
import io.fabric.sdk.android.services.network.HttpRequest.Base64;

public class SettingsHomeActivity extends BaseActivity {
    private String androidDeviceId;
    private String androidId;
    private String frequencyDetails;
    private boolean isPushNotificationOn;
    private TextView notificationTxt;
    private TextView pushNotificationTxt;
    private String versionName;
    private String volunteerEmail;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((int) R.layout.activity_settings_home);
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar_no_tabs));
        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);
        ab.setHomeAsUpIndicator((int) R.mipmap.ic_arrow_back_white_24dp);
        this.versionName = "1.0";
        ((TextView) findViewById(R.id.version_txt)).setText(this.versionName);
        View layoutSettingsAuthenticated = findViewById(R.id.layout_settings_authenticated);
        if (isUserLoggedIn()) {
            layoutSettingsAuthenticated.setVisibility(View.VISIBLE);
            this.notificationTxt = (TextView) findViewById(R.id.notification_txt);
            this.notificationTxt.setText("Off");
            this.pushNotificationTxt = (TextView) findViewById(R.id.push_notification_txt);
            this.notificationTxt.setText("Off");
            new GetVolunteerSttingsData().execute(new Void[0]);
            new GetVolunteerEmail().execute(new Void[0]);
            this.androidId = getGCMAccessToken();
            this.androidDeviceId = Secure.getString(getApplicationContext().getContentResolver(), "android_id");
            new GetVolunteerPushNotifications(this.androidDeviceId).execute(new Void[0]);
            return;
        }
        layoutSettingsAuthenticated.setVisibility(View.GONE);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_signout, menu);
        MenuItem item = menu.findItem(R.id.action_signout);
        if (isUserLoggedIn()) {
            item.setVisible(true);
        } else {
            item.setVisible(false);
        }
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case 16908332:
                finish();
                return true;
            case R.id.action_signout:
                if (this.isPushNotificationOn) {
                    String encodedBytes = "";
                    try {
                        encodedBytes = Base64.encodeBytes(this.androidId.getBytes(HttpRequest.CHARSET_UTF8));
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                    new DeleteVolunteerPushNotifications(encodedBytes).execute(new Void[0]);
                    return true;
                }
                logOut();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.act_account:
                startActivity(new Intent(this, AccountActivity.class));
                return;
            case R.id.notification_txt:
                intent = new Intent(this, NotificationsActivity.class);
                intent.putExtra(getResources().getString(R.string.EXTRA_MODEL_DETAILS), this.frequencyDetails);
                startActivityForResult(intent, ActivityCode.SETTINGSNOTIFICATION.getValue());
                return;
            case R.id.push_notification_txt:
                intent = new Intent(this, PushNotificationActivity.class);
                intent.putExtra("android.intent.extra.TEXT", this.isPushNotificationOn);
                startActivityForResult(intent, ActivityCode.PUSHSETTINGSNOTIFICATION.getValue());
                return;
            case R.id.privacy_txt:
                startActivity(new Intent(this, ProfilePrivacyActivity.class));
                return;
            case R.id.act_help:
                intent = new Intent(this, WebViewActivity.class);
                intent.putExtra(getResources().getString(R.string.WEB_VIEW_TITLE), getResources().getString(R.string.title_activity_help));
                intent.putExtra(getResources().getString(R.string.WEB_VIEW_URL), getResources().getString(R.string.link_help));
                startActivity(intent);
                return;
            case R.id.act_report_problem:
                intent = new Intent("android.intent.action.SEND");
                intent.setType("plain/text");
                intent.putExtra("android.intent.extra.EMAIL", new String[]{getResources().getString(R.string.support_email)});
                intent.putExtra("android.intent.extra.SUBJECT", "Android Support Request");
                intent.putExtra("android.intent.extra.TEXT", createEmail().toString());
                String fullName = VowMeApplication.getFileName();
                if (fullName != null) {
                    intent.putExtra("android.intent.extra.STREAM", Uri.parse("file://" + fullName));
                }
                try {
                    startActivity(intent);
                    return;
                } catch (ActivityNotFoundException e) {
                    Toast.makeText(this, "There is no email client installed.", Toast.LENGTH_SHORT).show();
                    return;
                }
            case R.id.act_privacy_statment:
                intent = new Intent(this, WebViewActivity.class);
                intent.putExtra(getResources().getString(R.string.WEB_VIEW_TITLE), getResources().getString(R.string.title_activity_privacy_statment));
                intent.putExtra(getResources().getString(R.string.WEB_VIEW_URL), getResources().getString(R.string.link_privacy_statment));
                startActivity(intent);
                return;
            case R.id.act_terms_use:
                intent = new Intent(this, WebViewActivity.class);
                intent.putExtra(getResources().getString(R.string.WEB_VIEW_TITLE), getResources().getString(R.string.title_activity_terms_use));
                intent.putExtra(getResources().getString(R.string.WEB_VIEW_URL), getResources().getString(R.string.link_terms_use));
                startActivity(intent);
                return;
            default:
                return;
        }
    }

    private StringBuilder createEmail() {
        StringBuilder bodyText = new StringBuilder();
        bodyText.append(System.getProperty("line.separator"));
        bodyText.append("----- Device/Profile Info -----");
        bodyText.append(System.getProperty("line.separator"));
        bodyText.append(System.getProperty("line.separator"));
        String model = Build.MODEL;
        if (!model.startsWith(Build.MANUFACTURER)) {
            model = Build.MANUFACTURER + " " + model;
        }
        bodyText.append("Android Device Type... " + model);
        bodyText.append(System.getProperty("line.separator"));
        bodyText.append("App Version....... " + this.versionName);
        Integer buidVersion = Integer.valueOf(VERSION.SDK_INT);
        for (Field field : VERSION_CODES.class.getFields()) {
            String fieldName = field.getName();
            int fieldValue = -1;
            try {
                fieldValue = field.getInt(new Object());
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e2) {
                e2.printStackTrace();
            } catch (NullPointerException e3) {
                e3.printStackTrace();
            }
            if (fieldValue == VERSION.SDK_INT) {
                bodyText.append(System.getProperty("line.separator"));
                bodyText.append("System Name....... " + fieldName);
            }
        }
        bodyText.append(System.getProperty("line.separator"));
        bodyText.append("System Version.... " + buidVersion);
        if (isUserLoggedIn()) {
            bodyText.append(System.getProperty("line.separator"));
            bodyText.append("Account........... " + this.volunteerEmail);
        }
        bodyText.append(System.getProperty("line.separator"));
        bodyText.append(System.getProperty("line.separator"));
        bodyText.append("-----------------------------------------");
        bodyText.append(System.getProperty("line.separator"));
        bodyText.append(System.getProperty("line.separator"));
        bodyText.append(System.getProperty("line.separator"));
        bodyText.append("Please type your message here. Let us know what you were doing and what went wrong.");
        return bodyText;
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != -1) {
            return;
        }
        if (ActivityCode.SETTINGSNOTIFICATION.getValue() == requestCode) {
            String frequencyDetails = data.getStringExtra(getResources().getString(R.string.EXTRA_MODEL_DETAILS));
            if (!TextUtils.isEmpty(frequencyDetails)) {
                this.frequencyDetails = frequencyDetails;
                updateFrequencyView();
            }
        } else if (ActivityCode.PUSHSETTINGSNOTIFICATION.getValue() == requestCode) {
            this.isPushNotificationOn = data.getBooleanExtra("android.intent.extra.TEXT", false);
            updatePushNotification();
        }
    }

    public void updateFrequencyView() {
        if (isoppportunityNotificationOn(JSONHelper.getJSONArrayFromString(this.frequencyDetails))) {
            this.notificationTxt.setText("On");
        } else {
            this.notificationTxt.setText("Off");
        }
    }

    private boolean isoppportunityNotificationOn(JSONArray frequencies) {
        boolean result = false;
        for (int i = 0; i < frequencies.length() && !result; i++) {
            try {
                if (new NotificationModel(frequencies.getJSONObject(i)).notificationId == getResources().getInteger(R.integer.OPPORTUNITY_NOTIFICATION_ID)) {
                    result = true;
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    public void updatePushNotification() {
        if (this.isPushNotificationOn) {
            this.pushNotificationTxt.setText("On");
        } else {
            this.pushNotificationTxt.setText("Off");
        }
    }

    private void logOut() {
        clearShortlisted();
        clearAccessTokenInfos();
        clearLocationFieldData();
        signoutExternalLogins();
        Intent launchIntent = new Intent();
        launchIntent.setClass(getApplicationContext(), GetStarted.class);
        startActivity(launchIntent);
        finish();
    }

    private class DeleteVolunteerPushNotifications extends ApiRestFullRequest {
        public DeleteVolunteerPushNotifications(String encodeAndroidId) {
            super(HttpRequestType.DELETE, SettingsHomeActivity.this.getString(R.string.apiVolunteerURL), "api/volunteer/pushnotification/" + encodeAndroidId + "/" + SettingsHomeActivity.this.androidDeviceId + "/Android", SettingsHomeActivity.this.getUserAccessToken());
        }

        protected void onPostExecuteBody(String result) {
            if (TextUtils.isEmpty(result)) {
                SettingsHomeActivity.this.isPushNotificationOn = false;
                SettingsHomeActivity.this.logOut();
            }
        }
    }

    private class GetVolunteerEmail extends ApiRestFullRequest {
        public GetVolunteerEmail() {
            super(HttpRequestType.GET, SettingsHomeActivity.this.getString(R.string.apiVolunteerURL), "api/volunteer/email", SettingsHomeActivity.this.getUserAccessToken());
        }

        protected void onPostExecuteBody(String result) {
            SettingsHomeActivity.this.volunteerEmail = result;
        }
    }

    private class GetVolunteerPushNotifications extends ApiRestFullRequest {
        public GetVolunteerPushNotifications(String androidDeviceID) {
            super(HttpRequestType.GET, SettingsHomeActivity.this.getString(R.string.apiVolunteerURL), "api/volunteer/pushnotification/" + androidDeviceID, SettingsHomeActivity.this.getUserAccessToken());
        }

        protected void onPostExecuteBody(String result) {
            try {
                JSONArray notifications = new JSONArray(result);
                boolean found = false;
                for (int i = 0; i < notifications.length() && !found; i++) {
                    if (SettingsHomeActivity.this.androidId.equals(notifications.getJSONObject(i).getString("deviceToken"))) {
                        found = true;
                    }
                }
                SettingsHomeActivity.this.isPushNotificationOn = found;
                SettingsHomeActivity.this.updatePushNotification();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    private class GetVolunteerSttingsData extends ApiRestFullRequest {
        public GetVolunteerSttingsData() {
            super(HttpRequestType.GET, SettingsHomeActivity.this.getString(R.string.apiVolunteerURL), "api/volunteer/notification", SettingsHomeActivity.this.getUserAccessToken());
        }

        protected void onPostExecuteBody(String result) {
            if (result.length() > 0) {
                SettingsHomeActivity.this.frequencyDetails = result;
                SettingsHomeActivity.this.updateFrequencyView();
            }
        }
    }
}

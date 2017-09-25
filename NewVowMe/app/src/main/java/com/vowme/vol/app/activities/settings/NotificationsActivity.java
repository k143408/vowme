package com.vowme.vol.app.activities.settings;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.design.widget.TextInputLayout;
import android.text.TextUtils;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;

import com.vowme.app.models.Enum.HttpRequestType;
import com.vowme.app.models.api.NotificationModel;
import com.vowme.app.models.api.SecondaryEmailModel;
import com.vowme.app.models.api.VolunteerSecondaryEmailModel;
import com.vowme.app.models.lookUp.Lookup;
import com.vowme.app.utilities.activities.SaveMenuFormValidationActivity;
import com.vowme.app.utilities.api.ApiRestFullRequest;
import com.vowme.app.utilities.customWidgets.CustomSpinnerTextView;
import com.vowme.app.utilities.helpers.ArrayListHelper;
import com.vowme.app.utilities.helpers.DefaultDataHelper;
import com.vowme.app.utilities.helpers.JSONHelper;
import com.vowme.vol.app.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class NotificationsActivity extends SaveMenuFormValidationActivity {
    private TextView email;
    private CustomSpinnerTextView frequency;
    private Integer frequencyId;
    private boolean isNotificationOn = false;
    private List<NotificationModel> notifications;
    private NotificationModel opportunityNotification;
    private VolunteerSecondaryEmailModel secondaryEmailModel;
    private LinearLayout subLayout;
    private Switch switchNotifications;
    private boolean tmpNotificationOn = false;

    protected void onCreate(Bundle savedInstanceState) {
        String frequencyName;
        super.onCreate(savedInstanceState);
        this.switchNotifications = (Switch) findViewById(R.id.switch_notifications);
        this.subLayout = (LinearLayout) findViewById(R.id.sub_lyt);
        this.frequency = (CustomSpinnerTextView) findViewById(R.id.spinner_txt);
        this.email = (TextView) findViewById(R.id.email_txt);
        if (this.switchNotifications.isChecked()) {
            this.subLayout.setVisibility(0);
        } else {
            this.subLayout.setVisibility(8);
        }
        this.switchNotifications.setOnCheckedChangeListener(new C08521());
        if (this.isNotificationOn) {
            frequencyName = ArrayListHelper.getNameFromId(this.frequencyId.intValue(), DefaultDataHelper.getNotificationFrequencies());
        } else {
            frequencyName = ((Lookup) DefaultDataHelper.getNotificationFrequencies().get(0)).getName();
        }
        this.frequency.setText(frequencyName);
        this.switchNotifications.setChecked(this.isNotificationOn);
        new GetVolunteerSecondaryEmail().execute(new Void[0]);
    }

    protected void initFields() {
        TextInputLayout floatingEmailText = (TextInputLayout) findViewById(R.id.input_layout_email);
        if (!(floatingEmailText == null || floatingEmailText.getEditText() == null)) {
            floatingEmailText.getEditText().addTextChangedListener(getFloatingTextRegexValidator(floatingEmailText, getResources().getString(R.string.email_regex), "Please enter your email address.", "Please enter a valid email address."));
        }
        this.frequency.setUpSpinnerTextView(17367043, DefaultDataHelper.getNotificationFrequencies());
        ((TextInputLayout) findViewById(R.id.input_layout_spinner)).setHint("Frequency");
    }

    protected void OnSaveOptionsItemSelected() {
        if (!this.tmpNotificationOn && this.isNotificationOn) {
            this.notifications.remove(this.opportunityNotification);
        } else if (this.tmpNotificationOn) {
            int tmpFrequency = ArrayListHelper.getIdFromName(this.frequency.getText().toString(), DefaultDataHelper.getNotificationFrequencies());
            if (!this.isNotificationOn) {
                this.opportunityNotification = new NotificationModel(getResources().getInteger(R.integer.OPPORTUNITY_NOTIFICATION_ID), Integer.valueOf(tmpFrequency));
                this.notifications.add(this.opportunityNotification);
            }
            for (NotificationModel item : this.notifications) {
                item.frequencyId = Integer.valueOf(tmpFrequency);
            }
        }
        this.modelAsString = JSONHelper.ToJSONArray(this.notifications).toString();
        new PutVolunteerNotification(this.modelAsString).execute(new Void[0]);
    }

    @LayoutRes
    protected int getLayoutResID() {
        return R.layout.activity_notifications;
    }

    protected void createModel() {
        JSONArray frequencies = JSONHelper.getJSONArrayFromString(this.modelAsString);
        this.notifications = new ArrayList();
        for (int i = 0; i < frequencies.length(); i++) {
            try {
                NotificationModel model = new NotificationModel(frequencies.getJSONObject(i));
                this.notifications.add(model);
                if (model.notificationId == getResources().getInteger(R.integer.OPPORTUNITY_NOTIFICATION_ID)) {
                    this.opportunityNotification = model;
                    this.isNotificationOn = true;
                    this.tmpNotificationOn = true;
                    this.frequencyId = model.frequencyId;
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    protected void finishActivity() {
        Intent intent = new Intent();
        intent.putExtra(getResources().getString(R.string.EXTRA_MODEL_DETAILS), this.modelAsString);
        setResult(-1, intent);
        finish();
    }

    class C08521 implements OnCheckedChangeListener {
        C08521() {
        }

        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            if (isChecked) {
                NotificationsActivity.this.tmpNotificationOn = true;
                NotificationsActivity.this.subLayout.setVisibility(0);
                return;
            }
            NotificationsActivity.this.tmpNotificationOn = false;
            NotificationsActivity.this.subLayout.setVisibility(8);
        }
    }

    private class GetVolunteerSecondaryEmail extends ApiRestFullRequest {
        public GetVolunteerSecondaryEmail() {
            super(HttpRequestType.GET, NotificationsActivity.this.getString(R.string.apiVolunteerURL), "api/volunteer/secondaryemail", NotificationsActivity.this.getUserAccessToken());
        }

        protected void onPostExecuteBody(String result) {
            try {
                NotificationsActivity.this.secondaryEmailModel = new VolunteerSecondaryEmailModel(new JSONObject(result));
                NotificationsActivity.this.email.setText(NotificationsActivity.this.secondaryEmailModel.secondaryEmail);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    private class PutVolunteerNotification extends ApiRestFullRequest {
        public PutVolunteerNotification(String param) {
            super(HttpRequestType.PUT, NotificationsActivity.this.getString(R.string.apiVolunteerURL), "api/volunteer/notification", param, NotificationsActivity.this.getUserAccessToken());
        }

        protected void onPostExecuteBody(String result) {
            String emailSecondary = NotificationsActivity.this.email.getText().toString();
            if (TextUtils.isEmpty(emailSecondary) || NotificationsActivity.this.secondaryEmailModel.oldSecondaryEmail.equals(emailSecondary)) {
                NotificationsActivity.this.finishActivity();
                return;
            }
            NotificationsActivity.this.secondaryEmailModel.oldSecondaryEmail = emailSecondary;
            NotificationsActivity.this.secondaryEmailModel.secondaryEmail = emailSecondary;
            new PutVolunteerSecondaryEmail(new SecondaryEmailModel(emailSecondary, NotificationsActivity.this.getResources().getString(R.string.client_id)).toJsonObject().toString()).execute(new Void[0]);
        }
    }

    private class PutVolunteerSecondaryEmail extends ApiRestFullRequest {
        public PutVolunteerSecondaryEmail(String param) {
            super(HttpRequestType.PUT, NotificationsActivity.this.getString(R.string.apiVolunteerURL), "api/volunteer/secondaryemail", param, NotificationsActivity.this.getUserAccessToken());
        }

        protected void onPostExecuteBody(String result) {
            NotificationsActivity.this.finishActivity();
        }
    }
}

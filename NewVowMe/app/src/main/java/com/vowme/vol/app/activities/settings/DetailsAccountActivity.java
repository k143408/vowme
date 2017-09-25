package com.vowme.vol.app.activities.settings;

import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AlertDialog.Builder;
import android.text.TextUtils;
import android.widget.TextView;

import com.vowme.app.models.Enum.HttpRequestType;
import com.vowme.app.models.api.UserAccountAPI;
import com.vowme.app.models.api.response.AppIdentityResult;
import com.vowme.app.utilities.activities.SaveMenuFormValidationActivity;
import com.vowme.app.utilities.api.ApiRestFullRequest;
import com.vowme.app.utilities.api.user.PostUserData;
import com.vowme.vol.app.R;

import org.json.JSONException;
import org.json.JSONObject;

public class DetailsAccountActivity extends SaveMenuFormValidationActivity {
    private TextView email;
    private UserAccountAPI model;
    private String oldEmail;
    private String oldUserName;
    private TextView userName;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.userName = (TextView) findViewById(R.id.username_txt);
        this.email = (TextView) findViewById(R.id.email_txt);
    }

    protected void createModel() {
        new GetVolunteerAccountDetails().execute(new Void[0]);
    }

    protected void initFields() {
        TextInputLayout floatingUsernameText = (TextInputLayout) findViewById(R.id.input_layout_username);
        if (!(floatingUsernameText == null || floatingUsernameText.getEditText() == null)) {
            floatingUsernameText.getEditText().addTextChangedListener(getFloatingTextLengthValidator(floatingUsernameText, 1, "Please enter a user name of your choosing."));
        }
        TextInputLayout floatingEmailText = (TextInputLayout) findViewById(R.id.input_layout_email);
        if (floatingEmailText != null && floatingEmailText.getEditText() != null) {
            floatingEmailText.getEditText().addTextChangedListener(getFloatingTextRegexValidator(floatingEmailText, getResources().getString(R.string.email_regex), "Please enter your email address.", "Please enter a valid email address."));
        }
    }

    protected void OnSaveOptionsItemSelected() {
        if (!this.oldUserName.equals(this.userName.getText().toString())) {
            new putVolunteerUserName(this.userName.getText().toString()).execute(new Void[0]);
        } else if (this.oldEmail.equals(this.email.getText().toString())) {
            finish();
        } else {
            new putVolunteerEmail(this.email.getText().toString()).execute(new Void[0]);
        }
    }

    protected int getLayoutResID() {
        return R.layout.activity_details_account;
    }

    private class GetVolunteerAccountDetails extends ApiRestFullRequest {
        public GetVolunteerAccountDetails() {
            super(HttpRequestType.GET, DetailsAccountActivity.this.getString(R.string.apiVolunteerURL), "api/users/Account", DetailsAccountActivity.this.getUserAccessToken());
        }

        protected void onPostExecuteBody(String result) {
            try {
                DetailsAccountActivity.this.model = new UserAccountAPI(new JSONObject(result));
                DetailsAccountActivity.this.userName.setText(DetailsAccountActivity.this.model.userName);
                DetailsAccountActivity.this.oldUserName = DetailsAccountActivity.this.model.userName;
                DetailsAccountActivity.this.email.setText(DetailsAccountActivity.this.model.email);
                DetailsAccountActivity.this.oldEmail = DetailsAccountActivity.this.model.email;
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    private class putVolunteerEmail extends PostUserData {

        public putVolunteerEmail(String email) {
            super(HttpRequestType.PUT, DetailsAccountActivity.this.getString(R.string.apiVolunteerURL), "api/users/" + DetailsAccountActivity.this.model.userId + "/email", "\"" + email + "\"", DetailsAccountActivity.this.getApliAccessToken());
        }

        protected void onPostExecuteBody(AppIdentityResult result) {
            if (result.succeeded) {
                DetailsAccountActivity.this.finish();
            } else {
                new Builder(DetailsAccountActivity.this).setMessage(TextUtils.join(", ", result.errors)).setNeutralButton(R.string.cast_invalid_stream_duration_text, new C08501()).setIcon(R.drawable.cast_ic_expanded_controller_stop).show();
            }
        }

        class C08501 implements OnClickListener {
            C08501() {
            }

            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        }
    }

    private class putVolunteerUserName extends PostUserData {

        public putVolunteerUserName(String username) {
            super(HttpRequestType.PUT, DetailsAccountActivity.this.getString(R.string.apiVolunteerURL), "api/users/" + DetailsAccountActivity.this.model.userId + "/username", "\"" + username + "\"", DetailsAccountActivity.this.getApliAccessToken());
        }

        protected void onPostExecuteBody(AppIdentityResult result) {
            if (!result.succeeded) {
                new Builder(DetailsAccountActivity.this).setMessage(TextUtils.join(", ", result.errors)).setNeutralButton(17039370, new C08511()).setIcon(17301543).show();
            } else if (DetailsAccountActivity.this.oldEmail.equals(DetailsAccountActivity.this.email.getText().toString())) {
                DetailsAccountActivity.this.finish();
            } else {
                new putVolunteerEmail(DetailsAccountActivity.this.email.getText().toString()).execute(new Void[0]);
            }
        }

        class C08511 implements OnClickListener {
            C08511() {
            }

            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        }
    }
}

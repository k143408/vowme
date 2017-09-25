package com.vowme.vol.app.activities.settings;

import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AlertDialog.Builder;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;

import com.vowme.app.models.Enum.HttpRequestType;
import com.vowme.app.models.api.ChangePasswordAPI;
import com.vowme.app.models.api.UserAccountAPI;
import com.vowme.app.models.api.response.AppIdentityResult;
import com.vowme.app.utilities.activities.SaveMenuFormValidationActivity;
import com.vowme.app.utilities.api.ApiRestFullRequest;
import com.vowme.app.utilities.api.user.PostUserData;
import com.vowme.vol.app.R;

import org.json.JSONException;
import org.json.JSONObject;

public class ChangePasswordActivity extends SaveMenuFormValidationActivity {
    private EditText currentPasswordTxt;
    private TextInputLayout floatingCurrentPasswordText;
    private UserAccountAPI model;
    private EditText passwordTxt;
    private EditText rePasswordText;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    protected void createModel() {
        new GetVolunteerAccountDetails().execute(new Void[0]);
    }

    protected void initFields() {
        this.currentPasswordTxt = (EditText) findViewById(R.id.current_password_txt);
        this.passwordTxt = (EditText) findViewById(R.id.password_txt);
        this.rePasswordText = (EditText) findViewById(R.id.repassword_txt);
        this.floatingCurrentPasswordText = (TextInputLayout) findViewById(R.id.input_layout_current_password);
        if (this.model != null) {
            initCurrentPassword();
        }
        TextInputLayout floatingPasswordText = (TextInputLayout) findViewById(R.id.input_layout_password);
        if (!(floatingPasswordText == null || floatingPasswordText.getEditText() == null)) {
            floatingPasswordText.getEditText().addTextChangedListener(getFloatingTextLengthValidator(floatingPasswordText, 1, "Please enter a new password."));
            floatingPasswordText.getEditText().addTextChangedListener(new C08481());
        }
        TextInputLayout floatingRePasswordText = (TextInputLayout) findViewById(R.id.input_layout_repassword);
        if (floatingRePasswordText != null && floatingRePasswordText.getEditText() != null) {
            floatingRePasswordText.getEditText().addTextChangedListener(getFloatingTextsCompareValidator(floatingRePasswordText, this.passwordTxt, "Please retype your password.", "Please retype your password."));
        }
    }

    protected void OnSaveOptionsItemSelected() {
        HttpRequestType typeRequest;
        String param;
        if (this.model.hasPassword) {
            typeRequest = HttpRequestType.PUT;
            param = new ChangePasswordAPI(this.currentPasswordTxt.getText().toString(), this.passwordTxt.getText().toString()).toJsonObject().toString();
        } else {
            typeRequest = HttpRequestType.POST;
            param = "\"" + this.passwordTxt.getText().toString() + "\"";
        }
        new updateVolunteerPassword(typeRequest, param).execute(new Void[0]);
    }

    protected int getLayoutResID() {
        return R.layout.activity_change_password;
    }

    private void initCurrentPassword() {
        if (this.model.hasPassword) {
            this.floatingCurrentPasswordText.setVisibility(View.VISIBLE);
            if (this.floatingCurrentPasswordText != null && this.floatingCurrentPasswordText.getEditText() != null) {
                this.floatingCurrentPasswordText.getEditText().addTextChangedListener(getFloatingTextLengthValidator(this.floatingCurrentPasswordText, 1, "Please enter your current password."));
                return;
            }
            return;
        }
        this.floatingCurrentPasswordText.setVisibility(View.GONE);
    }

    class C08481 implements TextWatcher {
        C08481() {
        }

        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        public void onTextChanged(CharSequence s, int start, int before, int count) {
            ChangePasswordActivity.this.rePasswordText.getText().clear();
        }

        public void afterTextChanged(Editable s) {
        }
    }

    private class GetVolunteerAccountDetails extends ApiRestFullRequest {
        public GetVolunteerAccountDetails() {
            super(HttpRequestType.GET, ChangePasswordActivity.this.getString(R.string.apiVolunteerURL), "api/users/Account", ChangePasswordActivity.this.getUserAccessToken());
        }

        protected void onPostExecuteBody(String result) {
            try {
                ChangePasswordActivity.this.model = new UserAccountAPI(new JSONObject(result));
                if (ChangePasswordActivity.this.floatingCurrentPasswordText != null) {
                    ChangePasswordActivity.this.initCurrentPassword();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    private class updateVolunteerPassword extends PostUserData {

        public updateVolunteerPassword(HttpRequestType typeRequest, String param) {
            super(typeRequest, ChangePasswordActivity.this.getString(R.string.apiVolunteerURL), "api/users/" + ChangePasswordActivity.this.model.userId + "/password", param, ChangePasswordActivity.this.getApliAccessToken());
        }

        protected void onPostExecuteBody(AppIdentityResult result) {
            if (result.succeeded) {
                ChangePasswordActivity.this.finish();
            } else {
                new Builder(ChangePasswordActivity.this).setMessage(TextUtils.join(", ", result.errors)).setNeutralButton(R.string.cast_invalid_stream_duration_text, new C08491()).setIcon(R.drawable.cast_ic_expanded_controller_stop).show();
            }
        }

        class C08491 implements OnClickListener {
            C08491() {
            }

            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        }
    }
}

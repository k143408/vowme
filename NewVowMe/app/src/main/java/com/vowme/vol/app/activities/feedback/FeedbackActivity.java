package com.vowme.vol.app.activities.feedback;

import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog.Builder;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.vowme.app.models.Enum.ActivityCode;
import com.vowme.app.models.Enum.HttpRequestType;
import com.vowme.app.models.api.VolunteerEoiModel;
import com.vowme.app.utilities.activities.FormValidationActivity;
import com.vowme.app.utilities.api.ApiRestFullRequest;
import com.vowme.app.utilities.api.ApiWCFRequest;
import com.vowme.app.utilities.customWidgets.CustomSpinnerTextView;
import com.vowme.app.utilities.helpers.TextViewHelper;
import com.vowme.vol.app.R;
import com.vowme.vol.app.activities.expressOfInterest.ExpressInterestSendedActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class FeedbackActivity extends FormValidationActivity {

    private TextView firstName;
    private TextView lastName;
    private VolunteerEoiModel model;
    private String oppId;
    private TextView skills;
    private Toolbar toolbar;

    private class GetVolunteerEoiDetails extends ApiRestFullRequest {
        public GetVolunteerEoiDetails() {
            super(HttpRequestType.GET, FeedbackActivity.this.getString(R.string.apiVolunteerURL1), "api/opportunity/eoi/"+FeedbackActivity.this.getUserAccessToken()+"/"+FeedbackActivity.this.oppId, FeedbackActivity.this.getUserAccessToken());
        }

        protected void onPostExecuteBody(String result) {
            if (result != null && !"".equals(result)) {
                try {
                    FeedbackActivity.this.model = new VolunteerEoiModel(new JSONObject(result), FeedbackActivity.this.getResources().getString(R.string.client_id));
                    FeedbackActivity.this.firstName.setText(FeedbackActivity.this.model.getFirstName());
                    FeedbackActivity.this.lastName.setText(FeedbackActivity.this.model.getLastName());
                    FeedbackActivity.this.skills.setText("");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private class postViktorEoi extends ApiWCFRequest {

        class C08061 implements OnClickListener {
            C08061() {
            }

            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        }

        class C08072 implements OnClickListener {
            C08072() {
            }

            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        }

        public postViktorEoi(JSONObject params) {
            super(HttpRequestType.POST, FeedbackActivity.this.getString(R.string.apiVolunteerURL1), "api/opportunity/feedback/"+FeedbackActivity.this.getUserAccessToken()+"/"+FeedbackActivity.this.oppId, params);
        }

        protected void onPostExecuteBody(String result) {
            try {
                CharSequence message = new JSONObject(result).getString("message");
                if (message.length() == 0 || message.equals("\"Created\"")) {
                    FeedbackActivity.this.dismissProgress();
                    FeedbackActivity.this.startActivityForResult(new Intent(FeedbackActivity.this, ExpressInterestSendedActivity.class), ActivityCode.EXPRESSEDINTEREST.getValue());
                    return;
                }
                if (message.equals("APPLICATION_EXISTS")) {
                    message = "You have already expressed an interest for this opportunity.";
                }
                if (message.equals("LIMIT_REACHED")) {
                    message = "You have reached your limit for expressing interest in opportunities. Please come back another time.";
                }
                FeedbackActivity.this.enableSubmitButton();
                FeedbackActivity.this.dismissProgress();
                new Builder(FeedbackActivity.this).setMessage(message).setNeutralButton(R.string.cast_ad_label, new C08061()).setIcon(R.drawable.ic_location_on_24dp).show();
            } catch (JSONException e) {
                e.printStackTrace();
                FeedbackActivity.this.dismissProgress();
                new Builder(FeedbackActivity.this).setMessage((CharSequence) "An error occurred").setNeutralButton(R.string.cast_ad_label, new C08072()).setIcon(R.drawable.ic_location_on_24dp).show();
            }
        }
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.oppId = new String(getIntent().getStringExtra(getResources().getString(R.string.EXTRA_OPP_ID)));
        if (isUserLoggedIn()) {
            setContentView(R.layout.activity_feedback);
            TextView oppFor = (TextView) findViewById(R.id.opp_for);
            ((TextView) findViewById(R.id.opp_title)).setText(getIntent().getStringExtra(getResources().getString(R.string.EXTRA_OPP_TITLE)));
            TextViewHelper.formatOppForSubtitle(this, oppFor, getIntent().getStringExtra(getResources().getString(R.string.EXTRA_ORGA_NAME)), getIntent().getStringExtra(getResources().getString(R.string.EXTRA_ORGA_CAUSE)), null);
            this.firstName = (TextView) findViewById(R.id.firstname_txt);
            this.lastName = (TextView) findViewById(R.id.lastname_txt);
            this.skills = (TextView) findViewById(R.id.skills_txt);
            initFieldsSubmitButton();
            new GetVolunteerEoiDetails().execute(new Void[0]);
        } else {
            setContentView((int) R.layout.activity_express_interest_unauthenticated);
            putUserIsFromExpressInterest(true);
        }
        this.toolbar = (Toolbar) findViewById(R.id.toolbar_no_tabs);
        setSupportActionBar(this.toolbar);
        ActionBar ab = getSupportActionBar();
        if (ab != null) {
            ab.setDisplayHomeAsUpEnabled(true);
            ab.setHomeAsUpIndicator((int) R.mipmap.ic_close_white_24dp);
        }
    }

    public boolean onPrepareOptionsMenu(Menu menu) {
        if (isUserLoggedIn()) {
            this.toolbar.setTitle(getResources().getString(R.string.title_activity_feedback));
        } else {
            this.toolbar.setTitle((CharSequence) "");
        }
        return true;
    }

    protected void initFieldsSubmitButton() {
        this.submitButton = (TextView) findViewById(R.id.button_express_interest);
        disableSubmitButton();
        TextInputLayout floatingFirstnameText = (TextInputLayout) findViewById(R.id.input_layout_firstname);
        if (!(floatingFirstnameText == null || floatingFirstnameText.getEditText() == null)) {
            floatingFirstnameText.getEditText().addTextChangedListener(getFloatingTextLengthValidator(floatingFirstnameText, 1, 100, "Please enter your first name.", "Your first name can have length up to 100 characters."));
        }
        TextInputLayout floatingLastnameText = (TextInputLayout) findViewById(R.id.input_layout_lastname);
        if (!(floatingLastnameText == null || floatingLastnameText.getEditText() == null)) {
            floatingLastnameText.getEditText().addTextChangedListener(getFloatingTextLengthValidator(floatingLastnameText, 1, 100, "Please enter your last name.", "Your last name can have length up to 100 characters."));
        }
    }

    public void Feedback(View view) {
        disableSubmitButton();
        this.model.setQualification(this.skills.getText().toString());
        showProgress("Please wait while sending your feedback...");
        new postViktorEoi(this.model.toJsonObject()).execute(new Void[0]);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == ActivityCode.EXPRESSEDINTEREST.getValue()) {
            if (resultCode == -1) {
                putUserNeedUpdateRecommended(true);
                setResult(-1, null);
                finish();
            }
        } else if (requestCode == ActivityCode.GETSTARTED.getValue() && resultCode == -1) {
            finish();
        }
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case 16908332:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}

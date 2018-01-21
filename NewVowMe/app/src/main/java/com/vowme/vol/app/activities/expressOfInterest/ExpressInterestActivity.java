package com.vowme.vol.app.activities.expressOfInterest;

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

import com.vowme.vol.app.R;
import com.vowme.app.models.Enum.ActivityCode;
import com.vowme.app.models.Enum.HttpRequestType;
import com.vowme.app.models.api.VolunteerEoiModel;
import com.vowme.app.utilities.activities.FormValidationActivity;
import com.vowme.app.utilities.api.ApiRestFullRequest;
import com.vowme.app.utilities.api.ApiWCFRequest;
import com.vowme.app.utilities.customWidgets.CustomSpinnerTextView;
import com.vowme.app.utilities.helpers.TextViewHelper;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class ExpressInterestActivity extends FormValidationActivity {
    private TextView email;
    private TextView firstName;
    private CustomSpinnerTextView gender;
    private TextView homePhone;
    private TextView lastName;
    private VolunteerEoiModel model;
    private String oppId;
    private TextView postcode;
    private TextView skills;
    private Toolbar toolbar;
    private final List<String> values = new C08051();
    private TextView yearBirth;

    class C08051 extends ArrayList<String> {
        C08051() {
            add("");
            add("Male");
            add("Female");
            add("X");
        }
    }

    private class GetVolunteerEoiDetails extends ApiRestFullRequest {
        public GetVolunteerEoiDetails() {
            super(HttpRequestType.GET, ExpressInterestActivity.this.getString(R.string.apiVolunteerURL1), "api/opportunity/eoi/"+ExpressInterestActivity.this.getUserAccessToken()+"/"+ExpressInterestActivity.this.oppId, ExpressInterestActivity.this.getUserAccessToken());
        }

        protected void onPostExecuteBody(String result) {
            if (result != null && !"".equals(result)) {
                try {
                    ExpressInterestActivity.this.model = new VolunteerEoiModel(new JSONObject(result), ExpressInterestActivity.this.getResources().getString(R.string.client_id));
                    ExpressInterestActivity.this.firstName.setText(ExpressInterestActivity.this.model.getFirstName());
                    ExpressInterestActivity.this.lastName.setText(ExpressInterestActivity.this.model.getLastName());
                    ExpressInterestActivity.this.yearBirth.setText(ExpressInterestActivity.this.model.getYearOfBirth().toString());
                    ExpressInterestActivity.this.postcode.setText(ExpressInterestActivity.this.model.getPostcode());
                    ExpressInterestActivity.this.email.setText(ExpressInterestActivity.this.model.getEmail());
                    String mGender = ExpressInterestActivity.this.model.getGender();
                    CustomSpinnerTextView access$600 = ExpressInterestActivity.this.gender;
                    if ("F".equals(mGender)) {
                        mGender = "Female";
                    } else if ("M".equals(mGender)) {
                        mGender = "Male";
                    }
                    access$600.setText(mGender);
                    ExpressInterestActivity.this.homePhone.setText(ExpressInterestActivity.this.model.getPhoneNumber());
                    ExpressInterestActivity.this.skills.setText(ExpressInterestActivity.this.model.getQualification());
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
            super(HttpRequestType.POST, ExpressInterestActivity.this.getString(R.string.apiVolunteerURL1), "api/opportunity/eoi/"+ExpressInterestActivity.this.getUserAccessToken()+"/"+ExpressInterestActivity.this.oppId, params);
        }

        protected void onPostExecuteBody(String result) {
            try {
                CharSequence message = new JSONObject(result).getString("message");
                if (message.length() == 0 || message.equals("\"Created\"")) {
                    ExpressInterestActivity.this.dismissProgress();
                    ExpressInterestActivity.this.startActivityForResult(new Intent(ExpressInterestActivity.this, ExpressInterestSendedActivity.class), ActivityCode.EXPRESSEDINTEREST.getValue());
                    return;
                }
                if (message.equals("APPLICATION_EXISTS")) {
                    message = "You have already expressed an interest for this opportunity.";
                }
                if (message.equals("LIMIT_REACHED")) {
                    message = "You have reached your limit for expressing interest in opportunities. Please come back another time.";
                }
                ExpressInterestActivity.this.enableSubmitButton();
                ExpressInterestActivity.this.dismissProgress();
                new Builder(ExpressInterestActivity.this).setMessage(message).setNeutralButton(R.string.cast_ad_label, new C08061()).setIcon(R.drawable.ic_location_on_24dp).show();
            } catch (JSONException e) {
                e.printStackTrace();
                ExpressInterestActivity.this.dismissProgress();
                new Builder(ExpressInterestActivity.this).setMessage((CharSequence) "An error occurred").setNeutralButton(R.string.cast_ad_label, new C08072()).setIcon(R.drawable.ic_location_on_24dp).show();
            }
        }
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.oppId = new String(getIntent().getStringExtra(getResources().getString(R.string.EXTRA_OPP_ID)));
        if (isUserLoggedIn()) {
            setContentView(R.layout.activity_express_interest);
            TextView oppFor = (TextView) findViewById(R.id.opp_for);
            ((TextView) findViewById(R.id.opp_title)).setText(getIntent().getStringExtra(getResources().getString(R.string.EXTRA_OPP_TITLE)));
            TextViewHelper.formatOppForSubtitle(this, oppFor, getIntent().getStringExtra(getResources().getString(R.string.EXTRA_ORGA_NAME)), getIntent().getStringExtra(getResources().getString(R.string.EXTRA_ORGA_CAUSE)), null);
            this.firstName = (TextView) findViewById(R.id.firstname_txt);
            this.lastName = (TextView) findViewById(R.id.lastname_txt);
            this.yearBirth = (TextView) findViewById(R.id.birth_txt);
            this.postcode = (TextView) findViewById(R.id.postcode_txt);
            this.email = (TextView) findViewById(R.id.email_txt);
            this.homePhone = (TextView) findViewById(R.id.phone_txt);
            this.skills = (TextView) findViewById(R.id.skills_txt);
            this.gender = (CustomSpinnerTextView) findViewById(R.id.spinner_txt);
            this.gender.setHint("Gender");
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
            this.toolbar.setTitle(getResources().getString(R.string.title_activity_express_interest));
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
        TextInputLayout floatingYearBirthText = (TextInputLayout) findViewById(R.id.input_layout_birth);
        Integer year = Integer.valueOf(Calendar.getInstance().get(Calendar.YEAR) - 18);
        if (!(floatingYearBirthText == null || floatingYearBirthText.getEditText() == null)) {
            floatingYearBirthText.getEditText().addTextChangedListener(getFloatingTextRangeYearValidator(floatingYearBirthText, 1900, year.intValue(), "Please enter your year of birth.", "Your year of birth should be 4 digits between 1900 and " + year.toString()));
        }
        TextInputLayout floatingPostcodeText = (TextInputLayout) findViewById(R.id.input_layout_postcode);
        if (!(floatingPostcodeText == null || floatingPostcodeText.getEditText() == null)) {
            floatingPostcodeText.getEditText().addTextChangedListener(getFloatingTextRegexValidator(floatingPostcodeText, getResources().getString(R.string.cnic_regex), "Please enter a proper CNIC format.", "The CNIC must be written in NADRA's format ."));
        }
        TextInputLayout floatingEmailText = (TextInputLayout) findViewById(R.id.input_layout_email);
        if (!(floatingEmailText == null || floatingEmailText.getEditText() == null)) {
            floatingEmailText.getEditText().addTextChangedListener(getFloatingTextRegexValidator(floatingEmailText, getResources().getString(R.string.email_regex), "Please enter your email address.", "Please enter a valid email address."));
        }
        /*TextInputLayout floatingPhoneText = (TextInputLayout) findViewById(R.id.input_layout_phone);
        if (!(floatingPhoneText == null || floatingPhoneText.getEditText() == null)) {
            floatingPhoneText.getEditText().addTextChangedListener(getFloatingTextRegexValidator(floatingPhoneText, getResources().getString(R.string.phone_regex), null, "Please enter a valid phone number."));
        }
        TextInputLayout floatingSkillText = (TextInputLayout) findViewById(R.id.input_layout_skills);
        if (!(floatingSkillText == null || floatingSkillText.getEditText() == null)) {
            floatingSkillText.getEditText().addTextChangedListener(getFloatingTextLengthValidator(floatingSkillText, 0, 500, "", "Your skills and qualifications can have length up to 500 characters."));
        }
        */
        this.gender.setUpSpinnerTextView(17367043, this.values);
        ((TextInputLayout) findViewById(R.id.input_layout_spinner)).setHint("Gender");
    }

    public void ExpressInterest(View view) {
        disableSubmitButton();
        this.model.setYearOfBirth(Integer.valueOf(Integer.parseInt(this.yearBirth.getText().toString())));
        this.model.setPostcode(this.postcode.getText().toString());
        this.model.setGender(this.gender.getText().toString());
        this.model.setPhoneNumber(this.homePhone.getText().toString());
        this.model.setQualification(this.skills.getText().toString());
        showProgress("Please wait while sending your express of interest...");
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

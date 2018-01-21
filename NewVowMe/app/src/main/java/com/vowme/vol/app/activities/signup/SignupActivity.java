package com.vowme.vol.app.activities.signup;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog.Builder;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.method.LinkMovementMethod;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.CallbackManager.Factory;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphRequest.GraphJSONObjectCallback;
import com.facebook.GraphResponse;
import com.facebook.Profile;
import com.facebook.ProfileTracker;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.common.api.OptionalPendingResult;
import com.linkedin.platform.APIHelper;
import com.linkedin.platform.LISession;
import com.linkedin.platform.LISessionManager;
import com.linkedin.platform.errors.LIApiError;
import com.linkedin.platform.errors.LIAuthError;
import com.linkedin.platform.listeners.ApiListener;
import com.linkedin.platform.listeners.ApiResponse;
import com.linkedin.platform.listeners.AuthListener;
import com.linkedin.platform.utils.Scope;
import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.TwitterCore;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.TwitterSession;
import com.twitter.sdk.android.core.identity.TwitterLoginButton;
import com.vowme.app.models.Enum.ActivityCode;
import com.vowme.app.models.Enum.HttpRequestType;
import com.vowme.app.models.Enum.LoginProvider;
import com.vowme.app.models.Enum.PostAccessTokenType;
import com.vowme.app.models.api.LocalVolunteerRegistrationModel;
import com.vowme.app.models.api.response.OauthRequestResponse;
import com.vowme.app.utilities.activities.FormValidationActivity;
import com.vowme.app.utilities.api.ApiRestFullRequest;
import com.vowme.app.utilities.api.oauth.PostAccessToken;
import com.vowme.app.utilities.helpers.TextViewHelper;
import com.vowme.app.utilities.spannables.CustomClickableSpan;
import com.vowme.app.utilities.validators.FloatingTextLengthValidator;
import com.vowme.app.utilities.validators.FloatingTextsCompareValidator;
import com.vowme.vol.app.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.Calendar;

public class SignupActivity extends FormValidationActivity implements OnConnectionFailedListener {
    private final int numberOfthread = 2;
    private TwitterLoginButton buttonTwitter;
    private CallbackManager callbackManager;
    private EditText emailTxt;
    private EditText firstNameTxt;
    private TextInputLayout floatingPasswordText;
    private TextInputLayout floatingRePasswordText;
    private String keyProvider = "";
    private EditText lastNameTxt;
    private String loginProvider = "";
    private LocalVolunteerRegistrationModel model;
    private EditText passwordTxt;
    private FloatingTextLengthValidator passwordValidator;
    private EditText postcodeTxt;
    private ProfileTracker profileTracker;
    private EditText rePasswordText;
    private FloatingTextsCompareValidator rePasswordValidator;
    private int threadCount;
    private Toolbar toolbar;
    private EditText userNameTxt;
    private Integer volunteerId;
    private EditText yearBirthTxt;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((int) R.layout.activity_signup);
        this.toolbar = (Toolbar) findViewById(R.id.toolbar_no_tabs);
        setSupportActionBar(this.toolbar);
        ActionBar ab = getSupportActionBar();
        if (ab != null) {
            ab.setDisplayHomeAsUpEnabled(true);
            ab.setHomeAsUpIndicator((int) R.mipmap.ic_close_white_24dp);
        }
        this.model = new LocalVolunteerRegistrationModel();
        this.passwordTxt = (EditText) findViewById(R.id.password_txt);
        this.rePasswordText = (EditText) findViewById(R.id.repassword_txt);
        TextView termPrivacy = (TextView) findViewById(R.id.term_privacy);
        termPrivacy.setMovementMethod(LinkMovementMethod.getInstance());
        TextViewHelper.formatTermsPrivacy(this, termPrivacy, null, null);
        initFieldsSubmitButton();
        initExternalLogins();
    }

    protected void initFieldsSubmitButton() {
        this.submitButton = findViewById(R.id.button_create_account);
        disableSubmitButton();
        this.firstNameTxt = (EditText) findViewById(R.id.firstname_txt);
        this.lastNameTxt = (EditText) findViewById(R.id.lastname_txt);
        this.yearBirthTxt = (EditText) findViewById(R.id.birth_txt);
        this.postcodeTxt = (EditText) findViewById(R.id.postcode_txt);
        this.userNameTxt = (EditText) findViewById(R.id.username_txt);
        this.emailTxt = (EditText) findViewById(R.id.email_txt);
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
        TextInputLayout floatingUsernameText = (TextInputLayout) findViewById(R.id.input_layout_username);
        if (!(floatingUsernameText == null || floatingUsernameText.getEditText() == null)) {
            floatingUsernameText.getEditText().addTextChangedListener(getFloatingTextLengthValidator(floatingUsernameText, 1, "Please enter a user name of your choosing."));
        }
        TextInputLayout floatingEmailText = (TextInputLayout) findViewById(R.id.input_layout_email);
        if (!(floatingEmailText == null || floatingEmailText.getEditText() == null)) {
            floatingEmailText.getEditText().addTextChangedListener(getFloatingTextRegexValidator(floatingEmailText, getResources().getString(R.string.email_regex), "Please enter your email address.", "Please enter a valid email address."));
        }
        this.floatingPasswordText = (TextInputLayout) findViewById(R.id.input_layout_password);
        if (!(this.floatingPasswordText == null || this.floatingPasswordText.getEditText() == null)) {
            this.passwordValidator = getFloatingTextLengthValidator(this.floatingPasswordText, 1, "Please enter your password.");
            this.floatingPasswordText.getEditText().addTextChangedListener(this.passwordValidator);
            this.floatingPasswordText.getEditText().addTextChangedListener(new C08563());
        }
        this.floatingRePasswordText = (TextInputLayout) findViewById(R.id.input_layout_repassword);
        if (this.floatingRePasswordText != null && this.floatingRePasswordText.getEditText() != null) {
            this.rePasswordValidator = getFloatingTextsCompareValidator(this.floatingRePasswordText, this.passwordTxt, "Please retype your password.", "Please retype your password.");
            this.floatingRePasswordText.getEditText().addTextChangedListener(this.rePasswordValidator);
        }
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case 16908332:
                signoutExternalLogins();
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void CreateAccount(View view) {
        boolean z = true;
        disableSubmitButton();
        this.model = new LocalVolunteerRegistrationModel(this.userNameTxt.getText().toString(), this.firstNameTxt.getText().toString(), this.lastNameTxt.getText().toString(), this.emailTxt.getText().toString(), this.yearBirthTxt.getText().toString(), this.postcodeTxt.getText().toString(), this.passwordTxt.getText().toString(), this.loginProvider, this.keyProvider, true);
        showProgress("Please wait while creating your account...");
        JSONObject toJsonObject = this.model.toJsonObject();
        if (TextUtils.isEmpty(this.loginProvider)) {
            z = false;
        }
        new postVolunteerRegistration(toJsonObject, z).execute(new Void[0]);
    }

    private String extractErrorss(JSONArray errors) throws JSONException {
        String result = "";
        int i = 0;
        while (i < errors.length() - 1) {
            result = result + errors.get(i) + "\n";
            i++;
        }
        return result + errors.get(i);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        this.callbackManager.onActivityResult(requestCode, resultCode, data);
    //    LISessionManager.getInstance(getApplicationContext()).onActivityResult(this, requestCode, resultCode, data);
    //    this.buttonTwitter.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ActivityCode.RC_SIGN_IN.getValue()) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            if (result.isSuccess()) {
                executeActionGoogle(result);
                return;
            }
            Auth.GoogleSignInApi.signOut(this.mGoogleApiClient);
            showExternalError("There has been an error connecting to Google+ please try again.");
        }
    }

    private void initExternalLogins() {
        initFacebook();
      //  initTwitter();
        initGoogle();
    }

    public void facebookLogin(View view) {
        AccessToken facebookToken = AccessToken.getCurrentAccessToken();
        if (facebookToken == null || facebookToken.getDeclinedPermissions().contains(facebookToken.getApplicationId()) || facebookToken.isExpired()) {
            LoginManager.getInstance().logInWithReadPermissions((Activity) this, Arrays.asList(new String[]{"public_profile", "email"}));
            return;
        }
        executeActionFacebbok(facebookToken);
    }

    private void initFacebook() {
        this.callbackManager = Factory.create();
        LoginManager.getInstance().registerCallback(this.callbackManager, new FacebookLoginListener());
        this.profileTracker = new ProfileTrackerLister();
    }

    private void executeActionFacebbok(AccessToken facebookToken) {
        updateView(LoginProvider.FACEBOOK);
        this.loginProvider = LoginProvider.FACEBOOK.getValue();
        this.keyProvider = facebookToken.getUserId();
        GraphRequest request = GraphRequest.newMeRequest(facebookToken, new GraphJSONObjectListener());
        Bundle parameters = new Bundle();
        parameters.putString(GraphRequest.FIELDS_PARAM, "email");
        request.setParameters(parameters);
        request.executeAsync();
        Profile profile = Profile.getCurrentProfile();
        if (profile != null) {
            setFields(profile.getName().replace(" ", ""), profile.getFirstName(), profile.getLastName());
        }
    }

    public void linkedinLogin(View view) {
        LISession session = LISessionManager.getInstance(getApplicationContext()).getSession();
        if (session == null || !session.isValid()) {
            initLiSessionManager();
            return;
        }
        com.linkedin.platform.AccessToken linkedinToken = LISessionManager.getInstance(getApplicationContext()).getSession().getAccessToken();
        if (linkedinToken == null || linkedinToken.isExpired()) {
            initLiSessionManager();
        } else {
            executeActionLinkedin(linkedinToken);
        }
    }

    private void initLiSessionManager() {
        LISessionManager.getInstance(getApplicationContext()).init(this, Scope.build(Scope.R_BASICPROFILE, Scope.R_EMAILADDRESS), new C08607(), true);
    }

    private void executeActionLinkedin(com.linkedin.platform.AccessToken linkedinToken) {
        updateView(LoginProvider.LINKEDIN);
        this.loginProvider = LoginProvider.LINKEDIN.getValue();
        APIHelper.getInstance(getApplicationContext()).getRequest(this, "https://api.linkedin.com/v1/people/~:(id,first-name,last-name,email-address)?format=json", new C08618());
    }

    private void initTwitter() {
        this.buttonTwitter = new TwitterLoginButton(this);
        this.buttonTwitter.setCallback(new C08629());
    }

    public void twitterLogin(View view) {
        TwitterSession session = (TwitterSession) TwitterCore.getInstance().getSessionManager().getActiveSession();
        if (session != null) {
            executeActionTwitter(session);
        } else {
            this.buttonTwitter.performClick();
        }
    }

    private void executeActionTwitter(TwitterSession session) {
        updateView(LoginProvider.TWITTER);
        this.loginProvider = LoginProvider.TWITTER.getValue();
        this.keyProvider = Long.toString(session.getUserId());
        String[] names = session.getUserName().split(" ");
        setFields(session.getUserName().replace(" ", ""), names[0], names.length > 1 ? names[1] : "");
    }

    private void initGoogle() {
        this.mGoogleApiClient = new GoogleApiClient.Builder(this).enableAutoManage(this, this).addApi(Auth.GOOGLE_SIGN_IN_API, new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().requestProfile().build()).build();
    }

    public void googleLogin(View view) {
        OptionalPendingResult<GoogleSignInResult> opr = Auth.GoogleSignInApi.silentSignIn(this.mGoogleApiClient);
        if (opr.isDone()) {
            executeActionGoogle((GoogleSignInResult) opr.get());
        } else {
            startActivityForResult(Auth.GoogleSignInApi.getSignInIntent(this.mGoogleApiClient), ActivityCode.RC_SIGN_IN.getValue());
        }
    }

    private void executeActionGoogle(GoogleSignInResult result) {
        updateView(LoginProvider.GOOGLE);
        this.loginProvider = LoginProvider.TWITTER.getValue();
        this.keyProvider = result.getSignInAccount().getId();
        String[] names = result.getSignInAccount().getDisplayName().split(" ");
        setFields(result.getSignInAccount().getDisplayName().replace(" ", ""), names[0], names.length > 1 ? names[1] : "");
        this.emailTxt.setText(result.getSignInAccount().getEmail());
    }

    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Auth.GoogleSignInApi.signOut(this.mGoogleApiClient);
        showExternalError("There has been an error connecting to Google+ please try again.");
    }

    private void setFields(String userName, String firstName, String lastName) {
        this.userNameTxt.setText(userName);
        this.firstNameTxt.setText(firstName);
        this.lastNameTxt.setText(lastName);
    }

    private void updateView(LoginProvider provider) {
        this.toolbar.setTitle("Connect with " + provider.getValue());
        invalidateOptionsMenu();
        this.passwordTxt.setVisibility(View.GONE);
        if (!(this.floatingPasswordText == null || this.floatingPasswordText.getEditText() == null)) {
            this.validationListHelper.removeValue(Integer.valueOf(this.floatingPasswordText.getId()));
            this.floatingPasswordText.getEditText().removeTextChangedListener(this.passwordValidator);
        }
        if (!(this.floatingRePasswordText == null || this.floatingRePasswordText.getEditText() == null)) {
            this.validationListHelper.removeValue(Integer.valueOf(this.floatingRePasswordText.getId()));
            this.floatingRePasswordText.getEditText().removeTextChangedListener(this.rePasswordValidator);
        }
        this.rePasswordText.setVisibility(View.GONE);
    }

    public void onDestroy() {
        super.onDestroy();
        this.profileTracker.stopTracking();
    }


    class C08563 implements TextWatcher {
        C08563() {
        }

        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        public void onTextChanged(CharSequence s, int start, int before, int count) {
            SignupActivity.this.rePasswordText.getText().clear();
        }

        public void afterTextChanged(Editable s) {
        }
    }

    class FacebookLoginListener implements FacebookCallback<LoginResult> {
        FacebookLoginListener() {
        }

        public void onSuccess(LoginResult loginResult) {
            SignupActivity.this.executeActionFacebbok(loginResult.getAccessToken());
        }

        public void onCancel() {
        }

        public void onError(FacebookException error) {
            LoginManager.getInstance().logOut();
            SignupActivity.this.showExternalError("There has been an error connecting to Facebook please try again.");
        }
    }

    class ProfileTrackerLister extends ProfileTracker {
        ProfileTrackerLister() {
        }

        protected void onCurrentProfileChanged(Profile oldProfile, Profile currentProfile) {
            if (currentProfile != null) {
                SignupActivity.this.setFields(currentProfile.getName().replace(" ", ""), currentProfile.getFirstName(), currentProfile.getLastName());
            }
        }
    }

    class GraphJSONObjectListener implements GraphJSONObjectCallback {
        GraphJSONObjectListener() {
        }

        public void onCompleted(JSONObject object, GraphResponse response) {
            try {
                SignupActivity.this.emailTxt.setText(object.getString("email"));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    class C08607 implements AuthListener {
        C08607() {
        }

        public void onAuthSuccess() {
            SignupActivity.this.executeActionLinkedin(LISessionManager.getInstance(SignupActivity.this.getApplicationContext()).getSession().getAccessToken());
        }

        public void onAuthError(LIAuthError error) {
            LISessionManager.getInstance(SignupActivity.this.getApplicationContext()).clearSession();
            SignupActivity.this.showExternalError("There has been an error connecting to Linkedin please try again.");
        }
    }

    class C08618 implements ApiListener {
        C08618() {
        }

        public void onApiSuccess(ApiResponse apiResponse) {
            JSONObject result = apiResponse.getResponseDataAsJson();
            try {
                SignupActivity.this.keyProvider = result.getString("id");
                SignupActivity.this.setFields(result.getString("firstName") + result.getString("lastName"), result.getString("firstName"), result.getString("lastName"));
                SignupActivity.this.emailTxt.setText(result.getString("emailAddress"));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        public void onApiError(LIApiError liApiError) {
            LISessionManager.getInstance(SignupActivity.this.getApplicationContext()).clearSession();
            SignupActivity.this.showExternalError("There has been an error connecting to Linkedin please try again.");
        }
    }

    class C08629 extends Callback<TwitterSession> {
        C08629() {
        }

        public void success(Result<TwitterSession> result) {
            SignupActivity.this.executeActionTwitter((TwitterSession) result.data);
        }

        public void failure(TwitterException e) {
            TwitterCore.getInstance().logOut();
            SignupActivity.this.showExternalError("There has been an error connecting to Twitter please try again.");
        }
    }

    private class GetAccessTokenUser extends PostAccessToken {
        public GetAccessTokenUser(String userName, String password) {
            super(SignupActivity.this.getString(R.string.apiVolunteerURL1), "oauth/token", PostAccessTokenType.PASSWORD, SignupActivity.this.getBaseContext(), userName, password);
        }

        protected void onPostExecuteBody(OauthRequestResponse result) {
            JSONObject jSONObject;
            JSONException e;
            try {
                JSONObject json = new JSONObject(result.getJsonAsString());

                if (result.getErrorCode() == ItemTouchHelper.Callback.DEFAULT_DRAG_ANIMATION_DURATION) {
                    SignupActivity.this.successLogin(json);
                }
                jSONObject = json;

            } catch (JSONException e3) {
                e = e3;
                e.printStackTrace();
                SignupActivity.this.dismissProgress();
                SignupActivity.this.finish();
            }
            SignupActivity.this.dismissProgress();
            SignupActivity.this.finish();
        }
    }

    private class postVolunteerRegistration extends ApiRestFullRequest {

        public postVolunteerRegistration(JSONObject param, boolean isExternal) {
            super(HttpRequestType.POST, SignupActivity.this.getString(R.string.apiVolunteerURL1), "api/account/register-volunteer", param, SignupActivity.this.getApliAccessToken());
        }

        protected void onPostExecuteBody(String result) {
            try {
                JSONObject json = new JSONObject(result);
                JSONArray errors = json.getJSONArray("errors");
                boolean isExistingVolunteer = json.getBoolean("existingVolunteer");
                if (errors.length() != 0 || isExistingVolunteer) {
                    SignupActivity.this.dismissProgress();
                    CharSequence message = "";
                    if (errors.length() > 0) {
                        message = SignupActivity.this.extractErrorss(errors);
                    } else if (isExistingVolunteer) {
                        message = "This user already exists.";
                    }
                    new Builder(SignupActivity.this).setMessage(message).setNeutralButton(R.string.cast_invalid_stream_duration_text, new C08631()).setIcon(R.drawable.cast_ic_expanded_controller_stop).show();
                    return;
                }
                SignupActivity.this.volunteerId = Integer.valueOf(json.getInt("volunteerId"));
                SignupActivity.this.threadCount = 0;
                new GetAccessTokenUser(SignupActivity.this.model.getUserName(), SignupActivity.this.model.getPassword()).execute(new Void[0]);
              //  new putVolunteerBasicData("interest", "\"" + TextUtils.join(",", SignupActivity.this.getAdjustmentInterests()) + "\"").execute(new Void[0]);
            } catch (JSONException e) {
                SignupActivity.this.enableSubmitButton();
                SignupActivity.this.dismissProgress();
                e.printStackTrace();
            }
        }

        class C08631 implements OnClickListener {
            C08631() {
            }

            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        }
    }

    private class putVolunteerBasicData extends ApiRestFullRequest {
        public putVolunteerBasicData(String dataName, String param) {
            super(HttpRequestType.PUT, SignupActivity.this.getString(R.string.apiVolunteerURL1), "api/volunteer/" + SignupActivity.this.volunteerId.toString() + "/" + dataName, param, SignupActivity.this.getApliAccessToken());
        }

        protected void onPostExecuteBody(String result) {
            SignupActivity.this.threadCount = SignupActivity.this.threadCount + 1;
            if (SignupActivity.this.threadCount == 2) {
                SignupActivity.this.dismissProgress();
                SignupActivity.this.showProgress("Please wait while logging in...");
                new GetAccessTokenUser(SignupActivity.this.model.getUserName(), SignupActivity.this.model.getPassword()).execute(new Void[0]);
            }
        }
    }
}

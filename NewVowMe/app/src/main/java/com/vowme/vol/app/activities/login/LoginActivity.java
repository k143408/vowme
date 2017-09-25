package com.vowme.vol.app.activities.login;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog.Builder;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.CallbackManager.Factory;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
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
import com.vowme.app.models.Enum.LoginProvider;
import com.vowme.app.models.Enum.PostAccessTokenType;
import com.vowme.app.models.api.response.OauthRequestResponse;
import com.vowme.app.utilities.activities.FormValidationActivity;
import com.vowme.app.utilities.api.oauth.PostAccessToken;
import com.vowme.vol.app.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;

public class LoginActivity extends FormValidationActivity implements OnConnectionFailedListener {
    private static final String INVALIDE_USER_MESSAGE = "Invalid user";
    private static final String MESSAGE_PROGRESS = "Please wait while logging in...";
    private static final String OAUTH_ERROR_DESCRIPTION = "error_description";
    private TwitterLoginButton buttonTwitter;
    private CallbackManager callbackManager;
    private EditText passwordText;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((int) R.layout.activity_login);
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar_no_tabs));
        ActionBar ab = getSupportActionBar();
        if (ab != null) {
            ab.setDisplayHomeAsUpEnabled(true);
            ab.setHomeAsUpIndicator((int) R.mipmap.ic_close_white_24dp);
        }
        initFieldsSubmitButton();
        initExternalLogins();
    }

    protected void initFieldsSubmitButton() {
        this.submitButton = (TextView) findViewById(R.id.button_sign_in);
        disableSubmitButton();
        TextInputLayout floatingUsernameText = (TextInputLayout) findViewById(R.id.input_layout_username);
        if (!(floatingUsernameText == null || floatingUsernameText.getEditText() == null)) {
            floatingUsernameText.getEditText().addTextChangedListener(getFloatingTextLengthValidator(floatingUsernameText, 1, "Please enter your user name or email address."));
        }
        TextInputLayout floatingPasswordText = (TextInputLayout) findViewById(R.id.input_layout_password);
        if (!(floatingPasswordText == null || floatingPasswordText.getEditText() == null)) {
            floatingPasswordText.getEditText().addTextChangedListener(getFloatingTextLengthValidator(floatingPasswordText, 1, "Please enter your password."));
        }
        this.passwordText = (EditText) findViewById(R.id.password_txt);
        this.passwordText.setOnEditorActionListener(new C08081());
    }

    public void SignIn(View view) {
        signInProcess();
    }

    private void signInProcess() {
        disableSubmitButton();
        EditText userNameTxt = (EditText) findViewById(R.id.username_txt);
        EditText passwordTxt = (EditText) findViewById(R.id.password_txt);
        showProgress(MESSAGE_PROGRESS);
        new GetAccessTokenUser(userNameTxt.getText().toString(), passwordTxt.getText().toString()).execute(new Void[0]);
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

    private void onPostExecuteBodyAccessToken(OauthRequestResponse result) {
        try {
            JSONObject json = new JSONObject(result.getJsonAsString());
            if (result.getErrorCode() == ItemTouchHelper.Callback.DEFAULT_DRAG_ANIMATION_DURATION) {
                successLogin(json);
                return;
            }
            enableSubmitButton();
            dismissProgress();
            CharSequence errorMessage = json.getString("error_description");
            if (INVALIDE_USER_MESSAGE.equals(errorMessage)) {
                errorMessage = "Please use your username and password or create a new account.";
            }
            new Builder(this).setMessage(errorMessage).setNeutralButton(17039370, new C08092()).setIcon(17301543).show();
        } catch (JSONException e) {
            dismissProgress();
            e.printStackTrace();
        }
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        this.callbackManager.onActivityResult(requestCode, resultCode, data);
        LISessionManager.getInstance(getApplicationContext()).onActivityResult(this, requestCode, resultCode, data);
        this.buttonTwitter.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ActivityCode.RC_SIGN_IN.getValue()) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            if (result.isSuccess()) {
                showProgress(MESSAGE_PROGRESS);
                new GetAccessTokenUserFromExternalLogin(LoginProvider.GOOGLE.getValue(), result.getSignInAccount().getId()).execute(new Void[0]);
                return;
            }
            Auth.GoogleSignInApi.signOut(this.mGoogleApiClient);
            showExternalError("There has been an error connecting to Google+ please try again.");
        }
    }

    private void initExternalLogins() {
        initFacebook();
        initTwitter();
        initGoogle();
    }

    public void facebookLogin(View view) {
        AccessToken facebookToken = AccessToken.getCurrentAccessToken();
        if (facebookToken == null || facebookToken.getDeclinedPermissions().contains(facebookToken.getApplicationId()) || facebookToken.isExpired()) {
            LoginManager.getInstance().logInWithReadPermissions((Activity) this, Arrays.asList(new String[]{"public_profile"}));
            return;
        }
        showProgress(MESSAGE_PROGRESS);
        new GetAccessTokenUserFromExternalLogin(LoginProvider.FACEBOOK.getValue(), facebookToken.getUserId()).execute(new Void[0]);
    }

    private void initFacebook() {
        this.callbackManager = Factory.create();
        LoginManager.getInstance().registerCallback(this.callbackManager, new C08103());
    }

    public void linkedinLogin(View view) {
        LISession session = LISessionManager.getInstance(getApplicationContext()).getSession();
        if (session == null || !session.isValid()) {
            initLiSessionManager();
            return;
        }
        com.linkedin.platform.AccessToken linkedinToken = session.getAccessToken();
        if (linkedinToken == null || linkedinToken.isExpired()) {
            initLiSessionManager();
        } else {
            requestLinkedInUserId();
        }
    }

    private void initLiSessionManager() {
        LISessionManager.getInstance(getApplicationContext()).init(this, Scope.build(Scope.R_BASICPROFILE), new C08114(), true);
    }

    private void requestLinkedInUserId() {
        APIHelper.getInstance(getApplicationContext()).getRequest(this, "https://api.linkedin.com/v1/people/~:(id,first-name,last-name)?format=json", new C08125());
    }

    private void initTwitter() {
        this.buttonTwitter = new TwitterLoginButton(this);
        this.buttonTwitter.setCallback(new C08136());
    }

    public void twitterLogin(View view) {
        TwitterSession session = (TwitterSession) TwitterCore.getInstance().getSessionManager().getActiveSession();
        if (session != null) {
            showProgress(MESSAGE_PROGRESS);
            new GetAccessTokenUserFromExternalLogin(LoginProvider.TWITTER.getValue(), Long.toString(session.getUserId())).execute(new Void[0]);
            return;
        }
        this.buttonTwitter.performClick();
    }

    private void initGoogle() {
        this.mGoogleApiClient = new GoogleApiClient.Builder(this).enableAutoManage(this, this).addApi(Auth.GOOGLE_SIGN_IN_API, new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().requestProfile().build()).build();
    }

    public void googleLogin(View view) {
        OptionalPendingResult<GoogleSignInResult> opr = Auth.GoogleSignInApi.silentSignIn(this.mGoogleApiClient);
        if (opr.isDone()) {
            GoogleSignInResult result = (GoogleSignInResult) opr.get();
            showProgress(MESSAGE_PROGRESS);
            new GetAccessTokenUserFromExternalLogin(LoginProvider.GOOGLE.getValue(), result.getSignInAccount().getId()).execute(new Void[0]);
            return;
        }
        startActivityForResult(Auth.GoogleSignInApi.getSignInIntent(this.mGoogleApiClient), ActivityCode.RC_SIGN_IN.getValue());
    }

    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Auth.GoogleSignInApi.signOut(this.mGoogleApiClient);
        showExternalError("There has been an error connecting to Google+ please try again.");
    }

    class C08081 implements OnEditorActionListener {
        C08081() {
        }

        public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
            if (LoginActivity.this.validationListHelper.isAllValuesValid()) {
                LoginActivity.this.signInProcess();
            }
            return true;
        }
    }

    class C08092 implements OnClickListener {
        C08092() {
        }

        public void onClick(DialogInterface dialog, int which) {
            dialog.cancel();
        }
    }

    class C08103 implements FacebookCallback<LoginResult> {
        C08103() {
        }

        public void onSuccess(LoginResult loginResult) {
            LoginActivity.this.showProgress(LoginActivity.MESSAGE_PROGRESS);
            new GetAccessTokenUserFromExternalLogin(LoginProvider.FACEBOOK.getValue(), loginResult.getAccessToken().getUserId()).execute(new Void[0]);
        }

        public void onCancel() {
        }

        public void onError(FacebookException error) {
            LoginManager.getInstance().logOut();
            LoginActivity.this.showExternalError("There has been an error connecting to Facebook please try again.");
        }
    }

    class C08114 implements AuthListener {
        C08114() {
        }

        public void onAuthSuccess() {
            LoginActivity.this.requestLinkedInUserId();
        }

        public void onAuthError(LIAuthError error) {
            LISessionManager.getInstance(LoginActivity.this.getApplicationContext()).clearSession();
            LoginActivity.this.showExternalError("There has been an error connecting to Linkedin please try again.");
        }
    }

    class C08125 implements ApiListener {
        C08125() {
        }

        public void onApiSuccess(ApiResponse apiResponse) {
            JSONObject result = apiResponse.getResponseDataAsJson();
            try {
                LoginActivity.this.showProgress(LoginActivity.MESSAGE_PROGRESS);
                new GetAccessTokenUserFromExternalLogin(LoginProvider.LINKEDIN.getValue(), result.getString("id")).execute(new Void[0]);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        public void onApiError(LIApiError liApiError) {
            LISessionManager.getInstance(LoginActivity.this.getApplicationContext()).clearSession();
            LoginActivity.this.showExternalError("There has been an error connecting to Linkedin please try again.");
        }
    }

    class C08136 extends Callback<TwitterSession> {
        C08136() {
        }

        public void success(Result<TwitterSession> result) {
            LoginActivity.this.showProgress(LoginActivity.MESSAGE_PROGRESS);
            new GetAccessTokenUserFromExternalLogin(LoginProvider.TWITTER.getValue(), Long.toString(((TwitterSession) result.data).getUserId())).execute(new Void[0]);
        }

        public void failure(TwitterException e) {
            TwitterCore.getInstance().getSessionManager().clearActiveSession();
            LoginActivity.this.showExternalError("There has been an error connecting to Twitter please try again.");
        }
    }

    private class GetAccessTokenUser extends PostAccessToken {
        public GetAccessTokenUser(String userName, String password) {
            super(LoginActivity.this.getString(R.string.apiVolunteerURL), "oauth/token", PostAccessTokenType.PASSWORD, LoginActivity.this.getBaseContext(), userName, password);
        }

        protected void onPostExecuteBody(OauthRequestResponse result) {
            LoginActivity.this.onPostExecuteBodyAccessToken(result);
        }
    }

    private class GetAccessTokenUserFromExternalLogin extends PostAccessToken {
        public GetAccessTokenUserFromExternalLogin(String loginProvider, String providerKey) {
            super(LoginActivity.this.getString(R.string.apiVolunteerURL), "oauth/token", LoginActivity.this.getBaseContext(), loginProvider, providerKey);
        }

        protected void onPostExecuteBody(OauthRequestResponse result) {
            LoginActivity.this.onPostExecuteBodyAccessToken(result);
        }
    }
}

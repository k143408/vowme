package com.vowme.vol.app.activities.settings;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.CallbackManager.Factory;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.internal.AnalyticsEvents;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient.Builder;
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
import com.vowme.app.models.api.AppUserLoginAPI;
import com.vowme.app.models.api.AppUserLoginInfo;
import com.vowme.app.utilities.activities.BaseActivity;
import com.vowme.app.utilities.api.ApiRestFullRequest;
import com.vowme.app.utilities.helpers.JSONHelper;
import com.vowme.vol.app.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.HashMap;

public class AccountActivity extends BaseActivity implements OnConnectionFailedListener {
    private final String CONNECTED = "Connected";
    private final String NOTCONNECTED = "Not Connected";
    private AppUserLoginInfo appUserLoginInfo;
    private TwitterLoginButton buttonTwitter;
    private CallbackManager callbackManager;
    private TextView facebookText;
    private TextView googleText;
    private TextView linkedinText;
    private TextView twitterText;
    private HashMap<LoginProvider, String> userLogins;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((int) R.layout.activity_account);
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar_no_tabs));
        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);
        ab.setHomeAsUpIndicator((int) R.mipmap.ic_arrow_back_white_24dp);
        this.facebookText = (TextView) findViewById(R.id.facebook_txt);
        this.linkedinText = (TextView) findViewById(R.id.linkedin_txt);
        this.twitterText = (TextView) findViewById(R.id.twitter_txt);
        this.googleText = (TextView) findViewById(R.id.google_txt);
        this.userLogins = new HashMap();
        new GetVolunteerLogins().execute(new Void[0]);
        initExternalLogins();
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

    public void onClick(View v) {
        Intent intent = null;
        switch (v.getId()) {
            case R.id.act_account_details:
                intent = new Intent(this, DetailsAccountActivity.class);
                break;
            case R.id.act_change_password:
                intent = new Intent(this, ChangePasswordActivity.class);
                break;
            case R.id.facebook_txt:
                if (!this.userLogins.containsKey(LoginProvider.FACEBOOK)) {
                    AccessToken facebookToken = AccessToken.getCurrentAccessToken();
                    if (facebookToken != null && !facebookToken.getDeclinedPermissions().contains(facebookToken.getApplicationId()) && !facebookToken.isExpired()) {
                        postLogin(LoginProvider.FACEBOOK, facebookToken.getUserId());
                        break;
                    }
                    LoginManager.getInstance().logInWithReadPermissions((Activity) this, Arrays.asList(new String[]{"public_profile"}));
                    break;
                }
                new DeleteVolunteerLogins(LoginProvider.FACEBOOK.getValue(), (String) this.userLogins.get(LoginProvider.FACEBOOK)).execute(new Void[0]);
                LoginManager.getInstance().logOut();
                break;

            case R.id.linkedin_txt:
                if (!this.userLogins.containsKey(LoginProvider.LINKEDIN)) {
                    LISession session = LISessionManager.getInstance(getApplicationContext()).getSession();
                    if (session != null && session.isValid()) {
                        com.linkedin.platform.AccessToken linkedinToken = session.getAccessToken();
                        if (linkedinToken != null && !linkedinToken.isExpired()) {
                            requestLinkedInUserId();
                            break;
                        } else {
                            initLiSessionManager();
                            break;
                        }
                    }
                    initLiSessionManager();
                    break;
                }
                new DeleteVolunteerLogins(LoginProvider.LINKEDIN.getValue(), (String) this.userLogins.get(LoginProvider.LINKEDIN)).execute(new Void[0]);
                break;
            case R.id.twitter_txt:
                if (!this.userLogins.containsKey(LoginProvider.TWITTER)) {
                    TwitterSession session2 = (TwitterSession) TwitterCore.getInstance().getSessionManager().getActiveSession();
                    if (session2 == null) {
                        this.buttonTwitter.performClick();
                        break;
                    } else {
                        postLogin(LoginProvider.TWITTER, Long.toString(session2.getUserId()));
                        break;
                    }
                }
                new DeleteVolunteerLogins(LoginProvider.TWITTER.getValue(), (String) this.userLogins.get(LoginProvider.TWITTER)).execute(new Void[0]);
                break;
            case R.id.google_txt:
                if (!this.userLogins.containsKey(LoginProvider.GOOGLE)) {
                    OptionalPendingResult<GoogleSignInResult> opr = Auth.GoogleSignInApi.silentSignIn(this.mGoogleApiClient);
                    if (!opr.isDone()) {
                        startActivityForResult(Auth.GoogleSignInApi.getSignInIntent(this.mGoogleApiClient), ActivityCode.RC_SIGN_IN.getValue());
                        break;
                    }
                    postLogin(LoginProvider.GOOGLE, ((GoogleSignInResult) opr.get()).getSignInAccount().getId());
                    break;
                }
                new DeleteVolunteerLogins(LoginProvider.GOOGLE.getValue(), (String) this.userLogins.get(LoginProvider.GOOGLE)).execute(new Void[0]);
                break;
            default:
                intent = null;
                break;
        }
        if (intent != null) {
            startActivity(intent);
        }
    }

    private void postLogin(LoginProvider loginProvide, String userID) {
        this.appUserLoginInfo = new AppUserLoginInfo(loginProvide.getValue(), userID);
        new PostVolunteerLogins(this.appUserLoginInfo).execute(new Void[0]);
    }

    private void initConnectedFieldStatus(String loginProvider, String keyProvider, boolean isAdded) {
        int obj = -1;
        switch (loginProvider.hashCode()) {
            case -198363565:
                if (loginProvider.equals("TWITTER")) {
                    obj = 2;
                    break;
                }
                break;
            case 1279756998:
                if (loginProvider.equals("FACEBOOK")) {
                    obj = 0;
                    break;
                }
                break;
            case 1977319678:
                if (loginProvider.equals("LINKEDIN")) {
                    obj = 1;
                    break;
                }
                break;
            case 2108052025:
                if (loginProvider.equals("GOOGLE")) {
                    obj = 3;
                    break;
                }
                break;
        }
        switch (obj) {
            case 0:
                if (isAdded) {
                    this.userLogins.put(LoginProvider.FACEBOOK, keyProvider);
                } else {
                    this.userLogins.remove(LoginProvider.FACEBOOK);
                }
                this.facebookText.setText(isAdded ? "Connected" : "Not Connected");
                return;
            case 1:
                if (isAdded) {
                    this.userLogins.put(LoginProvider.LINKEDIN, keyProvider);
                } else {
                    this.userLogins.remove(LoginProvider.LINKEDIN);
                }
                this.linkedinText.setText(isAdded ? "Connected" : "Not Connected");
                return;
            case 2:
                if (isAdded) {
                    this.userLogins.put(LoginProvider.TWITTER, keyProvider);
                } else {
                    this.userLogins.remove(LoginProvider.TWITTER);
                }
                this.twitterText.setText(isAdded ? "Connected" : "Not Connected");
                return;
            case 3:
                if (isAdded) {
                    this.userLogins.put(LoginProvider.GOOGLE, keyProvider);
                } else {
                    this.userLogins.remove(LoginProvider.GOOGLE);
                }
                this.googleText.setText(isAdded ? "Connected" : "Not Connected");
                return;
            default:
                return;
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
                postLogin(LoginProvider.GOOGLE, result.getSignInAccount().getId());
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

    private void initFacebook() {
        this.callbackManager = Factory.create();
        LoginManager.getInstance().registerCallback(this.callbackManager, new C08441());
    }

    private void initLiSessionManager() {
        LISessionManager.getInstance(getApplicationContext()).init(this, Scope.build(Scope.R_BASICPROFILE), new C08452(), true);
    }

    private void requestLinkedInUserId() {
        APIHelper.getInstance(getApplicationContext()).getRequest(this, "https://api.linkedin.com/v1/people/~:(id,first-name,last-name)?format=json", new C08463());
    }

    private void initTwitter() {
        this.buttonTwitter = new TwitterLoginButton(this);
        this.buttonTwitter.setCallback(new C08474());
    }

    private void initGoogle() {
        this.mGoogleApiClient = new Builder(this).enableAutoManage(this, this).addApi(Auth.GOOGLE_SIGN_IN_API, new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().requestProfile().build()).build();
    }

    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Auth.GoogleSignInApi.signOut(this.mGoogleApiClient);
        showExternalError("There has been an error connecting to Google+ please try again.");
    }

    class C08441 implements FacebookCallback<LoginResult> {
        C08441() {
        }

        public void onSuccess(LoginResult loginResult) {
            AccountActivity.this.postLogin(LoginProvider.FACEBOOK, loginResult.getAccessToken().getUserId());
        }

        public void onCancel() {
        }

        public void onError(FacebookException error) {
            LoginManager.getInstance().logOut();
            AccountActivity.this.showExternalError("There has been an error connecting to Facebook please try again.");
        }
    }

    class C08452 implements AuthListener {
        C08452() {
        }

        public void onAuthSuccess() {
            AccountActivity.this.requestLinkedInUserId();
        }

        public void onAuthError(LIAuthError error) {
            LISessionManager.getInstance(AccountActivity.this.getApplicationContext()).clearSession();
            AccountActivity.this.showExternalError("There has been an error connecting to Linkedin please try again.");
        }
    }

    class C08463 implements ApiListener {
        C08463() {
        }

        public void onApiSuccess(ApiResponse apiResponse) {
            try {
                AccountActivity.this.postLogin(LoginProvider.LINKEDIN, apiResponse.getResponseDataAsJson().getString("id"));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        public void onApiError(LIApiError liApiError) {
            LISessionManager.getInstance(AccountActivity.this.getApplicationContext()).clearSession();
            AccountActivity.this.showExternalError("There has been an error connecting to Linkedin please try again.");
        }
    }

    class C08474 extends Callback<TwitterSession> {
        C08474() {
        }

        public void success(Result<TwitterSession> result) {
            AccountActivity.this.postLogin(LoginProvider.TWITTER, Long.toString(((TwitterSession) result.data).getUserId()));
        }

        public void failure(TwitterException e) {
            TwitterCore.getInstance().logOut();
            AccountActivity.this.showExternalError("There has been an error connecting to Twitter please try again.");
        }
    }

    private class DeleteVolunteerLogins extends ApiRestFullRequest {
        private String loginProvider;

        public DeleteVolunteerLogins(String loginProvider, String providerKey) {
            super(HttpRequestType.DELETE, AccountActivity.this.getString(R.string.apiVolunteerURL), "api/users/logins?loginProvider=" + loginProvider + "&providerKey=" + providerKey, AccountActivity.this.getUserAccessToken());
            this.loginProvider = loginProvider;
        }

        protected void onPostExecuteBody(String result) {
            try {
                if (new JSONObject(result).getBoolean(AnalyticsEvents.PARAMETER_SHARE_OUTCOME_SUCCEEDED)) {
                    AccountActivity.this.initConnectedFieldStatus(this.loginProvider.toUpperCase(), "", false);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    private class GetVolunteerLogins extends ApiRestFullRequest {
        public GetVolunteerLogins() {
            super(HttpRequestType.GET, AccountActivity.this.getString(R.string.apiVolunteerURL), "api/users/logins", AccountActivity.this.getUserAccessToken());
        }

        protected void onPostExecuteBody(String result) {
            JSONArray logins = JSONHelper.getJSONArrayFromString(result);
            String loginProvider = "";
            for (int i = 0; i < logins.length(); i++) {
                try {
                    AppUserLoginAPI model = new AppUserLoginAPI(logins.getJSONObject(i));
                    AccountActivity.this.initConnectedFieldStatus(model.loginProvider.toUpperCase(), model.providerKey, true);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private class PostVolunteerLogins extends ApiRestFullRequest {
        private AppUserLoginInfo userLoginInfo;

        public PostVolunteerLogins(AppUserLoginInfo param) {
            super(HttpRequestType.POST, AccountActivity.this.getString(R.string.apiVolunteerURL), "api/users/logins", param.toJsonObject().toString(), AccountActivity.this.getUserAccessToken());
            this.userLoginInfo = param;
        }

        protected void onPostExecuteBody(String result) {
            try {
                if (new JSONObject(result).getBoolean(AnalyticsEvents.PARAMETER_SHARE_OUTCOME_SUCCEEDED)) {
                    AccountActivity.this.initConnectedFieldStatus(this.userLoginInfo.loginProvider.toUpperCase(), this.userLoginInfo.providerKey, true);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}

package com.vowme.app.utilities.activities;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog.Builder;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.helper.ItemTouchHelper.Callback;
import android.view.View;
import android.widget.TextView;

import com.facebook.FacebookSdk;
import com.facebook.login.LoginManager;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.common.api.GoogleApiClient;
import com.linkedin.platform.LISessionManager;
import com.vowme.app.models.Enum.ActivityCode;
import com.vowme.app.models.Enum.HttpRequestType;
import com.vowme.app.models.Enum.LookupType;
import com.vowme.app.models.Enum.PostAccessTokenType;
import com.vowme.app.models.Enum.SharedPreferencesKeys;
import com.vowme.app.models.SavedSearchOpportunitiesItem;
import com.vowme.app.models.SearchItem;
import com.vowme.app.models.api.OpportunityShortList;
import com.vowme.app.models.api.response.OauthRequestResponse;
import com.vowme.app.utilities.api.ApiRestFullRequest;
import com.vowme.app.utilities.api.ApiWCFRequest;
import com.vowme.app.utilities.api.oauth.PostAccessToken;
import com.vowme.app.utilities.helpers.ArrayListHelper;
import com.vowme.app.utilities.helpers.DefaultDataHelper;
import com.vowme.app.utilities.helpers.JSONHelper;
import com.vowme.app.utilities.helpers.sharedPreferences.UserAdjustmentSharedDataHelper;
import com.vowme.app.utilities.helpers.sharedPreferences.UserBookmarkSharedDataHelper;
import com.vowme.app.utilities.helpers.sharedPreferences.UserNavigationSharedDataHelper;
import com.vowme.app.utilities.helpers.sharedPreferences.UserOAuthSharedDataHelper;
import com.vowme.app.utilities.helpers.sharedPreferences.UserSearchFilterSharedDataHelper;
import com.vowme.app.utilities.helpers.sharedPreferences.UserSearchSharedLocationFieldHelper;
import com.vowme.app.utilities.helpers.sharedPreferences.UserShortlistSharedDataHelper;
import com.vowme.vol.app.R;
import com.vowme.vol.app.activities.MainActivity;
import com.vowme.vol.app.activities.login.LoginActivity;
import com.vowme.vol.app.activities.recommended.AdjustmentActivity;
import com.vowme.vol.app.activities.search.SearchFilteringActivity;
import com.vowme.vol.app.activities.signup.SignupActivity;
import com.vowme.vol.app.activities.start.WelcomeStepsActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;
import java.util.concurrent.ExecutionException;

//import com.parse.Parse;
//import com.parse.ParseTwitterUtils;
public class BaseActivity extends AppCompatActivity {
    protected DefaultDataHelper defaultDataHelper;
    protected GoogleApiClient mGoogleApiClient;
    protected ProgressDialog progressDialogActivity;
    private int threadCount;

    class C08731 implements OnClickListener {
        C08731() {
        }

        public void onClick(DialogInterface dialog, int which) {
            dialog.cancel();
        }
    }

    public class AddToShortList extends ApiRestFullRequest {
        public AddToShortList(JSONObject params) {
            super(HttpRequestType.POST, BaseActivity.this.getString(R.string.apiVolunteerURL), "api/opportunity/shortlist", params, BaseActivity.this.getUserAccessToken());
        }
    }

    public class DeleteFromShortlist extends ApiRestFullRequest {
        public DeleteFromShortlist(int oppId) {
            super(HttpRequestType.DELETE, BaseActivity.this.getString(R.string.apiVolunteerURL), "api/opportunity/shortlist/" + Integer.toString(oppId), BaseActivity.this.getUserAccessToken());
        }
    }

    private class GetApliAccessTokenUser extends PostAccessToken {
        public GetApliAccessTokenUser() {
            super(BaseActivity.this.getString(R.string.apiVolunteerURL), "oauth/token", PostAccessTokenType.CREDENTIAL, BaseActivity.this.getBaseContext());
        }

        protected void onPostExecuteBody(OauthRequestResponse result) {
            try {
                if (result.getErrorCode() == Callback.DEFAULT_DRAG_ANIMATION_DURATION) {
                    BaseActivity.this.putApliAccessTokenInfos(new JSONObject(result.getJsonAsString()));
                    BaseActivity.this.getStaticData();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    private class GetCityState extends ApiWCFRequest {
        public GetCityState() {
            super(HttpRequestType.GET, BaseActivity.this.getString(R.string.apiViktorURL), "Locations/" + BaseActivity.this.getString(R.string.apiViktorClientSecret) + "/" + BaseActivity.this.getString(R.string.apiViktorGetClientSecret));
        }
    }

    private class GetLookup extends ApiRestFullRequest {
        private LookupType lookupType;

        public GetLookup(LookupType lookupType) {
            super(HttpRequestType.GET, BaseActivity.this.getString(R.string.apiVolunteerURL), "api/lookup/" + lookupType.getValue(), BaseActivity.this.getApliAccessToken());
            this.lookupType = lookupType;
        }

        protected void onPostExecuteBody(String result) {
            if (result.length() != 0) {
                try {
                    BaseActivity.this.defaultDataHelper.loadLookup(new JSONArray(result), this.lookupType);
                    BaseActivity.this.threadCount = BaseActivity.this.threadCount + 1;
                    if (BaseActivity.this.threadCount == LookupType.values().length - 3) {
                        DefaultDataHelper defaultDataHelper = BaseActivity.this.defaultDataHelper;
                        DefaultDataHelper.setIsLoading(false);
                        defaultDataHelper = BaseActivity.this.defaultDataHelper;
                        DefaultDataHelper.setHasLoaded(true);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        this.defaultDataHelper = DefaultDataHelper.getInstance();
        //Parse.initialize(this, Constants.PARSE_APP_ID, Constants.PARSE_CLIENT_KEY);
        //TwitterAuthConfig authConfig = new TwitterAuthConfig(getResources().getString(R.string.twitter_consumerKey), getResources().getString(R.string.twitter_consumerSecret));
        //Fabric.with(this, new TwitterCore.(authConfig));
        if (isApliExpired()) {
            new GetApliAccessTokenUser().execute(new Void[0]);
        } else {
            getStaticData();
        }
    }

    private void getStaticData() {
        Exception e;
        int i;
        DefaultDataHelper defaultDataHelper = this.defaultDataHelper;
        if (!DefaultDataHelper.isLoading()) {
            defaultDataHelper = this.defaultDataHelper;
            if (!DefaultDataHelper.isHasLoaded()) {
                defaultDataHelper = this.defaultDataHelper;
                DefaultDataHelper.setIsLoading(true);
                this.defaultDataHelper.loadLookup(new JSONArray(), LookupType.AVAIBILITY);
                this.defaultDataHelper.loadLookup(new JSONArray(), LookupType.AVAIBILITYFILTER);
                try {
                    this.defaultDataHelper.loadLookup(JSONHelper.getJSONArrayFromString((String) new GetCityState().execute(new Void[0]).get()), LookupType.LOCATIONS);
                } catch (InterruptedException e2) {
                    e = e2;
                    e.printStackTrace();
                    this.threadCount = 0;
                    for (i = 0; i < LookupType.values().length - 3; i++) {
                        new GetLookup(LookupType.values()[i]).execute(new Void[0]);
                    }
                } catch (ExecutionException e3) {
                    e = e3;
                    e.printStackTrace();
                    this.threadCount = 0;
                    for (i = 0; i < LookupType.values().length - 3; i++) {
                        new GetLookup(LookupType.values()[i]).execute(new Void[0]);
                    }
                }
                this.threadCount = 0;
                for (i = 0; i < LookupType.values().length - 3; i++) {
                    new GetLookup(LookupType.values()[i]).execute(new Void[0]);
                }
            }
        }
    }

    private SharedPreferences getUserOAuthSharedData() {
        return getSharedPreferences(SharedPreferencesKeys.USER_OAUTH_SHARED_DATA.getValue(), 0);
    }

    private SharedPreferences getUserAdjustmentSharedData() {
        return getSharedPreferences(SharedPreferencesKeys.USER_ADJUSTMENT_SHARED_DATA.getValue(), 0);
    }

    private SharedPreferences getUserSearchFilterSharedData() {
        return getSharedPreferences(SharedPreferencesKeys.USER_SEARCH_FILTER_SHARED_DATA.getValue(), 0);
    }

    private SharedPreferences getUserSearchSharedLocationField() {
        return getSharedPreferences(SharedPreferencesKeys.USER_SEARCH_LOCATION_FIELD.getValue(), 0);
    }

    private SharedPreferences getUserShortlistSharedData() {
        return getSharedPreferences(SharedPreferencesKeys.USER_SHORTLIST_SHARED_DATA.getValue(), 0);
    }

    private SharedPreferences getUserBookmarkSharedData() {
        return getSharedPreferences(SharedPreferencesKeys.USER_BOOKMARK_SHARED_DATA.getValue(), 0);
    }

    private SharedPreferences getUserNavigationSharedData() {
        return getSharedPreferences(SharedPreferencesKeys.USER_NAVIGATION_SHARED_DATA.getValue(), 0);
    }

    public boolean isUserLoggedIn() {
        return UserOAuthSharedDataHelper.isUserLoggedIn(getUserOAuthSharedData());
    }

    public boolean isUserExpired() {
        return UserOAuthSharedDataHelper.isUserTokenExpired(getUserOAuthSharedData());
    }

    public void putAccessTokenInfos(JSONObject json, boolean isLoggedIn) {
        UserOAuthSharedDataHelper.putUserAccessToken(getUserOAuthSharedData(), json, isLoggedIn);
    }

    public String getRefreshToken() {
        return UserOAuthSharedDataHelper.getUserRefreshToken(getUserOAuthSharedData());
    }

    public String getUserAccessToken() {
        return UserOAuthSharedDataHelper.getUserAccessToken(getUserOAuthSharedData());
    }

    public boolean isApliExpired() {
        return UserOAuthSharedDataHelper.isApliTokenExpired(getUserOAuthSharedData());
    }

    public void putApliAccessTokenInfos(JSONObject json) {
        UserOAuthSharedDataHelper.putApliAccessToken(getUserOAuthSharedData(), json);
    }

    public String getApliAccessToken() {
        return UserOAuthSharedDataHelper.getApliAccessToken(getUserOAuthSharedData());
    }

    public void clearAccessTokenInfos() {
        UserOAuthSharedDataHelper.clearUserAccessToken(getUserOAuthSharedData());
    }

    public String getGCMAccessToken() {
        return UserOAuthSharedDataHelper.getGCMAccessToken(getUserOAuthSharedData());
    }

    public void putGCMAccessToken(String token) {
        UserOAuthSharedDataHelper.putGCMAccessToken(getUserOAuthSharedData(), token);
    }

    public boolean DeleteFromShortlist(int oppId) {
        if (isUserLoggedIn()) {
            new DeleteFromShortlist(oppId).execute(new Void[0]);
        }
        UserShortlistSharedDataHelper.removeFromShortlist(getUserShortlistSharedData(), Integer.valueOf(oppId));
        putUserNeedUpdateShortlist(true);
        putUserNeedUpdateRecommended(true);
        return true;
    }

    public boolean AddToShortList(int oppId) {
        boolean added = false;
        String source = "Mobile App";
        if (isUserLoggedIn()) {
            new AddToShortList(new OpportunityShortList(oppId, "Mobile App").toJsonObject()).execute(new Void[0]);
            UserShortlistSharedDataHelper.addToShortlist(getUserShortlistSharedData(), Integer.valueOf(oppId));
            added = true;
        } else if (UserShortlistSharedDataHelper.getShortlist(getUserShortlistSharedData()).size() < 5) {
            UserShortlistSharedDataHelper.addToShortlist(getUserShortlistSharedData(), Integer.valueOf(oppId));
            added = true;
        }
        putUserNeedUpdateShortlist(added);
        putUserNeedUpdateRecommended(added);
        return added;
    }

    public void initShortlist(TextView v, boolean isShortlisted) {
        if (isShortlisted) {
            v.setText(R.string.ic_shortlist);
            v.setTextColor(ContextCompat.getColor(v.getContext(), R.color.starShortListColor));
            return;
        }
        v.setText(R.string.ic_not_shortlist);
        v.setTextColor(ContextCompat.getColor(v.getContext(), R.color.sec_icon_light));
    }

    public void initItemMenuShortlist(TextView v, boolean isShortlisted) {
        if (v == null) {
            return;
        }
        if (isShortlisted) {
            v.setText(R.string.ic_shortlist);
            v.setTextColor(ContextCompat.getColor(v.getContext(), R.color.starShortListColor));
            return;
        }
        v.setText(R.string.ic_not_shortlist);
        v.setTextColor(-1);
    }

    public void clearShortlisted() {
        UserShortlistSharedDataHelper.clearShortlisted(getUserShortlistSharedData());
    }

    public boolean isShortlisted(int id) {
        return UserShortlistSharedDataHelper.isShortlisted(getUserShortlistSharedData(), Integer.valueOf(id));
    }

    public List<String> getShortlist() {
        return UserShortlistSharedDataHelper.getShortlist(getUserShortlistSharedData());
    }

    public void putShortlist(JSONArray opportunities) {
        UserShortlistSharedDataHelper.putShortlist(getUserShortlistSharedData(), opportunities);
    }

    public List<Integer> getAdjustmentCauses() {
        return ArrayListHelper.convertToListInteger(UserAdjustmentSharedDataHelper.getCauses(getUserAdjustmentSharedData()));
    }

    public List<Integer> getAdjustmentInterests() {
        return ArrayListHelper.convertToListInteger(UserAdjustmentSharedDataHelper.getInterests(getUserAdjustmentSharedData()));
    }

    public List<String> getAdjustmentCausesToString() {
        return UserAdjustmentSharedDataHelper.getCauses(getUserAdjustmentSharedData());
    }

    public List<String> getAdjustmentInterestsToString() {
        return UserAdjustmentSharedDataHelper.getInterests(getUserAdjustmentSharedData());
    }

    public List<String> getAdjustmentNameCauses() {
        return UserAdjustmentSharedDataHelper.getNameCauses(getUserAdjustmentSharedData());
    }

    public List<String> getAdjustmentNameInterests() {
        return UserAdjustmentSharedDataHelper.getNameInterests(getUserAdjustmentSharedData());
    }

    public void clearUserAdjustmentData() {
        UserAdjustmentSharedDataHelper.clearUserDatas(getUserAdjustmentSharedData());
    }

    public void putUserAdjustmentData(List<Integer> causes, List<Integer> interests, List<String> causesName, List<String> interestsName) {
        UserAdjustmentSharedDataHelper.putUserDatas(getUserAdjustmentSharedData(), ArrayListHelper.convertToListString(causes), ArrayListHelper.convertToListString(interests), causesName, interestsName);
    }

    public void putUserCausesData(List<Integer> causes, List<String> causesName) {
        UserAdjustmentSharedDataHelper.putUserCausesData(getUserAdjustmentSharedData(), ArrayListHelper.convertToListString(causes), causesName);
    }

    public void clearUserCausesData() {
        UserAdjustmentSharedDataHelper.clearUserCausesDatas(getUserAdjustmentSharedData());
    }

    public void putUserInterestsData(List<Integer> interests, List<String> interestsName) {
        UserAdjustmentSharedDataHelper.putUserInterestsData(getUserAdjustmentSharedData(), ArrayListHelper.convertToListString(interests), interestsName);
    }

    public void clearUserInterestsData() {
        UserAdjustmentSharedDataHelper.clearUserInterestsDatas(getUserAdjustmentSharedData());
    }

    public List<String> getRecentsLocationFieldSearch() {
        return UserSearchSharedLocationFieldHelper.getRecents(getUserSearchSharedLocationField());
    }

    public List<String> getLocationFieldNames() {
        return UserSearchSharedLocationFieldHelper.getNameLocations(getUserSearchSharedLocationField());
    }

    public void putLocationFieldNames(List<String> locations) {
        UserSearchSharedLocationFieldHelper.putNameLocations(getUserSearchSharedLocationField(), locations);
    }

    public void clearLocationFieldData() {
        UserSearchSharedLocationFieldHelper.clearUserDatas(getUserSearchSharedLocationField());
    }

    public List<Integer> getSearchFilterCauses() {
        return ArrayListHelper.convertToListInteger(UserSearchFilterSharedDataHelper.getCauses(getUserSearchFilterSharedData()));
    }

    public List<Integer> getSearchFilterInterests() {
        return ArrayListHelper.convertToListInteger(UserSearchFilterSharedDataHelper.getInterests(getUserSearchFilterSharedData()));
    }

    public List<Integer> getSearchFilterDurations() {
        return ArrayListHelper.convertToListInteger(UserSearchFilterSharedDataHelper.getDurations(getUserSearchFilterSharedData()));
    }

    public List<Integer> getSearchFilterPrograms() {
        return ArrayListHelper.convertToListInteger(UserSearchFilterSharedDataHelper.getPrograms(getUserSearchFilterSharedData()));
    }

    public List<Integer> getSearchFilterLocations() {
        return ArrayListHelper.convertToListInteger(UserSearchFilterSharedDataHelper.getLocations(getUserSearchFilterSharedData()));
    }

    public List<Integer> getSearchFilterAvailabilities() {
        return ArrayListHelper.convertToListInteger(UserSearchFilterSharedDataHelper.getAvailabilities(getUserSearchFilterSharedData()));
    }

    public List<String> getSearchFilterCausesToString() {
        return UserSearchFilterSharedDataHelper.getCauses(getUserSearchFilterSharedData());
    }

    public List<String> getSearchFilterInterestsToString() {
        return UserSearchFilterSharedDataHelper.getInterests(getUserSearchFilterSharedData());
    }

    public List<String> getSearchFilterDurationsToString() {
        return UserSearchFilterSharedDataHelper.getDurations(getUserSearchFilterSharedData());
    }

    public List<String> getSearchFilterProgramsToString() {
        return UserSearchFilterSharedDataHelper.getPrograms(getUserSearchFilterSharedData());
    }

    public List<String> getSearchFilterAvailabilitiesToString() {
        return UserSearchFilterSharedDataHelper.getAvailabilities(getUserSearchFilterSharedData());
    }

    public boolean getSearchFilterWheelchair() {
        return UserSearchFilterSharedDataHelper.getWheelchair(getUserSearchFilterSharedData());
    }

    public List<String> getSearchFilterNameCauses() {
        return UserSearchFilterSharedDataHelper.getNameCauses(getUserSearchFilterSharedData());
    }

    public List<String> getSearchFilterNameInterests() {
        return UserSearchFilterSharedDataHelper.getNameInterests(getUserSearchFilterSharedData());
    }

    public List<String> getSearchFilterNameDurations() {
        return UserSearchFilterSharedDataHelper.getNameDurations(getUserSearchFilterSharedData());
    }

    public List<String> getSearchFilterNamePrograms() {
        return UserSearchFilterSharedDataHelper.getNamePrograms(getUserSearchFilterSharedData());
    }

    public List<String> getSearchFilterNameLocations() {
        return UserSearchFilterSharedDataHelper.getNameLocations(getUserSearchFilterSharedData());
    }

    public List<String> getSearchFilterNameAvailabilities() {
        return UserSearchFilterSharedDataHelper.getNameAvailabilities(getUserSearchFilterSharedData());
    }

    public void clearSearchFilterData() {
        UserSearchFilterSharedDataHelper.clearUserDatas(getUserSearchFilterSharedData());
    }

    public void putSearchFilterData(List<Integer> causes, List<Integer> interests, List<Integer> durations, List<Integer> programs, List<Integer> availabilities, List<Integer> locations, List<String> causesName, List<String> interestsName, List<String> durationsName, List<String> programsName, List<String> availabilitiesName, List<String> locationsName, boolean wheelchairAccess) {
        UserSearchFilterSharedDataHelper.putFilters(getUserSearchFilterSharedData(), ArrayListHelper.convertToListString(causes), ArrayListHelper.convertToListString(interests), ArrayListHelper.convertToListString(durations), ArrayListHelper.convertToListString(programs), ArrayListHelper.convertToListString(availabilities), ArrayListHelper.convertToListString(locations), causesName, interestsName, durationsName, programsName, availabilitiesName, locationsName, wheelchairAccess);
    }

    public void addSearchItemToRecentsSearch(SavedSearchOpportunitiesItem value) {
        UserSearchFilterSharedDataHelper.addToRecents(getUserSearchFilterSharedData(), value.toJsonObject().toString());
    }

    public List<String> getRecentsSearch() {
        return UserSearchFilterSharedDataHelper.getRecents(getUserSearchFilterSharedData());
    }

    public void clearRecents() {
        UserSearchFilterSharedDataHelper.clearRecents(getUserSearchFilterSharedData());
    }

    public void addSearchItemToRecentsSearchKeyword(SearchItem value) {
        UserSearchFilterSharedDataHelper.addToRecentKeywords(getUserSearchFilterSharedData(), value.toJsonObject().toString());
    }

    public List<String> getRecentsSearchKeyword() {
        return UserSearchFilterSharedDataHelper.getRecentKeywords(getUserSearchFilterSharedData());
    }

    public void addSearchItemToBookmark(SavedSearchOpportunitiesItem value) {
        UserBookmarkSharedDataHelper.addToBookmark(getUserBookmarkSharedData(), value.toJsonObject().toString());
    }

    public void deleteSearchItemFromBookmark(String recent) {
        UserBookmarkSharedDataHelper.deleteFromBookmark(getUserBookmarkSharedData(), recent);
    }

    public List<String> getBookmarks() {
        return UserBookmarkSharedDataHelper.getBookmarks(getUserBookmarkSharedData());
    }

    public void putUserHasSeenWelcomeScreen(boolean hasSeenWelcomeScreen) {
        UserNavigationSharedDataHelper.putUserHasSeenWelcomeScreen(getUserNavigationSharedData(), hasSeenWelcomeScreen);
    }

    public boolean getUserHasSeenWelcomeScreen() {
        return UserNavigationSharedDataHelper.getUserHasSeenWelcomeScreen(getUserNavigationSharedData());
    }

    public void putUserIsFromExpressInterest(boolean hasSeenWelcomeScreen) {
        UserNavigationSharedDataHelper.putUserIsFromExpressInterest(getUserNavigationSharedData(), hasSeenWelcomeScreen);
    }

    public boolean getUserIsFromExpressInterest() {
        return UserNavigationSharedDataHelper.getUserIsFromExpressInterest(getUserNavigationSharedData());
    }

    public void putUserNeedUpdateRecommended(boolean needUpdateRecommended) {
        UserNavigationSharedDataHelper.putUserNeedUpdateRecommended(getUserNavigationSharedData(), needUpdateRecommended);
    }

    public boolean getUserNeedUpdateRecommended() {
        return UserNavigationSharedDataHelper.getUserNeedUpdateRecommended(getUserNavigationSharedData());
    }

    public void putUserNeedUpdateShortlist(boolean needUpdateShortlist) {
        UserNavigationSharedDataHelper.putUserNeedUpdateShortlist(getUserNavigationSharedData(), needUpdateShortlist);
    }

    public boolean getUserNeedUpdateShortlist() {
        return UserNavigationSharedDataHelper.getUserNeedUpdateShortlist(getUserNavigationSharedData());
    }

    public void putUserHaveDoneWizard(boolean haveDoneWizard) {
        UserNavigationSharedDataHelper.putUserHaveDoneWizard(getUserNavigationSharedData(), haveDoneWizard);
    }

    public boolean getUserHaveDoneWizard() {
        return UserNavigationSharedDataHelper.getUserHaveDoneWizard(getUserNavigationSharedData());
    }

    public void putUserNeedUpdateProfile(boolean needUpdateProfile) {
        UserNavigationSharedDataHelper.putUserNeedUpdateProfile(getUserNavigationSharedData(), needUpdateProfile);
    }

    public boolean getUserNeedUpdateProfile() {
        return UserNavigationSharedDataHelper.getUserNeedUpdateProfile(getUserNavigationSharedData());
    }

    public void goToLogin(View view) {
        startActivityForResult(new Intent(this, LoginActivity.class), ActivityCode.GETSTARTED.getValue());
    }

    public void goToCreateAnAccount(View view) {
        startActivity(new Intent(this, SignupActivity.class));
    }

    public void goToForgotPassword(View view) {
        startActivity(new Intent("android.intent.action.VIEW", Uri.parse(getResources().getString(R.string.forgot_password_url))));
    }

    public void goToAdjustments(View view) {
        startActivityForResult(new Intent(this, AdjustmentActivity.class), ActivityCode.ADJUSTMENT.getValue());
    }

    public void goToFiltering(View view) {
        startActivityForResult(new Intent(this, SearchFilteringActivity.class), ActivityCode.FILETERING.getValue());
    }

    protected void showExternalError(String message) {
        new Builder(this).setMessage((CharSequence) message).setNeutralButton(R.string.cast_notification_connecting_message, new C08731()).setIcon(R.drawable.selector_text_button).show();
    }

    protected void signoutExternalLogins() {
        LoginManager.getInstance().logOut();
        LISessionManager.getInstance(getApplicationContext()).clearSession();
        //  TwitterCore.getInstance().logOut();
        if (this.mGoogleApiClient != null) {
            Auth.GoogleSignInApi.signOut(this.mGoogleApiClient);
        }
    }

    protected void successLogin(JSONObject json) {
        clearShortlisted();
        clearLocationFieldData();
        putAccessTokenInfos(json, true);
        setResult(-1, null);
        dismissProgress();
        Intent intent = new Intent();
        if (!getUserHasSeenWelcomeScreen()) {
            intent.setClass(getApplicationContext(), WelcomeStepsActivity.class);
            startActivity(intent);
        } else if (!getUserIsFromExpressInterest()) {
            intent.setClass(getApplicationContext(), MainActivity.class);
            startActivity(intent);
        }
        putUserHaveDoneWizard(false);
        finish();
    }

    protected void dismissProgress() {
        if (this.progressDialogActivity != null) {
            this.progressDialogActivity.dismiss();
        }
    }

    protected void showProgress(String message) {
        if (this.progressDialogActivity == null) {
            this.progressDialogActivity = new ProgressDialog(this);
        }
        this.progressDialogActivity.setMessage(message);
        this.progressDialogActivity.setCancelable(false);
        this.progressDialogActivity.show();
    }
}

package com.vowme.vol.app.activities.profile;

import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.Switch;

import com.vowme.app.models.Enum.HttpRequestType;
import com.vowme.app.utilities.activities.ProfileFormActivity;
import com.vowme.app.utilities.api.ApiRestFullRequest;
import com.vowme.app.utilities.customWidgets.CustomMultiAutoCompleteTextView;
import com.vowme.app.utilities.customWidgets.CustomTokenizer;
import com.vowme.vol.app.R;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class LocationPreferencesActivity extends ProfileFormActivity {
    protected List<String> nameLocation;
    protected CustomMultiAutoCompleteTextView searchText;
    protected Switch switchWidenLocation;
    protected boolean tmpWidenLocation;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.nameLocation = new ArrayList();
        this.searchText = (CustomMultiAutoCompleteTextView) findViewById(R.id.search_edit_frame_location);
        this.switchWidenLocation = (Switch) findViewById(R.id.switchWidenLocation);
        this.switchWidenLocation.setOnCheckedChangeListener(new C08201());
        this.switchWidenLocation.setChecked(this.tmpWidenLocation);
        this.tmpWidenLocation = this.volModel.locality.surroundingAreas;
        extractLocations(this.volModel.locality.location);
        CustomTokenizer token = new CustomTokenizer();
        this.searchText.setUpSearchlocationText(this, getString(R.string.apiVolunteerURL1), getResources().getString(R.string.apiViktorClientSecret), getResources().getString(R.string.apiViktorGetClientSecret), getString(R.string.apiVolunteerURL1), getApliAccessToken(), this.nameLocation, true);
        for (int i = 0; i < this.nameLocation.size(); i++) {
            this.searchText.getText().append(token.terminateToken((CharSequence) this.nameLocation.get(i)));
            this.searchText.addToItems((String) this.nameLocation.get(i));
        }
    }

    protected void initFields() {
        this.searchText.addTextChangedListener(getCustomMultiLocationTextView(this.searchText, true));
    }

    protected int getLayoutResID() {
        return R.layout.activity_location_preferences;
    }

    protected void OnSaveOptionsItemSelected() {
        this.volModel.locality.surroundingAreas = this.tmpWidenLocation;
        this.volModel.locality.location = this.searchText.itemsJoined();
        new putVolunteerLocality(this.volModel.locality.toJsonObject()).execute(new Void[0]);
    }

    private void extractLocations(String location) {
        String[] locations = location.split(",");
        for (String add : locations) {
            this.nameLocation.add(add);
        }
    }

    class C08201 implements OnCheckedChangeListener {
        C08201() {
        }

        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            LocationPreferencesActivity.this.tmpWidenLocation = isChecked;
        }
    }

    private class putVolunteerLocality extends ApiRestFullRequest {
        public putVolunteerLocality(JSONObject param) {
            super(HttpRequestType.PUT, LocationPreferencesActivity.this.getString(R.string.apiVolunteerURL), "api/volunteer/locality", param, LocationPreferencesActivity.this.getUserAccessToken());
        }

        protected void onPostExecuteBody(String result) {
            LocationPreferencesActivity.this.finishActivity();
        }
    }
}

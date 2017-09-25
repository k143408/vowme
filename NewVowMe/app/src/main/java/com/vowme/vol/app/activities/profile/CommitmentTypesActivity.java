package com.vowme.vol.app.activities.profile;

import android.os.Bundle;

import com.vowme.app.models.Enum.HttpRequestType;
import com.vowme.app.models.Enum.LookupType;
import com.vowme.app.models.lookUp.LookupDesc;
import com.vowme.app.utilities.activities.DefaultDataActivity;
import com.vowme.app.utilities.api.ApiRestFullRequest;
import com.vowme.vol.app.R;

import java.util.ArrayList;
import java.util.List;

public class CommitmentTypesActivity extends DefaultDataActivity {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.hasExtraPutData = true;
    }

    protected void initFields() {
    }

    protected String getPutDataName() {
        return "duration";
    }

    protected LookupType getModelDataName() {
        return LookupType.DURATIONS;
    }

    protected void extraExtractData() {
        if (this.volModel.avaibilities.availableForGeneralVolunteering) {
            this.checked.add(Integer.valueOf(8));
        }
        if (this.volModel.avaibilities.availableForSpecialEvents) {
            this.checked.add(Integer.valueOf(7));
        }
        if (this.volModel.avaibilities.availableForEmergencyResponse) {
            this.checked.add(Integer.valueOf(6));
        }
    }

    protected void extraPutData() {
        if (this.checked.contains(Integer.valueOf(8))) {
            this.volModel.avaibilities.availableForGeneralVolunteering = true;
        } else {
            this.volModel.avaibilities.availableForGeneralVolunteering = false;
        }
        if (this.checked.contains(Integer.valueOf(7))) {
            this.volModel.avaibilities.availableForSpecialEvents = true;
        } else {
            this.volModel.avaibilities.availableForSpecialEvents = false;
        }
        if (this.checked.contains(Integer.valueOf(6))) {
            this.volModel.avaibilities.availableForEmergencyResponse = true;
        } else {
            this.volModel.avaibilities.availableForEmergencyResponse = false;
        }
        new putVolunteerAvailabilities(this.volModel.avaibilities.toJsonObject().toString()).execute(new Void[0]);
    }

    protected List<?> getBasicData() {
        List<LookupDesc> result = new ArrayList();
        result.add(new LookupDesc(2, "Event Volunteering", "Available for an event"));
        result.add(new LookupDesc(5, "Micro Volunteering", "Available for less than 3 hrs"));
        result.add(new LookupDesc(3, "Short Term", "Available for a few days"));
        result.add(new LookupDesc(4, "Long Term", "Available for ongoing work"));
        result.add(new LookupDesc(6, "On Call Emergency Response", "Asked to help in wake of a disaster"));
        result.add(new LookupDesc(7, "On Call Special Events", "Asked to help out for special events"));
        result.add(new LookupDesc(8, "On Call General Volunteering", "Asked to help if needed"));
        return result;
    }

    protected boolean isShowItemDescription() {
        return true;
    }

    public class putVolunteerAvailabilities extends ApiRestFullRequest {
        public putVolunteerAvailabilities(String param) {
            super(HttpRequestType.PUT, CommitmentTypesActivity.this.getString(R.string.apiVolunteerURL), "api/volunteer/availability", param, CommitmentTypesActivity.this.getUserAccessToken());
        }

        protected void onPostExecuteBody(String result) {
            CommitmentTypesActivity.this.finishActivity();
        }
    }
}

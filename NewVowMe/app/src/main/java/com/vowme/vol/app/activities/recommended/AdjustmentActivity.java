package com.vowme.vol.app.activities.recommended;

import android.os.Bundle;
import android.text.TextUtils;

import com.vowme.app.models.Enum.HttpRequestType;
import com.vowme.app.models.Enum.LookupType;
import com.vowme.app.models.api.VolunteerLocalityModel;
import com.vowme.app.models.api.VolunteerOnCallAvailableModel;
import com.vowme.app.utilities.activities.FilteringActivity;
import com.vowme.app.utilities.api.ApiRestFullRequest;
import com.vowme.app.utilities.helpers.ArrayListHelper;
import com.vowme.vol.app.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class AdjustmentActivity extends FilteringActivity {
    private final int numberOfthread = 4;
    private boolean isFromClear;
    private int threadCount;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public void getData() {
        this.isLocationTextVisible = true;
        this.isCausesVisible = true;
        this.isInterestsVisible = true;
        this.titleCommitments.setText("Commitments");
        if (isUserLoggedIn()) {
            this.isSurroundingVisible = true;
            this.isDurationsVisible = true;
            this.isProgramsVisible = true;
            new GetVolunteerBasicData().execute(new Void[0]);
            return;
        }
        this.idCausesSelected = getAdjustmentCauses();
        this.idInterestsSelected = getAdjustmentInterests();
        this.nameCauseSelected = getAdjustmentNameCauses();
        this.nameInterestsSelected = getAdjustmentNameInterests();
        this.nameLocation = getLocationFieldNames();
        initData();
        initLocations();
    }

    public void clearData() {
        if (isUserLoggedIn()) {
            disableSubmitButton();
            new putVolunteerBasicData("cause", "\"\"").execute(new Void[0]);
            new putVolunteerBasicData("interest", "\"\"").execute(new Void[0]);
            new putVolunteerBasicData("duration", "\"\"").execute(new Void[0]);
            new putVolunteerBasicData("program", "\"\"").execute(new Void[0]);
            this.isFromClear = true;
            return;
        }
        clearUserAdjustmentData();
        getData();
    }

    public boolean IsLoggedinSearh() {
        return isUserLoggedIn();
    }

    public void bodyDoneClikcked() {
        if (isUserLoggedIn()) {
            putUserNeedUpdateProfile(true);
            VolunteerOnCallAvailableModel params = new VolunteerOnCallAvailableModel();
            if (this.idDurationsSelected.contains(Integer.valueOf(8))) {
                params.availableForGeneralVolunteering = true;
            } else {
                params.availableForGeneralVolunteering = false;
            }
            if (this.idDurationsSelected.contains(Integer.valueOf(7))) {
                params.availableForSpecialEvents = true;
            } else {
                params.availableForSpecialEvents = false;
            }
            if (this.idDurationsSelected.contains(Integer.valueOf(6))) {
                params.availableForEmergencyResponse = true;
            } else {
                params.availableForEmergencyResponse = false;
            }
            new putVolunteerOnCallAvailabilities(params.toJsonObject()).execute(new Void[0]);
            finish();
            return;
        }
        putUserAdjustmentData(this.idCausesSelected, this.idInterestsSelected, this.nameCauseSelected, this.nameInterestsSelected);
        putLocationFieldNames(this.searchText.itemsList());
        setResult(-1, null);
        finish();
    }

    private void extractLocations(String location) {
        String[] locations = location.split(",");
        this.nameLocation = new ArrayList();
        for (String add : locations) {
            this.nameLocation.add(add);
        }
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void extractData(org.json.JSONArray r6, LookupType r7) {

    }

    private class GetVolunteerBasicData extends ApiRestFullRequest {
        public GetVolunteerBasicData() {
            super(HttpRequestType.GET, AdjustmentActivity.this.getString(R.string.apiVolunteerURL1), "api/volunteer/basic", AdjustmentActivity.this.getUserAccessToken());
        }

        protected void onPostExecuteBody(String result) {
            if (result.length() != 0) {
                try {
                    JSONObject datas = new JSONObject(result);
                    AdjustmentActivity.this.idCausesSelected = new ArrayList();
                    AdjustmentActivity.this.nameCauseSelected = new ArrayList();
                    AdjustmentActivity.this.idInterestsSelected = new ArrayList();
                    AdjustmentActivity.this.nameInterestsSelected = new ArrayList();
                    AdjustmentActivity.this.idDurationsSelected = new ArrayList();
                    AdjustmentActivity.this.nameDurationsSelected = new ArrayList();
                    AdjustmentActivity.this.idProgramsSelected = new ArrayList();
                    AdjustmentActivity.this.nameProgramsSelected = new ArrayList();
                    AdjustmentActivity.this.extractData(datas.getJSONArray("causes"), LookupType.CAUSES);
                    AdjustmentActivity.this.extractData(datas.getJSONArray("interests"), LookupType.INTERESTS);
                    AdjustmentActivity.this.extractData(datas.getJSONArray("durations"), LookupType.DURATIONS);
                    AdjustmentActivity.this.extractData(datas.getJSONArray("programs"), LookupType.PROGRAMS);
                    JSONObject localtyModel = datas.getJSONObject("locality");
                    AdjustmentActivity.this.tmpWidenLocation = localtyModel.getBoolean("surroundingAreas");
                    AdjustmentActivity.this.extractLocations(localtyModel.getString("location"));
                    new GetVolunteerOnCallAvailabilities().execute(new Void[0]);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private class GetVolunteerOnCallAvailabilities extends ApiRestFullRequest {
        public GetVolunteerOnCallAvailabilities() {
            super(HttpRequestType.GET, AdjustmentActivity.this.getString(R.string.apiVolunteerURL1), "api/volunteer/oncallavailability", AdjustmentActivity.this.getUserAccessToken());
        }

        protected void onPostExecuteBody(String result) {
            if (!TextUtils.isEmpty(result)) {
                try {
                    VolunteerOnCallAvailableModel volModel = new VolunteerOnCallAvailableModel(new JSONObject(result));
                    if (AdjustmentActivity.this.idDurationsSelected == null) {
                        AdjustmentActivity.this.idDurationsSelected = new ArrayList();
                    }
                    if (AdjustmentActivity.this.nameDurationsSelected == null) {
                        AdjustmentActivity.this.nameDurationsSelected = new ArrayList();
                    }
                    if (volModel.availableForGeneralVolunteering) {
                        AdjustmentActivity.this.idDurationsSelected.add(Integer.valueOf(8));
                        AdjustmentActivity.this.nameDurationsSelected.add("Emergency Response");
                    }
                    if (volModel.availableForSpecialEvents) {
                        AdjustmentActivity.this.idDurationsSelected.add(Integer.valueOf(7));
                        AdjustmentActivity.this.nameDurationsSelected.add("Special Events");
                    }
                    if (volModel.availableForEmergencyResponse) {
                        AdjustmentActivity.this.idDurationsSelected.add(Integer.valueOf(6));
                        AdjustmentActivity.this.nameDurationsSelected.add("Emergency Response");
                    }
                    AdjustmentActivity.this.initData();
                    AdjustmentActivity.this.initLocations();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private class putVolunteerBasicData extends ApiRestFullRequest {
        public putVolunteerBasicData(String dataName, String param) {
            super(HttpRequestType.PUT, AdjustmentActivity.this.getString(R.string.apiVolunteerURL1), "api/volunteer/" + dataName, param, AdjustmentActivity.this.getUserAccessToken());
        }

        protected void onPostExecuteBody(String result) {
            if (result.length() != 0) {
                try {
                    AdjustmentActivity.this.threadCount = AdjustmentActivity.this.threadCount + 1;
                    if (AdjustmentActivity.this.threadCount != 4) {
                        return;
                    }
                    if (AdjustmentActivity.this.isFromClear) {
                        AdjustmentActivity.this.isFromClear = false;
                        AdjustmentActivity.this.getData();
                        AdjustmentActivity.this.enableSubmitButton();
                        return;
                    }
                    AdjustmentActivity.this.setResult(-1, null);
                    AdjustmentActivity.this.finish();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private class putVolunteerLocality extends ApiRestFullRequest {
        public putVolunteerLocality(JSONObject param) {
            super(HttpRequestType.PUT, AdjustmentActivity.this.getString(R.string.apiVolunteerURL1), "api/volunteer/locality", param, AdjustmentActivity.this.getUserAccessToken());
        }

        protected void onPostExecuteBody(String result) {
            if (result.length() != 0) {
                AdjustmentActivity.this.threadCount = 0;
                new putVolunteerBasicData("cause", "\"" + TextUtils.join(",", ArrayListHelper.convertToListString(AdjustmentActivity.this.idCausesSelected)) + "\"").execute(new Void[0]);
                new putVolunteerBasicData("interest", "\"" + TextUtils.join(",", ArrayListHelper.convertToListString(AdjustmentActivity.this.idInterestsSelected)) + "\"").execute(new Void[0]);
                new putVolunteerBasicData("duration", "\"" + TextUtils.join(",", ArrayListHelper.convertToListString(AdjustmentActivity.this.idDurationsSelected)) + "\"").execute(new Void[0]);
                new putVolunteerBasicData("program", "\"" + TextUtils.join(",", ArrayListHelper.convertToListString(AdjustmentActivity.this.idProgramsSelected)) + "\"").execute(new Void[0]);
            }
        }
    }

    public class putVolunteerOnCallAvailabilities extends ApiRestFullRequest {
        public putVolunteerOnCallAvailabilities(JSONObject param) {
            super(HttpRequestType.PUT, AdjustmentActivity.this.getString(R.string.apiVolunteerURL1), "api/volunteer/oncallavailability", param, AdjustmentActivity.this.getUserAccessToken());
        }

        protected void onPostExecuteBody(String result) {
            new putVolunteerLocality(new VolunteerLocalityModel(AdjustmentActivity.this.searchText.itemsJoined(), AdjustmentActivity.this.tmpWidenLocation).toJsonObject()).execute(new Void[0]);
        }
    }
}

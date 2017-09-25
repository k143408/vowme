package com.vowme.vol.app.activities.search;

import android.content.Intent;
import android.os.Bundle;

import com.vowme.app.models.Enum.AvaibilityFilterId;
import com.vowme.app.models.Enum.BOOLEAN;
import com.vowme.app.models.Enum.LookupType;
import com.vowme.app.models.SavedSearchOpportunitiesItem;
import com.vowme.app.utilities.activities.FilteringActivity;
import com.vowme.app.utilities.helpers.ArrayListHelper;
import com.vowme.app.utilities.helpers.DefaultDataHelper;
import com.vowme.vol.app.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class SearchFilteringActivity extends FilteringActivity {
    private SavedSearchOpportunitiesItem potentialSavedSearch;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public void getData() {
        boolean z = true;
        Intent myIntent = getIntent();
        this.isCausesVisible = true;
        this.isInterestsVisible = true;
        this.isDurationsVisible = true;
        this.isAvaibilityVisible = true;
        this.isProgramsVisible = true;
        this.isWheelchairVisible = true;
        this.titleCommitments.setText("Durations");
        try {
            this.potentialSavedSearch = new SavedSearchOpportunitiesItem(new JSONObject(myIntent.getStringExtra(getResources().getString(R.string.EXTRA_SAVED_SEARCH))));
            if (this.potentialSavedSearch.isRadiusSearch()) {
                z = false;
            }
            this.isLocationsVisible = z;
            extractData(this.potentialSavedSearch.getSearchParameters().AdvanceSearchFilters.Organisations, LookupType.CAUSES);
            extractData(this.potentialSavedSearch.getSearchParameters().Interests, LookupType.INTERESTS);
            extractData(this.potentialSavedSearch.getSearchParameters().Durations, LookupType.DURATIONS);
            extractData(this.potentialSavedSearch.getSearchParameters().AdvanceSearchFilters.Suitabilities, LookupType.PROGRAMS);
            if (this.isLocationsVisible) {
                this.idLocationsSelected = ArrayListHelper.convertToListHashCode(this.potentialSavedSearch.getSearchParameters().Locations);
                this.nameLocationSelected = this.potentialSavedSearch.getSearchParameters().Locations;
            }
            this.idAvaibilitiesSelected = new ArrayList();
            if (BOOLEAN.TRUE.getValue().equals(this.potentialSavedSearch.getSearchParameters().AdvanceSearchFilters.AvailableDay)) {
                this.idAvaibilitiesSelected.add(AvaibilityFilterId.DAY.getValue());
            }
            if (BOOLEAN.TRUE.getValue().equals(this.potentialSavedSearch.getSearchParameters().AdvanceSearchFilters.AvailableEvening)) {
                this.idAvaibilitiesSelected.add(AvaibilityFilterId.EVENING.getValue());
            }
            if (BOOLEAN.TRUE.getValue().equals(this.potentialSavedSearch.getSearchParameters().AdvanceSearchFilters.AvailableWeekday)) {
                this.idAvaibilitiesSelected.add(AvaibilityFilterId.WEEKDAYS.getValue());
            }
            if (BOOLEAN.TRUE.getValue().equals(this.potentialSavedSearch.getSearchParameters().AdvanceSearchFilters.AvailableWeekend)) {
                this.idAvaibilitiesSelected.add(AvaibilityFilterId.WEEKEND.getValue());
            }
            extractData(ArrayListHelper.convertToListString(this.idAvaibilitiesSelected), LookupType.AVAIBILITYFILTER);
            this.tmpWheelchair = BOOLEAN.TRUE.getValue().equals(this.potentialSavedSearch.getSearchParameters().AdvanceSearchFilters.IsAccessible);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        initData();
    }

    private void extractData(List<String> list, LookupType type) {
        for (int i = 0; i < list.size(); i++) {
            Integer id = Integer.valueOf(Integer.parseInt((String) list.get(i)));
            List list2;
            int intValue;
            DefaultDataHelper defaultDataHelper;
            switch (type) {
                case CAUSES:
                    this.idCausesSelected.add(id);
                    list2 = this.nameCauseSelected;
                    intValue = id.intValue();
                    defaultDataHelper = this.defaultDataHelper;
                    list2.add(ArrayListHelper.getNameFromIdDesc(intValue, DefaultDataHelper.getCauses()));
                    break;
                case INTERESTS:
                    this.idInterestsSelected.add(id);
                    list2 = this.nameInterestsSelected;
                    intValue = id.intValue();
                    defaultDataHelper = this.defaultDataHelper;
                    list2.add(ArrayListHelper.getNameFromIdDesc(intValue, DefaultDataHelper.getInterests()));
                    break;
                case DURATIONS:
                    this.idDurationsSelected.add(id);
                    list2 = this.nameDurationsSelected;
                    intValue = id.intValue();
                    defaultDataHelper = this.defaultDataHelper;
                    list2.add(ArrayListHelper.getNameFromId(intValue, DefaultDataHelper.getDurations()));
                    break;
                case AVAIBILITYFILTER:
                    list2 = this.nameAvaibilitiesSelected;
                    intValue = id.intValue();
                    defaultDataHelper = this.defaultDataHelper;
                    list2.add(ArrayListHelper.getNameFromId(intValue, DefaultDataHelper.getAvaibilitiesFilter()));
                    break;
                default:
                    this.idProgramsSelected.add(id);
                    list2 = this.nameProgramsSelected;
                    intValue = id.intValue();
                    defaultDataHelper = this.defaultDataHelper;
                    list2.add(ArrayListHelper.getNameFromIdDesc(intValue, DefaultDataHelper.getPrograms()));
                    break;
            }
        }
    }

    public void clearData() {
        clearSearchFilterData();
        this.nameLocationSelected = new ArrayList();
        this.nameCauseSelected = new ArrayList();
        this.nameInterestsSelected = new ArrayList();
        this.nameDurationsSelected = new ArrayList();
        this.nameAvaibilitiesSelected = new ArrayList();
        this.nameProgramsSelected = new ArrayList();
        this.idLocationsSelected = new ArrayList();
        this.idCausesSelected = new ArrayList();
        this.idInterestsSelected = new ArrayList();
        this.idDurationsSelected = new ArrayList();
        this.idProgramsSelected = new ArrayList();
        this.idAvaibilitiesSelected = new ArrayList();
        this.tmpWheelchair = false;
        initData();
    }

    public boolean IsLoggedinSearh() {
        return false;
    }

    public void bodyDoneClikcked() {
        putSearchFilterData(this.idCausesSelected, this.idInterestsSelected, this.idDurationsSelected, this.idProgramsSelected, this.idAvaibilitiesSelected, this.idLocationsSelected, this.nameCauseSelected, this.nameInterestsSelected, this.nameDurationsSelected, this.nameProgramsSelected, this.nameAvaibilitiesSelected, this.nameLocationSelected, this.tmpWheelchair);
        setResult(-1, null);
        finish();
    }
}

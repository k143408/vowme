package com.vowme.vol.app.activities.search;

import android.Manifest;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog.Builder;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;

import com.vowme.app.models.Enum.ActivityCode;
import com.vowme.app.models.Enum.AutoCompleteCategorie;
import com.vowme.app.models.Enum.AvaibilityFilterId;
import com.vowme.app.models.Enum.BOOLEAN;
import com.vowme.app.models.OpportunityItem;
import com.vowme.app.models.SavedSearchOpportunitiesItem;
import com.vowme.app.models.SearchItem;
import com.vowme.app.utilities.activities.BaseActivity;
import com.vowme.app.utilities.fragments.ItemListFragment.OnListFragmentInteractionListener;
import com.vowme.vol.app.R;
import com.vowme.vol.app.activities.opportunity.OpportunityActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SearchResultActivity extends BaseActivity implements OnListFragmentInteractionListener {
    private int categorie;
    private Integer id = Integer.valueOf(0);
    private boolean isRadiusSearch;
    private String keyword;
    private SavedSearchOpportunitiesItem potentialSavedSearch;
    private String title;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((int) R.layout.activity_search_result);
        this.potentialSavedSearch = executeSearch(getIntent().getBooleanExtra(getResources().getString(R.string.EXTRA_IS_SAVED_SEARCH), false), false);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_no_tabs);
        toolbar.setTitle(this.title);
        setSupportActionBar(toolbar);
        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);
        ab.setHomeAsUpIndicator((int) R.mipmap.ic_arrow_back_white_24dp);
    }

    private SavedSearchOpportunitiesItem executeSearch(boolean isSavedSearch, boolean isFromFilter) {
        JSONException e;
        SavedSearchOpportunitiesItem result = new SavedSearchOpportunitiesItem();
        SearchResultListFragment fragment = (SearchResultListFragment) getSupportFragmentManager().getFragments().get(0);
        if (isSavedSearch) {

            SavedSearchOpportunitiesItem result2 = null;
            try {
                result2 = new SavedSearchOpportunitiesItem(new JSONObject(getIntent().getStringExtra(getResources().getString(R.string.EXTRA_SAVED_SEARCH))));
                setLocation(result2, result2.isRadiusSearch(), result2.getSearchParameters().Locations);
                this.title = result2.getName();
                result = result2;
            } catch (JSONException e2) {
                e = e2;
                result = result2;
                e.printStackTrace();
                fragment.updateSearchParameterFromSavedSearch(result);
                fragment.refresh();
                return result;
            }
        }

        this.isRadiusSearch = getIntent().getBooleanExtra(getResources().getString(R.string.EXTRA_IS_RADIUS_SEARCH), false);
        updateSearchParameterFromCurrentFilters(result, this.isRadiusSearch);
        if (!isFromFilter) {
            if (this.isRadiusSearch) {
                this.keyword = "";
                this.categorie = AutoCompleteCategorie.KEYWORD.getValue();
                this.id = Integer.valueOf(0);
                this.title = "Opportunities near me";
            } else {
                this.keyword = getIntent().getStringExtra(getResources().getString(R.string.EXTRA_KEYWORD));
                this.categorie = getIntent().getIntExtra(getResources().getString(R.string.EXTRA_TYPE_KEYWORD), AutoCompleteCategorie.KEYWORD.getValue());
                this.id = Integer.valueOf(getIntent().getIntExtra(getResources().getString(R.string.EXTRA_ID_KEYWORD), 0));
                this.title = this.keyword;
                addSearchItemToRecentsSearchKeyword(new SearchItem(AutoCompleteCategorie.values()[this.categorie], this.keyword, this.id.intValue(), false, true));
            }
        }
        updateKeyword(result, this.keyword, this.id.intValue(), this.categorie);
        result.setName(this.title);
        addSearchItemToRecentsSearch(result);
        fragment.updateSearchParameterFromSavedSearch(result);
        fragment.refresh();
        return result;
    }

    private void updateSearchParameterFromCurrentFilters(SavedSearchOpportunitiesItem result, boolean isRadiusSearch) {
        result.setRadiusSearch(isRadiusSearch);
        result.getSearchParameters().AdvanceSearchFilters.Organisations = getSearchFilterCausesToString();
        result.getSearchParameters().Interests = getSearchFilterInterestsToString();
        result.getSearchParameters().Durations = getSearchFilterDurationsToString();
        result.getSearchParameters().AdvanceSearchFilters.Suitabilities = getSearchFilterProgramsToString();
        setLocation(result, isRadiusSearch, getSearchFilterNameLocations());
        result.getSearchParameters().AdvanceSearchFilters.IsAccessible = Boolean.valueOf(getSearchFilterWheelchair()).toString();
        List<String> avaibilities = getSearchFilterAvailabilitiesToString();
        if (avaibilities.contains(AvaibilityFilterId.DAY.getValue().toString())) {
            result.getSearchParameters().AdvanceSearchFilters.AvailableDay = BOOLEAN.TRUE.getValue();
        }
        if (avaibilities.contains(AvaibilityFilterId.EVENING.getValue().toString())) {
            result.getSearchParameters().AdvanceSearchFilters.AvailableEvening = BOOLEAN.TRUE.getValue();
        }
        if (avaibilities.contains(AvaibilityFilterId.WEEKDAYS.getValue().toString())) {
            result.getSearchParameters().AdvanceSearchFilters.AvailableWeekday = BOOLEAN.TRUE.getValue();
        }
        if (avaibilities.contains(AvaibilityFilterId.WEEKEND.getValue().toString())) {
            result.getSearchParameters().AdvanceSearchFilters.AvailableWeekend = BOOLEAN.TRUE.getValue();
        }
    }

    private void updateKeyword(SavedSearchOpportunitiesItem result, String keyword, int id, int categorie) {
        if (addKeywordToFilter(result, categorie, Integer.valueOf(id))) {
            result.getSearchParameters().Keywords = "";
            return;
        }
        result.getSearchParameters().Keywords = keyword;
    }

    private void setLocation(SavedSearchOpportunitiesItem result, boolean isRadiusSearch, List<String> locations) {
        String postcode = "";
        if (isRadiusSearch) {
            LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
            boolean isGPSEnabled = locationManager.isProviderEnabled("gps");
            boolean isNetworkEnabled = locationManager.isProviderEnabled("network");
            if (isGPSEnabled || isNetworkEnabled) {
                Location location = getLastKnownLocation();
                if (location != null) {
                    Double lat = Double.valueOf(location.getLatitude());
                    Double lon = Double.valueOf(location.getLongitude());
                    if (!(lat.doubleValue() == 0.0d || lon.doubleValue() == 0.0d)) {
                        try {
                            List<Address> addresses = new Geocoder(this).getFromLocation(lat.doubleValue(), lon.doubleValue(), 1);
                            if (addresses.size() > 0) {
                                postcode = ((Address) addresses.get(0)).getPostalCode();
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
            if (TextUtils.isEmpty(postcode)) {
                new Builder(this).setMessage((CharSequence) "Sorry you current location is not available.").setNeutralButton(R.string.cast_invalid_stream_duration_text, new C08421()).setIcon(R.drawable.cast_ic_expanded_controller_stop).show();
                return;
            }
            result.getSearchParameters().Locations = new ArrayList();
            result.getSearchParameters().Locations.add(postcode + "+" + getResources().getString(R.string.RADIUS_OPP_NEAR_ME));
            return;
        }
        result.getSearchParameters().Locations = locations;
    }

    private Location getLastKnownLocation() {
        LocationManager locationManage = (LocationManager) getSystemService(LOCATION_SERVICE);
        Location bestLocation = null;
        for (String provider : locationManage.getProviders(true)) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.

            }
            Location location = locationManage.getLastKnownLocation(provider);
            if (location != null && (bestLocation == null || location.getAccuracy() < bestLocation.getAccuracy())) {
                bestLocation = location;
            }
        }
        return bestLocation;
    }

    private boolean addKeywordToFilter(SavedSearchOpportunitiesItem result, int categorie, Integer id) {
        switch (AutoCompleteCategorie.values()[categorie]) {
            case CAUSES:
                if (result.getSearchParameters().AdvanceSearchFilters.Organisations.contains(id.toString())) {
                    return true;
                }
                result.getSearchParameters().AdvanceSearchFilters.Organisations.add(id.toString());
                return true;
            case INTERESTS:
                if (result.getSearchParameters().Interests.contains(id.toString())) {
                    return true;
                }
                result.getSearchParameters().Interests.add(id.toString());
                return true;
            case ORGANISATIONS:
                if (result.getSearchParameters().AdvanceSearchFilters.OrganisationId.contains(id.toString())) {
                    return true;
                }
                result.getSearchParameters().AdvanceSearchFilters.OrganisationId = id.toString();
                return true;
            default:
                return false;
        }
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_filtering_search, menu);
        return true;
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == ActivityCode.FILETERING.getValue() && resultCode == -1) {
            this.potentialSavedSearch = executeSearch(false, true);
        }
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case 16908332:
                setResult(-1, null);
                finish();
                return true;
            case R.id.action_filter:
                Intent intent = new Intent(this, SearchFilteringActivity.class);
                intent.putExtra(getResources().getString(R.string.EXTRA_SAVED_SEARCH), this.potentialSavedSearch.toJsonObject().toString());
                startActivityForResult(intent, ActivityCode.FILETERING.getValue());
                return true;
            case R.id.action_save_search:
                Intent intentSave = new Intent(this, SavingSearchActivity.class);
                intentSave.putExtra(getResources().getString(R.string.EXTRA_SAVED_SEARCH), this.potentialSavedSearch.toJsonObject().toString());
                startActivity(intentSave);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void onListFragmentInteraction(OpportunityItem item) {
        Intent intent = new Intent(this, OpportunityActivity.class);
        intent.putExtra("android.intent.extra.TEXT", Integer.toString(item.getId()));
        startActivity(intent);
    }

    class C08421 implements OnClickListener {
        C08421() {
        }

        public void onClick(DialogInterface dialog, int which) {
            dialog.cancel();
        }
    }
}

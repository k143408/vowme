package com.vowme.app.utilities.activities;

import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog.Builder;
import android.support.v7.widget.AppCompatMultiAutoCompleteTextView;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;

import com.vowme.app.models.Enum.LookupType;
import com.vowme.app.models.lookUp.Lookup;
import com.vowme.app.models.lookUp.LookupDesc;
import com.vowme.app.utilities.adapters.DefaultDataRecyclerViewAdapter;
import com.vowme.app.utilities.adapters.DefaultDataRecyclerViewAdapter.OnCheckBoxListener;
import com.vowme.app.utilities.adapters.LocationAutoCompleteArrayAdapter;
import com.vowme.app.utilities.customWidgets.CustomMultiAutoCompleteTextView;
import com.vowme.app.utilities.customWidgets.CustomTokenizer;
import com.vowme.app.utilities.helpers.DefaultDataHelper;
import com.vowme.vol.app.R;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public abstract class FilteringActivity extends FormValidationActivity {
    protected List<Integer> idAvaibilitiesSelected;
    protected List<Integer> idCausesSelected;
    protected List<Integer> idDurationsSelected;
    protected List<Integer> idInterestsSelected;
    protected List<Integer> idLocationsSelected;
    protected List<Integer> idProgramsSelected;
    protected boolean isAvaibilityVisible;
    protected boolean isCausesVisible;
    protected boolean isDurationsVisible;
    protected boolean isInterestsVisible;
    protected boolean isLocationTextVisible;
    protected boolean isLocationsVisible;
    protected boolean isProgramsVisible;
    protected boolean isSurroundingVisible;
    protected boolean isWheelchairVisible;
    protected OnCheckBoxListener mOnEventListener;
    protected List<String> nameAvaibilitiesSelected;
    protected List<String> nameCauseSelected;
    protected List<String> nameDurationsSelected;
    protected List<String> nameInterestsSelected;
    protected List<String> nameLocation;
    protected List<String> nameLocationSelected;
    protected List<String> nameProgramsSelected;
    protected CustomMultiAutoCompleteTextView searchText;
    protected Switch switchWheelchair;
    protected Switch switchWidenLocation;
    protected TextView titleCommitments;
    protected List<Integer> tmpIdSelected;
    protected List<String> tmpNameSelected;
    protected boolean tmpWheelchair;
    protected boolean tmpWidenLocation;
    private DefaultDataRecyclerViewAdapter adapter;
    private List<Integer> checked;
    private Builder dialog;
    private List<?> items;
    private String title;

    public abstract boolean IsLoggedinSearh();

    public abstract void bodyDoneClikcked();

    public abstract void clearData();

    public abstract void getData();

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((int) R.layout.activity_filtering);
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar_no_tabs));
        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);
        ab.setHomeAsUpIndicator((int) R.mipmap.ic_close_white_24dp);
        this.isLocationTextVisible = false;
        this.isSurroundingVisible = false;
        this.isLocationsVisible = false;
        this.isCausesVisible = false;
        this.isInterestsVisible = false;
        this.isDurationsVisible = false;
        this.isAvaibilityVisible = false;
        this.isProgramsVisible = false;
        this.isWheelchairVisible = false;
        this.tmpNameSelected = new ArrayList();
        this.nameLocationSelected = new ArrayList();
        this.nameCauseSelected = new ArrayList();
        this.nameInterestsSelected = new ArrayList();
        this.nameDurationsSelected = new ArrayList();
        this.nameAvaibilitiesSelected = new ArrayList();
        this.nameProgramsSelected = new ArrayList();
        this.tmpIdSelected = new ArrayList();
        this.idLocationsSelected = new ArrayList();
        this.idCausesSelected = new ArrayList();
        this.idInterestsSelected = new ArrayList();
        this.idDurationsSelected = new ArrayList();
        this.idProgramsSelected = new ArrayList();
        this.idAvaibilitiesSelected = new ArrayList();
        this.nameLocation = new ArrayList();
        this.mOnEventListener = new C08751();
        this.titleCommitments = (TextView) findViewById(R.id.title_commitments);
        this.searchText = (CustomMultiAutoCompleteTextView) findViewById(R.id.search_edit_frame_location);
        this.searchText.setUpSearchlocationText(this, getString(R.string.apiVolunteerURL1), getResources().getString(R.string.apiViktorClientSecret), getResources().getString(R.string.apiViktorGetClientSecret), getString(R.string.apiVolunteerURL1), getApliAccessToken(), new ArrayList(), IsLoggedinSearh());
        this.checked = new ArrayList();
        this.items = new ArrayList();
        this.adapter = new DefaultDataRecyclerViewAdapter(this.items, this.mOnEventListener, this.checked);
        this.dialog = new Builder(this).setCancelable(true).setPositiveButton((CharSequence) "OK", new C08773()).setNegativeButton((CharSequence) "Cancel", new C08762());
        getAllData();
    }

    protected void initFieldsSubmitButton() {
        this.submitButton = (TextView) findViewById(R.id.button_do_filter);
        this.searchText.addTextChangedListener(getCustomMultiLocationTextView(this.searchText, IsLoggedinSearh()));
        isValidToSubmitForm();
    }

    private void getAllData() {
        getData();
        displayingLocations();
    }

    private void displayingLocations() {
        AppCompatMultiAutoCompleteTextView locationsTxt = (AppCompatMultiAutoCompleteTextView) findViewById(R.id.search_edit_frame_location);
        if (this.isLocationTextVisible) {
            locationsTxt.setVisibility(View.GONE);
        } else {
            locationsTxt.setVisibility(View.GONE);
        }
    }

    public void initLocations() {
        if (this.isLocationTextVisible) {
            initFieldsSubmitButton();
            CustomTokenizer token = new CustomTokenizer();
            this.searchText.setText("");
            this.searchText.clearItems();
            for (int i = 0; i < this.nameLocation.size(); i++) {
                this.searchText.getText().append(token.terminateToken((CharSequence) this.nameLocation.get(i)));
                this.searchText.addToItems((String) this.nameLocation.get(i));
            }
            ((LocationAutoCompleteArrayAdapter) this.searchText.getAdapter()).setRecents(getRecentsLocationFieldSearch());
            ((LocationAutoCompleteArrayAdapter) this.searchText.getAdapter()).setVolunteerLocation(this.nameLocation);
            return;
        }
        ((AppCompatMultiAutoCompleteTextView) findViewById(R.id.search_edit_frame_location)).setVisibility(View.GONE);
    }

    public void initData() {
        if (this.isLocationsVisible) {
            TextView locationsTxt = (TextView) findViewById(R.id.list_locations);
            if (this.nameLocationSelected.size() != 0) {
                locationsTxt.setText(TextUtils.join(",", this.nameLocationSelected));
            } else {
                locationsTxt.setText("Any");
            }
        } else if (!this.isLocationsVisible) {
            ((LinearLayout) findViewById(R.id.locations_lyt)).setVisibility(View.GONE);
        }
        if (this.isCausesVisible) {
            TextView causesTxt = (TextView) findViewById(R.id.list_causes);
            if (this.nameCauseSelected.size() != 0) {
                causesTxt.setText(TextUtils.join(",", this.nameCauseSelected));
            } else {
                causesTxt.setText("Any");
            }
        } else if (!this.isCausesVisible) {
            ((LinearLayout) findViewById(R.id.causes_lyt)).setVisibility(View.GONE);
        }
        if (this.isInterestsVisible) {
            TextView interestTxt = (TextView) findViewById(R.id.list_interests);
            if (this.nameInterestsSelected.size() != 0) {
                interestTxt.setText(TextUtils.join(",", this.nameInterestsSelected));
            } else {
                interestTxt.setText("Any");
            }
        } else if (!this.isInterestsVisible) {
            ((LinearLayout) findViewById(R.id.interests_lyt)).setVisibility(View.GONE);
        }
        if (this.isDurationsVisible) {
            TextView commitmentsTxt = (TextView) findViewById(R.id.list_commitments);
            if (this.nameDurationsSelected.size() != 0) {
                commitmentsTxt.setText(TextUtils.join(",", this.nameDurationsSelected));
            } else {
                commitmentsTxt.setText("Any");
            }
        } else if (!this.isDurationsVisible) {
            ((LinearLayout) findViewById(R.id.durations_lyt)).setVisibility(View.GONE);
        }
        if (this.isAvaibilityVisible) {
            TextView avaibilityTxt = (TextView) findViewById(R.id.list_avaibilities);
            if (this.nameAvaibilitiesSelected.size() != 0) {
                avaibilityTxt.setText(TextUtils.join(",", this.nameAvaibilitiesSelected));
            } else {
                avaibilityTxt.setText("Any time");
            }
        } else if (!this.isAvaibilityVisible) {
            ((LinearLayout) findViewById(R.id.avaibilities_lyt)).setVisibility(View.GONE);
        }
        if (this.isProgramsVisible) {
            TextView programsTxt = (TextView) findViewById(R.id.list_programs);
            if (this.nameProgramsSelected.size() != 0) {
                programsTxt.setText(TextUtils.join(",", this.nameProgramsSelected));
            } else {
                programsTxt.setText("Anyone");
            }
        } else if (!this.isProgramsVisible) {
            ((LinearLayout) findViewById(R.id.programs_lyt)).setVisibility(View.GONE);
        }
        if (this.isSurroundingVisible) {
            this.switchWidenLocation = (Switch) findViewById(R.id.switchWidenLocation);
            this.switchWidenLocation.setOnCheckedChangeListener(new C08784());
            this.switchWidenLocation.setChecked(this.tmpWidenLocation);
        } else {
            ((LinearLayout) findViewById(R.id.surrounding_lyt)).setVisibility(View.GONE);
        }
        if (this.isWheelchairVisible) {
            this.switchWheelchair = (Switch) findViewById(R.id.switchWheelchair);
            this.switchWheelchair.setOnCheckedChangeListener(new C08795());
            this.switchWheelchair.setChecked(this.tmpWheelchair);
            return;
        }
        ((LinearLayout) findViewById(R.id.wheelchair_lyt)).setVisibility(View.GONE);
    }

    public void showData(View view) {
        this.title = (String) view.getTag();
        DefaultDataHelper defaultDataHelper;
        Set<LookupDesc> setItem = new HashSet<>();
        switch (LookupType.valueOf(this.title.toUpperCase())) {
            case CAUSES:
                this.tmpIdSelected = new ArrayList(this.idCausesSelected);
                this.tmpNameSelected = new ArrayList(this.nameCauseSelected);
                defaultDataHelper = this.defaultDataHelper;
                setItem.clear();
                setItem.addAll(DefaultDataHelper.getCauses());
                this.items = new ArrayList<>(setItem);
                this.checked = this.idCausesSelected;
                break;
            case INTERESTS:
                this.tmpIdSelected = new ArrayList(this.idInterestsSelected);
                this.tmpNameSelected = new ArrayList(this.nameInterestsSelected);
                defaultDataHelper = this.defaultDataHelper;
                setItem.clear();
                setItem.addAll(DefaultDataHelper.getInterests());
                this.items = new ArrayList<>(setItem);
                this.checked = this.idInterestsSelected;
                break;
            case DURATIONS:
                this.tmpIdSelected = new ArrayList(this.idDurationsSelected);
                this.tmpNameSelected = new ArrayList(this.nameDurationsSelected);
                List<Lookup> result = new ArrayList();
                defaultDataHelper = this.defaultDataHelper;
                result.addAll(DefaultDataHelper.getDurations());
                if (IsLoggedinSearh()) {
                    result.add(new Lookup(6, "Emergency Response"));
                    result.add(new Lookup(7, "Special Events"));
                    result.add(new Lookup(8, "General Volunteering"));
                }
                this.items = result;
                this.checked = this.idDurationsSelected;
                break;
            case LOCATIONS:
                this.tmpIdSelected = new ArrayList(this.idLocationsSelected);
                this.tmpNameSelected = new ArrayList(this.nameLocationSelected);
                defaultDataHelper = this.defaultDataHelper;
                this.items = DefaultDataHelper.getCityStateFilter();
                this.checked = this.idLocationsSelected;
                break;
            case AVAIBILITY:
                this.tmpIdSelected = new ArrayList(this.idAvaibilitiesSelected);
                this.tmpNameSelected = new ArrayList(this.nameAvaibilitiesSelected);
                defaultDataHelper = this.defaultDataHelper;
                this.items = DefaultDataHelper.getAvaibilitiesFilter();
                this.checked = this.idAvaibilitiesSelected;
                break;
            default:
                this.tmpIdSelected = new ArrayList(this.idProgramsSelected);
                this.tmpNameSelected = new ArrayList(this.nameProgramsSelected);
                defaultDataHelper = this.defaultDataHelper;
                this.items = DefaultDataHelper.getPrograms();
                this.checked = this.idProgramsSelected;
                break;
        }
        this.adapter.updateItems(this.items);
        this.adapter.updateIdsChecked(this.checked);
        this.adapter.notifyDataSetChanged();
        RecyclerView recyclerView = (RecyclerView) getLayoutInflater().inflate(R.layout.list_static_default_data, null, false);

        recyclerView.setAdapter(this.adapter);
        this.dialog.setView(recyclerView);
        this.dialog.setTitle(this.title);
        this.dialog.show();
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_clear, menu);
        return true;
    }

    public void doneCliked(View view) {
        bodyDoneClikcked();
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case 16908332:
                finish();
                return true;
            case R.id.action_clear:
                new Builder(this).setMessage((CharSequence) "Clear all adjustments?").setCancelable(true).setPositiveButton((CharSequence) "CLEAR", new C08817()).setNegativeButton((CharSequence) "Cancel", new C08806()).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    class C08751 implements OnCheckBoxListener {
        C08751() {
        }

        public void OnCheckBoxListenerInteraction(Lookup item) {
            if (FilteringActivity.this.tmpIdSelected.contains(Integer.valueOf(item.getId()))) {
                FilteringActivity.this.tmpIdSelected.remove(new Integer(item.getId()));
                FilteringActivity.this.tmpNameSelected.remove(item.getName());
                return;
            }
            FilteringActivity.this.tmpIdSelected.add(Integer.valueOf(item.getId()));
            FilteringActivity.this.tmpNameSelected.add(item.getName());
        }
    }

    class C08762 implements OnClickListener {
        C08762() {
        }

        public void onClick(DialogInterface dialog, int which) {
        }
    }

    class C08773 implements OnClickListener {
        C08773() {
        }

        public void onClick(DialogInterface dialog, int which) {
            switch (LookupType.valueOf(FilteringActivity.this.title.toUpperCase())) {
                case CAUSES:
                    FilteringActivity.this.idCausesSelected.clear();
                    FilteringActivity.this.idCausesSelected.addAll(FilteringActivity.this.tmpIdSelected);
                    FilteringActivity.this.nameCauseSelected.clear();
                    FilteringActivity.this.nameCauseSelected.addAll(FilteringActivity.this.tmpNameSelected);
                    TextView causesTxt = (TextView) FilteringActivity.this.findViewById(R.id.list_causes);
                    if (FilteringActivity.this.nameCauseSelected.size() > 0) {
                        causesTxt.setText(TextUtils.join(",", FilteringActivity.this.nameCauseSelected));
                        return;
                    } else {
                        causesTxt.setText("Any");
                        return;
                    }
                case INTERESTS:
                    FilteringActivity.this.idInterestsSelected.clear();
                    FilteringActivity.this.idInterestsSelected.addAll(FilteringActivity.this.tmpIdSelected);
                    FilteringActivity.this.nameInterestsSelected.clear();
                    FilteringActivity.this.nameInterestsSelected.addAll(FilteringActivity.this.tmpNameSelected);
                    TextView interestTxt = (TextView) FilteringActivity.this.findViewById(R.id.list_interests);
                    if (FilteringActivity.this.nameInterestsSelected.size() > 0) {
                        interestTxt.setText(TextUtils.join(",", FilteringActivity.this.nameInterestsSelected));
                        return;
                    } else {
                        interestTxt.setText("Any");
                        return;
                    }
                case DURATIONS:
                    FilteringActivity.this.idDurationsSelected.clear();
                    FilteringActivity.this.idDurationsSelected.addAll(FilteringActivity.this.tmpIdSelected);
                    FilteringActivity.this.nameDurationsSelected.clear();
                    FilteringActivity.this.nameDurationsSelected.addAll(FilteringActivity.this.tmpNameSelected);
                    TextView commitmentsTxt = (TextView) FilteringActivity.this.findViewById(R.id.list_commitments);
                    if (FilteringActivity.this.nameDurationsSelected.size() > 0) {
                        commitmentsTxt.setText(TextUtils.join(",", FilteringActivity.this.nameDurationsSelected));
                        return;
                    } else {
                        commitmentsTxt.setText("Any");
                        return;
                    }
                case LOCATIONS:
                    FilteringActivity.this.idLocationsSelected.clear();
                    FilteringActivity.this.idLocationsSelected.addAll(FilteringActivity.this.tmpIdSelected);
                    FilteringActivity.this.nameLocationSelected.clear();
                    FilteringActivity.this.nameLocationSelected.addAll(FilteringActivity.this.tmpNameSelected);
                    TextView locationsTxt = (TextView) FilteringActivity.this.findViewById(R.id.list_locations);
                    if (FilteringActivity.this.nameLocationSelected.size() > 0) {
                        locationsTxt.setText(TextUtils.join(",", FilteringActivity.this.nameLocationSelected));
                        return;
                    } else {
                        locationsTxt.setText("Any");
                        return;
                    }
                case AVAIBILITY:
                    FilteringActivity.this.idAvaibilitiesSelected.clear();
                    FilteringActivity.this.idAvaibilitiesSelected.addAll(FilteringActivity.this.tmpIdSelected);
                    FilteringActivity.this.nameAvaibilitiesSelected.clear();
                    FilteringActivity.this.nameAvaibilitiesSelected.addAll(FilteringActivity.this.tmpNameSelected);
                    TextView avaibilityTxt = (TextView) FilteringActivity.this.findViewById(R.id.list_avaibilities);
                    if (FilteringActivity.this.nameAvaibilitiesSelected.size() > 0) {
                        avaibilityTxt.setText(TextUtils.join(",", FilteringActivity.this.nameAvaibilitiesSelected));
                        return;
                    } else {
                        avaibilityTxt.setText("Any time");
                        return;
                    }
                default:
                    FilteringActivity.this.idProgramsSelected.clear();
                    FilteringActivity.this.idProgramsSelected.addAll(FilteringActivity.this.tmpIdSelected);
                    FilteringActivity.this.nameProgramsSelected.clear();
                    FilteringActivity.this.nameProgramsSelected.addAll(FilteringActivity.this.tmpNameSelected);
                    TextView programsTxt = (TextView) FilteringActivity.this.findViewById(R.id.list_programs);
                    if (FilteringActivity.this.nameProgramsSelected.size() > 0) {
                        programsTxt.setText(TextUtils.join(",", FilteringActivity.this.nameProgramsSelected));
                        return;
                    } else {
                        programsTxt.setText("Anyone");
                        return;
                    }
            }
        }
    }

    class C08784 implements OnCheckedChangeListener {
        C08784() {
        }

        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            FilteringActivity.this.tmpWidenLocation = isChecked;
        }
    }

    class C08795 implements OnCheckedChangeListener {
        C08795() {
        }

        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            FilteringActivity.this.tmpWheelchair = isChecked;
        }
    }

    class C08806 implements OnClickListener {
        C08806() {
        }

        public void onClick(DialogInterface dialog, int which) {
        }
    }

    class C08817 implements OnClickListener {
        C08817() {
        }

        public void onClick(DialogInterface dialog, int which) {
            FilteringActivity.this.clearData();
        }
    }
}

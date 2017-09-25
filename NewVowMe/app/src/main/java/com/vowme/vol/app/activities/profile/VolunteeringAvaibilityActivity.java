package com.vowme.vol.app.activities.profile;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Switch;

import com.vowme.app.models.DayAvaibilityItem;
import com.vowme.app.models.Enum.HttpRequestType;
import com.vowme.app.utilities.activities.ProfileFormActivity;
import com.vowme.app.utilities.adapters.DayAvaibilityItemRecyclerViewAdapter;
import com.vowme.app.utilities.api.ApiRestFullRequest;
import com.vowme.app.utilities.helpers.ArrayListHelper;
import com.vowme.app.utilities.helpers.DefaultDataHelper;
import com.vowme.vol.app.R;

import java.util.List;

public class VolunteeringAvaibilityActivity extends ProfileFormActivity {
    private DayAvaibilityItemRecyclerViewAdapter adapter;
    private Switch allTimes;
    private List<DayAvaibilityItem> items;
    private RecyclerView recyclerView;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.items = DefaultDataHelper.getAvaibilities();
        this.allTimes = (Switch) findViewById(R.id.switch_all_times);
        this.allTimes.setOnClickListener(new C08301());
        Switch switchR = this.allTimes;
        boolean z = this.volModel.avaibilities.morning.size() == 8 && this.volModel.avaibilities.afternoon.size() == 8 && this.volModel.avaibilities.evening.size() == 8;
        switchR.setChecked(z);
        extractData();
        this.adapter = new DayAvaibilityItemRecyclerViewAdapter(this.items);
        this.adapter.notifyDataSetChanged();
        this.recyclerView = (RecyclerView) findViewById(R.id.day_avaibility_data_list);
        this.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        this.recyclerView.setAdapter(this.adapter);
        isSelectedAction();
    }

    protected void initFields() {
    }

    private void extractData() {
        for (DayAvaibilityItem day : this.items) {
            day.setMorning(this.volModel.avaibilities.morning.contains(Integer.valueOf(day.day)));
            day.setAfternoon(this.volModel.avaibilities.afternoon.contains(Integer.valueOf(day.day)));
            day.setEvening(this.volModel.avaibilities.evening.contains(Integer.valueOf(day.day)));
            day.setIsSelected();
        }
    }

    private void isSelectedAction() {
        if (this.allTimes.isChecked()) {
            this.recyclerView.setVisibility(8);
        } else {
            this.recyclerView.setVisibility(0);
        }
    }

    protected void OnSaveOptionsItemSelected() {
        this.volModel.avaibilities.morning.clear();
        this.volModel.avaibilities.afternoon.clear();
        this.volModel.avaibilities.evening.clear();
        if (this.volModel.avaibilities.availabileAllTimes) {
            for (int i = 1; i < 9; i++) {
                this.volModel.avaibilities.morning.add(Integer.valueOf(i));
                this.volModel.avaibilities.afternoon.add(Integer.valueOf(i));
                this.volModel.avaibilities.evening.add(Integer.valueOf(i));
            }
        } else {
            for (DayAvaibilityItem day : this.adapter.mValues) {
                if (day.isMorning()) {
                    this.volModel.avaibilities.morning.add(Integer.valueOf(day.day));
                }
                if (day.isAfternoon()) {
                    this.volModel.avaibilities.afternoon.add(Integer.valueOf(day.day));
                }
                if (day.isEvening()) {
                    this.volModel.avaibilities.evening.add(Integer.valueOf(day.day));
                }
            }
        }
        this.volModel.avaibilities.sentenceAvaibility = ArrayListHelper.buildSentence(this.volModel.avaibilities.morning, this.volModel.avaibilities.afternoon, this.volModel.avaibilities.evening);
        new putVolunteerAvailabilities(this.volModel.avaibilities.toJsonObject().toString()).execute(new Void[0]);
    }

    @LayoutRes
    protected int getLayoutResID() {
        return R.layout.activity_volunteering_avaibility;
    }

    class C08301 implements OnClickListener {
        C08301() {
        }

        public void onClick(View v) {
            VolunteeringAvaibilityActivity.this.volModel.avaibilities.availabileAllTimes = !VolunteeringAvaibilityActivity.this.volModel.avaibilities.availabileAllTimes;
            VolunteeringAvaibilityActivity.this.isSelectedAction();
            if (VolunteeringAvaibilityActivity.this.volModel.avaibilities.availabileAllTimes) {
                VolunteeringAvaibilityActivity.this.adapter.unCheckedAll();
                VolunteeringAvaibilityActivity.this.adapter.notifyDataSetChanged();
            }
        }
    }

    public class putVolunteerAvailabilities extends ApiRestFullRequest {
        public putVolunteerAvailabilities(String param) {
            super(HttpRequestType.PUT, VolunteeringAvaibilityActivity.this.getString(R.string.apiVolunteerURL), "api/volunteer/availability", param, VolunteeringAvaibilityActivity.this.getUserAccessToken());
        }

        protected void onPostExecuteBody(String result) {
            VolunteeringAvaibilityActivity.this.finishActivity();
        }
    }
}

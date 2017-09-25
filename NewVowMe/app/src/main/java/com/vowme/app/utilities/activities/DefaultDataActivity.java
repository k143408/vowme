package com.vowme.app.utilities.activities;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;

import com.vowme.app.models.Enum.HttpRequestType;
import com.vowme.app.models.Enum.LookupType;
import com.vowme.app.models.lookUp.Lookup;
import com.vowme.app.models.lookUp.LookupDesc;
import com.vowme.app.utilities.adapters.DefaultDataRecyclerViewAdapter;
import com.vowme.app.utilities.api.ApiRestFullRequest;
import com.vowme.app.utilities.helpers.ArrayListHelper;
import com.vowme.vol.app.R;

import java.util.ArrayList;
import java.util.List;

public abstract class DefaultDataActivity extends ProfileFormActivity {
    protected DefaultDataRecyclerViewAdapter adapter;
    protected List<Integer> checked;
    protected boolean hasExtraPutData;
    protected List<?> items;
    protected RecyclerView recyclerView;

    protected abstract List<?> getBasicData();

    protected abstract LookupType getModelDataName();

    protected abstract String getPutDataName();

    protected abstract boolean isShowItemDescription();

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        extractData(getModelDataName());
        extraExtractData();
        this.items = getBasicData();
        this.adapter = new DefaultDataRecyclerViewAdapter(this.items, null, this.checked);
        this.adapter.setShowItemDescription(isShowItemDescription());
        this.adapter.updateItems(this.items);
        this.adapter.updateIdsChecked(this.checked);
        this.adapter.notifyDataSetChanged();
        this.recyclerView = (RecyclerView) findViewById(R.id.basic_data_list);
        this.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        this.recyclerView.setAdapter(this.adapter);
    }

    protected void extraExtractData() {
    }

    protected void extraPutData() {
    }

    protected void OnSaveOptionsItemSelected() {
        updateData(getModelDataName());
        new putVolunteerBasicData(getPutDataName(), "\"" + TextUtils.join(",", ArrayListHelper.convertToListString(this.adapter.getIdsChecked())) + "\"").execute(new Void[0]);
    }

    @LayoutRes
    protected int getLayoutResID() {
        return R.layout.activity_basic_data;
    }

    protected void extractData(LookupType dataType) {
        List<Lookup> datas = new ArrayList();
        this.checked = new ArrayList();
        switch (dataType) {
            case CAUSES:
                datas = this.volModel.causes;
                break;
            case INTERESTS:
                datas = this.volModel.interests;
                break;
            case DURATIONS:
                datas = this.volModel.durations;
                break;
            case PROGRAMS:
                datas = this.volModel.programs;
                break;
            case REQUIREMENTS:
                datas = this.volModel.requirements;
                break;
        }
        for (Lookup item : datas) {
            this.checked.add(Integer.valueOf(item.getId()));
        }
    }

    private void updateData(LookupType dataType) {
        this.checked = this.adapter.getIdsChecked();
        switch (dataType) {
            case CAUSES:
                this.volModel.causes.clear();
                break;
            case INTERESTS:
                this.volModel.interests.clear();
                break;
            case DURATIONS:
                this.volModel.durations.clear();
                break;
            case PROGRAMS:
                this.volModel.programs.clear();
                break;
            case REQUIREMENTS:
                this.volModel.requirements.clear();
                break;
        }
        for (Object item : this.items) {
            if (this.checked.contains(Integer.valueOf(((Lookup) item).getId()))) {
                switch (dataType) {
                    case CAUSES:
                        this.volModel.causes.add((LookupDesc) item);
                        break;
                    case INTERESTS:
                        this.volModel.interests.add((LookupDesc) item);
                        break;
                    case DURATIONS:
                        if (!(((Lookup) item).getId() == 6 || ((Lookup) item).getId() == 7 || ((Lookup) item).getId() == 8)) {
                            this.volModel.durations.add((Lookup) item);
                            break;
                        }
                    case PROGRAMS:
                        this.volModel.programs.add((LookupDesc) item);
                        break;
                    case REQUIREMENTS:
                        this.volModel.requirements.add((Lookup) item);
                        break;
                    default:
                        break;
                }
            }
        }
    }

    public class putVolunteerBasicData extends ApiRestFullRequest {
        public putVolunteerBasicData(String dataName, String param) {
            super(HttpRequestType.PUT, DefaultDataActivity.this.getString(R.string.apiVolunteerURL), "api/volunteer/" + dataName, param, DefaultDataActivity.this.getUserAccessToken());
        }

        protected void onPostExecuteBody(String result) {
            if (DefaultDataActivity.this.hasExtraPutData) {
                DefaultDataActivity.this.extraPutData();
            } else {
                DefaultDataActivity.this.finishActivity();
            }
        }
    }
}

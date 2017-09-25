package com.vowme.vol.app.activities.profile;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.Adapter;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Spinner;
import android.widget.TextView;

import com.vowme.app.models.api.VolunteerHomeProfileModel;
import com.vowme.app.models.api.VolunteerSkillsModel;
import com.vowme.app.models.lookUp.LookupChild;
import com.vowme.app.utilities.activities.BaseActivity;
import com.vowme.app.utilities.adapters.LookupArrayAdapter;
import com.vowme.app.utilities.helpers.DefaultDataHelper;
import com.vowme.vol.app.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ProfessionalSkillsEditActivity extends BaseActivity {
    private LookupArrayAdapter expAdapter;
    private String profileDetails;
    private int skillId;
    private SubProSkillArrayAdapter subAdapter;
    private List<LookupChild> subProSkill;
    private RecyclerView subSkillListView;
    private VolunteerHomeProfileModel volModel;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((int) R.layout.activity_professional_skills_edit);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_no_tabs);
        toolbar.setTitle(getIntent().getStringExtra(getResources().getString(R.string.EXTRA_PRO_SKILL_TITLE)));
        setSupportActionBar(toolbar);
        ActionBar ab = getSupportActionBar();
        if (ab != null) {
            ab.setDisplayHomeAsUpEnabled(true);
            ab.setHomeAsUpIndicator((int) R.mipmap.ic_arrow_back_white_24dp);
        }
        this.profileDetails = getIntent().getStringExtra(getResources().getString(R.string.EXTRA_MODEL_DETAILS));
        this.skillId = getIntent().getIntExtra(getResources().getString(R.string.EXTRA_PRO_SKILL_ID), 0);
        try {
            this.volModel = new VolunteerHomeProfileModel(new JSONObject(this.profileDetails));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        this.subProSkill = new ArrayList();
        for (LookupChild lookup : DefaultDataHelper.getSubproSkills()) {
            if (lookup.getParentId() == this.skillId) {
                this.subProSkill.add(lookup);
            }
        }
        this.subSkillListView = (RecyclerView) findViewById(R.id.sub_skill_list);
        this.subSkillListView.setLayoutManager(new LinearLayoutManager(this));
        this.subAdapter = new SubProSkillArrayAdapter(this.subProSkill);
        this.subSkillListView.setAdapter(this.subAdapter);
        this.expAdapter = new LookupArrayAdapter(this, 17367043, DefaultDataHelper.getExperiences());
        this.expAdapter.setDropDownViewResource(17367049);
        this.expAdapter.notifyDataSetChanged();
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case 16908332:
                Intent intent = new Intent();
                intent.putExtra(getResources().getString(R.string.EXTRA_MODEL_DETAILS), this.volModel.toJsonObject().toString());
                setResult(-1, intent);
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private VolunteerSkillsModel getSubSkillFromId(int id) {
        VolunteerSkillsModel result = null;
        for (int i = 0; i < this.volModel.skills.size() && result == null; i++) {
            if (((VolunteerSkillsModel) this.volModel.skills.get(i)).subClassificationId.intValue() == id) {
                result = (VolunteerSkillsModel) this.volModel.skills.get(i);
            }
        }
        return result;
    }

    private class SubProSkillArrayAdapter extends Adapter<RecyclerView.ViewHolder> {
        private List<LookupChild> mValues;

        public SubProSkillArrayAdapter(List<LookupChild> mItemValues) {
            this.mValues = mItemValues;
        }

        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_sub_pro_skill, parent, false));
        }

        public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
            if (holder instanceof ViewHolder) {
                ViewHolder vh = (ViewHolder) holder;
                vh.mItem = (LookupChild) this.mValues.get(position);
                vh.itemText.setText(vh.mItem.getName());
                vh.spinner.setAdapter(ProfessionalSkillsEditActivity.this.expAdapter);
                vh.spinner.setOnItemSelectedListener(new OnItemSelectedListener() {
                    public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                        VolunteerSkillsModel result = ProfessionalSkillsEditActivity.this.getSubSkillFromId(((ViewHolder) holder).mItem.getId());
                        if (position == 0 && result != null) {
                            ProfessionalSkillsEditActivity.this.volModel.skills.remove(result);
                        } else if (position == 0) {
                        } else {
                            if (result != null) {
                                ((VolunteerSkillsModel) ProfessionalSkillsEditActivity.this.volModel.skills.get(ProfessionalSkillsEditActivity.this.volModel.skills.indexOf(result))).experienceId = Integer.valueOf(position);
                                return;
                            }
                            ProfessionalSkillsEditActivity.this.volModel.skills.add(new VolunteerSkillsModel(ProfessionalSkillsEditActivity.this.skillId, ((ViewHolder) holder).mItem.getId(), position));
                        }
                    }

                    public void onNothingSelected(AdapterView<?> adapterView) {
                    }
                });
                VolunteerSkillsModel result = ProfessionalSkillsEditActivity.this.getSubSkillFromId(vh.mItem.getId());
                if (result != null) {
                    vh.spinner.setSelection(result.experienceId.intValue());
                }
            }
        }

        public int getItemCount() {
            return this.mValues.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            public final TextView itemText;
            public final Spinner spinner;
            public LookupChild mItem;

            public ViewHolder(View view) {
                super(view);
                this.spinner = (Spinner) view.findViewById(R.id.exp_spinner);
                this.itemText = (TextView) view.findViewById(R.id.sub_name);
            }

            public String toString() {
                return super.toString() + " '" + "'";
            }
        }
    }
}

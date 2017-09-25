package com.vowme.vol.app.activities.profile;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.design.widget.TextInputLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.Adapter;
import android.support.v7.widget.helper.ItemTouchHelper.Callback;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TableRow;
import android.widget.TextView;

import com.vowme.app.models.Enum.HttpRequestType;
import com.vowme.app.models.api.OpportunityExperience;
import com.vowme.app.utilities.activities.ProfileFormActivity;
import com.vowme.app.utilities.api.ApiRestFullRequest;
import com.vowme.app.utilities.customWidgets.DividerItemDecoration;
import com.vowme.vol.app.R;

import java.util.List;

public class ExperienceActivity extends ProfileFormActivity {
    private TextView company;
    private TextView experience;
    private TextView jobTitle;
    private TextView linkedin;
    private RecyclerView volExperiencesRecyclerView;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    protected void initFields() {
        this.jobTitle = (TextView) findViewById(R.id.job_title_txt);
        this.company = (TextView) findViewById(R.id.company_txt);
        this.experience = (TextView) findViewById(R.id.previous_txt);
        this.linkedin = (TextView) findViewById(R.id.linkedin_txt);
        this.volExperiencesRecyclerView = (RecyclerView) findViewById(R.id.experiences_list);
        TextInputLayout floatingJobTitleText = (TextInputLayout) findViewById(R.id.input_layout_job_title);
        if (!(floatingJobTitleText == null || floatingJobTitleText.getEditText() == null)) {
            floatingJobTitleText.getEditText().addTextChangedListener(getFloatingTextLengthValidator(floatingJobTitleText, 0, 150, null, "Your job title can have lengh up to 150 characters."));
        }
        TextInputLayout floatingCompanyText = (TextInputLayout) findViewById(R.id.input_layout_company);
        if (!(floatingCompanyText == null || floatingCompanyText.getEditText() == null)) {
            floatingCompanyText.getEditText().addTextChangedListener(getFloatingTextLengthValidator(floatingCompanyText, 0, 150, null, "Your compagny name can have lengh up to 150 characters."));
        }
        TextInputLayout floatingExperienceText = (TextInputLayout) findViewById(R.id.input_layout_previous);
        if (!(floatingExperienceText == null || floatingExperienceText.getEditText() == null)) {
            floatingExperienceText.getEditText().addTextChangedListener(getFloatingTextLengthValidator(floatingExperienceText, 0, Callback.DEFAULT_DRAG_ANIMATION_DURATION, null, "Your previous experience can have lengh up to 200 characters."));
        }
        TextInputLayout floatingELinkedinText = (TextInputLayout) findViewById(R.id.input_layout_linkedin);
        if (!(floatingELinkedinText == null || floatingELinkedinText.getEditText() == null)) {
            floatingELinkedinText.getEditText().addTextChangedListener(getFloatingTextLengthValidator(floatingELinkedinText, 0, Callback.DEFAULT_SWIPE_ANIMATION_DURATION, null, "Your linkedin profile (URL) can have lengh up to 250 characters."));
        }
        this.jobTitle.setText(this.volModel.work.jobTitle);
        this.company.setText(this.volModel.work.compagny);
        this.experience.setText(this.volModel.work.workExperience);
        this.linkedin.setText(this.volModel.work.linkedin);
        this.volExperiencesRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        this.volExperiencesRecyclerView.addItemDecoration(new DividerItemDecoration(this, 1));
        this.volExperiencesRecyclerView.setAdapter(new volExperiencesArrayAdapter(this.volModel.work.experiences));
    }

    protected void OnSaveOptionsItemSelected() {
        this.volModel.work.jobTitle = this.jobTitle.getText().toString();
        this.volModel.work.compagny = this.company.getText().toString();
        this.volModel.work.workExperience = this.experience.getText().toString();
        this.volModel.work.linkedin = this.linkedin.getText().toString();
        new putVolunteerWork(this.volModel.work.toJsonObject().toString()).execute(new Void[0]);
    }

    @LayoutRes
    protected int getLayoutResID() {
        return R.layout.activity_experience;
    }

    private OpportunityExperience getById(int id) {
        OpportunityExperience result = null;
        boolean found = false;
        for (int i = 0; i < this.volModel.work.experiences.size() && !found; i++) {
            if (((OpportunityExperience) this.volModel.work.experiences.get(i)).id == id) {
                found = true;
                result = (OpportunityExperience) this.volModel.work.experiences.get(i);
            }
        }
        return result;
    }

    public class putVolunteerWork extends ApiRestFullRequest {
        public putVolunteerWork(String param) {
            super(HttpRequestType.PUT, ExperienceActivity.this.getString(R.string.apiVolunteerURL), "api/volunteer/work", param, ExperienceActivity.this.getUserAccessToken());
        }

        protected void onPostExecuteBody(String result) {
            ExperienceActivity.this.finishActivity();
        }
    }

    private class volExperiencesArrayAdapter extends Adapter<RecyclerView.ViewHolder> {
        private List<OpportunityExperience> mValues;

        public volExperiencesArrayAdapter(List<OpportunityExperience> mItemValues) {
            this.mValues = mItemValues;
        }

        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_experience, parent, false));
        }

        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            if (holder instanceof ViewHolder) {
                final ViewHolder vh = (ViewHolder) holder;
                vh.mItem = (OpportunityExperience) this.mValues.get(position);
                vh.itemText.setText(vh.mItem.name);
                vh.itemOrgaText.setText("for " + vh.mItem.organisationName);
                vh.isDisplay.setChecked(vh.mItem.isDisplayedExperience);
                vh.isDisplay.setOnClickListener(new OnClickListener() {
                    public void onClick(View v) {
                        OpportunityExperience result = ExperienceActivity.this.getById(vh.mItem.id);
                        if (result != null) {
                            result.isDisplayedExperience = !result.isDisplayedExperience;
                        }
                    }
                });
            }
        }

        public int getItemCount() {
            return this.mValues.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            public final CheckBox isDisplay;
            public final TextView itemOrgaText;
            public final TextView itemText;
            public final TableRow layout;
            public OpportunityExperience mItem;

            public ViewHolder(View view) {
                super(view);
                this.layout = (TableRow) view.findViewById(R.id.tableRow1);
                this.itemText = (TextView) view.findViewById(R.id.item_name);
                this.itemOrgaText = (TextView) view.findViewById(R.id.item_orga_name);
                this.isDisplay = (CheckBox) view.findViewById(R.id.item_checkBox);
            }

            public String toString() {
                return super.toString() + " '" + "'";
            }
        }
    }
}

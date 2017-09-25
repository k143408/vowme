package com.vowme.vol.app.activities.profile;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.design.widget.TextInputLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper.Callback;
import android.text.TextUtils;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.Switch;
import android.widget.TextView;

import com.vowme.app.models.Enum.HttpRequestType;
import com.vowme.app.models.lookUp.Lookup;
import com.vowme.app.utilities.activities.ProfileFormActivity;
import com.vowme.app.utilities.adapters.DefaultDataRecyclerViewAdapter;
import com.vowme.app.utilities.api.ApiRestFullRequest;
import com.vowme.app.utilities.helpers.ArrayListHelper;
import com.vowme.app.utilities.helpers.DefaultDataHelper;
import com.vowme.vol.app.R;

import java.util.ArrayList;
import java.util.List;

public class SkillsHobbiesActivity extends ProfileFormActivity {
    private DefaultDataRecyclerViewAdapter adapter;
    private List<Integer> checked;
    private TextView hobbies;
    private List<Lookup> items;
    private RecyclerView recyclerView;
    private TextView skillsQualifications;
    private Switch switchLanguages;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    protected void initFields() {
        this.skillsQualifications = (TextView) findViewById(R.id.skill_qual_txt);
        this.hobbies = (TextView) findViewById(R.id.hobbies_txt);
        TextInputLayout floatingSkillsQualText = (TextInputLayout) findViewById(R.id.input_layout_skill_qual);
        if (!(floatingSkillsQualText == null || floatingSkillsQualText.getEditText() == null)) {
            floatingSkillsQualText.getEditText().addTextChangedListener(getFloatingTextLengthValidator(floatingSkillsQualText, 0, 500, null, "Your skills and qualifications can have length up to 500 characters."));
        }
        TextInputLayout floatingHobbiesText = (TextInputLayout) findViewById(R.id.input_layout_hobbies);
        if (!(floatingHobbiesText == null || floatingHobbiesText.getEditText() == null)) {
            floatingHobbiesText.getEditText().addTextChangedListener(getFloatingTextLengthValidator(floatingHobbiesText, 0, Callback.DEFAULT_DRAG_ANIMATION_DURATION, null, "Your hobbies can have length up to 200 characters."));
        }
        this.skillsQualifications.setText(this.volModel.skillHobbies.qualification);
        this.hobbies.setText(this.volModel.skillHobbies.hobbies);
        this.checked = new ArrayList();
        for (Lookup item : this.volModel.languages) {
            this.checked.add(Integer.valueOf(item.getId()));
        }
        DefaultDataHelper defaultDataHelper = this.defaultDataHelper;
        this.items = DefaultDataHelper.getLanguages();
        this.adapter = new DefaultDataRecyclerViewAdapter(this.items, null, this.checked);
        this.adapter.setShowItemDescription(false);
        this.adapter.notifyDataSetChanged();
        this.recyclerView = (RecyclerView) findViewById(R.id.list_other_languages);
        this.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        this.recyclerView.setAdapter(this.adapter);
        this.switchLanguages = (Switch) findViewById(R.id.switch_other_languages);
        this.switchLanguages.setChecked(this.checked.size() != 0);
        if (this.checked.size() != 0) {
            this.recyclerView.setVisibility(0);
        } else {
            this.recyclerView.setVisibility(8);
        }
        this.switchLanguages.setOnCheckedChangeListener(new C08291());
    }

    protected void OnSaveOptionsItemSelected() {
        this.volModel.languages.clear();
        this.checked = this.adapter.getIdsChecked();
        for (Lookup item : this.items) {
            if (this.checked.contains(Integer.valueOf(item.getId()))) {
                this.volModel.languages.add(item);
            }
        }
        new putVolunteerBasicData("language", "\"" + TextUtils.join(",", ArrayListHelper.convertToListString(this.adapter.getIdsChecked())) + "\"").execute(new Void[0]);
    }

    @LayoutRes
    protected int getLayoutResID() {
        return R.layout.activity_skills_hobbies;
    }

    class C08291 implements OnCheckedChangeListener {
        C08291() {
        }

        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            if (isChecked) {
                SkillsHobbiesActivity.this.recyclerView.setVisibility(0);
                return;
            }
            SkillsHobbiesActivity.this.checked.clear();
            SkillsHobbiesActivity.this.adapter.notifyDataSetChanged();
            SkillsHobbiesActivity.this.recyclerView.setVisibility(8);
        }
    }

    public class putVolunteerAbout extends ApiRestFullRequest {
        public putVolunteerAbout(String param) {
            super(HttpRequestType.PUT, SkillsHobbiesActivity.this.getString(R.string.apiVolunteerURL), "api/volunteer/about", param, SkillsHobbiesActivity.this.getUserAccessToken());
        }

        protected void onPostExecuteBody(String result) {
            SkillsHobbiesActivity.this.finishActivity();
        }
    }

    public class putVolunteerBasicData extends ApiRestFullRequest {
        public putVolunteerBasicData(String dataName, String param) {
            super(HttpRequestType.PUT, SkillsHobbiesActivity.this.getString(R.string.apiVolunteerURL), "api/volunteer/" + dataName, param, SkillsHobbiesActivity.this.getUserAccessToken());
        }

        protected void onPostExecuteBody(String result) {
            SkillsHobbiesActivity.this.volModel.skillHobbies.qualification = SkillsHobbiesActivity.this.skillsQualifications.getText().toString();
            SkillsHobbiesActivity.this.volModel.skillHobbies.hobbies = SkillsHobbiesActivity.this.hobbies.getText().toString();
            new putVolunteerAbout(SkillsHobbiesActivity.this.volModel.skillHobbies.toJsonObject().toString()).execute(new Void[0]);
        }
    }
}

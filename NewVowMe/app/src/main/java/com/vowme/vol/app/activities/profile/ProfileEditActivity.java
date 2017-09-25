package com.vowme.vol.app.activities.profile;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;

import com.vowme.app.models.Enum.ActivityCode;
import com.vowme.app.utilities.activities.BaseActivity;
import com.vowme.vol.app.R;

import org.json.JSONException;
import org.json.JSONObject;

public class ProfileEditActivity extends BaseActivity {
    private String profileDetails;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((int) R.layout.activity_profile_edit);
        this.profileDetails = getIntent().getStringExtra(getResources().getString(R.string.EXTRA_MODEL_DETAILS));
        updateTitle();
        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);
        ab.setHomeAsUpIndicator((int) R.mipmap.ic_arrow_back_white_24dp);
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case 16908332:
                setResult(-1, new Intent().putExtra(getResources().getString(R.string.EXTRA_MODEL_DETAILS), this.profileDetails));
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.act_basic_info:
                intent = new Intent(this, BasicInfoActivity.class);
                break;
            case R.id.act_personal_info:
                intent = new Intent(this, PersonalInfoActivity.class);
                break;
            case R.id.act_commitment_types:
                intent = new Intent(this, CommitmentTypesActivity.class);
                break;
            case R.id.act_volunteering_avaibility:
                intent = new Intent(this, VolunteeringAvaibilityActivity.class);
                break;
            case R.id.act_location_preferences:
                intent = new Intent(this, LocationPreferencesActivity.class);
                break;
            case R.id.act_volunteering_suitability:
                intent = new Intent(this, VolunteeringSuitabilityActivity.class);
                break;
            case R.id.act_causes:
                intent = new Intent(this, CausesActivity.class);
                break;
            case R.id.act_interests:
                intent = new Intent(this, InterestsActivity.class);
                break;
            case R.id.act_skills_hobbies:
                intent = new Intent(this, SkillsHobbiesActivity.class);
                break;
            case R.id.act_licences_certificates:
                intent = new Intent(this, LicensesCertificatesActivity.class);
                break;
            case R.id.act_professional_skills:
                intent = new Intent(this, ProfessionalSkillsActivity.class);
                break;
            case R.id.act_experience:
                intent = new Intent(this, ExperienceActivity.class);
                break;
            default:
                intent = null;
                break;
        }
        if (intent != null) {
            intent.putExtra(getResources().getString(R.string.EXTRA_MODEL_DETAILS), this.profileDetails);
            startActivityForResult(intent, ActivityCode.PROFILEEDIT.getValue());
        }
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == -1) {
            String profileDetails = data.getStringExtra(getResources().getString(R.string.EXTRA_MODEL_DETAILS));
            if (!TextUtils.isEmpty(profileDetails)) {
                this.profileDetails = profileDetails;
                updateTitle();
                invalidateOptionsMenu();
            }
        }
    }

    private void updateTitle() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_no_tabs);
        if (!TextUtils.isEmpty(this.profileDetails)) {
            try {
                toolbar.setTitle(new JSONObject(this.profileDetails).getJSONObject("bases").getString("firstName"));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        setSupportActionBar(toolbar);
    }
}

package com.vowme.vol.app.activities.profile;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.vowme.app.models.Enum.HttpRequestType;
import com.vowme.app.models.Enum.LookupType;
import com.vowme.app.models.api.OpportunityExperience;
import com.vowme.app.models.api.VolunteerHomeProfileModel;
import com.vowme.app.models.api.VolunteerSkillsModel;
import com.vowme.app.models.lookUp.Lookup;
import com.vowme.app.utilities.adapters.BulletItemRecyclerViewAdapter;
import com.vowme.app.utilities.adapters.CircularImageItemRecyclerViewAdapter;
import com.vowme.app.utilities.api.ApiRestFullRequest;
import com.vowme.app.utilities.api.DownloadImageTask;
import com.vowme.app.utilities.customWidgets.CustomTokenizer;
import com.vowme.app.utilities.fragments.BaseFragment;
import com.vowme.app.utilities.helpers.DefaultDataHelper;
import com.vowme.app.utilities.helpers.ImageHelper;
import com.vowme.app.utilities.helpers.TextViewHelper;
import com.vowme.vol.app.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ProfileHomeFragment extends BaseFragment {
    private TextView atText;
    private CardView available;
    private CircularImageItemRecyclerViewAdapter availableAdapter;
    private RecyclerView availableRecyclerView;
    private CardView causes;
    private CircularImageItemRecyclerViewAdapter causesAdapter;
    private RecyclerView causesRecyclerView;
    private TextView companyText;
    private CardView experience;
    private TextView experienceText;
    private CardView hobbies;
    private TextView hobbiesText;
    private CardView interest;
    private TextView interestText;
    private CardView job;
    private TextView jobText;
    private TextView linkEditProfile;
    private TextView linkedinText;
    private CardView located;
    private TextView locatedText;
    private CardView proSkill;
    private BulletItemRecyclerViewAdapter proSkillAdapter;
    private RecyclerView proSkillrecyclerView;
    private String profileDetails;
    private BulletItemRecyclerViewAdapter sentenceAvailableAdapter;
    private RecyclerView sentenceAvailableRecyclerView;
    private CardView skill;
    private BulletItemRecyclerViewAdapter skillAdapter;
    private RecyclerView skillrecyclerView;
    private ImageView volAvatar;
    private TextView volEverydayTitle;
    private CardView volExperiences;
    private BulletItemRecyclerViewAdapter volExperiencesAdapter;
    private RecyclerView volExperiencesRecyclerView;
    private TextView volFullName;
    private VolunteerHomeProfileModel volModel;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getBaseActivity().isUserLoggedIn()) {
            new GetVolunteerBasicData().execute(new Void[0]);
        }
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (getBaseActivity().isUserLoggedIn()) {
            View result = inflater.inflate(R.layout.fragment_profile_home_authenticated, container, false);
            this.volFullName = (TextView) result.findViewById(R.id.vol_full_name);
            this.volEverydayTitle = (TextView) result.findViewById(R.id.every_title);
            this.volAvatar = (ImageView) result.findViewById(R.id.vol_avatar);
            this.linkEditProfile = (TextView) result.findViewById(R.id.act_edit_profile);
            this.linkEditProfile.setEnabled(false);
            this.located = (CardView) result.findViewById(R.id.located);
            this.locatedText = (TextView) result.findViewById(R.id.located_txt);
            this.interest = (CardView) result.findViewById(R.id.interest);
            this.interestText = (TextView) result.findViewById(R.id.interest_txt);
            this.hobbies = (CardView) result.findViewById(R.id.hobbies);
            this.hobbiesText = (TextView) result.findViewById(R.id.hobbies_txt);
            this.job = (CardView) result.findViewById(R.id.job);
            this.jobText = (TextView) result.findViewById(R.id.job_txt);
            this.companyText = (TextView) result.findViewById(R.id.company_txt);
            this.atText = (TextView) result.findViewById(R.id.at_txt);
            this.experience = (CardView) result.findViewById(R.id.experience);
            this.experienceText = (TextView) result.findViewById(R.id.experience_txt);
            this.linkedinText = (TextView) result.findViewById(R.id.linkedin_txt);
            this.skill = (CardView) result.findViewById(R.id.skill);
            this.skillrecyclerView = (RecyclerView) result.findViewById(R.id.skill_list);
            this.proSkill = (CardView) result.findViewById(R.id.pro_skill);
            this.proSkillrecyclerView = (RecyclerView) result.findViewById(R.id.pro_skill_list);
            this.causes = (CardView) result.findViewById(R.id.cause);
            this.causesRecyclerView = (RecyclerView) result.findViewById(R.id.cause_list);
            this.available = (CardView) result.findViewById(R.id.available);
            this.availableRecyclerView = (RecyclerView) result.findViewById(R.id.available_list);
            this.sentenceAvailableRecyclerView = (RecyclerView) result.findViewById(R.id.sentence_list);
            this.volExperiences = (CardView) result.findViewById(R.id.experiences);
            this.volExperiencesRecyclerView = (RecyclerView) result.findViewById(R.id.experiences_list);
            return result;
        }
        View result = inflater.inflate(R.layout.layout_create_an_account, container, false);
        getBaseActivity().putUserIsFromExpressInterest(false);
        return result;
    }

    public void refresh() {
        if (getBaseActivity().isUserLoggedIn()) {
            new GetVolunteerBasicData().execute(new Void[0]);
        }
    }

    public void onAttach(Context context) {
        super.onAttach(context);
    }

    public void onDetach() {
        super.onDetach();
    }

    public String getProfileDetails() {
        return this.profileDetails;
    }

    public void setProfileDetails(String profileDetails) {
        this.profileDetails = profileDetails;
    }

    public void updateView() {
        int i;
        try {
            this.volModel = new VolunteerHomeProfileModel(new JSONObject(this.profileDetails));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        this.linkEditProfile.setEnabled(true);
        this.volFullName.setText(this.volModel.bases.firstName + " " + this.volModel.bases.lastName);
        this.volEverydayTitle.setText(this.volModel.bases.everydayTitle);
        if (!TextUtils.isEmpty(this.volModel.bases.avatar)) {
            new DownloadImageTask(this.volAvatar, true, getBaseActivity()).execute(new String[]{ImageHelper.getAvatarUrl(this.volModel.bases.avatar, getResources().getString(R.string.AVATAR_SIZE_EXTENSION))});
        }
        CustomTokenizer token = new CustomTokenizer();
        if (this.volModel.locality == null || TextUtils.isEmpty(this.volModel.locality.location)) {
            this.located.setVisibility(View.GONE);
        } else {
            this.locatedText.setText("");
            String[] locations = this.volModel.locality.location.split(",");
            for (String terminateToken : locations) {
                this.locatedText.append(token.terminateToken(terminateToken));
            }
            this.located.setVisibility(View.VISIBLE);
        }
        if (this.volModel.interests.size() > 0) {
            this.interestText.setText("");
            for (Lookup item : this.volModel.interests) {
                this.interestText.append(token.terminateToken(item.getName()));
            }
            this.interest.setVisibility(View.VISIBLE);
        } else {
            this.interest.setVisibility(View.GONE);
        }
        if (TextUtils.isEmpty(this.volModel.skillHobbies.hobbies)) {
            this.hobbies.setVisibility(View.GONE);
        } else {
            this.hobbiesText.setText(this.volModel.skillHobbies.hobbies);
            this.hobbies.setVisibility(View.VISIBLE);
        }
        if (TextUtils.isEmpty(this.volModel.work.jobTitle) && TextUtils.isEmpty(this.volModel.work.compagny)) {
            this.job.setVisibility(View.GONE);
        } else {
            if (!TextUtils.isEmpty(this.volModel.work.jobTitle)) {
                this.jobText.setText(this.volModel.work.jobTitle);
            }
            if (TextUtils.isEmpty(this.volModel.work.compagny)) {
                this.atText.setVisibility(View.GONE);
            } else {
                this.companyText.setText(this.volModel.work.compagny);
                this.atText.setVisibility(View.VISIBLE);
            }
            this.job.setVisibility(View.VISIBLE);
        }
        if (TextUtils.isEmpty(this.volModel.work.workExperience) && TextUtils.isEmpty(this.volModel.work.linkedin)) {
            this.experience.setVisibility(View.GONE);
        } else {
            if (!TextUtils.isEmpty(this.volModel.work.workExperience)) {
                this.experienceText.setText(this.volModel.work.workExperience);
            }
            if (!TextUtils.isEmpty(this.volModel.work.linkedin)) {
                this.linkedinText.setText(this.volModel.work.linkedin);
            }
            this.experience.setVisibility(View.VISIBLE);
        }
        if (this.volModel.requirements.size() > 0) {
            List<SpannableStringBuilder> names = new ArrayList();
            for (Lookup item2 : this.volModel.requirements) {
                names.add(new SpannableStringBuilder(item2.getName()));
            }
            this.skillAdapter = new BulletItemRecyclerViewAdapter(names, getContext());
            this.skillAdapter.notifyDataSetChanged();
            this.skillrecyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
            this.skillrecyclerView.setAdapter(this.skillAdapter);
            this.skill.setVisibility(View.VISIBLE);
        } else {
            this.skill.setVisibility(View.GONE);
        }
        if (this.volModel.skills.size() > 0) {
            List<SpannableStringBuilder> skillNames = new ArrayList();
            for (Lookup item22 : DefaultDataHelper.getSubproSkills()) {
                if (isSubSkillFromId(item22.getId())) {
                    skillNames.add(new SpannableStringBuilder(item22.getName()));
                }
            }
            this.proSkillAdapter = new BulletItemRecyclerViewAdapter(skillNames, getContext());
            this.proSkillAdapter.notifyDataSetChanged();
            this.proSkillrecyclerView.setLayoutManager(new GridLayoutManager(getContext(), 1));
            this.proSkillrecyclerView.setAdapter(this.proSkillAdapter);
            this.proSkill.setVisibility(View.VISIBLE);
        } else {
            this.proSkill.setVisibility(View.GONE);
        }
        if (this.volModel.causes.size() > 0) {
            List<Lookup> causeNames = new ArrayList();
            for (Lookup item222 : this.volModel.causes) {
                causeNames.add(item222);
            }
            this.causesAdapter = new CircularImageItemRecyclerViewAdapter(causeNames, getContext(), LookupType.CAUSES);
            this.causesAdapter.notifyDataSetChanged();
            this.causesRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), 3));
            this.causesRecyclerView.setAdapter(this.causesAdapter);
            this.causes.setVisibility(View.VISIBLE);
        } else {
            this.causes.setVisibility(View.GONE);
        }
        if (this.volModel.avaibilities.sentenceAvaibility.size() > 0 || this.volModel.avaibilities.availableForEmergencyResponse || this.volModel.avaibilities.availableForGeneralVolunteering || this.volModel.avaibilities.availableForSpecialEvents || this.volModel.durations.size() > 0) {
            List<Lookup> result = new ArrayList();
            result.addAll(this.volModel.durations);
            if (this.volModel.avaibilities.availableForEmergencyResponse) {
                result.add(new Lookup(6, "Emergency Response"));
            }
            if (this.volModel.avaibilities.availableForSpecialEvents) {
                result.add(new Lookup(7, "Special Events"));
            }
            if (this.volModel.avaibilities.availableForGeneralVolunteering) {
                result.add(new Lookup(8, "General Volunteering"));
            }
            this.availableAdapter = new CircularImageItemRecyclerViewAdapter(result, getContext(), LookupType.AVAIBILITY);
            this.availableAdapter.notifyDataSetChanged();
            this.availableRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), 3));
            this.availableRecyclerView.setAdapter(this.availableAdapter);
            if (this.volModel.avaibilities.sentenceAvaibility.size() > 0) {
                List<SpannableStringBuilder> sentences = new ArrayList();
                for (List<String> item3 : this.volModel.avaibilities.sentenceAvaibility) {
                    sentences.add(new SpannableStringBuilder(TextUtils.join(";", item3)));
                }
                this.sentenceAvailableAdapter = new BulletItemRecyclerViewAdapter(sentences, getContext(), Boolean.valueOf(true));
                this.sentenceAvailableAdapter.notifyDataSetChanged();
                this.sentenceAvailableRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), 1));
                this.sentenceAvailableRecyclerView.setAdapter(this.sentenceAvailableAdapter);
            }
            this.available.setVisibility(View.VISIBLE);
        } else {
            this.available.setVisibility(View.GONE);
        }
        boolean atLeastOneDisplayed = false;
        for (i = 0; i < this.volModel.work.experiences.size() && !atLeastOneDisplayed; i++) {
            if (((OpportunityExperience) this.volModel.work.experiences.get(i)).isDisplayedExperience) {
                atLeastOneDisplayed = true;
            }
        }
        if (atLeastOneDisplayed) {
            List<SpannableStringBuilder> experienceNames = new ArrayList();
            for (OpportunityExperience item4 : this.volModel.work.experiences) {
                if (item4.isDisplayedExperience) {
                    experienceNames.add(TextViewHelper.formatOppForExperience(getBaseActivity(), item4.name, item4.organisationName));
                }
            }
            this.volExperiencesAdapter = new BulletItemRecyclerViewAdapter(experienceNames, getContext());
            this.volExperiencesAdapter.notifyDataSetChanged();
            this.volExperiencesRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), 1));
            this.volExperiencesRecyclerView.setAdapter(this.volExperiencesAdapter);
            this.volExperiences.setVisibility(View.VISIBLE);
            return;
        }
        this.volExperiences.setVisibility(View.GONE);
    }

    private boolean isSubSkillFromId(int id) {
        boolean result = false;
        for (int i = 0; i < this.volModel.skills.size() && !result; i++) {
            if (((VolunteerSkillsModel) this.volModel.skills.get(i)).subClassificationId.intValue() == id) {
                result = true;
            }
        }
        return result;
    }

    private class GetVolunteerBasicData extends ApiRestFullRequest {
        public GetVolunteerBasicData() {
            super(HttpRequestType.GET, ProfileHomeFragment.this.getString(R.string.apiVolunteerURL), "api/volunteer/homeProfile", ProfileHomeFragment.this.getBaseActivity().getUserAccessToken());
        }

        protected void onPostExecuteBody(String result) {
            if (result.length() != 0) {
                ProfileHomeFragment.this.profileDetails = result;
                ProfileHomeFragment.this.updateView();
            }
        }
    }
}

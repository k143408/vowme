package com.vowme.app.utilities.helpers;


import com.vowme.app.models.DayAvaibilityItem;
import com.vowme.app.models.Enum;
import com.vowme.app.models.lookUp.Lookup;
import com.vowme.app.models.lookUp.LookupChild;
import com.vowme.app.models.lookUp.LookupDesc;
import com.vowme.vol.app.R;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

public class DefaultDataHelper {
    private static List<DayAvaibilityItem> avaibilities;
    private static List<Lookup> avaibilitiesFilter;
    private static List<LookupDesc> causes;
    private static List<Lookup> cityStateFilter;
    private static List<Lookup> durations;
    private static List<Lookup> experiences;
    private static boolean hasLoaded;
    private static int[] imageAvaibilityNames = new int[]{0, R.drawable.bullhorn_icon, R.drawable.calendar_icon, R.drawable.briefcase_icon, R.drawable.clock_icon, R.drawable.bolt_icon, R.drawable.bell_icon, R.drawable.phone_icon};
    private static int[] imageCauseNames = new int[]{R.mipmap.arts_culture, R.mipmap.young_people, R.mipmap.disability_services, R.mipmap.environment_conservation, R.mipmap.education, R.mipmap.migrant_support, R.mipmap.health, R.mipmap.human_rights, R.mipmap.recreation, R.mipmap.seniors, R.mipmap.other, R.mipmap.sport, R.mipmap.emergency_response, R.mipmap.community_service, R.mipmap.museums_heritage, R.mipmap.family_support, R.mipmap.mentoring_advocacy, R.mipmap.disaster_relief, R.mipmap.animal_welfare, R.mipmap.drug_alcohol_support, R.mipmap.veteran_ex_service_community, R.mipmap.homeless};
    private static int[] imageIntrestNames = new int[]{R.mipmap.working_with_animals, R.mipmap.aged_care, R.mipmap.childcare, R.mipmap.education_training, R.mipmap.retail_sales, R.mipmap.information_tour_guides_heritage, R.mipmap.driving_transportation, R.mipmap.safety_emergency_services, R.mipmap.counselling_help_line, R.mipmap.accounting_finance, R.mipmap.fundraising_events, R.mipmap.garden_maintenance, R.mipmap.art_craft_photography, R.mipmap.food_preparation_service, 0, R.mipmap.it_web_development, R.mipmap.second_language, R.mipmap.governance_board_commitee, R.mipmap.library_services, R.mipmap.trades_maintenance, R.mipmap.tutoring_mentoring, R.mipmap.mediation_advocacy, R.mipmap.administration_office_management, R.mipmap.sport_physical_activities, R.mipmap.music_entertainment, R.mipmap.marketing_media_communications, R.mipmap.research_policy_analysis, R.mipmap.companionship_social_support, R.mipmap.writing_editing, R.mipmap.disability_support};
    private static DefaultDataHelper instance = null;
    private static List<LookupDesc> interests;
    private static boolean isLoading;
    private static List<Lookup> languages;
    private static List<Lookup> notificationFrequencies;
    private static List<Lookup> proSkills;
    private static List<LookupDesc> programs;
    private static List<Lookup> requirements;
    private static List<LookupChild> subproSkills;
    private static List<String> tShirtSize;
    private static List<String> titles;
    private static List<Lookup> transports;
    private static List<String> typeOfCars;

    protected DefaultDataHelper() {
        causes = new ArrayList();
        interests = new ArrayList();
        durations = new ArrayList();
        programs = new ArrayList();
        requirements = new ArrayList();
        languages = new ArrayList();
        avaibilitiesFilter = new ArrayList();
        cityStateFilter = new ArrayList();
        transports = new ArrayList();
        typeOfCars = new ArrayList();
        tShirtSize = new ArrayList();
        titles = new ArrayList();
        avaibilities = new ArrayList();
        proSkills = new ArrayList();
        experiences = new ArrayList();
        subproSkills = new ArrayList();
        notificationFrequencies = new ArrayList();
    }

    public static DefaultDataHelper getInstance() {
        if (instance == null) {
            instance = new DefaultDataHelper();
            DefaultDataHelper defaultDataHelper = instance;
            isLoading = false;
            defaultDataHelper = instance;
            hasLoaded = false;
        }
        return instance;
    }

    public static List<LookupDesc> getCauses() {
        return causes == null ? new ArrayList<LookupDesc>() : causes;
    }

    public static List<LookupDesc> getInterests() {
        return interests == null ? new ArrayList<LookupDesc>() : interests;
    }

    public static List<Lookup> getDurations() {
        return durations;
    }

    public static List<LookupDesc> getPrograms() {
        return programs;
    }

    public static List<Lookup> getRequirements() {
        return requirements;
    }

    public static List<Lookup> getAvaibilitiesFilter() {
        return avaibilitiesFilter;
    }

    public static List<Lookup> getLanguages() {
        return languages;
    }

    public static List<Lookup> getCityStateFilter() {
        return cityStateFilter;
    }

    public static List<Lookup> getTransports() {
        return transports;
    }

    public static List<String> getTypeOfCars() {
        return typeOfCars;
    }

    public static List<String> gettShirtSize() {
        return tShirtSize;
    }

    public static List<String> getTitles() {
        return titles;
    }

    public static List<DayAvaibilityItem> getAvaibilities() {
        return avaibilities;
    }

    public static List<Lookup> getProSkills() {
        return proSkills;
    }

    public static List<Lookup> getExperiences() {
        return experiences;
    }

    public static List<LookupChild> getSubproSkills() {
        return subproSkills;
    }

    public static int[] getImageCauseNames() {
        return imageCauseNames;
    }

    public static int[] getImageAvaibilityNames() {
        return imageAvaibilityNames;
    }

    public static int[] getImageIntrestNames() {
        return imageIntrestNames;
    }

    public static List<Lookup> getNotificationFrequencies() {
        return notificationFrequencies;
    }

    public static boolean isLoading() {
        return isLoading;
    }

    public static void setIsLoading(boolean isLoading) {
        isLoading = isLoading;
    }

    public static boolean isHasLoaded() {
        return hasLoaded;
    }

    public static void setHasLoaded(boolean hasLoaded) {
        hasLoaded = hasLoaded;
    }

    public void loadLookup(JSONArray datas, Enum.LookupType lookupType) {
        if (lookupType == Enum.LookupType.AVAIBILITYFILTER) {
            avaibilitiesFilter.add(new Lookup(Enum.AvaibilityFilterId.DAY.getValue().intValue(), "Day"));
            avaibilitiesFilter.add(new Lookup(Enum.AvaibilityFilterId.EVENING.getValue().intValue(), "Evening"));
            avaibilitiesFilter.add(new Lookup(Enum.AvaibilityFilterId.WEEKDAYS.getValue().intValue(), "Weekday"));
            avaibilitiesFilter.add(new Lookup(Enum.AvaibilityFilterId.WEEKEND.getValue().intValue(), "Weekend"));
            return;
        }
        if (lookupType == Enum.LookupType.AVAIBILITY) {
            avaibilities.add(new DayAvaibilityItem(1));
            avaibilities.add(new DayAvaibilityItem(2));
            avaibilities.add(new DayAvaibilityItem(3));
            avaibilities.add(new DayAvaibilityItem(4));
            avaibilities.add(new DayAvaibilityItem(5));
            avaibilities.add(new DayAvaibilityItem(6));
            avaibilities.add(new DayAvaibilityItem(7));
            avaibilities.add(new DayAvaibilityItem(8));
            return;
        }
        if (lookupType == Enum.LookupType.EXPERIENCES) {
            experiences.add(new Lookup(0, "0 Year"));
            experiences.add(new Lookup(1, "1 Years"));
            experiences.add(new Lookup(2, "2 Years"));
        }
        int i = 0;
        while (i < datas.length()) {
            try {
                switch (lookupType.ordinal()) {
                    case 0:
                        break;
                    case 1:
                        causes.add(new LookupDesc((datas != null && datas.length() != 0) ? datas.getJSONObject(i) : null));
                        break;

                    case 2: {
                        interests.add(new LookupDesc((datas != null && datas.length() != 0) ? datas.getJSONObject(i) : null));
                        break;
                    }
                    case 3: {
                        durations.add(new Lookup((datas != null && datas.length() != 0) ? datas.getJSONObject(i) : null));
                        break;
                    }
                    case 4: {
                        programs.add(new LookupDesc((datas != null && datas.length() != 0) ? datas.getJSONObject(i) : null));
                        break;
                    }
                    case 5: {
                        cityStateFilter.add(new Lookup((datas != null && datas.length() != 0) ? datas.getJSONObject(i) : null, false));
                        break;
                    }
                    case 6: {
                        requirements.add(new Lookup((datas != null && datas.length() != 0) ? datas.getJSONObject(i) : null));
                        break;
                    }
                    case 7: {
                        transports.add(new Lookup((datas != null && datas.length() != 0) ? datas.getJSONObject(i) : null));
                        break;
                    }
                    case 8: {
                        typeOfCars.add(new Lookup(datas.getJSONObject(i)).getName());
                        break;
                    }
                    case 9: {
                        tShirtSize.add(datas.getString(i));
                        break;
                    }
                    case 10: {
                        titles.add((datas != null && datas.length() != 0) ? datas.getString(i) : "");
                        break;
                    }
                    case 11: {
                        languages.add(new Lookup((datas != null && datas.length() != 0) ? datas.getJSONObject(i) : null));
                        break;
                    }
                    case 12: {
                        proSkills.add(new Lookup((datas != null && datas.length() != 0) ? datas.getJSONObject(i) : null));
                        break;
                    }
                    case 13: {
                        experiences.add(new Lookup((datas != null && datas.length() != 0) ? datas.getJSONObject(i) : null));
                        break;
                    }
                    case 14: {
                        subproSkills.add(new LookupChild((datas != null && datas.length() != 0) ? datas.getJSONObject(i) : null));
                        break;
                    }
                    case 15: {
                        notificationFrequencies.add(new Lookup((datas != null && datas.length() != 0) ? datas.getJSONObject(i) : null));
                        break;
                    }
                }
                i = i + 1;
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}

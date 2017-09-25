package com.vowme.vol.app.activities.profile;

import android.os.Bundle;

import com.vowme.app.models.Enum.LookupType;
import com.vowme.app.utilities.activities.DefaultDataActivity;
import com.vowme.app.utilities.helpers.DefaultDataHelper;

import java.util.List;

public class InterestsActivity extends DefaultDataActivity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    protected void initFields() {
    }

    protected String getPutDataName() {
        return "interest";
    }

    protected LookupType getModelDataName() {
        return LookupType.INTERESTS;
    }

    protected List<?> getBasicData() {
        DefaultDataHelper defaultDataHelper = this.defaultDataHelper;
        return DefaultDataHelper.getInterests();
    }

    protected boolean isShowItemDescription() {
        return false;
    }
}

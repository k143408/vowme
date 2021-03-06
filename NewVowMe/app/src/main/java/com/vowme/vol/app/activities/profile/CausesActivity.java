package com.vowme.vol.app.activities.profile;

import android.os.Bundle;

import com.vowme.app.models.Enum.LookupType;
import com.vowme.app.utilities.activities.DefaultDataActivity;
import com.vowme.app.utilities.helpers.DefaultDataHelper;

import java.util.List;

public class CausesActivity extends DefaultDataActivity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    protected void initFields() {
    }

    protected String getPutDataName() {
        return "cause";
    }

    protected LookupType getModelDataName() {
        return LookupType.CAUSES;
    }

    protected List<?> getBasicData() {
        DefaultDataHelper defaultDataHelper = this.defaultDataHelper;
        return DefaultDataHelper.getCauses();
    }

    protected boolean isShowItemDescription() {
        return false;
    }
}

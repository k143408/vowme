package com.vowme.vol.app.activities.settings;

import android.os.Bundle;
import android.support.annotation.LayoutRes;

import com.vowme.app.utilities.activities.SaveMenuFormValidationActivity;
import com.vowme.vol.app.R;

public class ProfilePrivacyActivity extends SaveMenuFormValidationActivity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    protected void createModel() {
    }

    protected void initFields() {
    }

    protected void OnSaveOptionsItemSelected() {
    }

    @LayoutRes
    protected int getLayoutResID() {
        return R.layout.activity_profile_privacy;
    }
}

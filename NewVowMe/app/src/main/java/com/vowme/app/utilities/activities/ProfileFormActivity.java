package com.vowme.app.utilities.activities;

import android.content.Intent;
import android.os.Bundle;

import com.vowme.app.models.api.VolunteerHomeProfileModel;
import com.vowme.vol.app.R;

import org.json.JSONException;
import org.json.JSONObject;

public abstract class ProfileFormActivity extends SaveMenuFormValidationActivity {
    protected VolunteerHomeProfileModel volModel;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    protected void createModel() {
        try {
            this.volModel = new VolunteerHomeProfileModel(new JSONObject(this.modelAsString));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    protected void finishActivity() {
        Intent intent = new Intent();
        intent.putExtra(getResources().getString(R.string.EXTRA_MODEL_DETAILS), this.volModel.toJsonObject().toString());
        setResult(-1, intent);
        finish();
    }
}

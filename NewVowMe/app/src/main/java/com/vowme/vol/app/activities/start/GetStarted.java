package com.vowme.vol.app.activities.start;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.vowme.app.models.Enum.ActivityCode;
import com.vowme.app.utilities.activities.BaseActivity;
import com.vowme.vol.app.R;

public class GetStarted extends BaseActivity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((int) R.layout.activity_getstarted);
    }

    public void getStarted(View view) {
        startActivity(new Intent(this, StartStepsActivity.class));
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == ActivityCode.GETSTARTED.getValue() && resultCode == -1) {
            finish();
        }
    }
}

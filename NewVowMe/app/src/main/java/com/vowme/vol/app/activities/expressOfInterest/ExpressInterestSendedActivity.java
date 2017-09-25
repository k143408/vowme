package com.vowme.vol.app.activities.expressOfInterest;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;

import com.vowme.vol.app.R;
import com.vowme.app.utilities.activities.BaseActivity;
import com.vowme.app.utilities.helpers.TypefacesHelper;

public class ExpressInterestSendedActivity extends BaseActivity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((int) R.layout.activity_express_interest_sended);
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar_no_tabs));
        ActionBar ab = getSupportActionBar();
        if (ab != null) {
            ab.setDisplayHomeAsUpEnabled(true);
            ab.setHomeAsUpIndicator((int) R.mipmap.ic_close_white_24dp);
        }
        ((TextView) findViewById(R.id.ic_tab_icon)).setTypeface(TypefacesHelper.get(getBaseContext(), "fonts/icomoon.ttf"));
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case 16908332:
                setResult(-1, null);
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}

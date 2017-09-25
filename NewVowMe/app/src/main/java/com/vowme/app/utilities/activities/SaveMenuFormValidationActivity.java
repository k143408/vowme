package com.vowme.app.utilities.activities;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.vowme.vol.app.R;


public abstract class SaveMenuFormValidationActivity extends FormValidationActivity {
    protected String modelAsString;
    protected MenuItem saveItem;

    protected abstract void OnSaveOptionsItemSelected();

    protected abstract void createModel();

    @LayoutRes
    protected abstract int getLayoutResID();

    protected abstract void initFields();

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutResID());
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar_no_tabs));
        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);
        ab.setHomeAsUpIndicator((int) R.mipmap.ic_arrow_back_white_24dp);
        this.modelAsString = getIntent().getStringExtra(getResources().getString(R.string.EXTRA_MODEL_DETAILS));
        createModel();
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_save, menu);
        this.saveItem = menu.findItem(R.id.action_save);
        initFieldsSubmitButton();
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case 16908332:
                finish();
                return true;
            case R.id.action_save:
                OnSaveOptionsItemSelected();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    protected void initFieldsSubmitButton() {
        this.submitButton = this.saveItem;
        initFields();
    }
}

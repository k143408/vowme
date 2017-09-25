package com.vowme.vol.app.activities.search;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import com.vowme.app.models.SavedSearchOpportunitiesItem;
import com.vowme.app.utilities.activities.BaseActivity;
import com.vowme.vol.app.R;

import org.json.JSONException;
import org.json.JSONObject;

public class SavingSearchActivity extends BaseActivity {
    private Integer categorie;
    private Integer id;
    private String keyword;
    private SavedSearchOpportunitiesItem potentialSavedSearch;
    private EditText searchText;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((int) R.layout.activity_saving_search);
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar_no_tabs));
        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);
        ab.setHomeAsUpIndicator((int) R.mipmap.ic_close_white_24dp);
        try {
            this.potentialSavedSearch = new SavedSearchOpportunitiesItem(new JSONObject(getIntent().getStringExtra(getResources().getString(R.string.EXTRA_SAVED_SEARCH))));
            this.searchText = (EditText) findViewById(R.id.search_txt);
            this.searchText.append(this.potentialSavedSearch.getName());
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_save, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case 16908332:
                finish();
                return true;
            case R.id.action_save:
                this.potentialSavedSearch.setName(this.searchText.getText().toString());
                addSearchItemToBookmark(this.potentialSavedSearch);
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}

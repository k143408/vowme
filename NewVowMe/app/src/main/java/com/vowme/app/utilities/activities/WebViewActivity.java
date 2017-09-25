package com.vowme.app.utilities.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.webkit.WebView;

import com.vowme.vol.app.R;


public class WebViewActivity extends AppCompatActivity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((int) R.layout.activity_web_view);
        Intent myIntent = getIntent();
        CharSequence title = myIntent.getStringExtra(getResources().getString(R.string.WEB_VIEW_TITLE));
        ((WebView) findViewById(R.id.webview)).loadUrl(myIntent.getStringExtra(getResources().getString(R.string.WEB_VIEW_URL)));
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_no_tabs);
        toolbar.setTitle(title);
        setSupportActionBar(toolbar);
        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);
        ab.setHomeAsUpIndicator((int) R.mipmap.ic_arrow_back_white_24dp);
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case 16908332:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}

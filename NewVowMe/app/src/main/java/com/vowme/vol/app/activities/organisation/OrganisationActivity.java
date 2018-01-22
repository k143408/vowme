package com.vowme.vol.app.activities.organisation;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.vowme.vol.app.R;
import com.vowme.app.models.Enum.HttpRequestType;
import com.vowme.app.models.api.OrganisationDetails;
import com.vowme.app.utilities.activities.BaseActivity;
import com.vowme.app.utilities.api.ApiRestFullRequest;
import com.vowme.app.utilities.api.ApiWCFRequest;
import com.vowme.app.utilities.api.DownloadImageTask;
import com.vowme.app.utilities.helpers.TypefacesHelper;

import org.json.JSONException;
import org.json.JSONObject;

public class OrganisationActivity extends BaseActivity {
    private String id;
    private TextView orgaCause;
    private TextView orgaDesc;
    private TextView orgaIcCause;
    private TextView orgaIcLoc;
    private TextView orgaLocation;
    private ImageView orgaLogo;
    private TextView orgaName;
    private TextView orgaSate;
    private TextView orgaStreet;
    private TextView orgaSubPostcode;
    private TextView orgaWebsite;
    private OrganisationDetails organisation;

    private class GetViktorOrganisation extends ApiWCFRequest {
        public GetViktorOrganisation() {
            super(HttpRequestType.GET, OrganisationActivity.this.getString(R.string.apiVolunteerURL1), "api/organisation/" + OrganisationActivity.this.id);
        }

        protected void onPostExecuteBody(String result) {
            try {
                OrganisationActivity.this.initDataOrganisation(new JSONObject(new JSONObject("{\"Value\":" + result + "}").getString("Value")), false);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    private class GetVolunteerOrganisation extends ApiRestFullRequest {
        public GetVolunteerOrganisation() {
            super(HttpRequestType.GET, OrganisationActivity.this.getString(R.string.apiVolunteerURL1), "api/organisation/" + OrganisationActivity.this.id, OrganisationActivity.this.getUserAccessToken());
        }

        protected void onPostExecuteBody(String result) {
            try {
                OrganisationActivity.this.initDataOrganisation(new JSONObject(result), true);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((int) R.layout.activity_organisation);
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar_no_tabs));
        ActionBar ab = getSupportActionBar();
        if (ab != null) {
            ab.setDisplayHomeAsUpEnabled(true);
            ab.setHomeAsUpIndicator((int) R.mipmap.ic_arrow_back_white_24dp);
        }
        this.organisation = new OrganisationDetails();
        this.id = getIntent().getStringExtra("android.intent.extra.TEXT");
        if (isUserLoggedIn()) {
            new GetVolunteerOrganisation().execute(new Void[0]);
        } else {
            new GetViktorOrganisation().execute(new Void[0]);
        }
        this.orgaName = (TextView) findViewById(R.id.orga_name);
        this.orgaWebsite = (TextView) findViewById(R.id.orga_website);
        this.orgaIcLoc = (TextView) findViewById(R.id.orga_ic_loc);
        this.orgaIcCause = (TextView) findViewById(R.id.orga_ic_cause);
        Typeface font = TypefacesHelper.get(this, "fonts/material_icon_font.ttf");
        this.orgaIcLoc.setTypeface(font);
        this.orgaIcCause.setTypeface(font);
        this.orgaLocation = (TextView) findViewById(R.id.orga_location);
        this.orgaCause = (TextView) findViewById(R.id.orga_cause);
        this.orgaDesc = (TextView) findViewById(R.id.orga_desc);
        this.orgaStreet = (TextView) findViewById(R.id.orga_street);
        this.orgaSubPostcode = (TextView) findViewById(R.id.orga_sub_postcode);
        this.orgaSate = (TextView) findViewById(R.id.orga_state);
        this.orgaLogo = (ImageView) findViewById(R.id.orga_logo);
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case 16908332:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void initDataOrganisation(JSONObject result, boolean isAutenticatedSearch) {
        this.organisation = new OrganisationDetails(result, isAutenticatedSearch);
        if (!TextUtils.isEmpty(this.organisation.getLogoFileName())) {
            new DownloadImageTask(this.orgaLogo, true, this).execute(new String[]{this.organisation.getLogoFileName()});
        }
        this.orgaName.setText(this.organisation.getName());
        this.orgaWebsite.setText(this.organisation.getWebsite());
        this.orgaLocation.setText(this.organisation.getSuburb() == null ? "" : this.organisation.getSuburb());
        this.orgaCause.setText(this.organisation.getServiceFocus());
        this.orgaDesc.setText(this.organisation.getDescription());
        this.orgaStreet.setText(this.organisation.getStreet());
        this.orgaSubPostcode.setText(this.organisation.getSuburb() + " " + this.organisation.getPostcode());
    }
}

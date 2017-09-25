package com.vowme.vol.app.activities.opportunity;

import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.vowme.app.models.Enum.ActivityCode;
import com.vowme.app.models.Enum.HttpRequestType;
import com.vowme.app.models.api.OpportunityDetails;
import com.vowme.app.utilities.activities.BaseActivity;
import com.vowme.app.utilities.api.ApiRestFullRequest;
import com.vowme.app.utilities.api.ApiWCFRequest;
import com.vowme.app.utilities.helpers.TextViewHelper;
import com.vowme.app.utilities.helpers.TypefacesHelper;
import com.vowme.app.utilities.spannables.CustomClickableSpan;
import com.vowme.vol.app.R;
import com.vowme.vol.app.activities.expressOfInterest.ExpressInterestActivity;
import com.vowme.vol.app.activities.organisation.OrganisationActivity;

import org.json.JSONException;
import org.json.JSONObject;

public class OpportunityActivity extends BaseActivity implements OnMapReadyCallback {
    private Button expressInterest;
    private Button expressedInterest;
    private String id;
    private TextView oppDesc;
    private TextView oppDuration;
    private TextView oppFor;
    private TextView oppIcLoc;
    private TextView oppIcTime;
    private TextView oppId;
    private ListView oppInterests;
    private TextView oppLocation;
    private TextView oppName;
    private TextView oppOrgaDes;
    private TextView oppReimbursement;
    private TextView oppTime;
    private TextView oppTraining;
    private OpportunityDetails opportunity;
    private TextView shortListMenuItem;
    private String volunteerEmail;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.id = getIntent().getStringExtra("android.intent.extra.TEXT");
        if (isUserLoggedIn()) {
            new GetVolunteerRecommendedOpportunity().execute(new Void[0]);
            new GetVolunteerEmail().execute(new Void[0]);
            return;
        }
        new GetViktorRecommendedOpportunity().execute(new Void[0]);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_opportunity, menu);
        MenuItem menuItemShort = menu.findItem(R.id.action_short);
        this.shortListMenuItem = (TextView) menuItemShort.getActionView();
        this.shortListMenuItem.setTypeface(TypefacesHelper.get(this, "fonts/icomoon.ttf"));
        this.shortListMenuItem.setOnClickListener(new C08151());
        menuItemShort.setActionView(this.shortListMenuItem);
        return true;
    }

    public boolean onPrepareOptionsMenu(Menu menu) {
        if (this.opportunity != null) {
            initItemMenuShortlist(this.shortListMenuItem, this.opportunity.isShortlisted());
        } else {
            MenuItem menuItemShort = menu.findItem(R.id.action_short);
            MenuItem menuItemShare = menu.findItem(R.id.action_share);
            menuItemShort.setVisible(false);
            menuItemShare.setVisible(false);
        }
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case 16908332:
                finishOpportunity(null);
                return true;
            case R.id.action_share:
                StringBuilder opportunityText = new StringBuilder();
                opportunityText.append("Check out this volunteering opportunity: ");
                opportunityText.append(this.opportunity.getName());
                opportunityText.append(System.getProperty("line.separator"));
                opportunityText.append(getResources().getString(R.string.app_opportunity_url));
                opportunityText.append(this.id);
                Intent myShareIntent = new Intent("android.intent.action.SEND");
                myShareIntent.setType("text/plain");
                myShareIntent.putExtra("android.intent.extra.TEXT", opportunityText.toString());
                myShareIntent.putExtra("android.intent.extra.SUBJECT", this.opportunity.getName());
                if (isUserLoggedIn()) {
                    myShareIntent.putExtra("android.intent.extra.CC", new String[]{this.volunteerEmail});
                }
                startActivity(Intent.createChooser(myShareIntent, null));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void finishOpportunity(View view) {
        setResult(-1, null);
        finish();
    }

    public void onMapReady(GoogleMap googleMap) {
    }

    private void initDataOpportunity(JSONObject result, boolean isAutenticatedSearch) {
        this.opportunity = new OpportunityDetails(result, isAutenticatedSearch);
        initItemMenuShortlist(this.shortListMenuItem, this.opportunity.isShortlisted());
        initMap();
        this.oppId.setText("ID " + this.id);
        this.oppName.setText(this.opportunity.getName());
        this.oppFor.setMovementMethod(LinkMovementMethod.getInstance());
        TextViewHelper.formatOppForSubtitle(this, this.oppFor, this.opportunity.getOrganisationName(), this.opportunity.getServiceFocus(), new C08162());
        this.oppLocation.setText(this.opportunity.getSuburb() + ", " + this.opportunity.getStateCode());
        this.oppDuration.setText(this.opportunity.getDuration());
        this.oppDesc.setText(this.opportunity.getDescription());
        this.oppTime.setText(this.opportunity.getTimeRequired());
        this.oppTraining.setText(this.opportunity.getTraining());
        this.oppReimbursement.setText(this.opportunity.getReimbursement());
        this.oppOrgaDes.setText(this.opportunity.getOrganisationDescription());
        this.oppInterests.setAdapter(new ArrayAdapter<String>(this, 0, this.opportunity.getInterests()) {
            public View getView(int position, View convertView, ViewGroup parent) {
                View tv = OpportunityActivity.this.getLayoutInflater().inflate(R.layout.item_bullet_text, parent, false);
                ((TextView) tv.findViewById(R.id.item_name)).setText((String) getItem(position));
                return tv;
            }
        });
        if (this.opportunity.isHasExpressedInterest()) {
            this.expressedInterest.setVisibility(View.VISIBLE);
            this.expressInterest.setVisibility(View.GONE);
            return;
        }
        this.expressedInterest.setVisibility(View.GONE);
        this.expressInterest.setVisibility(View.VISIBLE);
    }

    private void initMap() {
    }

    public void goToOrga(View view) {
        Intent intent = new Intent(this, OrganisationActivity.class);
        intent.putExtra("android.intent.extra.TEXT", Integer.toString(this.opportunity.getOrganisationId()));
        startActivity(intent);
    }

    public void goToExpressInterest(View view) {
        Intent intent = new Intent(this, ExpressInterestActivity.class);
        intent.putExtra(getResources().getString(R.string.EXTRA_OPP_ID), Integer.toString(this.opportunity.getId()));
        intent.putExtra(getResources().getString(R.string.EXTRA_OPP_TITLE), this.opportunity.getName());
        intent.putExtra(getResources().getString(R.string.EXTRA_ORGA_NAME), this.opportunity.getOrganisationName());
        intent.putExtra(getResources().getString(R.string.EXTRA_ORGA_CAUSE), this.opportunity.getOrgServiceFocus());
        startActivityForResult(intent, ActivityCode.EXPRESSEDINTEREST.getValue());
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == ActivityCode.EXPRESSEDINTEREST.getValue() && resultCode == -1) {
            this.opportunity.setHasExpressedInterest(true);
            this.expressedInterest.setVisibility(View.VISIBLE);
            this.expressInterest.setVisibility(View.GONE);
        }
    }

    public void goToGoogleMap(View view) {
        startActivity(new Intent("android.intent.action.VIEW", Uri.parse("http://maps.google.com/?q=" + this.opportunity.getLatitude() + "," + this.opportunity.getLongitude())));
    }

    private void initView() {
        setContentView((int) R.layout.activity_opportunity);
        this.opportunity = new OpportunityDetails();
        this.oppIcLoc = (TextView) findViewById(R.id.opp_ic_loc);
        this.oppIcTime = (TextView) findViewById(R.id.opp_ic_time);
        Typeface font = TypefacesHelper.get(this, "fonts/material_icon_font.ttf");
        this.oppIcLoc.setTypeface(font);
        this.oppIcTime.setTypeface(font);
        this.oppId = (TextView) findViewById(R.id.opp_id);
        this.oppName = (TextView) findViewById(R.id.opp_title);
        this.oppFor = (TextView) findViewById(R.id.opp_for);
        this.oppLocation = (TextView) findViewById(R.id.opp_location);
        this.oppDuration = (TextView) findViewById(R.id.opp_duration);
        this.oppDesc = (TextView) findViewById(R.id.opp_desc);
        this.oppTime = (TextView) findViewById(R.id.opp_time);
        this.oppInterests = (ListView) findViewById(R.id.list_interest);
        this.oppTraining = (TextView) findViewById(R.id.opp_training);
        this.oppReimbursement = (TextView) findViewById(R.id.opp_reimbursement);
        this.oppOrgaDes = (TextView) findViewById(R.id.opp_orga_desc);
        this.expressedInterest = (Button) findViewById(R.id.expressed_btn);
        this.expressInterest = (Button) findViewById(R.id.express_btn);
    }

    class C08151 implements OnClickListener {
        C08151() {
        }

        public void onClick(View v) {
            boolean result;
            if (OpportunityActivity.this.opportunity.isShortlisted()) {
                result = OpportunityActivity.this.DeleteFromShortlist(OpportunityActivity.this.opportunity.getId());
            } else {
                result = OpportunityActivity.this.AddToShortList(OpportunityActivity.this.opportunity.getId());
            }
            if (result) {
                OpportunityActivity.this.opportunity.setIsShortlisted(!OpportunityActivity.this.opportunity.isShortlisted());
                OpportunityActivity.this.initItemMenuShortlist(OpportunityActivity.this.shortListMenuItem, OpportunityActivity.this.opportunity.isShortlisted());
            }
        }
    }

    class C08162 extends CustomClickableSpan {
        C08162() {
        }

        public void onClick(View widget) {
            OpportunityActivity.this.goToOrga(widget);
        }
    }

    private class GetViktorRecommendedOpportunity extends ApiWCFRequest {
        public GetViktorRecommendedOpportunity() {
            super(HttpRequestType.GET, OpportunityActivity.this.getString(R.string.apiViktorURL), "Opportunities/" + OpportunityActivity.this.getResources().getString(R.string.apiViktorClientSecret) + "/" + OpportunityActivity.this.getResources().getString(R.string.apiViktorGetClientSecret) + "/" + OpportunityActivity.this.id);
        }

        protected void onPostExecuteBody(String result) {
            try {
                if (TextUtils.isEmpty(result)) {
                    OpportunityActivity.this.setContentView((int) R.layout.activity_opportunity_empty);
                    TextView tabIcon = (TextView) OpportunityActivity.this.findViewById(R.id.ic_tab_icon);
                    tabIcon.setTypeface(TypefacesHelper.get(tabIcon.getContext(), "fonts/material_icon_font.ttf"));
                } else {
                    OpportunityActivity.this.initView();
                    OpportunityActivity.this.initDataOpportunity(new JSONObject(new JSONObject("{\"Value\":" + result + "}").getString("Value")), false);
                }
                OpportunityActivity.this.setSupportActionBar((Toolbar) OpportunityActivity.this.findViewById(R.id.toolbar_no_tabs));
                ActionBar ab = OpportunityActivity.this.getSupportActionBar();
                ab.setDisplayHomeAsUpEnabled(true);
                ab.setHomeAsUpIndicator((int) R.mipmap.ic_arrow_back_white_24dp);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    private class GetVolunteerEmail extends ApiRestFullRequest {
        public GetVolunteerEmail() {
            super(HttpRequestType.GET, OpportunityActivity.this.getString(R.string.apiVolunteerURL), "api/volunteer/email", OpportunityActivity.this.getUserAccessToken());
        }

        protected void onPostExecuteBody(String result) {
            OpportunityActivity.this.volunteerEmail = result;
        }
    }

    private class GetVolunteerRecommendedOpportunity extends ApiRestFullRequest {
        public GetVolunteerRecommendedOpportunity() {
            super(HttpRequestType.GET, OpportunityActivity.this.getString(R.string.apiVolunteerURL), "api/opportunity/" + OpportunityActivity.this.id, OpportunityActivity.this.getUserAccessToken());
        }

        protected void onPostExecuteBody(String result) {
            try {
                if (TextUtils.isEmpty(result)) {
                    OpportunityActivity.this.setContentView((int) R.layout.activity_opportunity_empty);
                    TextView tabIcon = (TextView) OpportunityActivity.this.findViewById(R.id.ic_tab_icon);
                    tabIcon.setTypeface(TypefacesHelper.get(tabIcon.getContext(), "fonts/material_icon_font.ttf"));
                } else {
                    OpportunityActivity.this.initView();
                    OpportunityActivity.this.initDataOpportunity(new JSONObject(result), true);
                }
                OpportunityActivity.this.setSupportActionBar((Toolbar) OpportunityActivity.this.findViewById(R.id.toolbar_no_tabs));
                ActionBar ab = OpportunityActivity.this.getSupportActionBar();
                ab.setDisplayHomeAsUpEnabled(true);
                ab.setHomeAsUpIndicator((int) R.mipmap.ic_arrow_back_white_24dp);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}

package com.vowme.vol.app.activities;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.vowme.app.models.Enum;
import com.vowme.app.models.OpportunityItem;
import com.vowme.app.models.TimesheetItem;
import com.vowme.app.utilities.activities.BaseActivity;
import com.vowme.app.utilities.adapters.ViewPagerAdapter;
import com.vowme.app.utilities.api.ApiRestFullRequest;
import com.vowme.app.utilities.fragments.ItemListFragment;
import com.vowme.app.utilities.helpers.JSONHelper;
import com.vowme.app.utilities.helpers.TypefacesHelper;
import com.vowme.vol.app.R;
import com.vowme.vol.app.activities.opportunities.OpportunitiesFragment;
import com.vowme.vol.app.activities.opportunity.OpportunityActivity;
import com.vowme.vol.app.activities.profile.ProfileEditActivity;
import com.vowme.vol.app.activities.profile.ProfileHomeFragment;
import com.vowme.vol.app.activities.search.SearchFragment;
import com.vowme.vol.app.activities.search.SearchResultActivity;
import com.vowme.vol.app.activities.settings.SettingsHomeActivity;
import com.vowme.vol.app.activities.timesheet.LogHoursActivity;
import com.vowme.vol.app.activities.timesheet.TimesheetFragment;

import java.io.File;

public class MainActivity extends BaseActivity implements ItemListFragment.OnListFragmentInteractionListener, ItemListFragment.OnListTimesheetFragmentInteractionListener {
    private final int OPP_TAB_POSITION = 1;
    private final int PROFILE_TAB_POSITION = 2;
    private final int TIME_TAB_POSITION = 3;
    private final String profileFilename = "/MyVolunteerProfile.pdf";
    private TabLayout tabLayout;
    private int tabPosition;
    private Toolbar toolbar;
    private ViewPager viewPager;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((int) R.layout.toolbar_base);
        this.toolbar = (Toolbar) findViewById(R.id.toolbar_base);

        ActionBar ab = getSupportActionBar();
        if (ab != null) {
            ab.setDisplayHomeAsUpEnabled(false);
        }
        this.viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(this.viewPager);
        this.viewPager.setOffscreenPageLimit(4);
        this.tabLayout = (TabLayout) findViewById(R.id.tabs);
        this.tabLayout.setupWithViewPager(this.viewPager);
        setupTabIcons();
        this.viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(this.tabLayout));
        this.tabLayout.setOnTabSelectedListener(new EmptyTab());
    }

    public boolean onPrepareOptionsMenu(Menu menu) {
        MenuItem menuItemSettings = menu.findItem(R.id.action_settings);
        MenuItem menuItemSearch = menu.findItem(R.id.action_search);
        MenuItem menuItemAdd = menu.findItem(R.id.action_add);
        switch (this.tabPosition) {
            case 0:
                menuItemSearch.setVisible(true);
                menuItemSettings.setVisible(false);
                menuItemAdd.setVisible(false);
                break;
            case 2:
                menuItemSettings.setVisible(true);
                menuItemSearch.setVisible(false);
                menuItemAdd.setVisible(false);
                break;
            case 3:
                menuItemSettings.setVisible(false);
                menuItemSearch.setVisible(false);
                if (!isUserLoggedIn()) {
                    menuItemAdd.setVisible(false);
                    break;
                }
                menuItemAdd.setVisible(true);
                break;
            default:
                menuItemSettings.setVisible(false);
                menuItemSearch.setVisible(false);
                menuItemAdd.setVisible(false);
                break;
        }
        return true;
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_filter:
                goToAdjustments(null);
                return true;
            case R.id.action_settings:
                startActivity(new Intent(this, SettingsHomeActivity.class));
                return true;
            case R.id.action_add:
                TimesheetFragment timesheetFragment = (TimesheetFragment) ((ViewPagerAdapter) this.viewPager.getAdapter()).getItem(3);
                Intent intentBis = new Intent(this, LogHoursActivity.class);
                intentBis.putExtra(getResources().getString(R.string.EXTRA_MODEL_DETAILS), JSONHelper.ToJSONArray(timesheetFragment.opportunitiesToLog).toString());
                intentBis.putExtra("android.intent.extra.TEXT", false);
                startActivityForResult(intentBis, Enum.ActivityCode.LOGHOURS.getValue());
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        ViewPagerAdapter adapter = (ViewPagerAdapter) this.viewPager.getAdapter();
        if (requestCode == Enum.ActivityCode.PROFILEEDIT.getValue()) {
            if (resultCode == -1) {
                ProfileHomeFragment profileFragment = (ProfileHomeFragment) adapter.getItem(2);
                if (getUserNeedUpdateProfile()) {
                    putUserNeedUpdateProfile(false);
                    profileFragment.refresh();
                } else {
                    String profileDetails = data.getStringExtra(getResources().getString(R.string.EXTRA_MODEL_DETAILS));
                    if (!TextUtils.isEmpty(profileDetails)) {
                        profileFragment.setProfileDetails(profileDetails);
                        profileFragment.updateView();
                    }
                }
                invalidateOptionsMenu();
            }
        } else if (requestCode == Enum.ActivityCode.ADJUSTMENT.getValue()) {
            if (resultCode == -1) {
                OpportunitiesFragment oppFragment = (OpportunitiesFragment) adapter.getItem(1);
                if (!isUserLoggedIn()) {
                    oppFragment.getRecommendedListFragment().updateSearchParameter();
                }
                oppFragment.getRecommendedListFragment().refresh();
            }
        } else if (requestCode == Enum.ActivityCode.OPPORTUNITY.getValue()) {
            if (resultCode == -1) {
                OpportunitiesFragment oppFragment = (OpportunitiesFragment) adapter.getItem(1);
                if (getUserIsFromExpressInterest()) {
                    putUserIsFromExpressInterest(false);
                    setupViewPager(this.viewPager);
                    this.viewPager.setCurrentItem(1);
                } else {
                    if (getUserNeedUpdateRecommended()) {
                        oppFragment.getRecommendedListFragment().refresh();
                    }
                    if (getUserNeedUpdateShortlist()) {
                        oppFragment.getShortListFragment().refresh();
                    }
                }
                putUserNeedUpdateRecommended(false);
                putUserNeedUpdateShortlist(false);
            }
        } else if (requestCode == Enum.ActivityCode.LOGHOURS.getValue() && resultCode == -1) {
            ((TimesheetFragment) adapter.getItem(3)).refresh();
        }
    }

    public void onListFragmentInteraction(OpportunityItem item) {
        Intent intent = new Intent(this, OpportunityActivity.class);
        intent.putExtra("android.intent.extra.TEXT", Integer.toString(item.getId()));
        startActivityForResult(intent, Enum.ActivityCode.OPPORTUNITY.getValue());
    }

    public void goToOppNearMe(View v) {
        Intent searchResult = new Intent(this, SearchResultActivity.class);
        searchResult.putExtra(getResources().getString(R.string.EXTRA_IS_RADIUS_SEARCH), true);
        startActivityForResult(searchResult, Enum.ActivityCode.RESULTFROMSEARCH.getValue());
    }

    public void onEditProfileClick(View v) {
        ProfileHomeFragment profileFragment = (ProfileHomeFragment) ((ViewPagerAdapter) this.viewPager.getAdapter()).getItem(2);
        Intent intent = new Intent(this, ProfileEditActivity.class);
        intent.putExtra(getResources().getString(R.string.EXTRA_MODEL_DETAILS), profileFragment.getProfileDetails());
        startActivityForResult(intent, Enum.ActivityCode.PROFILEEDIT.getValue());
    }

    public void onDownloadProfileClick(View v) {
        new GetVolunteerProfilePdf().execute(new Void[0]);
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new SearchFragment(), getResources().getString(R.string.tab_search));
        adapter.addFragment(new OpportunitiesFragment(), getResources().getString(R.string.tab_opportunities));
        adapter.addFragment(new ProfileHomeFragment(), getResources().getString(R.string.tab_profile));
        adapter.addFragment(new TimesheetFragment(), getResources().getString(R.string.tab_timesheet));
        viewPager.setAdapter(adapter);
    }

    private void setupTabIcons() {
        Typeface font = TypefacesHelper.get(this, "fonts/material_icon_font.ttf");
        TextView tabSearch = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tab_search, null);
        tabSearch.setTypeface(font);
        this.tabLayout.getTabAt(0).setCustomView(tabSearch);
        TextView tabOpportunities = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tab_opportunities, null);
        tabOpportunities.setTypeface(font);
        this.tabLayout.getTabAt(1).setCustomView(tabOpportunities);
        this.tabLayout.getTabAt(1).select();
        tabOpportunities.setSelected(true);
        this.tabPosition = 1;
        TextView tabTimesheet = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tab_timesheet, null);
        tabTimesheet.setTypeface(font);
        this.tabLayout.getTabAt(3).setCustomView(tabTimesheet);
        TextView tabProfile = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tab_profil, null);
        tabProfile.setTypeface(TypefacesHelper.get(this, "fonts/icomoon.ttf"));
        this.tabLayout.getTabAt(2).setCustomView(tabProfile);
    }

    private void updateSelectedTab(int newPositionTab) {
        CharSequence title;
        this.tabPosition = newPositionTab;
        this.viewPager.setCurrentItem(this.tabPosition);
        String title2 = "";
        switch (this.tabPosition) {
            case 0:
                title = "";
                break;
            case 1:
                title = getResources().getString(R.string.tab_opportunities);
                break;
            case 2:
                title = getResources().getString(R.string.tab_profile);
                if (getUserNeedUpdateProfile()) {
                    putUserNeedUpdateProfile(false);
                    ((ProfileHomeFragment) ((ViewPagerAdapter) this.viewPager.getAdapter()).getItem(2)).refresh();
                    break;
                }
                break;
            case 3:
                title = getResources().getString(R.string.tab_timesheet);
                break;
            default:
                this.tabPosition = 1;
                title = getResources().getString(R.string.tab_opportunities);
                break;
        }
        this.toolbar.setTitle(title);
        invalidateOptionsMenu();
    }

    public void goToRecommendedTab(View view) {
         ((OpportunitiesFragment) ((ViewPagerAdapter) this.viewPager.getAdapter()).getItem(1)).updateSelectedTab(0);
    }

    public void onListFragmentInteraction(TimesheetItem item) {
        Intent intent = new Intent(this, LogHoursActivity.class);
        intent.putExtra(getResources().getString(R.string.EXTRA_MODEL_DETAILS), item.toJsonObject().toString());
        intent.putExtra("android.intent.extra.TEXT", true);
        startActivityForResult(intent, Enum.ActivityCode.LOGHOURS.getValue());
    }

    class EmptyTab implements TabLayout.OnTabSelectedListener {
        EmptyTab() {
        }

        public void onTabSelected(TabLayout.Tab tab) {
            MainActivity.this.updateSelectedTab(tab.getPosition());
        }

        public void onTabUnselected(TabLayout.Tab tab) {
        }

        public void onTabReselected(TabLayout.Tab tab) {
        }
    }

    private class GetVolunteerProfilePdf extends ApiRestFullRequest {
        public GetVolunteerProfilePdf() {
            super(Enum.HttpRequestType.GET, MainActivity.this.getString(R.string.apiVolunteerURL), "api/volunteer/pdf", MainActivity.this.getUserAccessToken(), true, "/MyVolunteerProfile.pdf", MainActivity.this.getExternalCacheDir());
        }

        protected void onPostExecuteBody(String result) {
            if (TextUtils.isEmpty(result)) {
                File file = new File(MainActivity.this.getExternalCacheDir(), "/MyVolunteerProfile.pdf");
                if (file != null) {
                    Intent target = new Intent("android.intent.action.VIEW");
                    target.setDataAndType(Uri.fromFile(file), "application/pdf");
                    try {
                        MainActivity.this.startActivity(Intent.createChooser(target, "Open File"));
                    } catch (ActivityNotFoundException e) {
                    }
                }
            }
        }
    }
}

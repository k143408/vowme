package com.vowme.vol.app.activities.search;

import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.design.widget.TabLayout.OnTabSelectedListener;
import android.support.design.widget.TabLayout.Tab;
import android.support.design.widget.TabLayout.TabLayoutOnPageChangeListener;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog.Builder;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.vowme.app.models.Enum.ActivityCode;
import com.vowme.app.models.SavedSearchOpportunitiesItem;
import com.vowme.app.utilities.activities.BaseActivity;
import com.vowme.app.utilities.adapters.ViewPagerAdapter;
import com.vowme.app.utilities.helpers.TypefacesHelper;
import com.vowme.vol.app.R;

public class SavedSearchActivity extends BaseActivity implements RecentsFragment.OnRecentListFragmentInteractionListener, BookmarksFragment.OnBookmarkListFragmentInteractionListener {
    private boolean isEditMode;
    private TabLayout tabLayout;
    private int tabPosition;
    private Toolbar toolbar;
    private ViewPager viewPager;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((int) R.layout.activity_saved_search);
        this.toolbar = (Toolbar) findViewById(R.id.toolbar_saved_searches);
        setSupportActionBar(this.toolbar);
        ActionBar ab = getSupportActionBar();
        if (ab != null) {
            ab.setDisplayHomeAsUpEnabled(true);
            ab.setHomeAsUpIndicator((int) R.mipmap.ic_close_white_24dp);
        }
        this.viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(this.viewPager);
        this.tabLayout = (TabLayout) findViewById(R.id.tabs);
        this.tabLayout.setupWithViewPager(this.viewPager);
        setupTabIcons();
        this.viewPager.addOnPageChangeListener(new TabLayoutOnPageChangeListener(this.tabLayout));
        this.tabLayout.setOnTabSelectedListener(new C08321());
        this.isEditMode = false;
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_saved_searches, menu);
        return true;
    }

    public boolean onPrepareOptionsMenu(Menu menu) {
        MenuItem menuItemEdit = menu.findItem(R.id.action_edit);
        MenuItem menuItemClear = menu.findItem(R.id.action_clear);
        initTitleMenuITem(menuItemEdit);
        switch (this.tabPosition) {
            case 0:
                menuItemEdit.setVisible(true);
                menuItemClear.setVisible(false);
                break;
            case 1:
                menuItemEdit.setVisible(false);
                menuItemClear.setVisible(true);
                break;
            default:
                menuItemEdit.setVisible(true);
                menuItemClear.setVisible(false);
                break;
        }
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case 16908332:
                finish();
                return true;
            case R.id.action_clear:
                new Builder(this).setMessage((CharSequence) "Clear all recents?").setCancelable(true).setPositiveButton((CharSequence) "CLEAR", new C08343()).setNegativeButton((CharSequence) "Cancel", new C08332()).show();
                return true;
            case R.id.action_edit:
                boolean z;
                if (this.isEditMode) {
                    z = false;
                } else {
                    z = true;
                }
                this.isEditMode = z;
                initTitleMenuITem(item);
                ((BookmarksFragment) ((ViewPagerAdapter) this.viewPager.getAdapter()).getItem(0)).showReorderIcon(this.isEditMode);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void initTitleMenuITem(MenuItem item) {
        if (this.isEditMode) {
            item.setTitle("DONE");
        } else {
            item.setTitle("EDIT");
        }
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new BookmarksFragment(), getResources().getString(R.string.tab_bookmark));
        adapter.addFragment(new RecentsFragment(), getResources().getString(R.string.tab_recent));
        viewPager.setAdapter(adapter);
    }

    private void setupTabIcons() {
        Typeface font = TypefacesHelper.get(this, "fonts/material_icon_font.ttf");
        View tabBookmark = (LinearLayout) LayoutInflater.from(this).inflate(R.layout.custom_tab_bookmark, null);
        ((TextView) tabBookmark.findViewById(R.id.tab_bookmark)).setTypeface(font);
        this.tabLayout.getTabAt(0).setCustomView(tabBookmark);
        this.tabLayout.getTabAt(0).select();
        tabBookmark.setSelected(true);
        View tabRecent = (LinearLayout) LayoutInflater.from(this).inflate(R.layout.custom_tab_recent, null);
        ((TextView) tabRecent.findViewById(R.id.tab_recent)).setTypeface(font);
        this.tabLayout.getTabAt(1).setCustomView(tabRecent);
    }

    public void onRecentListFragmentInteraction(SavedSearchOpportunitiesItem item) {
        goToResult(item);
    }

    public void onBookmarkListFragmentInteraction(SavedSearchOpportunitiesItem item) {
        if (!this.isEditMode) {
            goToResult(item);
        }
    }

    private void goToResult(SavedSearchOpportunitiesItem item) {
        Intent searchResult = new Intent(this, SearchResultActivity.class);
        searchResult.putExtra(getResources().getString(R.string.EXTRA_SAVED_SEARCH), item.toJsonObject().toString());
        searchResult.putExtra(getResources().getString(R.string.EXTRA_IS_SAVED_SEARCH), true);
        startActivityForResult(searchResult, ActivityCode.RESULTFROMBOOKMARK.getValue());
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == ActivityCode.RESULTFROMBOOKMARK.getValue() && resultCode == -1) {
            finish();
        }
    }

    class C08321 implements OnTabSelectedListener {
        C08321() {
        }

        public void onTabSelected(Tab tab) {
            SavedSearchActivity.this.viewPager.setCurrentItem(tab.getPosition());
            CharSequence title = SavedSearchActivity.this.getResources().getString(R.string.tab_bookmark);
            SavedSearchActivity.this.tabPosition = tab.getPosition();
            switch (tab.getPosition()) {
                case 0:
                    title = SavedSearchActivity.this.getResources().getString(R.string.tab_bookmark);
                    break;
                case 1:
                    title = SavedSearchActivity.this.getResources().getString(R.string.tab_recent);
                    break;
                default:
                    SavedSearchActivity.this.tabPosition = 0;
                    break;
            }
            SavedSearchActivity.this.toolbar.setTitle(title);
            SavedSearchActivity.this.invalidateOptionsMenu();
        }

        public void onTabUnselected(Tab tab) {
        }

        public void onTabReselected(Tab tab) {
        }
    }

    class C08332 implements OnClickListener {
        C08332() {
        }

        public void onClick(DialogInterface dialog, int which) {
        }
    }

    class C08343 implements OnClickListener {
        C08343() {
        }

        public void onClick(DialogInterface dialog, int which) {
            SavedSearchActivity.this.clearRecents();
            ((RecentsFragment) ((ViewPagerAdapter) SavedSearchActivity.this.viewPager.getAdapter()).getItem(1)).getData();
        }
    }
}

package com.vowme.vol.app.activities.start;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.vowme.app.models.lookUp.Lookup;
import com.vowme.app.utilities.activities.BaseActivity;
import com.vowme.app.utilities.adapters.ViewPagerAdapter;
import com.vowme.app.utilities.helpers.DefaultDataHelper;
import com.vowme.vol.app.R;
import com.vowme.vol.app.activities.MainActivity;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class StartStepsActivity extends BaseActivity {
    private ViewPagerAdapter adapter;
    private ImageView[] dotSteps;
    private StartLocationFragment locationFragment;
    private MenuItem menuItemDone;
    private MenuItem menuItemNext;
    private int previousPosition;
    private int tabPosition = 0;
    private ViewPager viewPager;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((int) R.layout.activity_start_steps);
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar_stepper));
        ActionBar ab = getSupportActionBar();
        if (ab != null) {
            ab.setDisplayHomeAsUpEnabled(true);
            ab.setHomeAsUpIndicator((int) R.mipmap.ic_arrow_back_white_24dp);
            View v = getLayoutInflater().inflate(R.layout.custom_dot_step, null);
            this.dotSteps = new ImageView[]{
                                    (ImageView) v.findViewById(R.id.step_1),
                                    (ImageView) v.findViewById(R.id.step_2),
                                    (ImageView) v.findViewById(R.id.step_3)
                                };
            ab.setCustomView(v);
            ab.setDisplayShowCustomEnabled(true);
        }
        this.viewPager = (ViewPager) findViewById(R.id.viewpager);
        this.dotSteps[0].setSelected(true);
        this.dotSteps[1].setSelected(false);
        this.dotSteps[2].setSelected(false);
        this.viewPager.setCurrentItem(this.tabPosition);
        this.viewPager.addOnPageChangeListener(new C08651());
        this.viewPager.setOffscreenPageLimit(3);
        setupViewPager(this.viewPager);
        this.previousPosition = 0;
    }

    private void setupViewPager(ViewPager viewPager) {
        this.adapter = new ViewPagerAdapter(getSupportFragmentManager());
        this.adapter.addFragment(new StartCausesFragment(), "");
        this.adapter.addFragment(new StartInterestsFragment(), "");
        this.locationFragment = new StartLocationFragment();
        this.adapter.addFragment(this.locationFragment, "");
        viewPager.setAdapter(this.adapter);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_dot_steps, menu);
        this.menuItemNext = menu.findItem(R.id.action_next);
        this.menuItemDone = menu.findItem(R.id.action_done);
        this.menuItemNext.setVisible(true);
        this.menuItemDone.setVisible(false);
        this.locationFragment.setUpMenuItemDone(this.menuItemDone);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case 16908332:
                actionPrevious();
                return true;
            case R.id.action_next:
                this.tabPosition++;
                this.viewPager.setCurrentItem(this.tabPosition);
                return true;
            case R.id.action_done:
                final String location = this.locationFragment.getLocation();
                List<String> list = new ArrayList<String>();
                list.add(location);
                putLocationFieldNames(list);
                startActivity(new Intent(this, MainActivity.class));
                putUserHaveDoneWizard(true);
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void actionNext() {
        Set<String> names = new HashSet<>();
        this.dotSteps[this.tabPosition - 1].setSelected(false);
        this.dotSteps[this.tabPosition].setSelected(true);
        List<Integer> ids;
        switch (this.tabPosition) {
            case 1:
                ids = ((StartCausesFragment) this.adapter.getItem(this.tabPosition - 1)).getChecked();
                clearUserCausesData();
                for (Lookup lookup : DefaultDataHelper.getCauses()) {
                    if (ids.contains(Integer.valueOf(lookup.getId()))) {
                        names.add(lookup.getName());
                    }
                }
                putUserCausesData(ids, new ArrayList<String>(names));
                return;
            case 2:
                ids = ((StartInterestsFragment) this.adapter.getItem(this.tabPosition - 1)).getChecked();
                clearUserInterestsData();
                for (Lookup lookup2 : DefaultDataHelper.getInterests()) {
                    if (ids.contains(Integer.valueOf(lookup2.getId()))) {
                        names.add(lookup2.getName());
                    }
                }
                putUserInterestsData(ids, new ArrayList<String>(names));
                this.menuItemNext.setVisible(false);
                this.menuItemDone.setVisible(true);
                return;
            default:
                return;
        }
    }

    private void actionPrevious() {
        this.menuItemNext.setVisible(true);
        this.menuItemDone.setVisible(false);
        if (this.tabPosition == 0) {
            finish();
            return;
        }
        this.dotSteps[this.tabPosition].setSelected(false);
        this.tabPosition--;
        this.dotSteps[this.tabPosition].setSelected(true);
        this.viewPager.setCurrentItem(this.tabPosition);
    }

    class C08651 implements OnPageChangeListener {
        C08651() {
        }

        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        }

        public void onPageSelected(int position) {
            StartStepsActivity.this.tabPosition = position;
            if (StartStepsActivity.this.previousPosition < position) {
                for (int i = 0; i < position - StartStepsActivity.this.previousPosition; i++) {
                    StartStepsActivity.this.actionNext();
                }
            }
            StartStepsActivity.this.previousPosition = position;
        }
        public void onPageScrollStateChanged(int state) {
        }
    }
}

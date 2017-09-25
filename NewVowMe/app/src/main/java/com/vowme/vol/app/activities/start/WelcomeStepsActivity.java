package com.vowme.vol.app.activities.start;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.widget.ImageView;

import com.vowme.app.utilities.activities.BaseActivity;
import com.vowme.app.utilities.adapters.ViewPagerAdapter;
import com.vowme.vol.app.R;
import com.vowme.vol.app.activities.MainActivity;

import java.util.ArrayList;
import java.util.List;

public class WelcomeStepsActivity extends BaseActivity {
    private ViewPagerAdapter adapter;
    private ImageView[] dotSteps;
    private int previousPosition;
    private int tabPosition = 0;
    private ViewPager viewPager;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        putUserHasSeenWelcomeScreen(true);
        setContentView((int) R.layout.activity_welcome_steps);
        ((ImageView) findViewById(R.id.custom_dot_step).findViewById(R.id.step_3)).setVisibility(View.GONE);
        View v = getLayoutInflater().inflate(R.layout.custom_dot_step, null);
        this.dotSteps = new ImageView[]{(ImageView) v.findViewById(R.id.step_1), (ImageView) v.findViewById(R.id.step_2)};
        this.viewPager = (ViewPager) findViewById(R.id.viewpager);
        this.dotSteps[0].setSelected(true);
        this.dotSteps[1].setSelected(false);
        this.viewPager.setCurrentItem(this.tabPosition);
        this.viewPager.addOnPageChangeListener(new C08671());
        this.viewPager.setOffscreenPageLimit(2);
        setupViewPager(this.viewPager);
        this.previousPosition = 0;
    }

    private void setupViewPager(ViewPager viewPager) {
        this.adapter = new ViewPagerAdapter(getSupportFragmentManager());
        this.adapter.addFragment(new WelcomeFragment(), "");
        this.adapter.addFragment(new WhatsNewFragment(), "");
        viewPager.setAdapter(this.adapter);
    }

    public void goToMain(View view) {
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }

    private void actionNext() {
        List<String> names = new ArrayList();
        this.dotSteps[this.tabPosition - 1].setSelected(false);
        this.dotSteps[this.tabPosition].setSelected(true);
    }

    private void actionPrevious() {
        if (this.tabPosition == 0) {
            finish();
            return;
        }
        this.dotSteps[this.tabPosition].setSelected(false);
        this.tabPosition--;
        this.dotSteps[this.tabPosition].setSelected(true);
        this.viewPager.setCurrentItem(this.tabPosition);
    }

    class C08671 implements OnPageChangeListener {
        C08671() {
        }

        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        }

        public void onPageSelected(int position) {
            WelcomeStepsActivity.this.tabPosition = position;
            if (WelcomeStepsActivity.this.previousPosition < position) {
                for (int i = 0; i < position - WelcomeStepsActivity.this.previousPosition; i++) {
                    WelcomeStepsActivity.this.actionNext();
                }
            }
            WelcomeStepsActivity.this.previousPosition = position;
        }

        public void onPageScrollStateChanged(int state) {
        }
    }
}

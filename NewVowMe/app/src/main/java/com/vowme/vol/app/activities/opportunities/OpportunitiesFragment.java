package com.vowme.vol.app.activities.opportunities;

import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.design.widget.TabLayout.OnTabSelectedListener;
import android.support.design.widget.TabLayout.Tab;
import android.support.design.widget.TabLayout.TabLayoutOnPageChangeListener;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.vowme.app.utilities.adapters.ViewPagerAdapter;
import com.vowme.app.utilities.fragments.BaseFragment;
import com.vowme.vol.app.R;
import com.vowme.vol.app.activities.expressOfInterest.ExpressOfInterestListFragment;
import com.vowme.vol.app.activities.recommended.RecommendedListFragment;
import com.vowme.vol.app.activities.shortlist.ShortListFragment;

public class OpportunitiesFragment extends BaseFragment {
    private final int recommendedTabPosition = 0;
    private final int shortlistTabPosition = 1;
    private ViewGroup container;
    private TabLayout tabLayoutOpp;
    private int tabPositionOpp;
    private ViewPager viewPagerOpp;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        this.container = container;
        View view = inflater.inflate(R.layout.fragment_opportunities, container, false);
        this.viewPagerOpp = (ViewPager) view.findViewById(R.id.opportunities_viewpager);
        setupViewPager(this.viewPagerOpp);
        this.tabLayoutOpp = (TabLayout) view.findViewById(R.id.opportunities_tabs);
        this.tabLayoutOpp.setupWithViewPager(this.viewPagerOpp);
        setupTabIcons();
        this.viewPagerOpp.addOnPageChangeListener(new TabLayoutOnPageChangeListener(this.tabLayoutOpp));
        this.tabLayoutOpp.setOnTabSelectedListener(new OpportunitiesFragmentTab());
        return view;
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapterOpp = new ViewPagerAdapter(getChildFragmentManager());
        adapterOpp.addFragment(new RecommendedListFragment(), getResources().getString(R.string.tab_recommended));
        adapterOpp.addFragment(new ShortListFragment(), getResources().getString(R.string.tab_shortlist));
        adapterOpp.addFragment(new ExpressOfInterestListFragment(), getResources().getString(R.string.tab_eoi));
        viewPager.setAdapter(adapterOpp);
    }

    private void setupTabIcons() {
        Typeface font = Typeface.createFromAsset(getBaseActivity().getAssets(), "fonts/material_icon_font.ttf");
        if (font != null) {
            LinearLayout tabRecommended = (LinearLayout) LayoutInflater.from(getBaseActivity()).inflate(R.layout.custom_tab_recommended, null);
            ((TextView) tabRecommended.findViewById(R.id.tab_recommended)).setTypeface(font);
            this.tabLayoutOpp.getTabAt(recommendedTabPosition).setCustomView(tabRecommended);
            this.tabLayoutOpp.getTabAt(recommendedTabPosition).select();
            tabRecommended.setSelected(true);
            LinearLayout tabShortlist = (LinearLayout) LayoutInflater.from(getBaseActivity()).inflate(R.layout.custom_tab_shortlist, null);
            ((TextView) tabShortlist.findViewById(R.id.tab_shortlist)).setTypeface(font);
            this.tabLayoutOpp.getTabAt(shortlistTabPosition).setCustomView(tabShortlist);
            LinearLayout tabExpressOfInterest = (LinearLayout) LayoutInflater.from(getBaseActivity()).inflate(R.layout.custom_tab_express_of_interest, null);
            ((TextView) tabExpressOfInterest.findViewById(R.id.tab_eoi)).setTypeface(font);
            this.tabLayoutOpp.getTabAt(2).setCustomView(tabExpressOfInterest);
        }
    }

    public void onAttach(Context context) {
        super.onAttach(context);
    }

    public void onDetach() {
        super.onDetach();
    }

    public void updateSelectedTab(int newPositionTab) {
        int previousTab = this.tabPositionOpp;
        this.tabPositionOpp = newPositionTab;
        this.viewPagerOpp.setCurrentItem(this.tabPositionOpp);
        switch (this.tabPositionOpp) {
            case 0:
                if (getBaseActivity().getUserNeedUpdateRecommended() && previousTab != 2) {
                    getRecommendedListFragment().refresh();
                }
                getBaseActivity().putUserNeedUpdateRecommended(false);
                return;
            case 1:
                if (getBaseActivity().getUserNeedUpdateShortlist()) {
                    getShortListFragment().refresh();
                }
                getBaseActivity().putUserNeedUpdateShortlist(false);
                return;
            default:
                return;
        }
    }

    public RecommendedListFragment getRecommendedListFragment() {
        return (RecommendedListFragment) ((ViewPagerAdapter) this.viewPagerOpp.getAdapter()).instantiateItem(this.container, recommendedTabPosition);
    }

    public ShortListFragment getShortListFragment() {
        return (ShortListFragment) ((ViewPagerAdapter) this.viewPagerOpp.getAdapter()).instantiateItem(this.container, shortlistTabPosition);
    }

    class OpportunitiesFragmentTab implements OnTabSelectedListener {
        OpportunitiesFragmentTab() {
        }

        public void onTabSelected(Tab tab) {
            OpportunitiesFragment.this.updateSelectedTab(tab.getPosition());
        }

        public void onTabUnselected(Tab tab) {
        }

        public void onTabReselected(Tab tab) {
        }
    }
}

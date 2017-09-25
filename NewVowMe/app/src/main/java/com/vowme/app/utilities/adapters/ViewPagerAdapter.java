package com.vowme.app.utilities.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class ViewPagerAdapter extends FragmentStatePagerAdapter {
    private final List<Fragment> mFragmentList = new ArrayList();
    private final List<String> mFragmentTitleList = new ArrayList();

    public ViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    public Fragment getItem(int position) {
        return (Fragment) this.mFragmentList.get(position);
    }

    public int getCount() {
        return this.mFragmentList.size();
    }

    public CharSequence getPageTitle(int position) {
        return null;
    }

    public void addFragment(Fragment fragment, String title) {
        this.mFragmentList.add(fragment);
        this.mFragmentTitleList.add(title);
    }
}

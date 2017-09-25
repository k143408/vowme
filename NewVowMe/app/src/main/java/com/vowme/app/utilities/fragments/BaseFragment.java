package com.vowme.app.utilities.fragments;

import android.support.v4.app.Fragment;

import com.vowme.app.utilities.activities.BaseActivity;


public class BaseFragment extends Fragment {
    protected BaseActivity getBaseActivity() {
        return (BaseActivity) getActivity();
    }
}

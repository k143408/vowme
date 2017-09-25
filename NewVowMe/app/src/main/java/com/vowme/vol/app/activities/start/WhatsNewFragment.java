package com.vowme.vol.app.activities.start;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.vowme.app.utilities.fragments.BaseFragment;
import com.vowme.vol.app.R;

public class WhatsNewFragment extends BaseFragment {
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_whatsnew, container, false);
    }

    public void onAttach(Context context) {
        super.onAttach(context);
    }

    public void onDetach() {
        super.onDetach();
    }
}



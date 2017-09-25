package com.vowme.app.utilities.fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.vowme.app.models.OpportunityItem;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

public class OpportunityListFragment extends ItemListFragment {
    protected OnListFragmentInteractionListener mListener;
    protected List<OpportunityItem> mValues = new ArrayList();

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    protected void getBodyData() {
    }

    protected JSONArray getJSONArrayOpportunities(String result) throws JSONException {
        return null;
    }

    protected void extraOnPostExecuteBodyAction(JSONArray opportunities) {
    }

    protected int getBodyAddItems(JSONArray opportunities) {
        int i = 0;
        while (i < opportunities.length()) {
            try {
                if (!this.isAuthenticated) {
                    OpportunityItem item = new OpportunityItem(opportunities.getJSONObject(i), this.isAuthenticated);
                    if (getBaseActivity() != null) {
                        item.setIsShortlisted(getBaseActivity().isShortlisted(item.getId()));
                    }
                    this.mValues.add(item);
                } else if (this.isAuthenticated) {
                    this.mValues.add(new OpportunityItem(opportunities.getJSONObject(i), this.isAuthenticated));
                }
            } catch (JSONException e) {
                e.printStackTrace();
                dismissProgress();
            }
            i++;
        }
        return i;
    }

    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnListFragmentInteractionListener) {
            this.mListener = (OnListFragmentInteractionListener) context;
            return;
        }
        throw new RuntimeException(context.toString() + " must implement OnListFragmentInteractionListener");
    }

    public void onDetach() {
        super.onDetach();
        this.mListener = null;
    }
}

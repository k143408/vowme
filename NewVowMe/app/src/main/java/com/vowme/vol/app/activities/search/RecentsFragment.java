package com.vowme.vol.app.activities.search;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.vowme.app.models.SavedSearchOpportunitiesItem;
import com.vowme.app.utilities.adapters.SavedSearchesRecyclerViewAdapter;
import com.vowme.app.utilities.customWidgets.DividerItemDecoration;
import com.vowme.app.utilities.fragments.BaseFragment;
import com.vowme.vol.app.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class RecentsFragment extends BaseFragment {
    protected OnRecentListFragmentInteractionListener mListener;
    private SavedSearchesRecyclerViewAdapter mAdapter;
    private List<SavedSearchOpportunitiesItem> mItemValues;
    private LinearLayoutManager mLayoutManager;
    private View mReciclerview;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.mItemValues = new ArrayList();
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        this.mReciclerview = inflater.inflate(R.layout.fragment_recents, container, false);
        if (this.mReciclerview instanceof RecyclerView) {
            RecyclerView recyclerView = (RecyclerView) this.mReciclerview;
            recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), 1));
            recyclerView.setHasFixedSize(true);
            this.mLayoutManager = new LinearLayoutManager(this.mReciclerview.getContext());
            recyclerView.setLayoutManager(this.mLayoutManager);
            this.mAdapter = new SavedSearchesRecyclerViewAdapter(this.mItemValues, getBaseActivity(), this.mListener);
            recyclerView.setAdapter(this.mAdapter);
            getData();
        }
        return this.mReciclerview;
    }

    public void getData() {
        List<String> recents = getBaseActivity().getRecentsSearch();
        this.mItemValues.clear();
        for (String recent : recents) {
            try {
                SavedSearchOpportunitiesItem potentialSavedSearch = new SavedSearchOpportunitiesItem(new JSONObject(recent));
                potentialSavedSearch.setFromRecents(true);
                this.mItemValues.add(potentialSavedSearch);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        this.mAdapter.notifyDataSetChanged();
    }

    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnRecentListFragmentInteractionListener) {
            this.mListener = (OnRecentListFragmentInteractionListener) context;
            return;
        }
        throw new RuntimeException(context.toString() + " must implement OnListFragmentInteractionListener");
    }

    public void onDetach() {
        super.onDetach();
        this.mListener = null;
    }

    public interface OnRecentListFragmentInteractionListener {
        void onRecentListFragmentInteraction(SavedSearchOpportunitiesItem savedSearchOpportunitiesItem);
    }
}

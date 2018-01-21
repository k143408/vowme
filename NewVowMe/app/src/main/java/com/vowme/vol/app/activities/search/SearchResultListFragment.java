package com.vowme.vol.app.activities.search;

import android.content.Context;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.vowme.app.models.Enum.HttpRequestType;
import com.vowme.app.models.Enum.ListViewType;
import com.vowme.app.models.SavedSearchOpportunitiesItem;
import com.vowme.app.models.api.SearchOpportunitiesParameter;
import com.vowme.app.utilities.adapters.OpportunityItemRecyclerViewAdapter;
import com.vowme.app.utilities.api.ApiWCFRequest;
import com.vowme.app.utilities.fragments.OpportunityListFragment;
import com.vowme.vol.app.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class SearchResultListFragment extends OpportunityListFragment {
    private SearchOpportunitiesParameter searchParameter;

    public SearchResultListFragment() {
        this.layoutID = R.layout.list_opportunities;
        this.searchParameter = new SearchOpportunitiesParameter();
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.initPage = 1;
        this.currentPage = this.initPage;
        this.isAuthenticated = false;
        this.visibleThreshold = 10;
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        this.mRecyclerview = super.onCreateView(inflater, container, savedInstanceState);
        if (this.mRecyclerview instanceof RecyclerView) {
            RecyclerView recyclerView = (RecyclerView) this.mRecyclerview;
            this.mAdapter = new OpportunityItemRecyclerViewAdapter(this.mValues, this.mListener, this);
            recyclerView.setAdapter(this.mAdapter);
        }
        return this.mRecyclerview;
    }

    protected void getBodyData() {
        new GetViktorRecommendedOpportunities(this.searchParameter.toJsonObject()).execute(new Void[0]);
    }

    public void onAttach(Context context) {
        super.onAttach(context);
    }

    public void onDetach() {
        super.onDetach();
        this.mListener = null;
    }

    protected JSONArray getJSONArrayOpportunities(String result) throws JSONException {
        JSONObject resultObject = new JSONObject(result);
        if (resultObject.getInt("totalElements") > 0) {
            return resultObject.getJSONArray("content");
        }
        return new JSONArray();
    }

    protected void extraOnPostExecuteBodyAction(JSONArray opportunities) {
    }

    private void setSubtitle(String result) {
        Toolbar toolbar = (Toolbar) getActivity().findViewById(R.id.toolbar_no_tabs);
        try {
            JSONObject resultObject = new JSONObject(result);

            toolbar.setSubtitle(resultObject.getInt("totalElements") + " listings");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void updateSearchParameterFromSavedSearch(SavedSearchOpportunitiesItem savedSearch) {
        if (savedSearch != null) {
            this.searchParameter = savedSearch.getSearchParameters();
        }
    }

    private class GetViktorRecommendedOpportunities extends ApiWCFRequest {
        public GetViktorRecommendedOpportunities(JSONObject params) {
            super(HttpRequestType.POST, SearchResultListFragment.this.getString(R.string.apiVolunteerURL1), "api/search?page=" + Integer.toString(SearchResultListFragment.this.currentPage), params);
        }

        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
            View view = SearchResultListFragment.this.getView();
            Rect scrollBounds = new Rect();
            if (view != null) {
                view.getHitRect(scrollBounds);
                if (view.getLocalVisibleRect(scrollBounds)) {
                    SearchResultListFragment.this.showProgress();
                }
            }
        }

        protected void onPostExecuteBody(String result) {
            SearchResultListFragment.this.onExecuteBody(result, ListViewType.SEARCHRESULT);
            SearchResultListFragment.this.setSubtitle(result);
        }
    }
}

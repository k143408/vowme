package com.vowme.vol.app.activities.recommended;

import android.graphics.Rect;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.internal.ServerProtocol;
import com.vowme.app.models.Enum.HttpRequestType;
import com.vowme.app.models.Enum.ListViewType;
import com.vowme.app.models.api.SearchOpportunitiesParameter;
import com.vowme.app.utilities.adapters.OpportunityItemRecyclerViewAdapter;
import com.vowme.app.utilities.api.ApiRestFullRequest;
import com.vowme.app.utilities.api.ApiWCFRequest;
import com.vowme.app.utilities.fragments.OpportunityListFragment;
import com.vowme.vol.app.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.LinkedHashMap;

public class RecommendedListFragment extends OpportunityListFragment {
    private TextView oppCount;
    private SearchOpportunitiesParameter searchParameter;

    public RecommendedListFragment() {
        this.searchParameter = new SearchOpportunitiesParameter();
        this.layoutID = R.layout.list_recommended;
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (!getBaseActivity().isUserLoggedIn()) {
            this.initPage = 1;
            this.currentPage = this.initPage;
            updateSearchParameter();
        }
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);
        this.oppCount = (TextView) view.findViewById(R.id.opp_count);
        this.mRecyclerview = view.findViewById(R.id.recommended_list);
        if (this.mRecyclerview instanceof RecyclerView) {
            RecyclerView recyclerView = (RecyclerView) this.mRecyclerview;
            this.mAdapter = new OpportunityItemRecyclerViewAdapter(this.mValues, this.mListener, this);
            recyclerView.setAdapter(this.mAdapter);
            getData();
        }
        return view;
    }

    protected void getBodyData() {
        this.isAuthenticated = getBaseActivity().isUserLoggedIn();
        if (this.isAuthenticated) {
            LinkedHashMap<String, String> params = new LinkedHashMap();
            params.put("pageIndex", Integer.toString(this.currentPage));
            params.put("pageSize", Integer.toString(this.visibleThreshold));
            params.put("mobile", ServerProtocol.DIALOG_RETURN_SCOPES_TRUE);
            new GetVolunteerRecommendedOpportunities(params).execute(new Void[0]);
            return;
        }
        new GetViktorRecommendedOpportunities(this.searchParameter.toJsonObject()).execute(new Void[0]);
    }

    protected JSONArray getJSONArrayOpportunities(String result) throws JSONException {
        JSONObject json = new JSONObject(result);
        int total = json.getInt("totalElements");
        this.oppCount.setText(Integer.toString(total) + " matches based on your preferences");
        if (total > 0) {
            return json.getJSONArray("content");
        }
        return new JSONArray();
    }

    protected void extraOnPostExecuteBodyAction(JSONArray opportunities) {
    }

    public void updateSearchParameter() {
        this.searchParameter.AdvanceSearchFilters.Organisations = getBaseActivity().getAdjustmentCausesToString();
        this.searchParameter.Interests = getBaseActivity().getAdjustmentInterestsToString();
        this.searchParameter.Locations = getBaseActivity().getLocationFieldNames();
    }

    private class GetViktorRecommendedOpportunities extends ApiWCFRequest {
        public GetViktorRecommendedOpportunities(JSONObject params) {
            super(HttpRequestType.POST, RecommendedListFragment.this.getString(R.string.apiVolunteerURL1), "api/search?page="+Integer.toString(RecommendedListFragment.this.currentPage), params);
        }

        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
            View view = RecommendedListFragment.this.getView();
            Rect scrollBounds = new Rect();
            if (view != null) {
                view.getHitRect(scrollBounds);
                if (view.getLocalVisibleRect(scrollBounds)) {
                    RecommendedListFragment.this.showProgress();
                }
            }
        }

        protected void onPostExecuteBody(String result) {
            if (TextUtils.isEmpty(result)) {
                RecommendedListFragment.this.oppCount.setText(" 0 matches based on your preferences");
            }
            RecommendedListFragment.this.onExecuteBody(result, ListViewType.RECOMMENDED);
        }
    }

    private class GetVolunteerRecommendedOpportunities extends ApiRestFullRequest {
        public GetVolunteerRecommendedOpportunities(HashMap<String, String> params) {
            super(HttpRequestType.GET, RecommendedListFragment.this.getString(R.string.apiVolunteerURL1), "api/opportunity/recommended/"+RecommendedListFragment.this.getBaseActivity().getUserAccessToken()+"?page="+params.get("pageIndex"), (HashMap) params, RecommendedListFragment.this.getBaseActivity().getUserAccessToken());
        }

        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
            View view = RecommendedListFragment.this.getView();
            Rect scrollBounds = new Rect();
            if (view != null) {
                view.getHitRect(scrollBounds);
                if (view.getLocalVisibleRect(scrollBounds)) {
                    RecommendedListFragment.this.showProgress();
                }
            }
        }

        protected void onPostExecuteBody(String result) {
            if (TextUtils.isEmpty(result)) {
                RecommendedListFragment.this.oppCount.setText(" 0 matches based on your preferences");
            }
            RecommendedListFragment.this.onExecuteBody(result, ListViewType.RECOMMENDED);
        }
    }
}

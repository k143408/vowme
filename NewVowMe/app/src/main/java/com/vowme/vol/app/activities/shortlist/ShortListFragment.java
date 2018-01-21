package com.vowme.vol.app.activities.shortlist;

import android.graphics.Rect;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.vowme.app.models.Enum.HttpRequestType;
import com.vowme.app.models.Enum.ListViewType;
import com.vowme.app.utilities.adapters.OpportunityItemRecyclerViewAdapter;
import com.vowme.app.utilities.api.ApiRestFullRequest;
import com.vowme.app.utilities.api.ApiWCFRequest;
import com.vowme.app.utilities.fragments.OpportunityListFragment;
import com.vowme.vol.app.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

public class ShortListFragment extends OpportunityListFragment {
    private List<JSONObject> listTmp;
    private int numberOfthread;
    private int threadCount;

    public ShortListFragment() {
        this.layoutID = R.layout.list_opportunities;
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.listTmp = new ArrayList();
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        this.mRecyclerview = super.onCreateView(inflater, container, savedInstanceState);
        if (this.mRecyclerview instanceof RecyclerView) {
            RecyclerView recyclerView = (RecyclerView) this.mRecyclerview;
            this.mAdapter = new OpportunityItemRecyclerViewAdapter(this.mValues, this.mListener, this);
            this.mAdapter.setIsLoggedIn(getBaseActivity().isUserLoggedIn());
            recyclerView.setAdapter(this.mAdapter);
            getData();
        }
        return this.mRecyclerview;
    }

    protected void getBodyData() {
        this.isAuthenticated = getBaseActivity().isUserLoggedIn();
        if (this.isAuthenticated) {
            LinkedHashMap<String, String> params = new LinkedHashMap();
            params.put("pageIndex", Integer.toString(this.currentPage));
            params.put("pageSize", Integer.toString(this.visibleThreshold));
            new GetVolunteerShortlistOpportunities(params).execute(new Void[0]);
            return;
        }
        List<String> oppIds = getBaseActivity().getShortlist();
        this.numberOfthread = oppIds.size();
        if (this.numberOfthread <= 0 || !this.needToReLoadData) {
            addItems(new JSONArray(), ListViewType.SHORTLIST);
            return;
        }
        this.threadCount = 0;
        this.listTmp.clear();
        for (int i = 0; i < this.numberOfthread; i++) {
            new GetVolunteerShortlistOpportunity((String) oppIds.get(i)).execute(new Void[0]);
        }
    }

    protected JSONArray getJSONArrayOpportunities(String result) throws JSONException {
        return new JSONObject(result).getJSONArray("content");
    }

    protected void extraOnPostExecuteBodyAction(JSONArray opportunities) {
        getBaseActivity().putShortlist(opportunities);
    }

    private class GetVolunteerShortlistOpportunities extends ApiRestFullRequest {
        public GetVolunteerShortlistOpportunities(HashMap<String, String> params) {
            super(HttpRequestType.GET, ShortListFragment.this.getString(R.string.apiVolunteerURL1), "api/opportunity/shortlist/"+ShortListFragment.this.getBaseActivity().getUserAccessToken()+"?page="+params.get("pageIndex"), (HashMap) params, ShortListFragment.this.getBaseActivity().getUserAccessToken());
        }

        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
            View view = ShortListFragment.this.getView();
            Rect scrollBounds = new Rect();
            if (view != null) {
                view.getHitRect(scrollBounds);
                if (view.getLocalVisibleRect(scrollBounds)) {
                    ShortListFragment.this.showProgress();
                }
            }
        }

        protected void onPostExecuteBody(String result) {
            ShortListFragment.this.onExecuteBody(result, ListViewType.SHORTLIST);
        }
    }

    private class GetVolunteerShortlistOpportunity extends ApiWCFRequest {
        public GetVolunteerShortlistOpportunity(String oppId) {
            super(HttpRequestType.GET, ShortListFragment.this.getString(R.string.apiVolunteerURL1), "api/opportunity/shortlist/"+ShortListFragment.this
            .getBaseActivity().getUserAccessToken());
        }

        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
            View view = ShortListFragment.this.getView();
            Rect scrollBounds = new Rect();
            if (view != null) {
                view.getHitRect(scrollBounds);
                if (view.getLocalVisibleRect(scrollBounds)) {
                    ShortListFragment.this.showProgress();
                }
            }
        }

        protected void onPostExecuteBody(String result) {
            try {
                if (result.length() != 0) {
                    ShortListFragment.this.listTmp.add(new JSONObject(new JSONObject("{ \"Opportunity\":" + result + "}").getString("Opportunity")));
                    ShortListFragment.this.threadCount = ShortListFragment.this.threadCount + 1;
                }
                if (ShortListFragment.this.threadCount == ShortListFragment.this.numberOfthread) {
                    ShortListFragment.this.addItems(new JSONArray(ShortListFragment.this.listTmp), ListViewType.SHORTLIST);
                    ShortListFragment.this.needToReLoadData = false;
                }
            } catch (JSONException e) {
                e.printStackTrace();
                ShortListFragment.this.dismissProgress();
                ShortListFragment.this.addItems(new JSONArray(), ListViewType.SHORTLIST);
            }
        }
    }
}

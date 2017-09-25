package com.vowme.vol.app.activities.expressOfInterest;

import android.content.Context;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.vowme.vol.app.R;
import com.vowme.app.models.Enum.HttpRequestType;
import com.vowme.app.models.Enum.ListViewType;
import com.vowme.app.models.GroupedEOIItem;
import com.vowme.app.models.OpportunityItem;
import com.vowme.app.utilities.adapters.GroupedEOIItemRecyclerViewAdapter;
import com.vowme.app.utilities.api.ApiRestFullRequest;
import com.vowme.app.utilities.fragments.ItemListFragment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TreeMap;

public class ExpressOfInterestListFragment extends ItemListFragment {
    protected OnListFragmentInteractionListener mListener;
    protected List<GroupedEOIItem> mValues;

    private class GetVolunteerExpressedOfInterest extends ApiRestFullRequest {
        public GetVolunteerExpressedOfInterest(HashMap<String, String> params) {
            super(HttpRequestType.GET, ExpressOfInterestListFragment.this.getString(R.string.apiVolunteerURL), "api/opportunity/expressedinterest", (HashMap) params, ExpressOfInterestListFragment.this.getBaseActivity().getUserAccessToken());
        }

        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
            View view = ExpressOfInterestListFragment.this.getView();
            Rect scrollBounds = new Rect();
            if (view != null) {
                view.getHitRect(scrollBounds);
                if (view.getLocalVisibleRect(scrollBounds)) {
                    ExpressOfInterestListFragment.this.showProgress();
                }
            }
        }

        protected void onPostExecuteBody(String result) {
            ExpressOfInterestListFragment.this.onExecuteBody(result, ListViewType.EXPRESSED);
        }
    }

    public ExpressOfInterestListFragment() {
        this.mValues = new ArrayList();
        this.layoutID = R.layout.list_grouped_eoi;
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        this.mRecyclerview = super.onCreateView(inflater, container, savedInstanceState);
        if (this.mRecyclerview instanceof RecyclerView) {
            RecyclerView recyclerView = (RecyclerView)this.mRecyclerview;
            this.mAdapter = new GroupedEOIItemRecyclerViewAdapter(this.mValues, this.mListener, getContext());
            recyclerView.setAdapter(this.mAdapter);
            getData();
        }
        return this.mRecyclerview;
    }

    protected int getBodyAddItems(JSONArray opportunities) {
        Map<Date, List<OpportunityItem>> expressionGrouped = new TreeMap(Collections.reverseOrder());
        SimpleDateFormat dateFormat = new SimpleDateFormat("MMM dd, yyyy", Locale.ENGLISH);
        int i = 0;
        while (i < opportunities.length()) {
            try {
                OpportunityItem opportunity = new OpportunityItem(opportunities.getJSONObject(i), this.isAuthenticated);
                Date day = dateFormat.parse(dateFormat.format(opportunity.getDateObject()));
                if (expressionGrouped.containsKey(day)) {
                    ((List) expressionGrouped.get(day)).add(opportunity);
                } else {
                    List<OpportunityItem> list = new ArrayList();
                    list.add(opportunity);
                    expressionGrouped.put(day, list);
                }
            } catch (Exception e) {
                e.printStackTrace();
                dismissProgress();
            }
            i++;
        }
        for (Date key : expressionGrouped.keySet()) {
            GroupedEOIItem gpEoi = new GroupedEOIItem();
            gpEoi.setDateGroupedEoi(key);
            gpEoi.getExpressOfInterest().addAll((Collection) expressionGrouped.get(key));
            this.mValues.add(gpEoi);
        }
        return i;
    }

    protected void getBodyData() {
        this.isAuthenticated = getBaseActivity().isUserLoggedIn();
        if (this.isAuthenticated) {
            LinkedHashMap<String, String> params = new LinkedHashMap();
            params.put("pageIndex", Integer.toString(this.currentPage));
            params.put("pageSize", Integer.toString(this.visibleThreshold));
            new GetVolunteerExpressedOfInterest(params).execute(new Void[0]);
            return;
        }
        addItems(new JSONArray(), ListViewType.EXPRESSED);
    }

    protected JSONArray getJSONArrayOpportunities(String result) throws JSONException {
        return new JSONObject(result).getJSONArray("items");
    }

    protected void extraOnPostExecuteBodyAction(JSONArray opportunities) {
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

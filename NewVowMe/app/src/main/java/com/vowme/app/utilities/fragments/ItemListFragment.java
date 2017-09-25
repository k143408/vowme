package com.vowme.app.utilities.fragments;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.OnScrollListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.vowme.app.models.Enum.ListViewType;
import com.vowme.app.models.OpportunityItem;
import com.vowme.app.models.TimesheetItem;
import com.vowme.app.utilities.adapters.ListItemRecyclerViewAdapter;
import com.vowme.vol.app.R;

import org.json.JSONArray;
import org.json.JSONException;

public abstract class ItemListFragment extends BaseFragment {
    protected int currentPage;
    protected boolean endReached;
    protected int initPage;
    protected boolean isAuthenticated;
    protected int layoutID;
    protected boolean loading;
    protected ListItemRecyclerViewAdapter mAdapter;
    protected LinearLayoutManager mLayoutManager;
    protected View mRecyclerview;
    protected boolean needToReLoadData;
    protected boolean noResults;
    protected ProgressDialog progressDialog;
    protected View view;
    protected int visibleThreshold;
    int lastVisibleItem;
    int totalItemCount;
    int visibleItemCount;

    protected abstract void extraOnPostExecuteBodyAction(JSONArray jSONArray);

    protected abstract int getBodyAddItems(JSONArray jSONArray);

    protected abstract void getBodyData();

    protected abstract JSONArray getJSONArrayOpportunities(String str) throws JSONException;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        this.visibleThreshold = getResources().getInteger(R.integer.COUNT_PER_PAGE);
        this.view = inflater.inflate(this.layoutID, container, false);
        if (this.layoutID == R.layout.list_recommended) {
            this.mRecyclerview = this.view.findViewById(R.id.recommended_list);
        } else {
            this.mRecyclerview = this.view;
        }
        if (this.mRecyclerview instanceof RecyclerView) {
            RecyclerView recyclerView = (RecyclerView) this.mRecyclerview;
            recyclerView.setHasFixedSize(true);
            this.mLayoutManager = new LinearLayoutManager(this.mRecyclerview.getContext());
            recyclerView.setLayoutManager(this.mLayoutManager);
            recyclerView.addOnScrollListener(new C09131());
        }
        return this.view;
    }

    protected void init() {
        this.loading = true;
        this.endReached = false;
        this.noResults = false;
        this.initPage = 0;
        this.currentPage = this.initPage;
        this.needToReLoadData = true;
    }

    protected void getData() {
        if (this.mAdapter != null) {
            this.mAdapter.clear();
            init();
            this.mAdapter.setFragmentViewType(ListViewType.LOADING);
            getBodyData();
        }
    }

    public void refresh() {
        getData();
    }

    protected void showProgress() {
        if (this.progressDialog == null) {
            this.progressDialog = new ProgressDialog(getContext());
            this.progressDialog.setMessage("Please wait while loading...");
            this.progressDialog.setCancelable(false);
        }
        this.progressDialog.show();
    }

    protected void dismissProgress() {
        if (this.progressDialog != null) {
            this.progressDialog.dismiss();
        }
    }

    protected void onExecuteBody(String result, ListViewType typeView) {
        if (result.length() == 0) {
            addItems(new JSONArray(), typeView);
            return;
        }
        try {
            JSONArray opportunities = getJSONArrayOpportunities(result);
            addItems(opportunities, typeView);
            extraOnPostExecuteBodyAction(opportunities);
        } catch (JSONException e) {
            e.printStackTrace();
            dismissProgress();
            addItems(new JSONArray(), typeView);
        }
    }

    protected void addItems(JSONArray opportunities, ListViewType listType) {
        this.mAdapter.setFragmentViewType(listType);
        int i = getBodyAddItems(opportunities);
        if (i == 0 && this.currentPage == this.initPage) {
            this.noResults = true;
        } else if (i != 0 || this.currentPage <= this.initPage) {
            this.currentPage++;
        } else {
            this.endReached = true;
        }
        this.mAdapter.notifyDataSetChanged();
        dismissProgress();
        this.loading = false;
    }

    public interface OnListFragmentInteractionListener {
        void onListFragmentInteraction(OpportunityItem opportunityItem);
    }

    public interface OnListTimesheetFragmentInteractionListener {
        void onListFragmentInteraction(TimesheetItem timesheetItem);
    }

    class C09131 extends OnScrollListener {
        C09131() {
        }

        public void onScrollStateChanged(RecyclerView view, int scrollState) {
        }

        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
            if (!ItemListFragment.this.endReached && !ItemListFragment.this.noResults) {
                ItemListFragment.this.visibleItemCount = ((RecyclerView) ItemListFragment.this.mRecyclerview).getChildCount();
                ItemListFragment.this.totalItemCount = ItemListFragment.this.mAdapter.getItemCount();
                ItemListFragment.this.lastVisibleItem = ItemListFragment.this.mLayoutManager.findLastVisibleItemPosition();
                if (!ItemListFragment.this.loading && ItemListFragment.this.lastVisibleItem == ItemListFragment.this.totalItemCount - 2) {
                    ItemListFragment.this.loading = true;
                    ItemListFragment.this.getBodyData();
                }
            }
        }
    }
}

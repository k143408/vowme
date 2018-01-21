package com.vowme.vol.app.activities.timesheet;

import android.content.Context;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.vowme.app.models.Enum.HttpRequestType;
import com.vowme.app.models.Enum.ListViewType;
import com.vowme.app.models.GroupedTimesheetItem;
import com.vowme.app.models.TimesheetItem;
import com.vowme.app.models.lookUp.Lookup;
import com.vowme.app.utilities.adapters.GroupedTimesheetItemRecyclerViewAdapter;
import com.vowme.app.utilities.api.ApiRestFullRequest;
import com.vowme.app.utilities.fragments.ItemListFragment;
import com.vowme.vol.app.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TreeMap;

public class TimesheetFragment extends ItemListFragment {
    public List<Lookup> opportunitiesToLog;
    protected OnListTimesheetFragmentInteractionListener mListener;
    protected List<GroupedTimesheetItem> mValues;

    public TimesheetFragment() {
        this.mValues = new ArrayList();
        this.layoutID = R.layout.list_grouped_timesheet;
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.opportunitiesToLog = new ArrayList();
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        this.mRecyclerview = super.onCreateView(inflater, container, savedInstanceState);
        if (this.mRecyclerview instanceof RecyclerView) {
            RecyclerView recyclerView = (RecyclerView) this.mRecyclerview;
            this.mAdapter = new GroupedTimesheetItemRecyclerViewAdapter(this.mValues, this.mListener, getContext());
            recyclerView.setAdapter(this.mAdapter);
            getData();
        }
        return this.mRecyclerview;
    }

    protected int getBodyAddItems(JSONArray opportunities) {
        Map<Date, GpTimesheetLookup> timesheetGrouped = new TreeMap(Collections.reverseOrder());
        SimpleDateFormat dateFormat = new SimpleDateFormat("MMM yyyy", Locale.ENGLISH);
        for (int i = 0; i < opportunities.length(); i++) {
            try {

                JSONObject opportunity = opportunities.getJSONObject(i);
                String name = opportunity.getString("name");
                Integer causeId = opportunity.getInt("id");
                this.opportunitiesToLog.add(new Lookup(causeId, name));
                JSONArray itemList = new JSONArray();
                if (opportunity.has("timesheets"))
                    itemList = opportunity.getJSONArray("timesheets");
                TimesheetItem timesheet = null;
                for (int j = 0; j < itemList.length(); j++) {
                    timesheet = new TimesheetItem(itemList.getJSONObject(j), name,causeId);
                    Date month = dateFormat.parse(dateFormat.format(timesheet.date));
                    if (timesheetGrouped.containsKey(month)) {
                        ((GpTimesheetLookup) timesheetGrouped.get(month)).totalHours += timesheet.hours.intValue();
                        ((GpTimesheetLookup) timesheetGrouped.get(month)).totalMinutes += timesheet.minutes.intValue();
                        ((GpTimesheetLookup) timesheetGrouped.get(month)).list.add(timesheet);
                    } else {
                        GpTimesheetLookup timesheetLookup = new GpTimesheetLookup();
                        timesheetLookup.totalHours = timesheet.hours.intValue();
                        timesheetLookup.totalMinutes = timesheet.minutes.intValue();
                        timesheetLookup.list.add(timesheet);
                        timesheetGrouped.put(month, timesheetLookup);
                    }
                }
                if (timesheet == null) {
                    timesheetGrouped.put(new Date(), new GpTimesheetLookup(new TimesheetItem(name,causeId)));
                }
            } catch (Exception e) {
                e.printStackTrace();
                dismissProgress();
            }
        }
        for (Date key : timesheetGrouped.keySet()) {
            GroupedTimesheetItem gpTimesheet = new GroupedTimesheetItem();
            gpTimesheet.date = dateFormat.format(key);
            gpTimesheet.totalHours = Integer.valueOf(((GpTimesheetLookup) timesheetGrouped.get(key)).totalHours);
            gpTimesheet.totalMinutes = Integer.valueOf(((GpTimesheetLookup) timesheetGrouped.get(key)).totalMinutes);
            gpTimesheet.getOpportunityHours().addAll(((GpTimesheetLookup) timesheetGrouped.get(key)).list);
            this.mValues.add(gpTimesheet);
        }
        return 0;
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
        addItems(new JSONArray(), ListViewType.TIMESHEET);
    }

    protected JSONArray getJSONArrayOpportunities(String result) throws JSONException {
        return new JSONObject(result).getJSONArray("content");
    }

    protected void extraOnPostExecuteBodyAction(JSONArray opportunities) {
    }

    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnListTimesheetFragmentInteractionListener) {
            this.mListener = (OnListTimesheetFragmentInteractionListener) context;
            return;
        }
        throw new RuntimeException(context.toString() + " must implement OnListFragmentInteractionListener");
    }

    public void onDetach() {
        super.onDetach();
        this.mListener = null;
    }

    private class GetVolunteerExpressedOfInterest extends ApiRestFullRequest {
        public GetVolunteerExpressedOfInterest(HashMap<String, String> params) {
            super(HttpRequestType.GET, TimesheetFragment.this.getString(R.string.apiVolunteerURL1), "api/opportunity/accepted/" + TimesheetFragment.this.getBaseActivity().getUserAccessToken() + "?page=" + params.get("pageIndex"), (HashMap) params, TimesheetFragment.this.getBaseActivity().getUserAccessToken());
        }

        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
            View view = TimesheetFragment.this.getView();
            Rect scrollBounds = new Rect();
            if (view != null) {
                view.getHitRect(scrollBounds);
                if (view.getLocalVisibleRect(scrollBounds)) {
                    TimesheetFragment.this.showProgress();
                }
            }
        }

        protected void onPostExecuteBody(String result) {
            TimesheetFragment.this.onExecuteBody(result, ListViewType.TIMESHEET);
        }
    }

    private class GpTimesheetLookup {

        List<TimesheetItem> list = new ArrayList(1);
        int totalHours = 0;
        int totalMinutes = 0;

        GpTimesheetLookup() {
        }
        GpTimesheetLookup(TimesheetItem timesheetItem) {
            list.add(timesheetItem);
        }

    }
}

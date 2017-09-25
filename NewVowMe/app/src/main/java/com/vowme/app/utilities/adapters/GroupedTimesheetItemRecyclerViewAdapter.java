package com.vowme.app.utilities.adapters;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.vowme.app.models.GroupedTimesheetItem;
import com.vowme.app.utilities.customWidgets.DividerItemDecoration;
import com.vowme.app.utilities.fragments.ItemListFragment.OnListTimesheetFragmentInteractionListener;
import com.vowme.app.utilities.helpers.TextViewHelper;
import com.vowme.vol.app.R;

import java.util.List;

public class GroupedTimesheetItemRecyclerViewAdapter extends ListItemRecyclerViewAdapter {
    private final Context context;
    private final OnListTimesheetFragmentInteractionListener mListener;

    public GroupedTimesheetItemRecyclerViewAdapter(List<GroupedTimesheetItem> items, OnListTimesheetFragmentInteractionListener listener, Context context) {
        super(items);
        this.mListener = listener;
        this.context = context;
    }

    protected RecyclerView.ViewHolder getViewHolder(ViewGroup parent) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_grouped_timesheet, parent, false));
    }

    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ViewHolder) {
            ViewHolder vh = (ViewHolder) holder;
            vh.mItem = (GroupedTimesheetItem) this.mValues.get(position);
            vh.date.setText(vh.mItem.date);
            vh.hours.setText(TextViewHelper.getHourTimeToString(vh.mItem.getHours()));
            vh.minutes.setText(TextViewHelper.getMinuteTimeToString(vh.mItem.getMinutes()));
            vh.timesheetItemList.setLayoutManager(new LinearLayoutManager(vh.timesheetItemList.getContext()));
            vh.timesheetItemList.addItemDecoration(new DividerItemDecoration(vh.timesheetItemList.getContext(), 1));
            vh.timesheetItemList.setAdapter(new TimesheetItemRecyclerViewAdapter(vh.mItem.getOpportunityHours(), this.mListener, this.context));
            vh.timesheetItemList.getAdapter().notifyDataSetChanged();
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final TextView date;
        public final TextView hours;
        public final LinearLayout mLayout;
        public final CardView mView;
        public final TextView minutes;
        public GroupedTimesheetItem mItem;
        public RecyclerView timesheetItemList;

        public ViewHolder(View view) {
            super(view);
            this.mView = (CardView) view;
            this.mLayout = (LinearLayout) view.findViewById(R.id.timesheet_layout);
            this.date = (TextView) view.findViewById(R.id.grouped_date);
            this.hours = (TextView) view.findViewById(R.id.grouped_hours);
            this.minutes = (TextView) view.findViewById(R.id.grouped_minutes);
            this.timesheetItemList = (RecyclerView) view.findViewById(R.id.timesheet_item_list);
        }

        public String toString() {
            return super.toString() + " '" + "'";
        }
    }
}

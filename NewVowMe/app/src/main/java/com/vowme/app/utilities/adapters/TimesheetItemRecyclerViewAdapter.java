package com.vowme.app.utilities.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.vowme.app.models.TimesheetItem;
import com.vowme.app.utilities.fragments.ItemListFragment.OnListTimesheetFragmentInteractionListener;
import com.vowme.app.utilities.helpers.TextViewHelper;
import com.vowme.vol.app.R;

import java.util.List;

public class TimesheetItemRecyclerViewAdapter extends ListItemRecyclerViewAdapter {
    private final Context context;
    private final OnListTimesheetFragmentInteractionListener mListener;

    public TimesheetItemRecyclerViewAdapter(List<TimesheetItem> items, OnListTimesheetFragmentInteractionListener listener, Context context) {
        super(items);
        this.mListener = listener;
        this.context = context;
    }

    protected android.support.v7.widget.RecyclerView.ViewHolder getViewHolder(ViewGroup parent) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_timesheet, parent, false));
    }

    public void onBindViewHolder(final android.support.v7.widget.RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ViewHolder) {
            ViewHolder vh = (ViewHolder) holder;
            vh.mItem = (TimesheetItem) this.mValues.get(position);
            vh.date.setText(vh.mItem.dateAsString);
            vh.name.setText(vh.mItem.name);
            vh.hours.setText(TextViewHelper.getHourTimeToString(vh.mItem.hours));
            vh.minutes.setText(TextViewHelper.getMinuteTimeToString(vh.mItem.minutes));
            vh.mLayout.setOnClickListener(new OnClickListener() {
                public void onClick(View v) {
                    if (TimesheetItemRecyclerViewAdapter.this.mListener != null) {
                        TimesheetItemRecyclerViewAdapter.this.mListener.onListFragmentInteraction(((ViewHolder) holder).mItem);
                    }
                }
            });
        }
    }

    public class ViewHolder extends android.support.v7.widget.RecyclerView.ViewHolder {
        public final TextView date;
        public final TextView hours;
        public final LinearLayout mLayout;
        public final TextView minutes;
        public final TextView name;
        public TimesheetItem mItem;

        public ViewHolder(View view) {
            super(view);
            this.mLayout = (LinearLayout) view;
            this.date = (TextView) view.findViewById(R.id.item_date);
            this.name = (TextView) view.findViewById(R.id.item_name);
            this.hours = (TextView) view.findViewById(R.id.item_hours);
            this.minutes = (TextView) view.findViewById(R.id.item_minutes);
        }

        public String toString() {
            return super.toString() + " '" + "'";
        }
    }
}

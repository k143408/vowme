package com.vowme.app.utilities.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.vowme.app.models.OpportunityItem;
import com.vowme.app.utilities.fragments.ItemListFragment.OnListFragmentInteractionListener;
import com.vowme.app.utilities.helpers.TextViewHelper;
import com.vowme.vol.app.R;

import java.util.List;

public class EOIItemRecyclerViewAdapter extends ListItemRecyclerViewAdapter {
    private final Context context;
    private final OnListFragmentInteractionListener mListener;

    public EOIItemRecyclerViewAdapter(List<OpportunityItem> items, OnListFragmentInteractionListener listener, Context context) {
        super(items);
        this.mListener = listener;
        this.context = context;
    }

    protected android.support.v7.widget.RecyclerView.ViewHolder getViewHolder(ViewGroup parent) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_expression_of_interest, parent, false));
    }

    public void onBindViewHolder(final android.support.v7.widget.RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ViewHolder) {
            ViewHolder vh = (ViewHolder) holder;
            vh.mItem = (OpportunityItem) this.mValues.get(position);
            vh.oppName.setText(vh.mItem.getName());
            TextViewHelper.formatOppForSubtitle(this.context, vh.oppFor, vh.mItem.getOrganisationName(), vh.mItem.getServiceFocus(), null);
            vh.mLayout.setOnClickListener(new OnClickListener() {
                public void onClick(View v) {
                    if (EOIItemRecyclerViewAdapter.this.mListener != null) {
                        EOIItemRecyclerViewAdapter.this.mListener.onListFragmentInteraction(((ViewHolder) holder).mItem);
                    }
                }
            });
        }
    }

    public class ViewHolder extends android.support.v7.widget.RecyclerView.ViewHolder {
        public final LinearLayout mLayout;
        public final TextView oppFor;
        public final TextView oppName;
        public OpportunityItem mItem;

        public ViewHolder(View view) {
            super(view);
            this.mLayout = (LinearLayout) view;
            this.oppName = (TextView) view.findViewById(R.id.opp_title);
            this.oppFor = (TextView) view.findViewById(R.id.opp_for);
        }

        public String toString() {
            return super.toString() + " '" + "'";
        }
    }
}

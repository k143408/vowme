package com.vowme.app.utilities.adapters;

import android.graphics.Typeface;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.vowme.app.models.Enum.ListViewType;
import com.vowme.app.models.OpportunityItem;
import com.vowme.app.utilities.activities.BaseActivity;
import com.vowme.app.utilities.fragments.ItemListFragment.OnListFragmentInteractionListener;
import com.vowme.app.utilities.fragments.OpportunityListFragment;
import com.vowme.app.utilities.helpers.TextViewHelper;
import com.vowme.app.utilities.helpers.TypefacesHelper;
import com.vowme.vol.app.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class OpportunityItemRecyclerViewAdapter extends ListItemRecyclerViewAdapter {
    private final OnListFragmentInteractionListener mListener;
    private OpportunityListFragment mfragment;

    public OpportunityItemRecyclerViewAdapter(List<OpportunityItem> items, OnListFragmentInteractionListener listener, OpportunityListFragment fragment) {
        super(items);
        this.mListener = listener;
        this.mfragment = fragment;
    }

    protected android.support.v7.widget.RecyclerView.ViewHolder getViewHolder(ViewGroup parent) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_opportunity, parent, false));
    }

    public void onBindViewHolder(final android.support.v7.widget.RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ViewHolder) {
            ViewHolder vh = (ViewHolder) holder;
            vh.mItem = (OpportunityItem) this.mValues.get(position);
            if (vh.mItem.getDateObject() != null) {
                Calendar calendar = Calendar.getInstance();
                calendar.add(Calendar.DAY_OF_YEAR, -7);
                Date newDate = calendar.getTime();
                if (vh.mItem.isExpired()) {
                    vh.oppStatus.setText("Expired");
                } else if (vh.mItem.getDateObject().after(newDate)) {
                    vh.oppStatus.setText("New");
                } else {
                    vh.oppStatus.setText(new SimpleDateFormat("dd/MM/yyyy").format(vh.mItem.getDateObject()));
                }
            }
            vh.oppName.setText(vh.mItem.getName());
            TextViewHelper.formatOppForSubtitle(this.mfragment.getActivity(), vh.oppFor, vh.mItem.getOrganisationName(), vh.mItem.getServiceFocus(), null);
            vh.oppShortDesc.setText(vh.mItem.getShortDescription());
            vh.oppLocation.setText(vh.mItem.getSuburb() + ", " + vh.mItem.getStateCode());
            vh.oppDuration.setText(vh.mItem.getDuration());
            vh.mLayout.setOnClickListener(new OnClickListener() {
                public void onClick(View v) {
                    if (OpportunityItemRecyclerViewAdapter.this.mListener != null) {
                        OpportunityItemRecyclerViewAdapter.this.mListener.onListFragmentInteraction(((ViewHolder) holder).mItem);
                    }
                }
            });
            ((BaseActivity) this.mfragment.getActivity()).initShortlist(vh.oppIsShortlisted, vh.mItem.isShortlisted());
            if (this.fragmentViewType == ListViewType.RECOMMENDED || this.fragmentViewType == ListViewType.SEARCHRESULT) {
                vh.oppIsShortlisted.setOnClickListener(new OnClickListener() {
                    public void onClick(View v) {
                        boolean result;
                        if (((ViewHolder) holder).mItem.isShortlisted()) {
                            result = ((BaseActivity) OpportunityItemRecyclerViewAdapter.this.mfragment.getActivity()).DeleteFromShortlist(((ViewHolder) holder).mItem.getId());
                        } else {
                            result = ((BaseActivity) OpportunityItemRecyclerViewAdapter.this.mfragment.getActivity()).AddToShortList(((ViewHolder) holder).mItem.getId());
                        }
                        if (result) {
                            ((ViewHolder) holder).mItem.setIsShortlisted(!((ViewHolder) holder).mItem.isShortlisted());
                            ((BaseActivity) OpportunityItemRecyclerViewAdapter.this.mfragment.getActivity()).initShortlist((TextView) v, ((ViewHolder) holder).mItem.isShortlisted());
                        }
                    }
                });
            } else if (this.fragmentViewType == ListViewType.SHORTLIST) {
                if (vh.mItem.isShortlisted()) {
                    ((ViewHolder) holder).mLayout.setVisibility(View.VISIBLE);
                } else {
                    ((ViewHolder) holder).mLayout.setVisibility(View.GONE);
                }
                vh.oppIsShortlisted.setOnClickListener(new OnClickListener() {
                    public void onClick(View v) {
                        if (((BaseActivity) OpportunityItemRecyclerViewAdapter.this.mfragment.getActivity()).DeleteFromShortlist(((ViewHolder) holder).mItem.getId())) {
                            ((ViewHolder) holder).mLayout.setVisibility(View.GONE);
                        }
                    }
                });
                vh.oppUndoShortlisted.setOnClickListener(new OnClickListener() {
                    public void onClick(View v) {
                        if (((BaseActivity) OpportunityItemRecyclerViewAdapter.this.mfragment.getActivity()).AddToShortList(((ViewHolder) holder).mItem.getId())) {
                            ((ViewHolder) holder).mLayout.setVisibility(View.VISIBLE);
                        }
                    }
                });
            }
        }
    }

    public class ViewHolder extends android.support.v7.widget.RecyclerView.ViewHolder {
        public final LinearLayout mLayout;
        public final CardView mView;
        public final TextView oppDuration;
        public final TextView oppFor;
        public final TextView oppIcLoc;
        public final TextView oppIcTime;
        public final TextView oppIsShortlisted;
        public final TextView oppLocation;
        public final TextView oppName;
        public final TextView oppShortDesc;
        public final TextView oppStatus;
        public final TextView oppUndoShortlisted;
        public OpportunityItem mItem;

        public ViewHolder(View view) {
            super(view);
            this.mView = (CardView) view;
            this.mLayout = (LinearLayout) view.findViewById(R.id.opp_layout);
            this.oppIcLoc = (TextView) view.findViewById(R.id.opp_ic_loc);
            this.oppIcTime = (TextView) view.findViewById(R.id.opp_ic_time);
            this.oppIsShortlisted = (TextView) view.findViewById(R.id.opp_is_shortlisted);
            Typeface font = TypefacesHelper.get(view.getContext(), "fonts/material_icon_font.ttf");
            this.oppIcLoc.setTypeface(font);
            this.oppIcTime.setTypeface(font);
            this.oppIsShortlisted.setTypeface(TypefacesHelper.get(view.getContext(), "fonts/icomoon.ttf"));
            this.oppUndoShortlisted = (TextView) view.findViewById(R.id.opp_undo_shortlisted);
            this.oppStatus = (TextView) view.findViewById(R.id.opp_status);
            this.oppName = (TextView) view.findViewById(R.id.opp_title);
            this.oppFor = (TextView) view.findViewById(R.id.opp_for);
            this.oppShortDesc = (TextView) view.findViewById(R.id.opp_short_desc);
            this.oppLocation = (TextView) view.findViewById(R.id.opp_location);
            this.oppDuration = (TextView) view.findViewById(R.id.opp_duration);
        }

        public String toString() {
            return super.toString() + " '" + "'";
        }
    }
}

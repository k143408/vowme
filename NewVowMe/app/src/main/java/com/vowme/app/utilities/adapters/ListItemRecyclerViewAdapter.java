package com.vowme.app.utilities.adapters;

import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView.Adapter;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.vowme.app.models.Enum.ListViewType;
import com.vowme.app.utilities.helpers.TypefacesHelper;
import com.vowme.vol.app.R;

import java.util.List;

public abstract class ListItemRecyclerViewAdapter extends Adapter<ViewHolder> {
    protected final List<?> mValues;
    protected ListViewType fragmentViewType;
    protected boolean isLoggedIn = true;

    public ListItemRecyclerViewAdapter(List<?> items) {
        this.mValues = items;
    }

    protected abstract ViewHolder getViewHolder(ViewGroup viewGroup);

    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == 0) {
            return getViewHolder(parent);
        }
        if (viewType == ListViewType.LASTROW.getValue()) {
            return new LastRowViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_last_row_shortlist, parent, false), viewType);
        }
        View view;
        if (viewType == ListViewType.RECOMMENDED.getValue()) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_recommended_empty, parent, false);
        } else if (viewType == ListViewType.SHORTLIST.getValue()) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_shortlist_empty, parent, false);
        } else if (viewType == ListViewType.EXPRESSED.getValue()) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_eoi_empty, parent, false);
        } else if (viewType == ListViewType.TIMESHEET.getValue()) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_timesheet_empty, parent, false);
        } else {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_search_result_empty, parent, false);
        }
        return new EmptyViewHolder(view, viewType);
    }

    public void onBindViewHolder(ViewHolder holder, int position) {
    }

    public void remove(int position) {
        this.mValues.remove(position);
        notifyItemRemoved(position);
    }

    public void clear() {
        this.mValues.clear();
        notifyDataSetChanged();
    }

    public int getItemCount() {
        if (this.mValues.size() > 0) {
            if (this.fragmentViewType != ListViewType.SHORTLIST || this.isLoggedIn) {
                return this.mValues.size();
            }
            return this.mValues.size() + 1;
        } else if (this.fragmentViewType == ListViewType.LOADING) {
            return 0;
        } else {
            return 1;
        }
    }

    public int getItemViewType(int position) {
        if (this.mValues.size() == 0) {
            return this.fragmentViewType == null ? 0 : this.fragmentViewType.getValue();
        }
        if (this.fragmentViewType != ListViewType.SHORTLIST || this.isLoggedIn || getItemCount() <= 0 || position != getItemCount() - 1) {
            return super.getItemViewType(position);
        }
        return ListViewType.LASTROW.getValue();
    }

    public void setIsLoggedIn(boolean isLoggedIn) {
        this.isLoggedIn = isLoggedIn;
    }

    public void setFragmentViewType(ListViewType viewType) {
        this.fragmentViewType = viewType;
    }

    public class EmptyViewHolder extends ViewHolder {
        public final TextView tabIcon;

        public EmptyViewHolder(View itemView, int viewType) {
            super(itemView);
            Typeface font;
            if (viewType == ListViewType.SEARCHRESULT.getValue() || viewType == ListViewType.TIMESHEET.getValue()) {
                font = TypefacesHelper.get(itemView.getContext(), "fonts/material_icon_font.ttf");
            } else {
                font = TypefacesHelper.get(itemView.getContext(), "fonts/icomoon.ttf");
            }
            this.tabIcon = (TextView) itemView.findViewById(R.id.ic_tab_icon);
            this.tabIcon.setTypeface(font);
        }
    }

    public class LastRowViewHolder extends ViewHolder {
        public LastRowViewHolder(View itemView, int viewType) {
            super(itemView);
        }
    }
}

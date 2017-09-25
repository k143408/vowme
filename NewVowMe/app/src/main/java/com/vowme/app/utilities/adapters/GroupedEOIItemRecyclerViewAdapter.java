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

import com.vowme.app.models.GroupedEOIItem;
import com.vowme.app.utilities.fragments.ItemListFragment.OnListFragmentInteractionListener;
import com.vowme.vol.app.R;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

public class GroupedEOIItemRecyclerViewAdapter extends ListItemRecyclerViewAdapter {
    private final Context context;
    private final OnListFragmentInteractionListener mListener;

    public GroupedEOIItemRecyclerViewAdapter(List<GroupedEOIItem> items, OnListFragmentInteractionListener listener, Context context) {
        super(items);
        this.mListener = listener;
        this.context = context;
    }

    protected RecyclerView.ViewHolder getViewHolder(ViewGroup parent) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_grouped_eoi, parent, false));
    }

    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ViewHolder) {
            ViewHolder vh = (ViewHolder) holder;
            vh.mItem = (GroupedEOIItem) this.mValues.get(position);
            vh.eoiStatus.setText(new SimpleDateFormat("MMM dd, yyyy", Locale.ENGLISH).format(vh.mItem.dateGroupedEoi));
            vh.eoiList.setLayoutManager(new LinearLayoutManager(vh.eoiList.getContext()));
            vh.eoiList.setAdapter(new EOIItemRecyclerViewAdapter(vh.mItem.expressOfInterest, this.mListener, this.context));
            vh.eoiList.getAdapter().notifyDataSetChanged();
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final TextView eoiStatus;
        public final LinearLayout mLayout;
        public final CardView mView;
        public RecyclerView eoiList;
        public GroupedEOIItem mItem;

        public ViewHolder(View view) {
            super(view);
            this.mView = (CardView) view;
            this.mLayout = (LinearLayout) view.findViewById(R.id.eoi_layout);
            this.eoiStatus = (TextView) view.findViewById(R.id.grouped_id);
            this.eoiList = (RecyclerView) view.findViewById(R.id.eoi_item_list);
        }

        public String toString() {
            return super.toString() + " '" + "'";
        }
    }
}

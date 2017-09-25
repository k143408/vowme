package com.vowme.app.utilities.adapters;

import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.Adapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Switch;
import android.widget.TextView;

import com.vowme.app.models.DayAvaibilityItem;
import com.vowme.vol.app.R;

import java.util.List;

public class DayAvaibilityItemRecyclerViewAdapter extends Adapter<RecyclerView.ViewHolder> {
    public List<DayAvaibilityItem> mValues;

    public DayAvaibilityItemRecyclerViewAdapter(List<DayAvaibilityItem> items) {
        this.mValues = items;
    }

    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_day_avaibility, parent, false));
    }

    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ViewHolder) {
            ViewHolder vh = (ViewHolder) holder;
            vh.mItem = (DayAvaibilityItem) this.mValues.get(position);
            vh.name.setText(vh.mItem.getType().getValue());
            vh.isSelected.setChecked(vh.mItem.isSelected);
            vh.avaibilityList.setLayoutManager(new LinearLayoutManager(vh.avaibilityList.getContext()));
            vh.avaibilityList.setAdapter(new AvaibilityItemRecyclerViewAdapter(vh.mItem.avaibilityItems));
            vh.avaibilityList.getAdapter().notifyDataSetChanged();
            isSelectedAction(vh);
            vh.isSelected.setOnClickListener(new OnClickListener() {
                public void onClick(View v) {
                    ((ViewHolder) holder).mItem.isSelected = !((ViewHolder) holder).mItem.isSelected;
                    DayAvaibilityItemRecyclerViewAdapter.this.isSelectedAction((ViewHolder) holder);
                    if (!((ViewHolder) holder).mItem.isSelected) {
                        ((ViewHolder) holder).mItem.unCheckedAll();
                        ((ViewHolder) holder).avaibilityList.getAdapter().notifyDataSetChanged();
                    }
                }
            });
        }
    }

    private void isSelectedAction(ViewHolder vh) {
        if (vh.mItem.isSelected) {
            vh.avaibilityList.setVisibility(0);
        } else {
            vh.avaibilityList.setVisibility(8);
        }
    }

    public int getItemCount() {
        return this.mValues.size();
    }

    public void unCheckedAll() {
        for (DayAvaibilityItem avaibilityDay : this.mValues) {
            avaibilityDay.isSelected = false;
            avaibilityDay.unCheckedAll();
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final Switch isSelected;
        public final CardView mLayout;
        public final TextView name;
        public RecyclerView avaibilityList;
        public DayAvaibilityItem mItem;

        public ViewHolder(View view) {
            super(view);
            this.mLayout = (CardView) view;
            this.name = (TextView) view.findViewById(R.id.day_name);
            this.isSelected = (Switch) view.findViewById(R.id.switch_day);
            this.avaibilityList = (RecyclerView) view.findViewById(R.id.day_item_list);
        }

        public String toString() {
            return super.toString() + " '" + "'";
        }
    }
}

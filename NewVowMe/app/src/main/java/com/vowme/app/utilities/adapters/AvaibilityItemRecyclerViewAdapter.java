package com.vowme.app.utilities.adapters;

import android.support.v7.widget.RecyclerView.Adapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.vowme.app.models.AvaibilityItem;
import com.vowme.vol.app.R;

import java.util.List;

public class AvaibilityItemRecyclerViewAdapter extends Adapter<android.support.v7.widget.RecyclerView.ViewHolder> {
    protected final List<AvaibilityItem> mValues;

    public AvaibilityItemRecyclerViewAdapter(List<AvaibilityItem> items) {
        this.mValues = items;
    }

    public android.support.v7.widget.RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_avaibility, parent, false));
    }

    public void onBindViewHolder(final android.support.v7.widget.RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ViewHolder) {
            ViewHolder vh = (ViewHolder) holder;
            vh.mItem = (AvaibilityItem) this.mValues.get(position);
            vh.name.setText(vh.mItem.type.getValue());
            vh.isSelected.setChecked(vh.mItem.isChecked);
            vh.isSelected.setOnClickListener(new OnClickListener() {
                public void onClick(View v) {
                    ((ViewHolder) holder).mItem.isChecked = !((ViewHolder) holder).mItem.isChecked;
                }
            });
        }
    }

    public int getItemCount() {
        return this.mValues.size();
    }

    public class ViewHolder extends android.support.v7.widget.RecyclerView.ViewHolder {
        public final CheckBox isSelected;
        public final LinearLayout mLayout;
        public final TextView name;
        public AvaibilityItem mItem;

        public ViewHolder(View view) {
            super(view);
            this.mLayout = (LinearLayout) view;
            this.name = (TextView) view.findViewById(R.id.davaibility_name);
            this.isSelected = (CheckBox) view.findViewById(R.id.avaibility_checkBox);
        }

        public String toString() {
            return super.toString() + " '" + "'";
        }
    }
}

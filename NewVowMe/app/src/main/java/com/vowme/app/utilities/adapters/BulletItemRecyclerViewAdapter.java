package com.vowme.app.utilities.adapters;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView.Adapter;
import android.text.SpannableStringBuilder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.vowme.app.utilities.helpers.TextViewHelper;
import com.vowme.vol.app.R;

import java.util.List;

public class BulletItemRecyclerViewAdapter extends Adapter<android.support.v7.widget.RecyclerView.ViewHolder> {
    protected final Context context;
    protected final List<SpannableStringBuilder> mValues;
    protected Boolean isTextFormatted = Boolean.valueOf(false);

    public BulletItemRecyclerViewAdapter(List<SpannableStringBuilder> items, Context context) {
        this.mValues = items;
        this.context = context;
    }

    public BulletItemRecyclerViewAdapter(List<SpannableStringBuilder> items, Context context, Boolean isTextFormatted) {
        this.mValues = items;
        this.context = context;
        this.isTextFormatted = isTextFormatted;
    }

    public android.support.v7.widget.RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_bullet_text, parent, false));
    }

    public void onBindViewHolder(android.support.v7.widget.RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ViewHolder) {
            ViewHolder vh = (ViewHolder) holder;
            vh.bullet.setTextColor(ContextCompat.getColor(this.context, R.color.colorAccent));
            vh.mItem = (SpannableStringBuilder) this.mValues.get(position);
            if (this.isTextFormatted.booleanValue()) {
                String[] values = vh.mItem.toString().split(";");
                TextViewHelper.formatAvaibiliTySentence(this.context, vh.name, values[0], values[1], values[2]);
                return;
            }
            vh.name.setText(vh.mItem);
        }
    }

    public int getItemCount() {
        return this.mValues.size();
    }

    public class ViewHolder extends android.support.v7.widget.RecyclerView.ViewHolder {
        public final TextView bullet;
        public final TextView name;
        public SpannableStringBuilder mItem;

        public ViewHolder(View view) {
            super(view);
            this.name = (TextView) view.findViewById(R.id.item_name);
            this.bullet = (TextView) view.findViewById(R.id.item_bullet);
        }

        public String toString() {
            return super.toString() + " '" + "'";
        }
    }
}

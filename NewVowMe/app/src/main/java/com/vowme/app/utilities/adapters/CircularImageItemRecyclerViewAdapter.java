package com.vowme.app.utilities.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v7.widget.RecyclerView.Adapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.vowme.app.models.Enum.LookupType;
import com.vowme.app.models.lookUp.Lookup;
import com.vowme.app.utilities.helpers.DefaultDataHelper;
import com.vowme.app.utilities.helpers.ImageHelper;
import com.vowme.vol.app.R;

import java.util.List;

public class CircularImageItemRecyclerViewAdapter extends Adapter<android.support.v7.widget.RecyclerView.ViewHolder> {
    protected final Context context;
    protected final LookupType lookupType;
    protected final List<Lookup> mValues;

    public CircularImageItemRecyclerViewAdapter(List<Lookup> items, Context context, LookupType lookupType) {
        this.mValues = items;
        this.context = context;
        this.lookupType = lookupType;
    }

    public android.support.v7.widget.RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_circular_image, parent, false));
    }

    public void onBindViewHolder(android.support.v7.widget.RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ViewHolder) {
            Bitmap image;
            ViewHolder vh = (ViewHolder) holder;
            vh.mItem = (Lookup) this.mValues.get(position);
            if (this.lookupType == LookupType.CAUSES) {
                int resourceId = DefaultDataHelper.getImageCauseNames()[vh.mItem.getId() - 1];
                int largeSize = this.context.getResources().getInteger(R.integer.LARGE_SIZE);
                image = ImageHelper.createSquaredBitmap(ImageHelper.decodeSampledBitmapFromResource(this.context.getResources(), resourceId, largeSize, largeSize));
                vh.image.setBackground(this.context.getResources().getDrawable(R.drawable.circle_coloraccent));
            } else {
                image = ImageHelper.drawableToBitmap(this.context, DefaultDataHelper.getImageAvaibilityNames()[vh.mItem.getId() - 1]);
                vh.image.setBackground(this.context.getResources().getDrawable(R.drawable.circle_grey));
            }
            RoundedBitmapDrawable drawable = RoundedBitmapDrawableFactory.create(this.context.getResources(), image);
            drawable.setCircular(true);
            vh.image.setImageDrawable(drawable);
            vh.name.setText(vh.mItem.getName());
        }
    }

    public int getItemCount() {
        return this.mValues.size();
    }

    public class ViewHolder extends android.support.v7.widget.RecyclerView.ViewHolder {
        public final ImageView image;
        public final TextView name;
        public Lookup mItem;

        public ViewHolder(View view) {
            super(view);
            this.name = (TextView) view.findViewById(R.id.item_name);
            this.image = (ImageView) view.findViewById(R.id.circlular_image);
        }

        public String toString() {
            return super.toString() + " '" + "'";
        }
    }
}

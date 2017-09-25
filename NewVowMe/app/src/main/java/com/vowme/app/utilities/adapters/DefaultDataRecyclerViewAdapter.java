package com.vowme.app.utilities.adapters;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView.Adapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.twitter.sdk.android.core.TwitterApiErrorConstants;
import com.vowme.app.models.Enum.LookupType;
import com.vowme.app.models.lookUp.Lookup;
import com.vowme.app.models.lookUp.LookupDesc;
import com.vowme.app.utilities.helpers.DefaultDataHelper;
import com.vowme.app.utilities.helpers.ImageHelper;
import com.vowme.vol.app.R;

import java.util.List;

public class DefaultDataRecyclerViewAdapter extends Adapter<android.support.v7.widget.RecyclerView.ViewHolder> {
    private Context context;
    private List<Integer> idsChecked;
    private boolean isStartStep = false;
    private LookupType lookupType;
    private OnCheckBoxListener mListener;
    private List<?> mValues;
    private boolean showItemDescription;

    public DefaultDataRecyclerViewAdapter(List<?> items, OnCheckBoxListener mListener, List<Integer> idsChecked) {
        this.mValues = items;
        this.showItemDescription = false;
        this.mListener = mListener;
        this.idsChecked = idsChecked;
    }

    public DefaultDataRecyclerViewAdapter(List<?> items, OnCheckBoxListener mListener, List<Integer> idsChecked, boolean isStartStep, LookupType lookupType, Context context) {
        this.mValues = items;
        this.showItemDescription = false;
        this.mListener = mListener;
        this.idsChecked = idsChecked;
        this.isStartStep = isStartStep;
        this.lookupType = lookupType;
        this.context = context;
    }

    public void updateItems(List<?> items) {
        this.mValues = items;
    }

    public void updateIdsChecked(List<Integer> idsChecked) {
        this.idsChecked = idsChecked;
    }

    public List<Integer> getIdsChecked() {
        return this.idsChecked;
    }

    public android.support.v7.widget.RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == 0) {
            return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_default_data, parent, false));
        }
        return new StartStepViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_start_step, parent, false));
    }

    public int getItemViewType(int position) {
        if (this.isStartStep) {
            return 1;
        }
        return 0;
    }

    public void onBindViewHolder(final android.support.v7.widget.RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ViewHolder) {
            ViewHolder vh = (ViewHolder) holder;
            vh.mItem = (Lookup) this.mValues.get(position);
            vh.lookupName.setText(vh.mItem.getName());
            if ((vh.mItem instanceof LookupDesc) && this.showItemDescription) {
                vh.lookupDes.setText(((LookupDesc) vh.mItem).getDescription());
            } else {
                vh.lookupDes.setVisibility(View.GONE);
            }
            if (this.idsChecked.contains(new Integer(vh.mItem.getId()))) {
                vh.isSelected.setChecked(true);
            } else {
                vh.isSelected.setChecked(false);
            }
            vh.isSelected.setOnClickListener(new OnClickListener() {
                public void onClick(View v) {
                    if (DefaultDataRecyclerViewAdapter.this.idsChecked.contains(Integer.valueOf(((ViewHolder) holder).mItem.getId()))) {
                        DefaultDataRecyclerViewAdapter.this.idsChecked.remove(new Integer(((ViewHolder) holder).mItem.getId()));
                    } else {
                        DefaultDataRecyclerViewAdapter.this.idsChecked.add(Integer.valueOf(((ViewHolder) holder).mItem.getId()));
                    }
                    if (DefaultDataRecyclerViewAdapter.this.mListener != null) {
                        DefaultDataRecyclerViewAdapter.this.mListener.OnCheckBoxListenerInteraction(((ViewHolder) holder).mItem);
                    }
                }
            });
        } else if (holder instanceof StartStepViewHolder) {
            int resourceId;
            StartStepViewHolder vh2 = (StartStepViewHolder) holder;
            vh2.mItem = (Lookup) this.mValues.get(position);
            vh2.lookupName.setText(vh2.mItem.getName());
            vh2.isSelected.setOnClickListener(new OnClickListener() {
                public void onClick(View v) {
                    if (DefaultDataRecyclerViewAdapter.this.idsChecked.contains(Integer.valueOf(((StartStepViewHolder) holder).mItem.getId()))) {
                        DefaultDataRecyclerViewAdapter.this.idsChecked.remove(new Integer(((StartStepViewHolder) holder).mItem.getId()));
                    } else {
                        DefaultDataRecyclerViewAdapter.this.idsChecked.add(Integer.valueOf(((StartStepViewHolder) holder).mItem.getId()));
                    }
                }
            });
            if (this.lookupType == LookupType.CAUSES) {
                resourceId = DefaultDataHelper.getImageCauseNames()[vh2.mItem.getId() - 1];
            } else {
                resourceId = DefaultDataHelper.getImageIntrestNames()[vh2.mItem.getId() - 1];
            }
            vh2.image.setImageBitmap(ImageHelper.decodeSampledBitmapFromResource(this.context.getResources(), resourceId, TwitterApiErrorConstants.REGISTRATION_INVALID_INPUT, TwitterApiErrorConstants.REGISTRATION_INVALID_INPUT));
        }
    }

    public int getItemCount() {
        return this.mValues == null ? 0 : this.mValues.size();
    }

    public void setShowItemDescription(boolean showItemDescription) {
        this.showItemDescription = showItemDescription;
    }

    public interface OnCheckBoxListener {
        void OnCheckBoxListenerInteraction(Lookup lookup);
    }

    public class StartStepViewHolder extends android.support.v7.widget.RecyclerView.ViewHolder {
        public final ImageView image;
        public final CheckBox isSelected;
        public final TextView lookupName;
        public final CardView mLayout;
        public Lookup mItem;

        public StartStepViewHolder(View view) {
            super(view);
            this.mLayout = (CardView) view;
            this.isSelected = (CheckBox) view.findViewById(R.id.data_checkBox);
            this.lookupName = (TextView) view.findViewById(R.id.item_name);
            this.image = (ImageView) view.findViewById(R.id.rect_image);
        }

        public String toString() {
            return super.toString() + " '" + "'";
        }
    }

    public class ViewHolder extends android.support.v7.widget.RecyclerView.ViewHolder {
        public final CheckBox isSelected;
        public final TextView lookupDes;
        public final TextView lookupName;
        public final LinearLayout mLayout;
        public Lookup mItem;

        public ViewHolder(View view) {
            super(view);
            this.mLayout = (LinearLayout) view;
            this.isSelected = (CheckBox) view.findViewById(R.id.data_checkBox);
            this.lookupName = (TextView) view.findViewById(R.id.data_name);
            this.lookupDes = (TextView) view.findViewById(R.id.data_description);
        }

        public String toString() {
            return super.toString() + " '" + "'";
        }
    }
}

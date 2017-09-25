package com.vowme.app.utilities.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView.Adapter;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.vowme.app.models.SavedSearchOpportunitiesItem;
import com.vowme.app.utilities.helpers.TypefacesHelper;
import com.vowme.vol.app.R;
import com.vowme.vol.app.activities.search.BookmarksFragment;
import com.vowme.vol.app.activities.search.RecentsFragment;

import java.util.List;

public class SavedSearchesRecyclerViewAdapter extends Adapter<ViewHolder> {
    private static final int bookmarkType = 1;
    private static final int recentType = 0;
    protected final List<SavedSearchOpportunitiesItem> mValues;
    private final BookmarksFragment.OnBookmarkListFragmentInteractionListener mBookmarkListener;
    private final Context mComtext;
    private final RecentsFragment.OnRecentListFragmentInteractionListener mRecentListener;
    private boolean isShowen;

    public SavedSearchesRecyclerViewAdapter(List<SavedSearchOpportunitiesItem> items, Context comtext, RecentsFragment.OnRecentListFragmentInteractionListener recentListener) {
        this.mValues = items;
        this.mComtext = comtext;
        this.mRecentListener = recentListener;
        this.mBookmarkListener = null;
    }

    public SavedSearchesRecyclerViewAdapter(List<SavedSearchOpportunitiesItem> items, Context comtext, BookmarksFragment.OnBookmarkListFragmentInteractionListener bookmarkListener) {
        this.mValues = items;
        this.mComtext = comtext;
        this.mBookmarkListener = bookmarkListener;
        this.mRecentListener = null;
    }

    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == 0) {
            return new RecentViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.search_autocomplete_default, parent, false));
        }
        return new BookmarkViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_bookmark, parent, false));
    }

    public void onBindViewHolder(final ViewHolder holder, int position) {
        if (holder instanceof RecentViewHolder) {
            RecentViewHolder vh = (RecentViewHolder) holder;
            vh.mItem = (SavedSearchOpportunitiesItem) this.mValues.get(position);
            vh.itemText.setText(vh.mItem.getName());
            vh.icon.setTypeface(TypefacesHelper.get(this.mComtext, "fonts/material_icon_font.ttf"));
            vh.icon.setText(this.mComtext.getString(R.string.ic_access_time));
            vh.layout.setOnClickListener(new OnClickListener() {
                public void onClick(View v) {
                    if (SavedSearchesRecyclerViewAdapter.this.mRecentListener != null) {
                        SavedSearchesRecyclerViewAdapter.this.mRecentListener.onRecentListFragmentInteraction(((RecentViewHolder) holder).mItem);
                    }
                }
            });
        }
        if (holder instanceof BookmarkViewHolder) {
            BookmarkViewHolder vh2 = (BookmarkViewHolder) holder;
            vh2.mItem = (SavedSearchOpportunitiesItem) this.mValues.get(position);
            vh2.itemText.setText(vh2.mItem.getName());
            if (this.isShowen) {
                vh2.icon.setVisibility(View.VISIBLE);
                vh2.layout.setClickable(false);
                return;
            }
            vh2.icon.setVisibility(View.GONE);
            vh2.layout.setClickable(true);
            vh2.layout.setOnClickListener(new OnClickListener() {
                public void onClick(View v) {
                    if (SavedSearchesRecyclerViewAdapter.this.mBookmarkListener != null) {
                        SavedSearchesRecyclerViewAdapter.this.mBookmarkListener.onBookmarkListFragmentInteraction(((BookmarkViewHolder) holder).mItem);
                    }
                }
            });
        }
    }

    public void remove(int position) {
        this.mValues.remove(position);
        notifyItemRemoved(position);
    }

    public int getItemViewType(int position) {
        if (((SavedSearchOpportunitiesItem) this.mValues.get(position)).isFromRecents()) {
            return 0;
        }
        return 1;
    }

    public int getItemCount() {
        return this.mValues.size();
    }

    public void setIsShowen(boolean isShowen) {
        this.isShowen = isShowen;
    }

    public class BookmarkViewHolder extends ViewHolder {
        public final ImageView icon;
        public final TextView itemText;
        public final FrameLayout layout;
        public final FrameLayout mRemoveableView;
        public SavedSearchOpportunitiesItem mItem;

        public BookmarkViewHolder(View view) {
            super(view);
            this.layout = (FrameLayout) view.findViewById(R.id.item_bookmark_lyt);
            this.itemText = (TextView) view.findViewById(R.id.bookmark_name);
            this.icon = (ImageView) view.findViewById(R.id.reorder_ic);
            this.mRemoveableView = (FrameLayout) view.findViewById(R.id.removeable_layout);
        }

        public FrameLayout getSwipableView() {
            return this.mRemoveableView;
        }

        public String toString() {
            return super.toString() + " '" + "'";
        }
    }

    public class RecentViewHolder extends ViewHolder {
        public final TextView icon;
        public final TextView itemText;
        public final RelativeLayout layout;
        public SavedSearchOpportunitiesItem mItem;

        public RecentViewHolder(View view) {
            super(view);
            this.layout = (RelativeLayout) view.findViewById(R.id.search_autocomplete_iconlayout);
            this.itemText = (TextView) view.findViewById(R.id.search_autocomplete_title);
            this.icon = (TextView) view.findViewById(R.id.search_autocomplete_icon);
        }

        public String toString() {
            return super.toString() + " '" + "'";
        }
    }
}

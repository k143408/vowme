package com.vowme.vol.app.activities.search;

import android.content.Context;
import android.graphics.Canvas;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.support.v7.widget.helper.ItemTouchHelper.Callback;
import android.support.v7.widget.helper.ItemTouchHelper.SimpleCallback;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.vowme.app.models.SavedSearchOpportunitiesItem;
import com.vowme.app.utilities.adapters.SavedSearchesRecyclerViewAdapter;
import com.vowme.app.utilities.adapters.SavedSearchesRecyclerViewAdapter.BookmarkViewHolder;
import com.vowme.app.utilities.customWidgets.DividerItemDecoration;
import com.vowme.app.utilities.fragments.BaseFragment;
import com.vowme.vol.app.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class BookmarksFragment extends BaseFragment {
    protected OnBookmarkListFragmentInteractionListener mListener;
    private boolean isShowen;
    private SavedSearchesRecyclerViewAdapter mAdapter;
    private List<SavedSearchOpportunitiesItem> mItemValues;
    private LinearLayoutManager mLayoutManager;
    private RecyclerView mReciclerview;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.mItemValues = new ArrayList();
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_bookmark, container, false);
        if (view instanceof RecyclerView) {
            this.mReciclerview = (RecyclerView) view;
            this.mReciclerview.addItemDecoration(new DividerItemDecoration(getActivity(), 1));
            this.mReciclerview.setHasFixedSize(true);
            this.mLayoutManager = new LinearLayoutManager(this.mReciclerview.getContext());
            this.mReciclerview.setLayoutManager(this.mLayoutManager);
            this.mAdapter = new SavedSearchesRecyclerViewAdapter(this.mItemValues, getBaseActivity(), this.mListener);
            new ItemTouchHelper(new bookmarkTouchHelper(this.mAdapter)).attachToRecyclerView(this.mReciclerview);
            this.mReciclerview.setAdapter(this.mAdapter);
            getData();
        }
        return this.mReciclerview;
    }

    public void getData() {
        List<String> bookmarks = getBaseActivity().getBookmarks();
        this.mItemValues.clear();
        for (String recent : bookmarks) {
            try {
                SavedSearchOpportunitiesItem potentialSavedSearch = new SavedSearchOpportunitiesItem(new JSONObject(recent));
                potentialSavedSearch.setFromRecents(false);
                this.mItemValues.add(potentialSavedSearch);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        this.mAdapter.notifyDataSetChanged();
    }

    public void showReorderIcon(boolean isShowen) {
        this.isShowen = isShowen;
        this.mAdapter.setIsShowen(isShowen);
        this.mAdapter.notifyDataSetChanged();
    }

    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnBookmarkListFragmentInteractionListener) {
            this.mListener = (OnBookmarkListFragmentInteractionListener) context;
            return;
        }
        throw new RuntimeException(context.toString() + " must implement OnListFragmentInteractionListener");
    }

    public void onDetach() {
        super.onDetach();
        this.mListener = null;
    }

    public interface OnBookmarkListFragmentInteractionListener {
        void onBookmarkListFragmentInteraction(SavedSearchOpportunitiesItem savedSearchOpportunitiesItem);
    }

    private class bookmarkTouchHelper extends SimpleCallback {
        private SavedSearchesRecyclerViewAdapter mAdapter;

        public bookmarkTouchHelper(SavedSearchesRecyclerViewAdapter mAdapter) {
            super(0, 8);
            this.mAdapter = mAdapter;
        }

        public boolean onMove(RecyclerView recyclerView, ViewHolder viewHolder, ViewHolder target) {
            return false;
        }

        public int getMovementFlags(RecyclerView recyclerView, ViewHolder viewHolder) {
            if ((viewHolder instanceof BookmarkViewHolder) && BookmarksFragment.this.isShowen) {
                return Callback.makeMovementFlags(0, 48);
            }
            return 0;
        }

        public void clearView(RecyclerView recyclerView, ViewHolder viewHolder) {
            Callback.getDefaultUIUtil().clearView(((BookmarkViewHolder) viewHolder).getSwipableView());
        }

        public void onSelectedChanged(ViewHolder viewHolder, int actionState) {
            if (viewHolder != null) {
                Callback.getDefaultUIUtil().onSelected(((BookmarkViewHolder) viewHolder).getSwipableView());
            }
        }

        public void onChildDraw(Canvas c, RecyclerView recyclerView, ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
            Callback.getDefaultUIUtil().onDraw(c, recyclerView, ((BookmarkViewHolder) viewHolder).getSwipableView(), dX, dY, actionState, isCurrentlyActive);
        }

        public void onChildDrawOver(Canvas c, RecyclerView recyclerView, ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
            Callback.getDefaultUIUtil().onDrawOver(c, recyclerView, ((BookmarkViewHolder) viewHolder).getSwipableView(), dX, dY, actionState, isCurrentlyActive);
        }

        public void onSwiped(ViewHolder viewHolder, int direction) {
            if (BookmarksFragment.this.mItemValues.size() != 0) {
                this.mAdapter.remove(viewHolder.getAdapterPosition());
                if (viewHolder instanceof BookmarkViewHolder) {
                    BookmarksFragment.this.getBaseActivity().deleteSearchItemFromBookmark(((BookmarkViewHolder) viewHolder).mItem.toJsonObject().toString());
                }
            }
        }
    }
}

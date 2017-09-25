package com.vowme.app.utilities.customWidgets;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ItemDecoration;
import android.support.v7.widget.RecyclerView.State;
import android.view.View;

public class VerticalSpaceItemDecoration extends ItemDecoration {
    private final int mVerticalSpaceHeight;

    public VerticalSpaceItemDecoration(int mVerticalSpaceHeight) {
        this.mVerticalSpaceHeight = mVerticalSpaceHeight;
    }

    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, State state) {
        outRect.bottom = this.mVerticalSpaceHeight;
    }
}

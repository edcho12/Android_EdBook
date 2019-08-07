package com.be.edbook.view;

import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.be.edbook.R;


public class CustomDividerItemDecoration extends RecyclerView.ItemDecoration {

    private Drawable mDivider;
    private Resources resources;

    public CustomDividerItemDecoration(Resources resources) {
        this.resources = resources;
        mDivider = this.resources.getDrawable(R.drawable.divider);

    }

    @Override
    public void onDrawOver(@NonNull Canvas c, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        int left = this.resources.getDimensionPixelSize(R.dimen.margin_80);
        int right = parent.getWidth() - this.resources.getDimensionPixelSize(R.dimen.margin_10);


        int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View child = parent.getChildAt(i);

            RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child.getLayoutParams();

            int top = child.getBottom() + params.bottomMargin;
            int bottom = top + mDivider.getIntrinsicHeight();

            mDivider.setBounds(left, top, right, bottom);
            mDivider.draw(c);
        }
    }
}

package com.rsbunda.myenviro.home;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.ViewHolder;

import static android.graphics.Paint.ANTI_ALIAS_FLAG;

public class DividerDecoration extends RecyclerView.ItemDecoration {

    private static final int[] ATTRS =
            new int[] { android.R.attr.dividerHeight, android.R.attr.divider };
    private final Paint mDividerPaint = new Paint(ANTI_ALIAS_FLAG);
    private final float mHalfHeight;

    public DividerDecoration(float dividerHeight, @ColorInt int dividerColor) {
        mDividerPaint.setStrokeWidth(dividerHeight);
        mHalfHeight = dividerHeight / 2f;
        mDividerPaint.setColor(dividerColor);
    }

    public DividerDecoration(@NonNull Context context) {
        final TypedArray a = context.obtainStyledAttributes(ATTRS);
        float dividerHeight = a.getDimension(0, 0f);
        mDividerPaint.setStrokeWidth(dividerHeight);
        mHalfHeight = dividerHeight / 2f;
        mDividerPaint.setColor(a.getColor(1, 0xff00ff));
        a.recycle();
    }

    @Override
    public void onDraw(Canvas canvas, RecyclerView rv, RecyclerView.State state) {
        int count = rv.getChildCount();
        if (count < 2) return;
        float[] points = new float[count * 4];
        boolean previousItemNeedsDivider = false;

        LinearLayoutManager layoutManager = (LinearLayoutManager) rv.getLayoutManager();
        int firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition();
        for (int i = 0; i < count; i++) {
            ViewHolder holder = rv.findViewHolderForAdapterPosition(firstVisibleItemPosition + i);
            boolean needsDivider = holder instanceof Divided;
            if (previousItemNeedsDivider && needsDivider) {
                points[4 * i] = layoutManager.getDecoratedLeft(holder.itemView);
                float top = layoutManager.getDecoratedTop(holder.itemView)
                        + holder.itemView.getTranslationY() + mHalfHeight;
                points[(4 * i) + 1] = top;
                points[(4 * i) + 2] = layoutManager.getDecoratedRight(holder.itemView);
                points[(4 * i) + 3] = top;
            }
            previousItemNeedsDivider = needsDivider;
        }
        canvas.drawLines(points, mDividerPaint);
    }

    /**
     * Empty marker interface, used to denote a {@link ViewHolder} as requiring a divider.
     */
    public interface Divided { }

}

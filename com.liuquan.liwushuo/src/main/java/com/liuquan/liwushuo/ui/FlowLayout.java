package com.liuquan.liwushuo.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by PC on 2016/3/28.
 */
public class FlowLayout extends ViewGroup {

    private int paddingHorizontal;
    private int paddingVertical;

    public FlowLayout(Context context) {
        super(context);
        init();
    }

    public FlowLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
        init();
    }

    public FlowLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    private void init() {
        paddingHorizontal = this.getPaddingLeft()+0;
        paddingVertical = this.getPaddingTop()+0;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int mHeight = 0;

        int childLeft = getPaddingLeft();
        int childTop = getPaddingTop();
        int myWidth = resolveSize(getWidth(), widthMeasureSpec);
        int wantedHeight = 0;

        for (int i = 0; i < getChildCount(); i++) {

            final View thisChild = getChildAt(i);
            if (thisChild.getVisibility() == View.GONE) {
                continue;
            }

            thisChild.measure(getChildMeasureSpec(widthMeasureSpec, 0, thisChild.getLayoutParams().width),
                    getChildMeasureSpec(heightMeasureSpec, 0, thisChild.getLayoutParams().height));

            int childWidth = thisChild.getMeasuredWidth();
            int childHeight = thisChild.getMeasuredHeight();

            mHeight = Math.max(childHeight, mHeight);

            if (childWidth + childLeft + getPaddingRight() > myWidth) {
                childLeft = getPaddingLeft();
                childTop += paddingVertical + mHeight;
                mHeight = childHeight;
            }

            childLeft += childWidth + paddingHorizontal;
        }
        wantedHeight += childTop + mHeight + getPaddingBottom();
        setMeasuredDimension(myWidth, resolveSize(wantedHeight, heightMeasureSpec));
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        int childLeft = getPaddingLeft();
        int childTop = getPaddingTop();
        int lineHeight = 0;

        int myWidth = right - left;

        for (int i = 0; i < getChildCount(); i++) {
            final View child = getChildAt(i);
            if (child.getVisibility() == View.GONE) {
                continue;
            }
            int childWidth = child.getMeasuredWidth();
            int childHeight = child.getMeasuredHeight();
            lineHeight = Math.max(childHeight, lineHeight);
            if (childWidth + childLeft + getPaddingRight() > myWidth) {
                childLeft = getPaddingLeft();
                childTop += paddingVertical + lineHeight;
                lineHeight = childHeight;
            }
            child.layout(childLeft, childTop, childLeft + childWidth, childTop + childHeight);
            childLeft += childWidth + paddingHorizontal;
        }
    }
}


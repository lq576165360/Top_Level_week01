package com.liuquan.liwushuo.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.GridView;

/**
 * Created by PC on 2016/3/21.
 */
public class MyGruidView extends GridView {
    public MyGruidView(Context context) {
        super(context);
    }

    public MyGruidView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyGruidView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int measureSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, measureSpec);
    }
}

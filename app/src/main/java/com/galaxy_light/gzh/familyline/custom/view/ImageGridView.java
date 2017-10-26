package com.galaxy_light.gzh.familyline.custom.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.GridView;

/**
 * 图片GridView
 * Created by gzh on 2017-10-26.
 */

public class ImageGridView extends GridView {
    public ImageGridView(Context context) {
        super(context);
    }

    public ImageGridView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ImageGridView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);
    }
}

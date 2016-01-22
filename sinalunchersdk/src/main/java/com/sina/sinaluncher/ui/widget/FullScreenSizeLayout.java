package com.sina.sinaluncher.ui.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.widget.RelativeLayout;

/**
 * Created by sinash94857 on 2016/1/12.
 */
public class FullScreenSizeLayout extends RelativeLayout {

    private int widthMeasureSpec;
    private int heightMeasureSpec;
    public FullScreenSizeLayout(Context context, AttributeSet attrs){
        super(context, attrs);
        DisplayMetrics metrics =context.getResources().getDisplayMetrics();
        widthMeasureSpec =MeasureSpec.makeMeasureSpec( metrics.widthPixels,MeasureSpec.EXACTLY);
        heightMeasureSpec =MeasureSpec.makeMeasureSpec( metrics.heightPixels,MeasureSpec.EXACTLY);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(this.widthMeasureSpec, this.heightMeasureSpec);
    }
}

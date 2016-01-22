package com.sina.sinaluncher.ui.widget;

import android.content.Context;
import android.util.AttributeSet;

import com.sina.sinaluncher.R;
import com.sina.sinaluncher.ui.GridViewAdaper;
import com.sina.sinaluncher.ui.IItemMeasureListener;
import com.sina.sinaluncher.utils.Preferences;

/**
 * Created by sinash94857 on 2016/1/8.
 */
public class AutoHeightGridView extends HeaderFooterGridView {

    private GridViewAdaper adaper;
    private int mMaxHeight = 0;
    private int mHeight;
    private int mWidth;

    public AutoHeightGridView(Context context) {
        super(context);
        mWidth = getResources().getDimensionPixelSize(R.dimen.dialog_fragment_grid_width);
        mMaxHeight = Preferences.getCacheItemHeight(getContext());
    }

    public AutoHeightGridView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mWidth = getResources().getDimensionPixelSize(R.dimen.dialog_fragment_grid_width);
        mMaxHeight = Preferences.getCacheItemHeight(getContext());
    }

    public void setAdapter(GridViewAdaper adapter,final Runnable readyRunable) {
        this.adaper = adapter;
        if(mMaxHeight <=0 ) {
            adaper.setItemMeasureListener(new IItemMeasureListener() {
                @Override
                public void onMeasure(int height, int paddingTop, int paddingBottom) {
                    mMaxHeight = height * 3 + paddingTop + paddingBottom;
                    Preferences.saveItemHeight(getContext(), mMaxHeight);
                    post(new Runnable() {
                        @Override
                        public void run() {
                            mHeight = getMeasuredHeight();
                            requestLayout();
                            post(readyRunable);
                        }
                    });
                    adaper.clearItemMeasureListener();
                }
            });
        }else{
            post(readyRunable);
        }
        super.setAdapter(adapter);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int newHeightMeasureSpec = MeasureSpec.makeMeasureSpec(mMaxHeight, MeasureSpec.EXACTLY);
        int newWidthMeasureSpec = MeasureSpec.makeMeasureSpec(mWidth,MeasureSpec.EXACTLY);
        super.onMeasure(newWidthMeasureSpec, newHeightMeasureSpec);
    }
    private int mNumColumns;

    public int getNumColumns(){
        return mNumColumns;
    }

    @Override
    public void setNumColumns(int numColumns) {
        super.setNumColumns(numColumns);
        mNumColumns = numColumns;
    }
}

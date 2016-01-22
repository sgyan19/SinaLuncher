package com.sina.sinaluncher.ui;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ImageView;

import com.sina.sinaluncher.R;
import com.sina.sinaluncher.core.SALInfo;
import com.sina.sinaluncher.utils.Utils;
import com.sina.sinaluncher.ui.widget.AutoHeightGridView;
import com.sina.sinaluncher.ui.widget.LetterSpacingTextView;

import java.util.List;

/**
 * Created by sinash94857 on 2016/1/7.
 */
public class MainDialogFragment extends DialogFragment implements AdapterView.OnItemClickListener{

    private AutoHeightGridView mGridView;
    private ViewGroup mRootView;
    private ViewGroup mGridContainer;
    private ImageView mDismissView;
    private Bitmap mBackgroundReady = null;
    private boolean mGridMeasureReady = false;

    public MainDialogFragment(){
        super();
        //setStyle(DialogFragment.STYLE_NORMAL, android.R.style.Theme_Material_NoActionBar_TranslucentDecor);
        //setStyle(DialogFragment.STYLE_NORMAL, android.R.style.Theme_Light_Panel);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        getDialog().getWindow().setBackgroundDrawable(new
                ColorDrawable(Color.TRANSPARENT));

        View view = inflater.inflate(R.layout.sal_dialog_fragment,container,false);
        return view;
    }

    @Override
    public void onViewCreated(View view,  Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mGridView = (AutoHeightGridView) view.findViewById(R.id.sal_gridview);
        mGridContainer = (ViewGroup) view.findViewById(R.id.grid_container);
        List<SALInfo> data = Utils.getTestData();
        Utils.improveAppsInfo(getActivity(), data);

        mGridView.addHeaderView(makeHeaderOrFooterView());
        mGridView.addFooterView(makeHeaderOrFooterView());
        mGridView.setAdapter(new GridViewAdaper(getActivity(), data), new Runnable() {
            @Override
            public void run() {
                mGridMeasureReady = true;
                readyToShow();
            }
        });
        mGridView.setOnItemClickListener(this);
        mRootView = (ViewGroup) view.findViewById(R.id.container);
        mDismissView = (ImageView)view.findViewById(R.id.dismiss);
        mDismissView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        final LetterSpacingTextView v = (LetterSpacingTextView)view.findViewById(R.id.title);
        v.setSpacing(2);
        v.setText(getResources().getString(R.string.main_fragment_title));

//        mRootView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                dismiss();
//            }
//        });
        // 高斯处理
//        GaussianBlur.getAsyncBlur(getActivity(), new GaussianBlur.BlurEvent() {
//            @Override
//            public void onBlurPictureReady(Bitmap bmp) {
//                mBackgroundReady = bmp;
//                readyToShow();
//            }
//        });
    }

    private View makeHeaderOrFooterView(){
        View v = new View(getActivity());
        DisplayMetrics metrics = getResources().getDisplayMetrics();
        int size = getResources().getDimensionPixelSize(R.dimen.dialog_fragment_grid_vertical_padding);
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,size);
        v.setLayoutParams(params);
        return v;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        position = position + mGridView.getNumColumns() * mGridView.getHeaderViewCount();
        SALInfo info = (SALInfo)mGridView.getAdapter().getItem(position);
        if(info.isInstall){
            Utils.jumpApp(getActivity(),info);
        }else{
            Utils.jumpMarket(getActivity(),info);
        }
        dismiss();
    }

    private void readyToShow(){
        mRootView.setVisibility(View.VISIBLE);
    }
//    private void readyToShow(){
//        if(mBackgroundReady != null && mGridMeasureReady) {
//            mRootView.setBackgroundDrawable(new BitmapDrawable( mBackgroundReady));
//            mGridContainer.setVisibility(View.VISIBLE);
//            mBackgroundReady = null;
//        }
//        //mRootView.setBackground(new ColorDrawable(0xffffffff));
//    }
}

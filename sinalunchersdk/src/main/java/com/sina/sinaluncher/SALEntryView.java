package com.sina.sinaluncher;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.support.v4.app.FragmentActivity;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.ImageView;

import com.sina.sinaluncher.core.SALInfo;
import com.sina.sinaluncher.ui.GridViewAdaper;
import com.sina.sinaluncher.ui.MainDialogFragment;
import com.sina.sinaluncher.utils.Utils;

/**
 * Created by sinash94857 on 2016/1/7.
 */
public class SALEntryView extends FrameLayout implements View.OnClickListener{

    private OnClickListener outListener;
    private ImageView mainBtn;
    public SALEntryView(Context context){
        super(context);
        setVisibility(INVISIBLE);
        init(context);
    }

    public SALEntryView(Context context,int resLayout){
        super(context);
        setVisibility(INVISIBLE);
        init(context,resLayout);
    }

    public SALEntryView(Context context, AttributeSet attrs){
        super(context, attrs);
        setVisibility(INVISIBLE);
        init(context);
    }

    private void init(Context context){
        LayoutInflater.from(context).inflate(R.layout.sal_entry, this, true);
        mainBtn = (ImageView) findViewById(R.id.main_button);
        Handler handler = new Handler();
        Global.getInstance().init((Activity) context, new Runnable() {
            @Override
            public void run() {
                switch (Global.getInstance().getEntryStatus()) {
                    case SALInfo.ENTRY_STATUS_SHOW:
                        setVisibility(VISIBLE);
                        mainBtn.setOnClickListener(null);
                        break;
                    case SALInfo.ENTRY_STATUS_HIDE:
                        // TODO:隐藏时的逻辑
                         setVisibility(INVISIBLE);
                         mainBtn.setOnClickListener(null);
                         break;
                    case SALInfo.ENTRY_STATUS_USE:
                        setVisibility(VISIBLE);
                        mainBtn.setOnClickListener(SALEntryView.this);
                        GridViewAdaper.resetIfExist(Global.getInstance().getAppList());
                        break;
                }
            }
        }, handler);
    }

    private void init(Context context,int resLayout){
        LayoutInflater.from(context).inflate(resLayout, this, true);
        setOnClickListener(this);
    }

    @Override
    public void setOnClickListener(OnClickListener l) {
        //super.setOnClickListener(l);
        outListener = l;
    }

    @Override
    public void onClick(View v) {
        MainDialogFragment f = new MainDialogFragment();
        f.show(((FragmentActivity)getContext()).getSupportFragmentManager(),"MainDialogFragment");
        if(outListener!=null){
            outListener.onClick(this);
        }
    }
}

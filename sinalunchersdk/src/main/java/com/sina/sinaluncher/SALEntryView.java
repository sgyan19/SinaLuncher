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
import android.widget.ImageView;

import com.sina.sinaluncher.ui.MainDialogFragment;

/**
 * Created by sinash94857 on 2016/1/7.
 */
public class SALEntryView extends FrameLayout implements View.OnClickListener{

    private OnClickListener outListener;

    public SALEntryView(Context context){
        super(context);
        init(context);
    }

    public SALEntryView(Context context,int resLayout){
        super(context);
        init(context,resLayout);
    }

    public SALEntryView(Context context, AttributeSet attrs){
        super(context,attrs);
        init(context);
    }

    private void init(Context context){
        LayoutInflater.from(context).inflate(R.layout.sal_entry, this, true);
        Handler handler = new Handler();
        Global.getInstance().init((Activity) context, new Runnable() {
            @Override
            public void run() {

            }
        }, handler);
        ImageView mainBtn = (ImageView) findViewById(R.id.main_button);
        mainBtn.setOnClickListener(this);
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

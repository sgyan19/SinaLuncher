package com.sina.sinaluncher.ui;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.sina.sinaluncher.core.SALInfo;
import com.sina.sinaluncher.R;
import com.sina.sinaluncher.ui.widget.CheckableLayout;

import java.util.List;

/**
 * Created by sinash94857 on 2016/1/7.
 */
public class GridViewAdaper extends BaseArrayAdapter<SALInfo,GridItemHolder> {

    public void setItemMeasureListener(IItemMeasureListener listener){
        CheckableLayout.listener = listener;
    }

    public void clearItemMeasureListener(){
        CheckableLayout.listener = null;
    }

    public GridViewAdaper(Context context, List<SALInfo> objects){
        super(context, R.layout.sal_grid_item,objects);
    }

    @Override
    protected GridItemHolder createHolder() {
        return new GridItemHolder();
    }

    @Override
    protected void initHolder(int position, View v, GridItemHolder holder) {
        holder.mIconView = (ImageView)v.findViewById(R.id.icon);
        holder.mNameView = (TextView) v.findViewById(R.id.name);
        holder.mCheckableView = (CheckableLayout) v.findViewById(R.id.container);
        holder.mBlackShadow = (ImageView) v.findViewById(R.id.black_shadow);
        holder.mShadow =(ImageView) v.findViewById(R.id.shadow);
    }

    @Override
    protected void initParamsHolder(int position, GridItemHolder holder, SALInfo item) {
    }

    @Override
    protected void bundleValue(int position, GridItemHolder holder, SALInfo item) {
        if(item.appIconDrawable != null)
            holder.mIconView.setImageDrawable(item.appIconDrawable);
        holder.mNameView.setText(item.appName);
        holder.mCheckableView.setChecked(item.isInstall);
        if(item.self){
            holder.mShadow.setImageResource(R.drawable.sal_self_shadow);
            holder.mShadow.setVisibility(View.VISIBLE);
            holder.mBlackShadow.setVisibility(View.INVISIBLE);
        }else if(!item.isInstall){
            holder.mShadow.setImageResource(R.drawable.sal_uninstall_shadow);
            holder.mShadow.setVisibility(View.VISIBLE);
            holder.mBlackShadow.setVisibility(View.VISIBLE);
        }else{
            holder.mShadow.setVisibility(View.INVISIBLE);
            holder.mBlackShadow.setVisibility(View.INVISIBLE);
        }
    }
}

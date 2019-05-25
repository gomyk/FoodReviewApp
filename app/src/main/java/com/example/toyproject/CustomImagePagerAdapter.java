package com.example.toyproject;

import android.content.Context;
import android.net.Uri;
import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.toyproject.utils.ImageUtil;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

class CustomImagePagerAdapter extends PagerAdapter {
    final String TAG = "CustomImagePagerAdapter";
    Context mContext;
    LayoutInflater mLayoutInflater;
    List<Uri> mResources;

    public CustomImagePagerAdapter(Context context) {
        mContext = context;
        mResources = new ArrayList<>();
        mLayoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public void setImageResource(List<Uri> resources) {
        mResources = resources;
    }

    @Override
    public int getCount() {
        return mResources.size();
    }

    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == ((LinearLayout) object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View itemView = mLayoutInflater.inflate(R.layout.pager_item, container, false);
        ImageView imageView = (ImageView) itemView.findViewById(R.id.review_imageView);
        try{
            imageView.setImageBitmap(ImageUtil.decodeUriWithResize(mContext,mResources.get(position),256));
        } catch(FileNotFoundException e){
            e.printStackTrace();
        } finally{
            Log.d(TAG,"ImageLoaded : " +mResources.get(position));
        }

        container.addView(itemView);
        return itemView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((LinearLayout) object);
    }
}
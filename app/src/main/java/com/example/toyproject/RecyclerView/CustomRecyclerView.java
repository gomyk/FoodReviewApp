package com.example.toyproject.RecyclerView;


import android.graphics.Bitmap;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.example.toyproject.Database.ReviewItem;
import com.example.toyproject.utils.CommonContextHolder;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class CustomRecyclerView {

    private static final String TAG = "CustomRecyclerView";
    protected RecyclerView mRecyclerView;
    protected CustomRecyclerViewAdapter mAdapter;
    protected RecyclerView.LayoutManager mLayoutManager;

    public CustomRecyclerView(RecyclerView recyclerView) {
        mRecyclerView = recyclerView;
        mLayoutManager = new LinearLayoutManager(CommonContextHolder.getContext());
    }

    public void Initialize() {
        ExecutorService executorService = Executors.newFixedThreadPool(2);
        executorService.submit(new Runnable() {
            @Override
            public void run() {
                mAdapter = new CustomRecyclerViewAdapter();
                mRecyclerView.setAdapter(mAdapter);
                mRecyclerView.setLayoutManager(mLayoutManager);
            }
        });
    }

    public void setVisibility(boolean visibility) {
        if (visibility) {
            mRecyclerView.setVisibility(RecyclerView.VISIBLE);
        } else {
            mRecyclerView.setVisibility(RecyclerView.INVISIBLE);
        }
    }

    public void setDataSet(List<ReviewItem> reviewItemList) {
        mAdapter.setData(reviewItemList);

    }
}
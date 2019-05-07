package com.example.toyproject.RecyclerView;


import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.example.toyproject.utils.CommonContextHolder;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class CustomRecyclerView {

    private static final String TAG = "CustomRecyclerView";
    protected RecyclerView mRecyclerView;
    protected CustomRecyclerViewAdapter mAdapter;
    protected RecyclerView.LayoutManager mLayoutManager;
    protected String[] mDataset;
    private static final int DATASET_COUNT = 60;

    public CustomRecyclerView(RecyclerView recyclerView) {
        mRecyclerView = recyclerView;
        mLayoutManager = new LinearLayoutManager(CommonContextHolder.getContext());
    }

    public void Initialize() {
        ExecutorService executorService = Executors.newFixedThreadPool(2);
        executorService.submit(new Runnable() {
            @Override
            public void run() {
                initDataset();
                mAdapter = new CustomRecyclerViewAdapter(mDataset);
                mRecyclerView.setAdapter(mAdapter);
                mRecyclerView.setLayoutManager(mLayoutManager);
            }
        });
    }


    private void initDataset() {
        mDataset = new String[DATASET_COUNT];
        for (int i = 0; i < DATASET_COUNT; i++) {
            mDataset[i] = "This is donghyun #" + i;
        }
    }
}
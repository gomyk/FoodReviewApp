package com.example.toyproject.RecyclerView;


import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.toyproject.utils.CommonContextHolder;

import java.util.concurrent.Executor;


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
        Thread thread = new Thread(() -> {
            initDataset();
            mAdapter = new CustomRecyclerViewAdapter(mDataset);
            mRecyclerView.setAdapter(mAdapter);
            mRecyclerView.setLayoutManager(mLayoutManager);
        });
        thread.run();
    }


    private void initDataset() {
        mDataset = new String[DATASET_COUNT];
        for (int i = 0; i < DATASET_COUNT; i++) {
            mDataset[i] = "This is donghyun #" + i;
        }
    }
}
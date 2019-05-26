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
    private RecyclerViewListener mRecyclerViewListener;
    public CustomRecyclerView(RecyclerView recyclerView) {
        mRecyclerView = recyclerView;
        mLayoutManager = new LinearLayoutManager(CommonContextHolder.getContext());
    }

    public void setRecyclerViewListener(RecyclerViewListener recyclerViewListener) {
        mRecyclerViewListener = recyclerViewListener;
    }

    public void Initialize() {
        ExecutorService executorService = Executors.newFixedThreadPool(2);
        executorService.submit(new Runnable() {
            @Override
            public void run() {
                mAdapter = new CustomRecyclerViewAdapter();
                mAdapter.setRecyclerViewAdapterListener(new CustomRecyclerViewAdapter.RecyclerViewAdapterListener() {
                    @Override
                    public void onItemTouched(ReviewItem item) {
                        mRecyclerViewListener.onItemTouched(item);
                    }
                });
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
    public interface RecyclerViewListener {
        public void onItemTouched(ReviewItem item);
    }
}
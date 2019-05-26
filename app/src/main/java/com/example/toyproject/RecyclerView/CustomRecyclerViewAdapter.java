package com.example.toyproject.RecyclerView;

import android.graphics.Bitmap;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.toyproject.Database.ReviewItem;
import com.example.toyproject.Database.ReviewItemDao;
import com.example.toyproject.R;
import com.example.toyproject.utils.CommonContextHolder;
import com.example.toyproject.utils.ImageUtil;
import com.example.toyproject.utils.ReviewDataManager;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;


public class CustomRecyclerViewAdapter extends RecyclerView.Adapter<CustomRecyclerViewAdapter.ViewHolder> {
    private static final String TAG = "CustomRecyclerViewAdapter";
    protected List<ReviewItem> mReviewSet;

    public CustomRecyclerViewAdapter() {
        mReviewSet = new ArrayList<>();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView text_title;
        public TextView text_comment;
        public TextView text_rate;
        public ImageView image;
        public Button delete_button;

        public ViewHolder(View v) {
            super(v);
            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d(TAG, "Element " + getAdapterPosition() + " clicked.");
                    //call back
                }
            });
            text_title = (TextView) v.findViewById(R.id.row_text_title);
            text_comment = (TextView) v.findViewById(R.id.row_text_comment);
            text_rate = (TextView) v.findViewById(R.id.row_text_rating);
            image = (ImageView) v.findViewById(R.id.row_imageView);
            delete_button = (Button) v.findViewById(R.id.row_delete_button);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.row_item, viewGroup, false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {
        Log.d(TAG, "Element " + position + " set.");
        viewHolder.delete_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Delete object
                ReviewDataManager.sDatabase.getItemDAO().delete(mReviewSet.get(position));
                mReviewSet.remove(position);
                notifyDataSetChanged();
                return;
            }
        });
        Float rating = mReviewSet.get(position).getRating();
        viewHolder.text_title.setText(mReviewSet.get(position).getTitle());
        viewHolder.text_comment.setText(mReviewSet.get(position).getComment());
        viewHolder.text_rate.setText(rating.toString());
        if (!mReviewSet.get(position).getImages().isEmpty()) {
            //Todo : cannot get permission for getting image from provider , so need to implement with http url
        }
    }

    @Override
    public int getItemCount() {
        return mReviewSet.size();
    }

    void setData(List<ReviewItem> reviewItems) {
        mReviewSet = reviewItems;
        notifyDataSetChanged();
    }
}

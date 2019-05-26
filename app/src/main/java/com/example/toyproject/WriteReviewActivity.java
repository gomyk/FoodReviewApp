package com.example.toyproject;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Toast;

import com.example.toyproject.AccountManager.UserAccountDataHolder;
import com.example.toyproject.Database.ReviewItem;
import com.example.toyproject.Database.ReviewItemDao;
import com.example.toyproject.utils.CommonContracts;
import com.example.toyproject.utils.ReviewDataManager;

import java.util.ArrayList;
import java.util.List;

public class WriteReviewActivity extends AppCompatActivity {
    private Context mContext;
    private RatingBar mRatingBar;
    private float mRating;
    private Button mSubmitButton;
    private EditText mMenuTitle;
    private EditText mMenuComment;
    private ViewPager mViewPager;
    private List<Uri> mImageList;
    private CustomImagePagerAdapter mPagerAdapter;
    private Button mImageSelect;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write_review);
        mContext = this;
        mImageList = new ArrayList<>();
        mRatingBar = findViewById(R.id.menu_rating);
        mMenuTitle = findViewById(R.id.menu_review_name);
        mMenuComment = findViewById(R.id.menu_review_comment);
        mSubmitButton = findViewById(R.id.review_upload_button);
        mViewPager = (ViewPager) findViewById(R.id.review_viewPager);
        mPagerAdapter = new CustomImagePagerAdapter(this);
        mViewPager.setOffscreenPageLimit(5);
        mViewPager.setAdapter(mPagerAdapter);
        mViewPager.setPageMargin(20);
        mImageSelect = findViewById(R.id.review_image_select_button);
        mRatingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            public void onRatingChanged(RatingBar ratingBar, float rating,
                                        boolean fromUser) {
                mRating = rating;
            }
        });
        mSubmitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkValidationAndSubmit();
            }
        });
        mImageSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mImageList.clear();
                mPagerAdapter.setImageResource(mImageList);
                mPagerAdapter.notifyDataSetChanged();
                openGallery(CommonContracts.REVIEW_IMAGE_SELECT);
            }
        });
    }

    private void openGallery(int req_code) {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
        startActivityForResult(Intent.createChooser(intent,
                "Select file to upload "), req_code);
    }

    private void checkValidationAndSubmit() {
        //Save in room
        ReviewItemDao itemDAO = ReviewDataManager.sDatabase.getItemDAO();
        ReviewItem item = new ReviewItem();
        item.setAuthor(UserAccountDataHolder.sId);
        item.setTitle(mMenuTitle.getText().toString());
        item.setComment(mMenuComment.getText().toString());
        item.setRating(mRatingBar.getRating());
        item.setImages(convertUriListToStringList(mImageList));
        item.setLongitude(getIntent().getExtras().getDouble("longitude"));
        item.setLatitude(getIntent().getExtras().getDouble("latitude"));
        itemDAO.insert(item);
        setResult(CommonContracts.SUCCESS);
        finish();
    }

    private List<String> convertUriListToStringList(List<Uri> uriList){
        List<String> ret = new ArrayList<>();
        for(int i=0;i<uriList.size();++i){
            ret.add(uriList.get(i).toString());
        }
        return ret;
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case CommonContracts.REVIEW_IMAGE_SELECT: {
                if (null != data) {
                    if (null != data.getClipData()) { //multiple data
                        int size = data.getClipData().getItemCount();
                        if(size > 5){
                            Toast.makeText(this,"Number of images can't over 5",Toast.LENGTH_SHORT).show();
                            size = 5;
                        }
                        for (int i = 0; i < size; ++i) {
                                mImageList.add(data.getClipData().getItemAt(i).getUri());
                        }
                    } else {
                        mImageList.add(data.getData());
                    }
                    mPagerAdapter.setImageResource(mImageList);
                    mPagerAdapter.notifyDataSetChanged();
                }
            }
        }
    }
}

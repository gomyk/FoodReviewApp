package com.example.toyproject;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Handler;
import android.app.Activity;
import android.os.Bundle;
import android.widget.ImageView;

public class SplashActivity extends Activity {
    private Handler mWaitHandler = new Handler();
    private ImageView mContainer;
    private AnimationDrawable mAnimationDrawable;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        mContainer = findViewById(R.id.iv_icons);
        mContainer.setBackgroundResource(R.drawable.splash_animation);

        mAnimationDrawable = (AnimationDrawable) mContainer.getBackground();
    }
    @Override
    protected void onResume() {
        super.onResume();
        mAnimationDrawable.start();
        checkAnimationStatus(50, mAnimationDrawable);
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        mWaitHandler.removeCallbacksAndMessages(null);
    }
    private void checkAnimationStatus(final int time, final AnimationDrawable animationDrawable) {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (animationDrawable.getCurrent() != animationDrawable.getFrame(animationDrawable.getNumberOfFrames() - 1)){
                    checkAnimationStatus(time, animationDrawable);
                } else{
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        }, time);
    }
}
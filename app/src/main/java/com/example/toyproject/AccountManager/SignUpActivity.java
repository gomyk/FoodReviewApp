package com.example.toyproject.AccountManager;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;
import com.example.toyproject.MainActivity;
import com.example.toyproject.R;
import com.example.toyproject.utils.CommonContracts;
import com.kakao.auth.ApiResponseCallback;
import com.kakao.network.ErrorResult;

import com.kakao.usermgmt.ApiErrorCode;
import com.kakao.usermgmt.UserManagement;
import com.kakao.usermgmt.callback.MeV2ResponseCallback;
import com.kakao.usermgmt.response.MeV2Response;
import com.kakao.usermgmt.response.model.User;
import com.kakao.usermgmt.response.model.UserAccount;
import com.kakao.util.OptionalBoolean;

import java.io.InputStream;
import java.util.Map;

public class SignUpActivity extends Activity {
    private final String TAG = "SignupActivity";
    private ImageLoader mImageLoader;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestMe();
    }

    protected void showSignup() {
        setContentView(R.layout.activity_sign_up);
        final ExtraUserPropertyLayout extraUserPropertyLayout = findViewById(R.id.extra_user_property);
        Button signupButton = findViewById(R.id.buttonSignup);
        signupButton.setOnClickListener(view -> requestSignUp(extraUserPropertyLayout.getProperties()));
    }

    private void requestSignUp(final Map<String, String> properties) {
        UserManagement.getInstance().requestSignup(new ApiResponseCallback<Long>() {
            @Override
            public void onNotSignedUp() {
            }

            @Override
            public void onSuccess(Long result) {
                requestMe();
            }

            @Override
            public void onFailure(ErrorResult errorResult) {
                final String message = "UsermgmtResponseCallback : failure : " + errorResult;
                com.kakao.util.helper.log.Logger.w(message);
                Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
                finish();
            }

            @Override
            public void onSessionClosed(ErrorResult errorResult) {
            }
        }, properties);
    }

    protected void requestMe() {
        UserManagement.getInstance().me(new MeV2ResponseCallback() {
            @Override
            public void onFailure(ErrorResult errorResult) {
                String message = "failed to get user info. msg=" + errorResult;
                Log.d(TAG, message);

                int result = errorResult.getErrorCode();
                if (result == ApiErrorCode.CLIENT_ERROR_CODE) {
                    Toast.makeText(getApplicationContext(), "service not available", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    //redirectLoginActivity();
                }
            }

            @Override
            public void onSessionClosed(ErrorResult errorResult) {
                Log.e(TAG, "onSessionClosed");
                //redirectLoginActivity();
            }

            @Override
            public void onSuccess(MeV2Response result) {
                if (result.hasSignedUp() == OptionalBoolean.FALSE) {
                    showSignup();
                } else {
                    UserAccountDataHolder.setId(result.getId());
                    UserAccountDataHolder.setNickName(result.getNickname());
                    UserAccountDataHolder.setProfileImagePath(result.getProfileImagePath());
                    UserAccountDataHolder.setThumbnailPath(result.getThumbnailImagePath());
                    UserAccountDataHolder.setUserProperty(result.getProperties());
                    redirectMainActivity();
                }
            }
        });
    }

    private void redirectMainActivity() {
        Toast.makeText(getApplicationContext(), "Already logged in by kakao account", Toast.LENGTH_LONG).show();
        setResult(CommonContracts.LOGIN_SUCCESS, null);
        finish();
    }


}
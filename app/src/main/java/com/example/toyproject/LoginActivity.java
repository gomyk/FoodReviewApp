package com.example.toyproject;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

public class LoginActivity extends AppCompatActivity {

    private Button mLogin;
    private Button mCancel;
    private EditText mEmail;
    private EditText mPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mLogin = findViewById(R.id.button_login);
        mCancel = findViewById(R.id.button_cancel);
        mEmail = findViewById(R.id.email_input);
        mPassword = findViewById(R.id.password_input);

        mLogin.setOnClickListener(view -> {
            Intent returnIntent = new Intent();
            //Todo : Request to login server
            returnIntent.putExtra("result", true);
            returnIntent.putExtra("id", mEmail.getText());
            setResult(Activity.RESULT_OK, returnIntent);
            finish();
        });
        mCancel.setOnClickListener(view -> {
            setResult(Activity.RESULT_CANCELED, null);
            finish();
        });
    }
}

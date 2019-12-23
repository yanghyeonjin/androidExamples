package com.yanghyeonjin.androidexamples;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

public class FirebaseGoogleLoginSuccess extends AppCompatActivity {

    private TextView tvFirebaseGoogleLoginResult;
    private ImageView ivFirebaseGoogleLoginProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_firebase_google_login_success);

        /* 아이디 연결 */
        tvFirebaseGoogleLoginResult = findViewById(R.id.tv_firebase_google_login_result);
        ivFirebaseGoogleLoginProfile = findViewById(R.id.iv_firebase_google_login_profile);

        Intent intent = getIntent();
        String displayName = intent.getStringExtra("displayName");
        String photoURL = intent.getStringExtra("photoURL");

        tvFirebaseGoogleLoginResult.setText(displayName);
        Glide.with(this)
                .load(photoURL)
                .into(ivFirebaseGoogleLoginProfile);
    }
}

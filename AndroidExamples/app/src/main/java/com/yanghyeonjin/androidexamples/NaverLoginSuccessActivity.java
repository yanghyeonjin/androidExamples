package com.yanghyeonjin.androidexamples;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

public class NaverLoginSuccessActivity extends AppCompatActivity {

    private String naverID, naverNickname, naverName, naverEmail, naverGender, naverAge, naverBirthday, naverProfileImage;
    private TextView tvNaverID, tvNaverNickname, tvNaverName, tvNaverEmail, tvNaverGender, tvNaverAge, tvNaverBirthday;
    private ImageView ivNaverProfileImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_naver_login_success);

        Intent intent = getIntent();
        naverID = intent.getStringExtra("naverID");
        naverNickname = intent.getStringExtra("naverNickname");
        naverName = intent.getStringExtra("naverName");
        naverEmail = intent.getStringExtra("naverEmail");
        naverGender = intent.getStringExtra("naverGender");
        naverAge = intent.getStringExtra("naverAge");
        naverBirthday = intent.getStringExtra("naverBirthday");
        naverProfileImage = intent.getStringExtra("naverProfileImage");

        /* 아이디 연결 */
        tvNaverID = findViewById(R.id.tv_naver_id);
        tvNaverNickname = findViewById(R.id.tv_naver_nickname);
        tvNaverName = findViewById(R.id.tv_naver_name);
        tvNaverEmail = findViewById(R.id.tv_naver_email);
        tvNaverGender = findViewById(R.id.tv_naver_gender);
        tvNaverAge = findViewById(R.id.tv_naver_age);
        tvNaverBirthday =findViewById(R.id.tv_naver_birthday);
        ivNaverProfileImage = findViewById(R.id.iv_naver_profile_image);

        /* 사용자 정보 보여주기 */
        tvNaverID.setText(naverID);
        tvNaverNickname.setText(naverNickname);
        tvNaverName.setText(naverName);
        tvNaverEmail.setText(naverEmail);
        tvNaverGender.setText(naverGender);
        tvNaverAge.setText(naverAge);
        tvNaverBirthday.setText(naverBirthday);

        Glide.with(this)
                .load(naverProfileImage)
                .into(ivNaverProfileImage);

        ivNaverProfileImage.setOnClickListener(view -> {
            Intent bigImageIntent = new Intent(NaverLoginSuccessActivity.this, BigImageActivity.class);
            bigImageIntent.putExtra("bigImage", naverProfileImage);
            startActivity(bigImageIntent);
        });
    }
}

package com.yanghyeonjin.androidexamples;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.kakao.usermgmt.LoginButton;

public class KakaoLoginActivity extends AppCompatActivity {

    private LoginButton kakaoLoginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kakao_login);

        /* 아이디 연결 */
        kakaoLoginButton = findViewById(R.id.btn_kakaologin);
    }
}

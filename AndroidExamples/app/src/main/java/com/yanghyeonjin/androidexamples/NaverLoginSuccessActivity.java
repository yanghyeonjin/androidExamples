package com.yanghyeonjin.androidexamples;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.nhn.android.naverlogin.OAuthLogin;
import com.nhn.android.naverlogin.data.OAuthLoginState;

import java.lang.ref.WeakReference;

public class NaverLoginSuccessActivity extends AppCompatActivity {

    private String naverID, naverNickname, naverName, naverEmail, naverGender, naverAge, naverBirthday, naverProfileImage;
    private TextView tvNaverID, tvNaverNickname, tvNaverName, tvNaverEmail, tvNaverGender, tvNaverAge, tvNaverBirthday;
    private ImageView ivNaverProfileImage;
    private Button btnLogoutNaver, btnDeleteNaverAccount;

    private static OAuthLogin mOAuthLoginModule;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_naver_login_success);

        mOAuthLoginModule = OAuthLogin.getInstance();

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
        btnLogoutNaver = findViewById(R.id.btn_logout_naver);
        btnDeleteNaverAccount = findViewById(R.id.btn_delete_naver_account);

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

        /* 로그아웃 */
        btnLogoutNaver.setOnClickListener(view -> {
            mOAuthLoginModule.logout(NaverLoginSuccessActivity.this);
            OAuthLoginState naverState = mOAuthLoginModule.getState(NaverLoginSuccessActivity.this);
            if (naverState.equals(OAuthLoginState.NEED_LOGIN)) {

                redirectNaverLogin();
            }
        });

        /* 네이버 연동 해제 */
        btnDeleteNaverAccount.setOnClickListener(view -> naverUnlink());
    }

    private void redirectNaverLogin() {
        Intent intent = new Intent(this, NaverLoginActivity.class);
        startActivity(intent);
        finish();
    }

    public void naverUnlink() {
        new DeleteTokenTask().execute();
    }

    private class DeleteTokenTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {
            boolean isSuccessDeleteToken =  mOAuthLoginModule.logoutAndDeleteToken(NaverLoginSuccessActivity.this);

            if (!isSuccessDeleteToken) {
                // 서버에서 토큰 삭제에 실패했어도 클라이언트에 있는 토큰은 삭제되어 로그아웃된 상태입니다.
                // 클라이언트에 토큰 정보가 없기 때문에 추가로 처리할 수 있는 작업은 없습니다.
                Log.d("NaverLoginSuccess", "errorCode:" + mOAuthLoginModule.getLastErrorCode(NaverLoginSuccessActivity.this));
                Log.d("NaverLoginSuccess", "errorDesc:" + mOAuthLoginModule.getLastErrorDesc(NaverLoginSuccessActivity.this));

            } else {
                /* 네이버 연동 해제 성공 */
                redirectNaverLogin();
            }
            return null;
        }
    }
}

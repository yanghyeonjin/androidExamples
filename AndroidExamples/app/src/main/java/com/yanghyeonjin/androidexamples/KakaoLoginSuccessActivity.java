package com.yanghyeonjin.androidexamples;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.kakao.network.ErrorResult;
import com.kakao.usermgmt.UserManagement;
import com.kakao.usermgmt.callback.LogoutResponseCallback;
import com.kakao.usermgmt.callback.UnLinkResponseCallback;
import com.kakao.util.helper.log.Logger;

public class KakaoLoginSuccessActivity extends AppCompatActivity {

    private TextView tvKakaoID, tvKakaoEmail, tvKakaoBirth, tvKakaoPhone, tvKakaoAge, tvKakaoGender, tvIsKakaoTalkUser, tvIsEmailValid, tvIsEmailVerified, tvKakaoNickname;
    private String kakaoID, kakaoEmail, kakaoBirth, kakaoPhone, kakaoAge, kakaoGender, isKakaoTalkUser, isEmailValid, isEmailVerified, kakaoNickname, kakaoProfile;
    private Button logoutKakaoButton, deleteKakaoAccountButton;
    private ImageView ivKakaoProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kakao_login_success);

        /* 아이디 연결 */
        tvKakaoID = findViewById(R.id.tv_kakao_id);
        tvKakaoEmail = findViewById(R.id.tv_kakao_email);
        tvKakaoBirth = findViewById(R.id.tv_kakao_birth);
        tvKakaoPhone = findViewById(R.id.tv_kakao_phone);
        tvKakaoAge = findViewById(R.id.tv_kakao_age);
        tvKakaoGender = findViewById(R.id.tv_kakao_gender);
        tvIsKakaoTalkUser = findViewById(R.id.tv_is_kakaotalk_user);
        tvIsEmailValid = findViewById(R.id.tv_is_email_valid);
        tvIsEmailVerified = findViewById(R.id.tv_is_email_verified);
        logoutKakaoButton = findViewById(R.id.btn_logout_kakao);
        deleteKakaoAccountButton = findViewById(R.id.btn_delete_kakao_account);
        tvKakaoNickname = findViewById(R.id.tv_kakao_nickname);
        ivKakaoProfile = findViewById(R.id.iv_kakao_profile);

        /* 데이터 받기 */
        Intent intent = getIntent();
        kakaoID = intent.getStringExtra("kakaoID");
        kakaoEmail = intent.getStringExtra("kakaoEmail");
        kakaoBirth = intent.getStringExtra("kakaoBirth");
        kakaoPhone = intent.getStringExtra("kakaoPhone");
        kakaoAge = intent.getStringExtra("kakaoAge");
        kakaoGender = intent.getStringExtra("kakaoGender");
        isKakaoTalkUser = intent.getStringExtra("isKakaoTalkUser");
        isEmailValid = intent.getStringExtra("isEmailValid");
        isEmailVerified = intent.getStringExtra("isEmailVerified");
        kakaoNickname = intent.getStringExtra("kakaoNickname");
        kakaoProfile = intent.getStringExtra("kakaoProfile");

        /* 받은 데이터 보여주기 */
        tvKakaoID.setText(kakaoID);
        tvKakaoEmail.setText(kakaoEmail);
        tvKakaoBirth.setText(kakaoBirth);
        tvKakaoPhone.setText(kakaoPhone);
        tvKakaoAge.setText(kakaoAge);
        tvKakaoGender.setText(kakaoGender);
        tvIsKakaoTalkUser.setText(isKakaoTalkUser);
        tvIsEmailValid.setText(isEmailValid);
        tvIsEmailVerified.setText(isEmailVerified);
        tvKakaoNickname.setText(kakaoNickname);
        Glide.with(this)
                .load(kakaoProfile)
                .into(ivKakaoProfile);



        /* 로그아웃 클릭 */
        logoutKakaoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickLogout();
            }
        });

        /* 회원탈퇴 클릭 */
        deleteKakaoAccountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickUnlink();
            }
        });
    }

    /* 카카오 계정 로그아웃 */
    private void onClickLogout() {
        UserManagement.getInstance().requestLogout(new LogoutResponseCallback() {
            @Override
            public void onCompleteLogout() {
                redirectLoginActivity();
            }
        });
    }

    /* 로그인 화면으로 */
    private void redirectLoginActivity() {
        Intent intent = new Intent(this, KakaoLoginActivity.class);
        startActivity(intent);
        finish();
    }

    /* 카카오 계정 회원탈퇴 */
    private void onClickUnlink() {
        final String appendMessage = getString(R.string.com_kakao_confirm_unlink);
        new AlertDialog.Builder(this)
                .setMessage(appendMessage)
                .setPositiveButton(getString(R.string.com_kakao_ok_button),
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                UserManagement.getInstance().requestUnlink(new UnLinkResponseCallback() {
                                    @Override
                                    public void onFailure(ErrorResult errorResult) {
                                        Logger.e(errorResult.toString());
                                    }

                                    @Override
                                    public void onSessionClosed(ErrorResult errorResult) {
                                        redirectLoginActivity();
                                    }

                                    @Override
                                    public void onNotSignedUp() {
                                        // redirectSignUpActivity();
                                    }

                                    @Override
                                    public void onSuccess(Long userId) {
                                        redirectLoginActivity();
                                    }
                                });
                                dialog.dismiss();
                            }
                        })
                .setNegativeButton(getString(R.string.com_kakao_cancel_button),
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        }).show();
    }
}

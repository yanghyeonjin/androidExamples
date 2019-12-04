package com.yanghyeonjin.androidexamples;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.kakao.auth.ISessionCallback;
import com.kakao.auth.Session;
import com.kakao.network.ErrorResult;
import com.kakao.usermgmt.LoginButton;
import com.kakao.usermgmt.UserManagement;
import com.kakao.usermgmt.callback.MeV2ResponseCallback;
import com.kakao.usermgmt.response.MeV2Response;
import com.kakao.util.exception.KakaoException;

import java.util.ArrayList;
import java.util.List;

public class KakaoLoginActivity extends AppCompatActivity {

    private LoginButton kakaoLoginButton; // 카카오계정으로 로그인 버튼
    private SessionCallback kakaoCallback; // 콜백 선언

    final static String LOG_TAG = "KakaoLogin";

    /**
     * 로그인 버튼을 클릭 했을시 access token을 요청하도록 설정한다.
     *
     * @param savedInstanceState 기존 session 정보가 저장된 객체
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kakao_login);

        /* 아이디 연결 */
        kakaoLoginButton = findViewById(R.id.btn_kakaologin);

        /* 카카오 로그인 콜백받기 */
        kakaoCallback = new SessionCallback();
        Session.getCurrentSession().addCallback(kakaoCallback);
        Session.getCurrentSession().checkAndImplicitOpen();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (Session.getCurrentSession().handleActivityResult(requestCode, resultCode, data)) {
            return;
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Session.getCurrentSession().removeCallback(kakaoCallback);
    }

    private class SessionCallback implements ISessionCallback {

        @Override
        public void onSessionOpened() {
            requestMe();
        }

        @Override
        public void onSessionOpenFailed(KakaoException exception) {
            if (exception != null) {
                Log.e(LOG_TAG, String.valueOf(exception));
            }
            setContentView(R.layout.activity_kakao_login); // 세션 연결이 실패 했을 때, 로그인 화면을 다시 불러옴
        }
    }

    /* 유저의 정보를 받아오는 함수 */
    private void requestMe() {
        List<String> keys = new ArrayList<>();
        keys.add("properties.nickname");
        keys.add("properties.profile_image");
        keys.add("properties.thumbnail_image");
        keys.add("kakao_account.profile");
        keys.add("kakao_account.email");
        keys.add("kakao_account.age_range");
        keys.add("kakao_account.birthday");
        keys.add("kakao_account.gender");

        UserManagement.getInstance().me(keys, new MeV2ResponseCallback() {
            @Override
            public void onFailure(ErrorResult errorResult) {
                String message = "failed to get user info. msg=" + errorResult;
                Log.d(LOG_TAG, message);
            }

            /* 세션 오픈 실패. 세션이 삭제된 경우 */
            @Override
            public void onSessionClosed(ErrorResult errorResult) {
                redirectLoginActivity();
            }

            /* 사용자 정보 요청에 성공한 경우 */
            @Override
            public void onSuccess(MeV2Response response) {
                Log.e(LOG_TAG, "onSuccess");

                /* 고유아이디, 이메일, 생일, 휴대폰번호, 연령대, 성별 */
                /* 카카오톡 유저 확인, 이메일 유효, 이메일 인증 */
                String kakaoID = String.valueOf(response.getId());
                String kakaoEmail = response.getKakaoAccount().getEmail();
                String kakaoBirth = response.getKakaoAccount().getBirthday();
                String kakaoPhone = response.getKakaoAccount().getPhoneNumber();
                String kakaoAge = String.valueOf(response.getKakaoAccount().getAgeRange());
                String kakaoGender = String.valueOf(response.getKakaoAccount().getGender());
                String isKakaoTalkUser = String.valueOf(response.getKakaoAccount().isKakaoTalkUser());
                String isEmailValid = String.valueOf(response.getKakaoAccount().isEmailValid());
                String isEmailVerified = String.valueOf(response.getKakaoAccount().isEmailVerified());
                String kakaoNickname = response.getKakaoAccount().getProfile().getNickname();
                String kakaoProfile = response.getKakaoAccount().getProfile().getProfileImageUrl();

                Intent intent = new Intent(KakaoLoginActivity.this, KakaoLoginSuccessActivity.class);
                intent.putExtra("kakaoID", kakaoID);
                intent.putExtra("kakaoEmail", kakaoEmail);
                intent.putExtra("kakaoBirth", kakaoBirth);
                intent.putExtra("kakaoPhone", kakaoPhone);
                intent.putExtra("kakaoAge", kakaoAge);
                intent.putExtra("kakaoGender", kakaoGender);
                intent.putExtra("isKakaoTalkUser", isKakaoTalkUser);
                intent.putExtra("isEmailValid", isEmailValid);
                intent.putExtra("isEmailVerified", isEmailVerified);
                intent.putExtra("kakaoNickname", kakaoNickname);
                intent.putExtra("kakaoProfile", kakaoProfile);
                startActivity(intent);
                finish();

            }
        });
    }

    private void redirectLoginActivity() {
        Intent intent = new Intent(this, KakaoLoginActivity.class);
        startActivity(intent);
        finish();
    }
}

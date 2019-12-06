package com.yanghyeonjin.androidexamples;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.facebook.CallbackManager;
import com.facebook.login.LoginManager;

public class FacebookLoginSuccessActivity extends AppCompatActivity {

    private String facebookID, facebookName;
    private TextView tvFacebookID, tvFacebookName;
    private Button btnLogoutFacebook, btnDeleteFacebookAccount;

    private static final String LOG_TAG = "FacebookLoginSuccess";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_facebook_login_success);

        /* 아이디 연결 */
        tvFacebookID = findViewById(R.id.tv_facebook_id);
        tvFacebookName = findViewById(R.id.tv_facebook_name);
        btnLogoutFacebook = findViewById(R.id.btn_logout_facebook);
        btnDeleteFacebookAccount = findViewById(R.id.btn_delete_facebook_account);


        /* 인텐트 전달받기 */
        Intent intent = getIntent();
        if (intent != null) {
            facebookID = intent.getStringExtra("facebookID");
            facebookName = intent.getStringExtra("facebookName");

            /* 데이터 보여주기 */
            tvFacebookID.setText(facebookID);
            tvFacebookName.setText(facebookName);
        } else {
            Log.e(LOG_TAG, "intent is null");
        }

        /* 로그아웃 */
        btnLogoutFacebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LoginManager.getInstance().logOut();
                redirectFacebookLogin();
            }
        });

        /* 회원탈퇴 */
        btnDeleteFacebookAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

    void redirectFacebookLogin() {
        Intent intent = new Intent(this, FacebookLoginActivity.class);
        startActivity(intent);
        finish();
    }
}

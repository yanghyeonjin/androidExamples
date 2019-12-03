package com.yanghyeonjin.androidexamples;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

public class LoginSuccessActivity extends AppCompatActivity {

    private TextView tv_id, tv_password, tv_name, tv_age;
    private static final int INT_DEFAULT_VAL = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_success);

        /* 아이디 연결 */
        tv_id = findViewById(R.id.tv_id);
        tv_password = findViewById(R.id.tv_password);
        tv_name = findViewById(R.id.tv_name);
        tv_age = findViewById(R.id.tv_age);

        /* 전달된 인텐트 받기 */
        Intent intent = getIntent();
        String userID = intent.getStringExtra("userID");
        String userPassword = intent.getStringExtra("userPassword");
        String userName = intent.getStringExtra("userName");
        String userAge = String.valueOf(intent.getIntExtra("userAge", INT_DEFAULT_VAL));

        Log.d("IntentData", "ID: " + userID + "Password: " + userPassword + "Name: " + userName + "Age: " + userAge);

        /* 레이아웃에 뿌리기 */
        tv_id.setText(userID);
        tv_password.setText(userPassword);
        tv_name.setText(userName);
        tv_age.setText(userAge);
    }
}

package com.yanghyeonjin.androidexamples;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;

import java.util.List;

public class SMSReceiverActivity extends AppCompatActivity {

    private EditText etPhoneNum, etSMSContents, etTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sms_receiver);

        /* 권한설정 */
        TedPermission.with(this)
                .setPermissionListener(permissionListener)
                .setRationaleMessage("문자메시지 내용을 가져오기 위하여 권한을 허용해주세요.")
                .setDeniedMessage("권한이 거부되었습니다. 설정 > 권한에서 허용해주세요.")
                .setPermissions(Manifest.permission.RECEIVE_SMS)
                .check();

        /* 아이디 연결 */
        etPhoneNum = findViewById(R.id.et_phone_num);
        etSMSContents = findViewById(R.id.et_sms_contents);
        etTime = findViewById(R.id.et_time);

        Intent passedIntent = getIntent();
        processIntent(passedIntent);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        processIntent(intent);
    }

    private void processIntent(Intent intent) {
        if (intent != null) {
            String sender = intent.getStringExtra("sender");
            String contents = intent.getStringExtra("contents");
            String receivedDate = intent.getStringExtra("receivedDate");

            etPhoneNum.setText(sender);
            etSMSContents.setText(contents);
            etTime.setText(receivedDate);
        }
    }

    /* 권한 설정 리스너 */
    PermissionListener permissionListener = new PermissionListener() {
        @Override
        public void onPermissionGranted() {
            Toast.makeText(getApplicationContext(), "문자 권한 허가", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onPermissionDenied(List<String> deniedPermissions) {
            Toast.makeText(getApplicationContext(), "문자 권한 거부", Toast.LENGTH_SHORT).show();
        }
    };
}

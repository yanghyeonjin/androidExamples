package com.yanghyeonjin.androidexamples;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;

public class SnackbarAlertDialogActivity extends AppCompatActivity {

    private TextView tvMsgClickButton;
    private Button btnShowAlertDialog, btnShowSnackbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_snackbar_alert_dialog);

        /* 아이디 연결 */
        tvMsgClickButton = findViewById(R.id.tv_msg_click_button);
        btnShowAlertDialog = findViewById(R.id.btn_show_alert_dialog);
        btnShowSnackbar = findViewById(R.id.btn_show_snackbar);


        btnShowAlertDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showAlertDialog();
            }
        });


        btnShowSnackbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar snackbar = Snackbar.make(view, "스낵바입니다.", Snackbar.LENGTH_LONG);
                View sbView = snackbar.getView();

                /* 스낵바 배경색 지정 */
                sbView.setBackgroundColor(Color.BLACK);

                /* 스낵바 글자색 지정 */
                TextView tvSnackbar = sbView.findViewById(com.google.android.material.R.id.snackbar_text);
                tvSnackbar.setTextColor(Color.WHITE);

                snackbar.show();
            }
        });
    }

    private void showAlertDialog() {
        // 대화상자를 만들기 위한 빌더 객체 생성
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("안내");
        builder.setMessage("종료하시겠습니까?");
        builder.setIcon(android.R.drawable.ic_dialog_alert);
        builder.setPositiveButton("예", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                String message = "예 버튼이 눌렸습니다. ";
                tvMsgClickButton.setText(message);
            }
        });

        builder.setNeutralButton("취소", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                String message = "취소 버튼이 눌렸습니다. ";
                tvMsgClickButton.setText(message);
            }
        });

        builder.setNegativeButton("아니오", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                String message = "아니오 버튼이 눌렸습니다. ";
                tvMsgClickButton.setText(message);
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }
}

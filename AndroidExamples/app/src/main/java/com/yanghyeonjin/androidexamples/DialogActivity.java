package com.yanghyeonjin.androidexamples;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class DialogActivity extends AppCompatActivity {

    private Button btn_dialog;
    private TextView tv_dialog_result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dialog);

        btn_dialog = findViewById(R.id.btn_dialog);
        tv_dialog_result = findViewById(R.id.tv_dialog_result);

        btn_dialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(DialogActivity.this);
                builder.setIcon(R.mipmap.ic_launcher_round);
                builder.setTitle("홍드로이드 다이얼로그");
                builder.setMessage("가장 좋아하는 음식은?");

                final EditText editText = new EditText(DialogActivity.this);
                builder.setView(editText); // 동적으로 뷰 만들기

                builder.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String result = editText.getText().toString();
                        tv_dialog_result.setText(result);
                        dialogInterface.dismiss();
                    }
                });

                builder.setNegativeButton("취소", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });

                builder.show(); // show를 해주어야 다이얼로그 나타남.
            }
        });
    }
}

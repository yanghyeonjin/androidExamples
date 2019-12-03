package com.yanghyeonjin.androidexamples;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

public class CheckBoxActivity extends AppCompatActivity {

    private CheckBox chk_red, chk_blue, chk_green;
    private TextView tv_chk_result;
    private Button btn_chk_result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_box);

        /* 아이디 연결 */
        chk_red = findViewById(R.id.chk_red);
        chk_blue = findViewById(R.id.chk_blue);
        chk_green = findViewById(R.id.chk_green);
        tv_chk_result = findViewById(R.id.tv_chk_result);
        btn_chk_result = findViewById(R.id.btn_chk_result);


        /* 결과버튼을 클릭했을 때 */
        btn_chk_result.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String str_chk_result = ""; // null 아니고 공백. 결과 값을 매 번 초기화

                if (chk_red.isChecked()) {
                    str_chk_result += chk_red.getText().toString();
                }
                if (chk_blue.isChecked()) {
                    str_chk_result += chk_blue.getText().toString();
                }
                if (chk_green.isChecked()) {
                    str_chk_result += chk_green.getText().toString();
                }

                tv_chk_result.setText(str_chk_result);

            }
        });
    }
}

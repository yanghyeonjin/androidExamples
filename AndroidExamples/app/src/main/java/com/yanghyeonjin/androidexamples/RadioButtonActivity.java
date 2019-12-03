package com.yanghyeonjin.androidexamples;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

public class RadioButtonActivity extends AppCompatActivity {

    private RadioGroup rg_gender;
    private RadioButton rb_man, rb_woman;
    private Button btn_radio_result;
    private String radio_result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_radio_button);

        rg_gender = findViewById(R.id.rg_gender); // 라디오 버튼들을 담고있는 그룹
        rb_man = findViewById(R.id.rb_man); // 라디오 버튼
        rb_woman = findViewById(R.id.rb_woman); // 라디오 버튼
        btn_radio_result = findViewById(R.id.btn_radio_result); // 결과 값을 출력하라는 신호를 보낼 버튼

        rg_gender.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() { // 라디오 버튼들의 상태 값의 변경됨을 감지
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if (i == R.id.rb_man) {
                    Toast.makeText(RadioButtonActivity.this, "남자 클릭", Toast.LENGTH_SHORT).show();
                    radio_result = rb_man.getText().toString();
                } else if (i == R.id.rb_woman) {
                    Toast.makeText(RadioButtonActivity.this, "여자 클릭", Toast.LENGTH_SHORT).show();
                    radio_result = rb_woman.getText().toString();
                }
            }
        });

        btn_radio_result.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (radio_result != null) {
                    Toast.makeText(RadioButtonActivity.this, "결과: " + radio_result, Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(RadioButtonActivity.this, "성별을 선택해주세요", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }
}

package com.yanghyeonjin.androidexamples;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class ComebackSubActivity extends AppCompatActivity {

    private EditText et_comeback_sub;
    private Button btn_close;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comeback_sub);

        et_comeback_sub = findViewById(R.id.et_comeback_sub);
        btn_close = findViewById(R.id.btn_close);

        btn_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.putExtra("result", et_comeback_sub.getText().toString()); // 입력폼에 적은 값 담아주기
                setResult(RESULT_OK, intent);
                finish(); // 현재 액티비티 종료
            }
        });
    }
}

package com.yanghyeonjin.androidexamples;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.yanghyeonjin.androidexamples.model.Example;

public class AddExampleActivity extends AppCompatActivity {

    private EditText titleEditText;
    private Button addExampleButton, backButton;
    private TextView idTextView, categoryTextView;
    private String exampleID, category;

    private DatabaseReference database;
    private DatabaseReference exampleTable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_example);

        /* 아이디 연결 */
        addExampleButton = findViewById(R.id.btn_add_example);
        titleEditText = findViewById(R.id.et_title);
        idTextView = findViewById(R.id.tv_id);
        backButton = findViewById(R.id.btn_back);
        categoryTextView = findViewById(R.id.tv_category);

        database = FirebaseDatabase.getInstance().getReference(); // 파이어베이스 데이터베이스 연동
        exampleTable = database.child("example"); // DB 테이블 연결

        Intent intent = getIntent();
        exampleID = intent.getStringExtra("exampleID");
        category = intent.getStringExtra("category");
        idTextView.setText(exampleID); // 형 변환해서 추가되는 예제 아이디 표시
        categoryTextView.setText(category);


        // 예제 추가 버튼 눌렀을 때
        addExampleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String addCategory = category;
                String addTitle = titleEditText.getText().toString();
                String addImage = "";

                /* 카테고리 별 이미지 지정 */
                if (addCategory.equals("홍드로이드")) {
                    addImage = "https://firebasestorage.googleapis.com/v0/b/androidexamples-e5752.appspot.com/o/hongdroid.jpg?alt=media&token=047f1ba3-c0bf-44c6-837a-c721f02a659d";
                } else if (addCategory.equals("Do it! 개정 5판")) {
                    addImage = "https://firebasestorage.googleapis.com/v0/b/androidexamples-e5752.appspot.com/o/doit.jpg?alt=media&token=ec6dd4be-d3fa-4f65-909f-357a2eb41e12";
                } else if (addCategory.equals("기타")) {
                    addImage = "https://firebasestorage.googleapis.com/v0/b/androidexamples-e5752.appspot.com/o/guitar.jpg?alt=media&token=38bacac4-d3c9-4eed-b3c5-c31adb51ab3d";
                }
                Example example = new Example(addImage, addCategory, addTitle);
                exampleTable.child(exampleID).setValue(example).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(AddExampleActivity.this, "예제 추가 성공", Toast.LENGTH_SHORT).show();
                            finish(); // 뒤로가기 (메인화면으로)
                        } else {
                            Toast.makeText(AddExampleActivity.this, "예제 추가 실패", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });

        // 뒤로가기 버튼 눌렀을 때
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}

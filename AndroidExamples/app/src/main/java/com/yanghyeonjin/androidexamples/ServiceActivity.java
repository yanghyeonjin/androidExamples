package com.yanghyeonjin.androidexamples;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.yanghyeonjin.androidexamples.service.MyService;

public class ServiceActivity extends AppCompatActivity {

    private EditText et_service;
    private Button btn_send_to_service;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service);

        et_service = findViewById(R.id.et_service);
        btn_send_to_service = findViewById(R.id.btn_send_to_service);

        Intent passedIntent = getIntent();
        processIntent(passedIntent);

        btn_send_to_service.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = et_service.getText().toString();

                Intent intent = new Intent(ServiceActivity.this, MyService.class);
                intent.putExtra("command", "show");
                intent.putExtra("name", name);
                startService(intent);
            }
        });
    }

    @Override
    protected void onNewIntent(Intent intent) {
        processIntent(intent);

        super.onNewIntent(intent);
    }

    private void processIntent(Intent intent) {
        if (intent != null) {
            String command = intent.getStringExtra("command");
            String name = intent.getStringExtra("name");

            Toast.makeText(this, "command : " + command + ", name : " + name, Toast.LENGTH_LONG).show();
        }
    }
}

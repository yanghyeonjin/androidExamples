package com.yanghyeonjin.androidexamples;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class ThreadHandlerActivity extends AppCompatActivity {

    Button btn_thread_start, btn_thread_stop;
    Thread thread;
    boolean isThread = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thread_handler);

        // 스레드 시작
        btn_thread_start = findViewById(R.id.btn_thread_start);
        btn_thread_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isThread = true;
                thread = new Thread() {
                    public void run() {
                        while (isThread) {
                            try {
                                sleep(5000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }

                            handler.sendEmptyMessage(0);
                        }
                    }
                };
                thread.start();

            }
        });

        // 스레드 종료
        btn_thread_stop = findViewById(R.id.btn_thread_stop);
        btn_thread_stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isThread = false;
            }
        });
    }

    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(@NonNull Message msg) {
            Toast.makeText(getApplicationContext(), "홍드로이드 스레드 강의", Toast.LENGTH_SHORT).show();
        }
    };
}

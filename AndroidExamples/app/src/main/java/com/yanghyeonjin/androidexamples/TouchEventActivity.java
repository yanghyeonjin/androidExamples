package com.yanghyeonjin.androidexamples;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

public class TouchEventActivity extends AppCompatActivity {

    private TextView tvTouchResult;
    private View vTouchEvent, v2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_touch_event);

        /* 아이디 연결 */
        tvTouchResult = findViewById(R.id.tv_touch_result);
        vTouchEvent = findViewById(R.id.v_touch_event);

        /* 뷰를 터치했을 때 이벤트를 전달받을 리스너 등록*/
        vTouchEvent.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                int action = motionEvent.getAction();

                /* 터치한 곳의 좌표확인 */
                float curX = motionEvent.getX();
                float curY = motionEvent.getY();

                if (action == MotionEvent.ACTION_DOWN) {

                    /* 눌렀을 때 */
                    println("손가락 눌림: " + curX + ", " + curY);
                } else if (action == MotionEvent.ACTION_MOVE) {

                    /* 누른 상태로 움직일 때 */
                    println("손가락 움직임: " + curX + ", " + curY);
                } else if (action == MotionEvent.ACTION_UP) {

                    /* 손가락 떼졌을 때*/
                    println("손가락 뗌: " + curX + ", " + curY);
                }
                return true;
            }
        });
    }

    private void println(String str) {
        tvTouchResult.append(str + "\n");
    }
}

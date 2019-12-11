package com.yanghyeonjin.androidexamples;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.GestureDetector;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class TouchEventActivity extends AppCompatActivity {

    private TextView tvTouchResult, tvGestureResult;
    private View vTouchEvent, vGestureEvent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_touch_event);

        /* 아이디 연결 */
        tvTouchResult = findViewById(R.id.tv_touch_result);
        tvGestureResult = findViewById(R.id.tv_gesture_result);
        vTouchEvent = findViewById(R.id.v_touch_event);
        vGestureEvent = findViewById(R.id.v_gesture_event);

        /* GestureDetector 객체 선언 */
        GestureDetector gestureDetector;
        gestureDetector = new GestureDetector(this, new GestureDetector.OnGestureListener() {
            @Override
            public boolean onDown(MotionEvent motionEvent) {
                println2("onDown() 호출됨.");
                return true;
            }

            @Override
            public void onShowPress(MotionEvent motionEvent) {
                println2("onShowPress() 호출됨.");
            }

            @Override
            public boolean onSingleTapUp(MotionEvent motionEvent) {
                println2("onSingleTapUp() 호출됨.");
                return true;
            }

            /* 손가락으로 드래그하는 일반적인 경우. 이동한 거리값이 중요하게 처리됨 */
            @Override
            public boolean onScroll(MotionEvent motionEvent, MotionEvent motionEvent1, float v, float v1) {
                println2("onScroll() 호출됨 : " + v + ", " + v1);
                return true;
            }

            @Override
            public void onLongPress(MotionEvent motionEvent) {
                println2("onLongPress() 호출됨.");
            }

            /* 빠른 속도로 스크롤을 하는 경우. 이동한 속도값이 중요하게 처리됨 */
            @Override
            public boolean onFling(MotionEvent motionEvent, MotionEvent motionEvent1, float v, float v1) {
                println2("onFling() 호출됨.");
                return true;
            }
        });

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

        /* 뷰를 터치했을 때 발생하는 터치 이벤트를 제스처 디텍터로 전달 */
        vGestureEvent.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                gestureDetector.onTouchEvent(motionEvent);
                return true;
            }
        });
    }

    private void println(String str) {
        tvTouchResult.append(str + "\n");
    }

    private void println2(String str) {
        tvGestureResult.append(str + "\n");
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        /* 시스템 [BACK] 버튼이 눌렀을 경우 토스트 메시지 보여주기 */
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            Toast.makeText(this, "시스템 [BACK] 버튼이 눌렸습니다.", Toast.LENGTH_LONG).show();
            super.onKeyDown(keyCode, event);
            return true;
        }

        return false;
    }
}

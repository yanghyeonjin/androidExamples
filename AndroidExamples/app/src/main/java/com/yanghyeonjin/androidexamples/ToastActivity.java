package com.yanghyeonjin.androidexamples;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class ToastActivity extends AppCompatActivity {

    private EditText etToastX, etToastY;
    private Button btnShowToast, btnShowToast2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_toast);

        /* 아이디 연결 */
        etToastX = findViewById(R.id.et_toast_x);
        etToastY = findViewById(R.id.et_toast_y);
        btnShowToast = findViewById(R.id.btn_show_toast);
        btnShowToast2 = findViewById(R.id.btn_show_toast2);


        btnShowToast.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    Toast toastView = Toast.makeText(getApplicationContext(), "위치가 바뀐 토스트 메시지입니다.", Toast.LENGTH_LONG);
                    int xOffset = Integer.parseInt(etToastX.getText().toString());
                    int yOffset = Integer.parseInt(etToastY.getText().toString());

                    toastView.setGravity(Gravity.TOP| Gravity.LEFT, xOffset, yOffset);
                    toastView.show();
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }
            }
        });

        btnShowToast2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LayoutInflater inflater = getLayoutInflater();

                View layout = inflater.inflate(R.layout.toast_border, findViewById(R.id.linear_toast_root));
                TextView tvToast = layout.findViewById(R.id.tv_toast);
                Toast toast = new Toast(getApplicationContext());
                tvToast.setText("모양 바꾼 토스트");
                toast.setGravity(Gravity.CENTER, 0, -100);
                toast.setDuration(Toast.LENGTH_LONG);
                toast.setView(layout);
                toast.show();
            }
        });
    }
}

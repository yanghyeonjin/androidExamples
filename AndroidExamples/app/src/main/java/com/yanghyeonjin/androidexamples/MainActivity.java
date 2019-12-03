package com.yanghyeonjin.androidexamples;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.IntentFilter;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.yanghyeonjin.androidexamples.adapter.ExampleAdapter;
import com.yanghyeonjin.androidexamples.model.Example;
import com.yanghyeonjin.androidexamples.receiver.NetworkReceiver;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private RecyclerView exampleRecyclerView;
    private RecyclerView.Adapter exampleAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private ArrayList<Example> exampleArrayList;
    private TextView goToAddExampleButton;

    private FirebaseDatabase database;
    private DatabaseReference databaseReference;

    private static final String LOG_TAG = "MainActivity";

    private long backBtnTime = 0;

    private NetworkReceiver networkReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        exampleRecyclerView = findViewById(R.id.rv_example); // 아이디 연결
        exampleRecyclerView.setHasFixedSize(true); // 리사이클러뷰 기존성능 강화
        layoutManager = new LinearLayoutManager(this);
        exampleRecyclerView.setLayoutManager(layoutManager);
        exampleArrayList = new ArrayList<>(); // Example 객체를 담을 ArrayList (Adapter 쪽으로 전달)

        database = FirebaseDatabase.getInstance(); // 파이어베이스 데이터베이스 연동
        databaseReference = database.getReference("example"); // DB 테이블 연결

        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // 파이어베이스 데이터베이스의 데이터를 받아오는 부분

                exampleArrayList.clear(); // 기존 배열리스트가 존재하지 않게 초기화
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) { // 반복문으로 데이터 리스트 추출
                    Example example = snapshot.getValue(Example.class); // Example 객체에 데이터를 담는다.
                    exampleArrayList.add(example); // 담은 데이터들을 배열리스트에 넣고 리사이클러뷰로 보낼 준비를 한다.
                }

                exampleAdapter.notifyDataSetChanged(); // 리스트 저장 및 새로고침
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) { // DB를 가져오던 중 에러 발생 시
                Log.e(LOG_TAG, String.valueOf(databaseError.toException())); // 로그로 에러 출력

            }
        });

        exampleAdapter = new ExampleAdapter(exampleArrayList, this);
        exampleRecyclerView.setAdapter(exampleAdapter); // 리사이클러뷰 어댑터 연결

        // 브로드 캐스트 리시버 등록 !
        IntentFilter intentFilter = new IntentFilter();
        networkReceiver = new NetworkReceiver();

        // 네트워크 상태 변화되는 값을 IntentFilter에 추가
        // NetworkReceiver 클래스의 intent.getAction()과 연관있음.
        intentFilter.addAction(WifiManager.NETWORK_STATE_CHANGED_ACTION);
        registerReceiver(networkReceiver, intentFilter);

        goToAddExampleButton = findViewById(R.id.btn_go_to_add_example);
        goToAddExampleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AddExampleActivity.class);
                int arraySize = exampleArrayList.size();
                arraySize++; // 추가되는 예제의 아이디
                intent.putExtra("total", arraySize);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onBackPressed() {
        long curTime = System.currentTimeMillis();
        long gapTime = curTime - backBtnTime;

        if (gapTime >= 0 && gapTime <= 2000) {
            super.onBackPressed();
        } else {
            backBtnTime = curTime;
            Toast.makeText(this, "한번 더 누르면 종료됩니다.", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // 파이어베이스 데이터베이스의 데이터를 받아오는 부분

                exampleArrayList.clear(); // 기존 배열리스트가 존재하지 않게 초기화
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) { // 반복문으로 데이터 리스트 추출
                    Example example = snapshot.getValue(Example.class); // Example 객체에 데이터를 담는다.
                    exampleArrayList.add(example); // 담은 데이터들을 배열리스트에 넣고 리사이클러뷰로 보낼 준비를 한다.
                }
                exampleAdapter.notifyDataSetChanged(); // 리스트 저장 및 새로고침
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) { // DB를 가져오던 중 에러 발생 시
                Log.e(LOG_TAG, String.valueOf(databaseError.toException())); // 로그로 에러 출력

            }
        });


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        // 브로드 캐스트 리시버 해제 !
        // 광역적으로 네트워크 상태를 체크한다면 아래 구문을 만들 필요가 없다.
        // 그러나 어디에서도 해제하지 않으면 앱을 종료해도 계속 체크하기 때문에 휴대폰 과부하가 올 수 있음.
        // 따라서 어떤 한 액티비티 내에서는 만들어줘야한다.
        unregisterReceiver(networkReceiver);
    }
}

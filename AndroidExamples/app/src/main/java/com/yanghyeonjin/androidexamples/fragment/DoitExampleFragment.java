package com.yanghyeonjin.androidexamples.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.yanghyeonjin.androidexamples.AddExampleActivity;
import com.yanghyeonjin.androidexamples.R;
import com.yanghyeonjin.androidexamples.adapter.ExampleAdapter;
import com.yanghyeonjin.androidexamples.model.Example;

import java.util.ArrayList;

public class DoitExampleFragment extends Fragment {
    private View view;

    private RecyclerView exampleRecyclerView;
    private RecyclerView.Adapter exampleAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private ArrayList<Example> exampleArrayList;
    private TextView goToAddExampleButton, examTotalTextView;

    private FirebaseDatabase database;
    private DatabaseReference databaseReference;

    private static final String LOG_TAG = "DoitExamFrag";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_doit, container, false);

        examTotalTextView = view.findViewById(R.id.tv_doit_exam_total);

        exampleRecyclerView = view.findViewById(R.id.rv_doit_example); // 아이디 연결
        exampleRecyclerView.setHasFixedSize(true); // 리사이클러뷰 기존성능 강화
        layoutManager = new LinearLayoutManager(getContext());
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

                    if (example != null && example.getCategory().equals("Do it! 개정 5판")) {
                        exampleArrayList.add(example); // 담은 데이터들을 배열리스트에 넣고 리사이클러뷰로 보낼 준비를 한다.
                    }

                }

                exampleAdapter.notifyDataSetChanged(); // 리스트 저장 및 새로고침
                examTotalTextView.setText(String.valueOf(exampleArrayList.size())); // 전체 예제 수 표시
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) { // DB를 가져오던 중 에러 발생 시
                Log.e(LOG_TAG, String.valueOf(databaseError.toException())); // 로그로 에러 출력

            }
        });

        exampleAdapter = new ExampleAdapter(exampleArrayList, getContext());
        exampleRecyclerView.setAdapter(exampleAdapter); // 리사이클러뷰 어댑터 연결

        goToAddExampleButton = view.findViewById(R.id.btn_go_to_add_doit_example);
        goToAddExampleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), AddExampleActivity.class);
                int arraySize = exampleArrayList.size();
                arraySize++; // 추가되는 예제의 번호

                String exampleID = "doit" + arraySize; // 홍드로이드 예제 아이디 만들기
                String category= "Do it! 개정 5판";

                intent.putExtra("exampleID", exampleID); // 예제 아이디 전달
                intent.putExtra("category", category); // 예제 카테고리 전달
                startActivity(intent);
            }
        });


        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // 파이어베이스 데이터베이스의 데이터를 받아오는 부분

                exampleArrayList.clear(); // 기존 배열리스트가 존재하지 않게 초기화
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) { // 반복문으로 데이터 리스트 추출
                    Example example = snapshot.getValue(Example.class); // Example 객체에 데이터를 담는다.

                    if (example != null && example.getCategory().equals("Do it! 개정 5판")) {
                        exampleArrayList.add(example); // 담은 데이터들을 배열리스트에 넣고 리사이클러뷰로 보낼 준비를 한다.
                    }
                }
                exampleAdapter.notifyDataSetChanged(); // 리스트 저장 및 새로고침
                examTotalTextView.setText(String.valueOf(exampleArrayList.size())); // 전체 예제 수 표시
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) { // DB를 가져오던 중 에러 발생 시
                Log.e(LOG_TAG, String.valueOf(databaseError.toException())); // 로그로 에러 출력

            }
        });
    }
}

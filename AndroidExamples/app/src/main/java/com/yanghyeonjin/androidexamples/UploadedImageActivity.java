package com.yanghyeonjin.androidexamples;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.yanghyeonjin.androidexamples.adapter.ImageAdapter;
import com.yanghyeonjin.androidexamples.model.Image;

import java.util.ArrayList;

public class UploadedImageActivity extends AppCompatActivity {

    /* 파이어베이스 데이터베이스 */
    private DatabaseReference imageTable;

    private RecyclerView rvUploadedImage;
    private RecyclerView.Adapter uploadedImageAdapter;
    private RecyclerView.LayoutManager gridLayoutManager;
    private ArrayList<Image> uploadedImageArrayList;

    private Context uploadedImageContext;

    private static final String LOG_TAG = "UploadedImage";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_uploaded_image_acvitity);

        uploadedImageContext = UploadedImageActivity.this;

        /* 아이디 연결 */
        rvUploadedImage = findViewById(R.id.rv_uploaded_image);


        /* 리사이클러뷰 셋팅 */
        rvUploadedImage.setHasFixedSize(true); // 리사이클러뷰 기존성능 강화
        gridLayoutManager = new GridLayoutManager(uploadedImageContext, 4);
        rvUploadedImage.setLayoutManager(gridLayoutManager);


        /* Image 객체를 담을 ArrayList (Adapter 쪽으로 전달) */
        uploadedImageArrayList = new ArrayList<>();

        /* 파이어베이스 image 테이블 참조 */
        imageTable = FirebaseDatabase.getInstance().getReference().child("image");
        imageTable.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Image existImage = snapshot.getValue(Image.class);

                    if (existImage != null) {
                        uploadedImageArrayList.add(existImage);
                    } else {
                        Log.e(LOG_TAG, "existImage is null");
                    }
                }

                /* 리스트 저장 및 새로고침 */
                uploadedImageAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        /* 리사이클러뷰 어댑터 연결 */
        uploadedImageAdapter = new ImageAdapter(uploadedImageArrayList, uploadedImageContext);
        rvUploadedImage.setAdapter(uploadedImageAdapter);
    }
}

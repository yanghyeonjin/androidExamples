package com.yanghyeonjin.androidexamples;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class FirebaseStorageActivity extends AppCompatActivity {

    private static final String LOG_TAG = "FirebaseStorage";

    private Button btnChooseImage, btnUploadImage, btnViewUploadedImage;
    private ImageView ivPreviewImage;

    private Uri filePath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_firebase_storage);


        /* 아이디 연결 */
        btnChooseImage = findViewById(R.id.btn_choose_image);
        btnUploadImage = findViewById(R.id.btn_upload_image);
        ivPreviewImage = findViewById(R.id.iv_preview_image);
        btnViewUploadedImage = findViewById(R.id.btn_view_uploaded_image);




        /* 버튼 클릭 이벤트 */
        btnChooseImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /* 이미지를 선택 */
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "이미지를 선택하세요."), 0);
            }
        });

        btnUploadImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /* 업로드 */
                uploadFile();
            }
        });

        btnViewUploadedImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /* 업로드 된 이미지 모두 보러가기 */
                startActivity(new Intent(FirebaseStorageActivity.this, UploadedImageActivity.class));
            }
        });

    }

    /* 결과 처리 */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        /* requestCode가 0이고 OK를 선택했고 data에 뭔가가 들어 있다면 */
        if (requestCode == 0 && resultCode == RESULT_OK) {
            filePath = data.getData();
            Log.d(LOG_TAG, "uri: " + filePath);

            /* Glide 라이브러리 사용하여 선택한 사진 프리뷰 출력 */
            Glide.with(getApplicationContext()).load(filePath).into(ivPreviewImage);
        }
    }

    /* 파일 업로드 수행 */
    private void uploadFile() {
        /* 업로드한 파일이 있으면 수행 */
        if (filePath != null) {
            /* 업로드 진행 progressbar 보이기 */
            ProgressBar progressBar = findViewById(R.id.pb_progressbar);


            /* storage */
            FirebaseStorage storage = FirebaseStorage.getInstance();

            /* Unique한 파일명 만들기 */
            @SuppressLint("SimpleDateFormat")
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd_HHmmss");
            Date now = new Date();
            String filename = dateFormat.format(now) + ".jpg";

            /* storage 주소와 폴더 파일명을 지정해준다 */
            StorageReference storageRef = storage.getReferenceFromUrl(getString(R.string.firebase_storage_location)).child("images/" + filename);

            /* 업로드!! */
            storageRef.putFile(filePath)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            /* 업로드 완료 후 프리뷰 삭제, progressbar 초기화 */
                            Toast.makeText(getApplicationContext(), "업로드 완료!", Toast.LENGTH_SHORT).show();
                            ivPreviewImage.setImageResource(android.R.color.transparent);
                            progressBar.setProgress(0);
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(getApplicationContext(), "업로드 실패..", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(@NonNull UploadTask.TaskSnapshot taskSnapshot) {
                            double progress = (100 * taskSnapshot.getBytesTransferred()) / (taskSnapshot.getTotalByteCount());

                            /* progressbar 진행률 넣어주기 */
                            progressBar.setProgress((int) progress);
                        }
                    });
        } else {
            Toast.makeText(getApplicationContext(), "파일을 먼저 선택하세요.", Toast.LENGTH_SHORT).show();
        }
    }
}
